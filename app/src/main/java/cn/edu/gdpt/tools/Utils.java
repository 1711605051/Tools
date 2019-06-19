package cn.edu.gdpt.tools;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Utils {
    static OkHttpClient okHttpClient = new OkHttpClient();

    public static String get(String s) throws IOException {

        return okHttpClient.newCall(new Request.Builder().get().url(s).build()).execute().body().string();
    }
}

