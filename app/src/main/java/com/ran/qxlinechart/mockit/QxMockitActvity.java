package com.ran.qxlinechart.mockit;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.ran.qxlinechart.MSBaseActivity;
import com.ran.qxlinechart.QxApplication;
import com.ran.qxlinechart.R;
import com.ran.qxlinechart.unittest.QxBroacastReceiver;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by houqixin on 2017/5/11.
 */
public class QxMockitActvity extends MSBaseActivity {
    @BindView(R.id.btn_changge)
    Button mbtn;
    @BindView(R.id.textView)
    TextView mTv;
    public String name;
    private QxBroacastReceiver receiver;
    private Handler mHandler;
    public int mCount = 0;

    @Inject
    private QxNetMananger mNetMananger;

    @Inject
    private QxVertifyUrl mVertifyUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_test);
        ButterKnife.bind(this);
        name = "create";
        mHandler = new Handler(getMainLooper());

        QxApplication.getAppComponent().inject(this);

    }

    @OnClick(R.id.btn_changge)
    public void onclickBtn() {
        mTv.setText("hello");
        if (mVertifyUrl.vertifyUrl("")) {

            mNetMananger.load(mTv.getText().toString(), new QxNetMananger.QxNetCallback() {
                @Override
                public void onSuccess(String msg) {
                    Log.e("QxMockitActvity:", msg);
                }

                @Override
                public void onFail(String msg) {
                    Log.e("QxMockitActvity:", msg);
                }
            });
        }
    }
}
