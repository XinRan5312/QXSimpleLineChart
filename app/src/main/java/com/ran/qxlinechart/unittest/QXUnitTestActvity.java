package com.ran.qxlinechart.unittest;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.ran.qxlinechart.MSBaseActivity;
import com.ran.qxlinechart.MSCurrentInvestActivity;
import com.ran.qxlinechart.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by houqixin on 2017/5/10.
 */
public class QXUnitTestActvity extends MSBaseActivity {
    @BindView(R.id.btn_changge) Button mbtn;
    @BindView(R.id.textView) TextView mTv;
    public String name;
    private QxBroacastReceiver receiver;
    private Handler mHandler;
    public int mCount=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_test);
        ButterKnife.bind(this);
        name="create";
        registerBroacast();
        mHandler=new Handler(getMainLooper());
    }

    private void registerBroacast() {
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.wr.qx.ok");
       receiver= new QxBroacastReceiver();
        registerReceiver(receiver,filter);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.btn_changge) void changeText(){
        mTv.setText("我被修改了");
        if(name.equals("jump")){
            startActivity(new Intent(this,MSCurrentInvestActivity.class));
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCount=6;
            }
        },500);

        Toast.makeText(this,"hello",Toast.LENGTH_LONG).show();

        Dialog dialog=new Dialog(this);
        TextView tv=new TextView(this);
        tv.setText("我是一个dialog");
        dialog.setContentView(tv);
        dialog.show();
        dialog.setCancelable(true);

        final AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setIcon(getDrawable(R.mipmap.ic_launcher))
                .setMessage("hello")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        name="start";
    }

    @Override
    protected void onResume() {
        super.onResume();
        name="jump";
    }

    @Override
    protected void onPause() {
        super.onPause();
        name="pause";
    }

    @Override
    protected void onStop() {
        super.onStop();
        name="stop";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        name="destroy";
        unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        name="onBackPressed";
    }
}
