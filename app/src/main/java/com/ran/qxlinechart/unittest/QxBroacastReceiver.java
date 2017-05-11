package com.ran.qxlinechart.unittest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ran.qxlinechart.MSCurrentInvestActivity;

/**
 * Created by houqixin on 2017/5/10.
 */
public class QxBroacastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent!=null&&intent.getAction().equals("com.wr.qx.ok")){
            context.startActivity(new Intent(context, MSCurrentInvestActivity.class));
        }
    }
}
