package com.ran.qxlinechart.mockit;

import android.os.Build;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.ran.qxlinechart.BuildConfig;
import com.ran.qxlinechart.R;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import static org.junit.Assert.*;

/**
 * Created by houqixin on 2017/5/11.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class QxMockActvityTest {
    private QxMockActvity mActvity;
    private Button mbtn;
    private TextView mTv;


    @Test
    public void testInit() {
        Assert.assertEquals("actvity packageName is error", "com.ran.qxlinechart", mActvity.getPackageName());
        Assert.assertNotNull("button is null", mbtn);
        Assert.assertNotNull("testView is null", mTv);
        Assert.assertEquals("textView text is error", "text", mTv.getText().toString());
    }

    @Test
    public void testClickButton() {
        mbtn.performClick();
        Assert.assertEquals("textView text is error", "我被修改了", mTv.getText().toString());
    }
    @Before
    public void setUp() {

        //加上这么一句话无论是测试代码中的log或者被测试中的log都会在控制台输出的
        ShadowLog.stream=System.out;

        Log.d("Test_log","开启了log日志");


        //相当于启动了actvity 经过了 oncreate，onStart和onResume这三个生命周期
        //一般只是在启动activiy的时候调用
        mActvity = Robolectric.setupActivity(QxMockActvity.class);
        mbtn = (Button) mActvity.findViewById(R.id.btn_changge);
        mTv = (TextView) mActvity.findViewById(R.id.textView);
    }

    @After
    public void tearDown() {
        mActvity = null;
        mbtn = null;
        mTv = null;
    }
}