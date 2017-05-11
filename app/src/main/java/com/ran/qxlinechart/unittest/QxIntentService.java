package com.ran.qxlinechart.unittest;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by houqixin on 2017/5/11.
 * public abstract class IntentService extends Service这是继承关系
 */
public class QxIntentService extends IntentService {
    public QxIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            SharedPreferences preferences=getSharedPreferences("jia",MODE_PRIVATE);
            if(action.equals("wr.com")){
                preferences.edit().putString("wr","handle").commit();
            }else{
                preferences.edit().putString("wr","handleNot").commit();
            }
        }
    }
}
