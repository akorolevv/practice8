package com.example.practice8;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThirdActivity extends AppCompatActivity {
    private ImageView imageView;
    private RandomDogApi randomDogApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);

        imageView = findViewById(R.id.imageView);
        randomDogApi = ApiClient.getClient().create(RandomDogApi.class);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> loadRandomDog());
    }

    private void loadRandomDog() {
        randomDogApi.getRandomDog().enqueue(new Callback<RandomDogResponse>() {
            @Override
            public void onResponse(Call<RandomDogResponse> call, Response<RandomDogResponse> response) {
                if (response.isSuccessful()) {
                    String imageUrl = response.body().getUrl();
                    Glide.with(ThirdActivity.this)
                            .load(imageUrl)
                            .into(imageView);
                } else {
                    Toast.makeText(ThirdActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RandomDogResponse> call, Throwable t) {
                Toast.makeText(ThirdActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}