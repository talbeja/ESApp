package com.talb.esapp.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://reqres.in/api/")  // Base URL of the API
                    .addConverterFactory(GsonConverterFactory.create())  // Converts JSON to Java objects
                    .build();
        }
        return retrofit;
    }
}
