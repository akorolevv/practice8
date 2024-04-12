package com.example.practice8;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class SecondActivity extends AppCompatActivity {

    private TextView textViewTask1, textViewTask2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        textViewTask1 = findViewById(R.id.task1_status_text_view);
        textViewTask2 = findViewById(R.id.task2_status_text_view);

        findViewById(R.id.start_tasks_button).setOnClickListener(v -> startTasks());
    }

    private void startTasks()
    {
        // Создаем два отдельных WorkRequest для двух задач с различной длительностью
        OneTimeWorkRequest task1 = new OneTimeWorkRequest.Builder(Task1Worker.class)
                .build();
        OneTimeWorkRequest task2 = new OneTimeWorkRequest.Builder(Task2Worker.class)
                .build();

        // Получаем ссылки на LiveData для каждого WorkRequest
        LiveData<WorkInfo> task1LiveData = WorkManager.getInstance(this).getWorkInfoByIdLiveData(task1.getId());
        LiveData<WorkInfo> task2LiveData = WorkManager.getInstance(this).getWorkInfoByIdLiveData(task2.getId());

        // Подписываемся на изменения состояния задач
        task1LiveData.observe(this, this::updateTask1State);
        task2LiveData.observe(this, this::updateTask2State);

        //  this: Это ссылка на текущий экземпляр. Это необходимо, чтобы связать подписку на LiveData с текущей активностью.
        //  b. this::updateTask1State: Это ссылка на метод updateTask1State().
        //  метод должен быть вызван, когда значение task1LiveData изменится.

        // Запускаем оба WorkRequest одновременно
        WorkManager.getInstance(this).enqueue(Arrays.asList(task1, task2));
    }

    private void updateTask1State(WorkInfo workInfo) {
        textViewTask1.setText("Task 1 state: " + workInfo.getState().toString());
    }

    private void updateTask2State(WorkInfo workInfo) {
        textViewTask2.setText("Task 2 state: " + workInfo.getState().toString());
    }

    public static class Task1Worker extends Worker {
        public Task1Worker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
        }

        @NonNull
        @Override
        public Result doWork() {
            // Выполнение задачи 1 (5 секунд)
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            } catch (InterruptedException e) {
                return Result.failure();
            }
            return Result.success();
        }
    }

    public static class Task2Worker extends Worker {
        public Task2Worker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
        }

        @NonNull
        @Override
        public Result doWork() {
            // Выполнение задачи 2 (7 секунд)
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(7));
            } catch (InterruptedException e) {
                return Result.failure();
            }
            return Result.success();
        }
    }

}