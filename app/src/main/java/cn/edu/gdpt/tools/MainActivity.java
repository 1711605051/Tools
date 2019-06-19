package cn.edu.gdpt.tools;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    String url = "http://apicloud.mob.com/appstore/health/search?key=25c1f67d8be28&name=";
    private Toolbar toolbar;
    private ListView qqq;
    private SwipeRefreshLayout sssss;
    EditText editText;

    public static final String[] health_data_array = new String[]{"学","人","香","恋爱","近视","瓜","苹果","西瓜","芒果","雪梨","香蕉","桃子","樱桃","菠萝","番茄","白菜","鸡","女","男","婴儿","猪","牛","乳"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData("樱桃");
        sssss.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int math = (int) (Math.random() * health_data_array.length);
                String s = health_data_array[math];
                editText.setText(s);
                initData(s);
            }
        });
    }

    private void initData(final String xx) {
        sssss.setRefreshing(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = Utils.get(url + xx);
                    final HealthBean healthBean = new Gson().fromJson(s, HealthBean.class);
                    final List<HealthBean.ResultBean.ListBean> list = healthBean.getResult().getList();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            qqq.setAdapter(new BaseAdapter() {

                                class ViewHolder {
                                    public View rootView;
                                    public TextView q1;
                                    public TextView q2;

                                    public ViewHolder(View rootView) {
                                        this.rootView = rootView;
                                        this.q1 = (TextView) rootView.findViewById(R.id.q1);
                                        this.q2 = (TextView) rootView.findViewById(R.id.q2);
                                    }
                                }

                                @Override
                                public int getCount() {
                                    return list.size();
                                }

                                @Override
                                public Object getItem(int position) {
                                    return position;
                                }

                                @Override
                                public long getItemId(int position) {
                                    return position;
                                }

                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item, parent, false);
                                    ViewHolder holder = new ViewHolder(convertView);
                                    holder.q1.setText(list.get(position).getTitle() + "");
                                    holder.q2.setText(list.get(position).getContent() + "");
                                    return convertView;
                                }
                            });
                            sssss.setRefreshing(false);
                        }
                    });

                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "出错了", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        qqq = (ListView) findViewById(R.id.qqq);
        sssss = (SwipeRefreshLayout) findViewById(R.id.sssss);
        editText = findViewById(R.id.ed);
    }
}
