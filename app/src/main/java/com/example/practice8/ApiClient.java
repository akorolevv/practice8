package com.example.practice8;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() // Создание ретрофит-клиента, используя url api и конвертер gson для преобразования json ответа в Java-объекты
    {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://random.dog/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
