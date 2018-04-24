package com.sunbd.mobileerpbd.Retrofit;


import com.sunbd.mobileerpbd.Helper.SettingsHandler;


import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface APIService {

    @Headers({
            "Accept: application/json",
    })

    @POST("/api/register")
    @FormUrlEncoded
    Observable<JSONObject> saveRegistration(@Field("name") String name,
                                            @Field("email") String email,
                                            @Field("username") String username,
                                            @Field("password") String password,
                                            @Field("c_password") String confirm_password);

    @POST("/api/login")
    @FormUrlEncoded
    Observable<Object> saveLogin(@Field("username") String username,
                                 @Field("password") String password);

    //This method is used for "GET"
    @GET("/api/products")
    Observable<Object> getList();
}
