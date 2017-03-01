package com.example.rodri.vocabbuilder.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.rodri.vocabbuilder.service.ReviewWordsService;

/**
 * Created by rodri on 3/1/2017.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {

    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ACTION)) {
            System.out.println("BootBroadcastReceiver started!");

            Intent serviceIntent = new Intent(context, ReviewWordsService.class);
            context.startService(serviceIntent);
        }

    }
}
