package com.sunbd.mobileerpbd.Retrofit;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient implements Interceptor {

    private static Retrofit retrofit = null;



    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(new MyRetrofitInterceptor())
                           .connectTimeout(10, TimeUnit.SECONDS)
                           .readTimeout(10, TimeUnit.SECONDS).build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
