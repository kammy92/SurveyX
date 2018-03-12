package com.resultier.surveyx.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.resultier.surveyx.service.AlarmService;

public class AlarmReceiver extends BroadcastReceiver {
    
    NotificationManager notificationManager;
    
    @Override
    public void onReceive (Context context, Intent intent) {
        Intent service1 = new Intent (context, AlarmService.class);
        context.startService (service1);
    }
}