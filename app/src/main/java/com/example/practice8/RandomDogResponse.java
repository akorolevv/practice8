package com.example.practice8;

// Этот POJO-класс (Plain Old Java Object) используется Retrofit для автоматического десериализации JSON-ответа в Java-объект.
public class RandomDogResponse {
    private String url;

    public String getUrl() {
        return url;
    }
}
