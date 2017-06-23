package com.ran.qxlinechart.fingerManager;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ran.qxlinechart.QxApplication;
import com.ran.qxlinechart.R;

public class FingerPrintActivity extends AppCompatActivity {

    private Context mContext;
    private FingerManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print);
        mContext = this;
        mManager=FingerManager.newInstance(QxApplication.getContext());
    }
    public void onFingerprintClick(View v){
        FingerManager.FingerManagerUserCode result = mManager.isFinger();

        if(result==FingerManager.FingerManagerUserCode.FINGER_MANAGER_CAN_USE){
            mManager.startListening(null, new FingerManager.FingerPrintLinstener() {
                AlertDialog dialog;
                @Override
                public void onFailed() {
                    showToast("解锁失败");
                }

                @Override
                public void onError(int errorCode, CharSequence errString) {
                    showToast(errString.toString());
                    if (dialog != null  &&dialog.isShowing()){
                        dialog.dismiss();
                        handler.removeMessages(0);
                    }
                }

                @Override
                public void onHelp(int helpCode, CharSequence helpString) {
                    showToast(helpString.toString());
                }

                @Override
                public void onSuccess() {
                    showToast("解锁成功");
                    if (dialog != null  &&dialog.isShowing()){
                        dialog.dismiss();
                        handler.removeMessages(0);
                    }
                }

                @Override
                public void onStart() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    View view = LayoutInflater.from(mContext).inflate(R.layout.layout_fingerprint,null);
                    initView(view);
                    builder.setView(view);
                    builder.setCancelable(false);
                    Log.e("dialog:","builder");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            handler.removeMessages(0);
                            mManager.cancel();
                        }
                    });
                    dialog = builder.create();
                    dialog.show();
                }
            });

        }else{
            showToast(result.getMessage());
        }

    }
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                int i = postion % 5;
                if (i == 0){
                    tv[4].setBackground(null);
                    tv[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
                else{
                    tv[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    tv[i-1].setBackground(null);
                }
                postion++;
                handler.sendEmptyMessageDelayed(0,100);
            }
        }
    };
    TextView[] tv = new TextView[5];
    private int postion = 0;
    private void initView(View view) {
        postion = 0;
        tv[0] = (TextView) view.findViewById(R.id.tv_1);
        tv[1] = (TextView) view.findViewById(R.id.tv_2);
        tv[2] = (TextView) view.findViewById(R.id.tv_3);
        tv[3] = (TextView) view.findViewById(R.id.tv_4);
        tv[4] = (TextView) view.findViewById(R.id.tv_5);
        handler.sendEmptyMessageDelayed(0,100);
    }


    public void showToast(String name ){
        Toast.makeText(FingerPrintActivity.this,name,Toast.LENGTH_SHORT).show();
    }
}
