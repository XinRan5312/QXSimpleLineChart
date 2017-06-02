package com.ran.qxlinechart.saoma;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ran.qxlinechart.MSBaseActivity;
import com.ran.qxlinechart.R;
import com.znq.zbarcode.CaptureActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QXSaoMaActivity extends MSBaseActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qxsao_ma);
        ButterKnife.bind(this);

       findViewById(R.id.btn_sao_ma).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(QXSaoMaActivity.this, CaptureActivity.class);

                startActivityForResult(intent,100);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==100&&data!=null){

            String msg=data.getStringExtra(CaptureActivity.EXTRA_STRING);

            Log.e("CaptureActivity_MSG:",msg+"width:"+data.getIntExtra("width",0));


        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
