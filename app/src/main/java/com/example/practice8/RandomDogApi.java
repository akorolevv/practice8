package com.example.practice8;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RandomDogApi {
    @GET("woof.json")
    Call<RandomDogResponse> getRandomDog();
}