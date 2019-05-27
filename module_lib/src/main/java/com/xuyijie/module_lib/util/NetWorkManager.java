package com.xuyijie.module_lib.util;

import android.util.Log;

import com.xuyijie.module_lib.api.Api;
import com.xuyijie.module_lib.entity.UserStateBean;
import com.xuyijie.module_lib.entity.UserStateListBean;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class NetWorkManager {
    public static String AppKey="336de4d8c4f50b28ed4cfbfa";
    public static String masterSecret = "61ecaa510cafa8e0047bbb72";
    public static String base64_auth_string = Base64Utils.getBase64(AppKey +":"+masterSecret);

    public static OkHttpClient.Builder httpClient;


    /*获取请求头*/
    public static void headers() {
        httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {

            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .addHeader("Authorization", "Basic " + base64_auth_string)
                        .build();
                Log.d("onrequest", "request:" + request.toString());
                Log.d("onrequestHeader", "request headers:" + request.headers().toString());
                return chain.proceed(request);
            }
        });
    }


    /*判断好友在线状态*/
    public static void isFriendState(String userName, Callback<UserStateBean> callback){
        headers();
        Log.e("base64_auth_string", base64_auth_string);
        OkHttpClient client=httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BaseUrl.JPUSH_ROOT)
                .client(client)
                .build();
        Api aPi = retrofit.create(Api.class);
        Call<UserStateBean> call = aPi.isFriendState(userName);
        call.enqueue(callback);

    }

    /*批量查询好友在线状态*/
    public static void isFriendStateList(String[] list, Callback<UserStateListBean> callback){
        headers();
        OkHttpClient client=httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BaseUrl.JPUSH_ROOT)
                .client(client)
                .build();
        Api aPi = retrofit.create(Api.class);
        Call<UserStateListBean> call = aPi.isFriendsStateList(
                list);
        call.enqueue(callback);
    }

}