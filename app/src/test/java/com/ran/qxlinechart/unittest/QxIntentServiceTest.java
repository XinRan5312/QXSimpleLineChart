package com.ran.qxlinechart.unittest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ran.qxlinechart.BuildConfig;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.IntentServiceController;
import org.robolectric.android.controller.ServiceController;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboSharedPreferences;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowLog;

import static org.junit.Assert.*;

/**
 * Created by houqixin on 2017/5/11.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class QxIntentServiceTest {

    @Before
    public void setUp() throws Exception {
        //加上这么一句话无论是测试代码中的log或者被测试中的log都会在控制台输出的
        ShadowLog.stream=System.out;

        Log.d("Test_log","开启了log日志");

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testService(){

        IntentServiceController<QxIntentService> serviceController= Robolectric.buildIntentService(QxIntentService.class);

        QxIntentService service=serviceController.get();

        service.onHandleIntent(new Intent("wr.com"));
        //RoboSharedPreferences其实就相当于SharedPreferences的影子只是它没有用Shadow，可能是有特殊的功能吧

        RoboSharedPreferences roboSharedPreferences= (RoboSharedPreferences) RuntimeEnvironment.application.getSharedPreferences("jia", Context.MODE_PRIVATE);

        Assert.assertEquals("handle",roboSharedPreferences.getString("wr","not"));
    }
}