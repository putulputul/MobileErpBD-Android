package com.sunbd.mobileerpbd.Retrofit;



import com.sunbd.mobileerpbd.Helper.SettingsHandler;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MyRetrofitInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();

        Request newRequest;

        String token = SettingsHandler.getInstance().getAccess_token();

        if(token==null)
        {
            newRequest  = originalRequest.newBuilder()
                    .header("Accept", "application/json")
                    .build();

        }
        else
        {
             newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + token)
                    .build();
        }



        return chain.proceed(newRequest);
    }
}
