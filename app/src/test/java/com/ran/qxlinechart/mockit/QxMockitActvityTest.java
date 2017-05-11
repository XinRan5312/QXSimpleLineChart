package com.ran.qxlinechart.mockit;

import android.os.Build;
import android.util.Log;
import android.widget.Button;

import com.ran.qxlinechart.BuildConfig;
import com.ran.qxlinechart.R;
import com.ran.qxlinechart.unittest.QxAppNetModle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doAnswer;

import static org.junit.Assert.*;

/**
 * Created by houqixin on 2017/5/11.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,sdk = Build.VERSION_CODES.LOLLIPOP)
public class QxMockitActvityTest {

    private QxMockitActvity mMockitActvity;
    private Button mbtn;
    @Mock
    private QxNetMananger mMananger;
    @Mock
    private QxVertifyUrl mVertifyUrl;

    @Before
    public void setUp() throws Exception {

        ShadowLog.stream=System.out;//启动测试的时候所有的Log日志都输出
        MockitoAnnotations.initMocks(this);//初始化Mockit

        //QxMockitActvity中的QxNetMananger和QxVertifyUrl都是从QxAppNetModle这里得到的，所以我们从这里开始Mock才能保证
        //QxMockitActvity中的QxNetMananger和QxVertifyUrl也是mock的
        QxAppNetModle netModle=Mockito.mock(QxAppNetModle.class);

        when(netModle.provideNetManager()).thenReturn(mMananger);
        when(netModle.provideVertifyUrl()).thenReturn(mVertifyUrl);

        mMockitActvity= Robolectric.setupActivity(QxMockitActvity.class);//启动Activity
        mbtn= (Button) mMockitActvity.findViewById(R.id.btn_changge);

    }

    @After
    public void tearDown() throws Exception {
        mMockitActvity.finish();
        mMockitActvity=null;
    }

    @Test
    public void testload(){
        //第一次触发返回true，第二次触发返回false，第三次触发返回true
        when(mVertifyUrl.vertifyUrl(anyString())).thenReturn(true).thenReturn(false).thenReturn(true);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();//得到截取方法所有的参数
                //QxNetCallback是方法的第二个参数
                QxNetMananger.QxNetCallback callback= (QxNetMananger.QxNetCallback) arguments[1];
                //模拟成功了
                callback.onSuccess("qx");
                Log.e("Qx","qx");
                return "wr";
            }
        }).when(mMananger).load(anyString(), Mockito.any(QxNetMananger.QxNetCallback.class));



        mbtn.performClick();
        mbtn.performClick();
        mbtn.performClick();
        //验证是否调用了俩次
        verify(mMananger,times(2)).load(anyString(),Mockito.any(QxNetMananger.QxNetCallback.class));

    }
}