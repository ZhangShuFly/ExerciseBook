package com.ilyzs.exercisebook.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class TestService extends Service {

    private static final String TAG = "TestService";

    public TestService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,TAG+"+start+"+new Date().toString(),Toast.LENGTH_SHORT).show();

        Log.w(TAG, "onStartCommand: "+new Date().toString());

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent i = new Intent(this, TestService.class);
        PendingIntent pi = PendingIntent.getService(this,0,i,0);

        long triggerAtTime = SystemClock.elapsedRealtime() + 30 * 1000;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            manager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        }else{
            manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "onDestroy: ");
    }
}
