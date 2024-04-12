package com.example.practice8;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView taskStatusTextView;
    private Button startTasksButton;
    private Button goToSecondActivityButton;
    private Runnable taskRunnable;
    private Thread taskThread;

    private boolean isTaskRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskStatusTextView = findViewById(R.id.task_status_text_view);

        startTasksButton = findViewById(R.id.start_tasks_button);
        startTasksButton.setOnClickListener(this);
        goToSecondActivityButton = findViewById(R.id.go_to_second_activity);

        goToSecondActivityButton.setOnClickListener(this);
        // Создаем экземпляр Runnable, который будет выполнять последовательность задач
        taskRunnable = new TaskRunnable();

        // Создаем поток, но не запускаем его пока
        taskThread = new Thread(taskRunnable);
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.start_tasks_button)
        {
            if (!isTaskRunning)
            {
                // Запускаем последовательность задач
                startTasks();
            }
            else {

                // Задачи уже выполняются, выводим сообщение
                Toast.makeText(this, "Задачи уже выполняются", Toast.LENGTH_SHORT).show();
            }
        }
        else if (v.getId() == R.id.go_to_second_activity)
        {
            // Переход ко второй активности
            startActivity(new Intent(this, SecondActivity.class));
        }

    }


    private void startTasks() {
        isTaskRunning = true;
        // Запускаем поток, который будет выполнять задачи
        taskThread.start();
    }

    private class TaskRunnable implements Runnable {
        @Override
        public void run() {
            // Выполняем первую задачу
            task1();

            // Выполняем вторую задачу
            task2();

            // Выполняем третью задачу
            task3();
        }
    }

    private void task1() {
        // Имитация выполнения первой задачи
        updateTaskStatus("Задача 1 выполнена");
        Log.d("MainActivity", "Задача 1 выполнена");

        // Имитируем паузу между задачами
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void task2() {
        // Имитация выполнения второй задачи
        updateTaskStatus("Задача 2 выполнена");
        Log.d("MainActivity", "Задача 2 выполнена");

        // Имитируем паузу между задачами
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void task3() {
        // Имитация выполнения третьей задачи
        updateTaskStatus("Задача 3 выполнена");
        Log.d("MainActivity", "Задача 3 выполнена");
    }

    private void updateTaskStatus(String status) {
        // Обновляем текст на экране
        runOnUiThread(() -> taskStatusTextView.setText(status));
    }
}