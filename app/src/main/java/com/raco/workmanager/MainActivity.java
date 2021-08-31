package com.raco.workmanager;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

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

        WorkManager.getInstance(this).enqueue(downloadRequest);

        PeriodicWorkRequest periodicWorkRequest =
                new PeriodicWorkRequest.Builder(SampleWorker.class,7, TimeUnit.DAYS)
                .setInputData(data)
                .setConstraints(constraints)
                .addTag("periodic")
//                .setInitialDelay(10, TimeUnit.HOURS)
                .build();
//        WorkManager.getInstance(this).enqueue(periodicWorkRequest);

//        WorkManager.getInstance(this).getWorkInfosByTagLiveData("download").observe(this,
//                new Observer<List<WorkInfo>>() {
//                    public void onChanged(List<WorkInfo> workInfos){
//                        for (WorkInfo w: workInfos){
//                            Log.d(TAG, "onChanged: Work status: " + w.getState());
//                        }
//                    }
//
//                });

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(downloadRequest.getId()).observe(this,
                new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        Log.d(TAG, "onChanged: Work status: " + workInfo.getState());
                    }
                });
    }
}