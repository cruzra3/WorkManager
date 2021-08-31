package com.raco.workmanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data data = new Data.Builder()
                .putInt("number", 10)
                .build();

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
//                .setRequiresCharging(true)
                .build();

        OneTimeWorkRequest downloadRequest = new OneTimeWorkRequest.Builder(SampleWorker.class)
                .setInputData(data)
//                .setConstraints(constraints)
//                .setInitialDelay(5, TimeUnit.HOURS)
                .addTag("download")
                .build();

//        WorkManager.getInstance(this).enqueue(downloadRequest);

        PeriodicWorkRequest periodicWorkRequest =
                new PeriodicWorkRequest.Builder(SampleWorker.class,7, TimeUnit.DAYS)
                .setInputData(data)
                .setConstraints(constraints)
                .addTag("periodic")
//                .setInitialDelay(10, TimeUnit.HOURS)
                .build();
        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
    }
}