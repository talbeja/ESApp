package com.talb.esapp.data.network;

import com.talb.esapp.data.network.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("users")
    Call<UserResponse> getUsers(@Query("page") int page, @Query("per_page") int perPage);
}
