package com.sunbd.mobileerpbd.Helper;

import com.sunbd.mobileerpbd.Retrofit.APIService;
import com.sunbd.mobileerpbd.Retrofit.RetrofitClient;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://192.168.10.135:8000";

    public static APIService getAPIService() {



        return RetrofitClient
                .getClient(BASE_URL)
                .create(APIService.class);

    }
}
