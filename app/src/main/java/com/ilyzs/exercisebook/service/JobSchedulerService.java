package com.ilyzs.exercisebook.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class JobSchedulerService extends JobService {

    private static final String TAG = "JobSchedulerService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.w(TAG, "onStartJob: "+new Date());
        Toast.makeText(this,TAG+"+start+"+new Date().toString(),Toast.LENGTH_SHORT).show();

        JobScheduler jobScheduler  = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        JobInfo.Builder builder = new JobInfo.Builder(1,new ComponentName(getPackageName(), JobSchedulerService.class.getName()));
        builder.setOverrideDeadline(10000).setMinimumLatency(5000);

        if(jobScheduler.schedule(builder.build()) == JobScheduler.RESULT_FAILURE ) {
            Log.e(TAG, "onCreate: jobScheduler is error");
        }

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.w(TAG, "onStopJob: "+new Date());
        Toast.makeText(this,TAG+"+stop+"+new Date().toString(),Toast.LENGTH_SHORT).show();
        return false;
    }
}
