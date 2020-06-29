package com.example.musicandroid.util;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HttpUtil {

    public static String baseUrl = "http://localhost:8080";
    public static int userId;
    public static int postUserId = 1;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        //OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static void postRequest(String url, Map<String, String> map, okhttp3.Callback callback){
        if (url == null || callback == null){
            return;
        }

        //创建okHttpClient对象
        OkHttpClient client = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        Set<String> keySet=map.keySet();
        for (String i:keySet) {
            //从集合中一一取到对应的key和value
            String str = map.get(i);
            builder.add(i, str);
        }

        RequestBody requestBody = builder.build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        client.newCall(request).enqueue(callback);

    }

    public static Response post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

}
