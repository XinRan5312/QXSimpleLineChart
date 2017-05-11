package com.ran.qxlinechart.mockit;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.ran.qxlinechart.MSBaseActivity;
import com.ran.qxlinechart.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by houqixin on 2017/5/11.
 */
public class QxMockActvity extends MSBaseActivity {
    @BindView(R.id.btn_changge)
    Button mbtn;
    @BindView(R.id.textView)
    TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_test);
        ButterKnife.bind(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.btn_changge)
    void changeText() {
        mTv.setText("我被修改了");
    }
}
