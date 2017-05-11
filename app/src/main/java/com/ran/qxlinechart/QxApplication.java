package com.ran.qxlinechart;

import android.app.Application;

import com.ran.qxlinechart.unittest.AppComponent;
import com.ran.qxlinechart.unittest.DaggerAppComponent;
import com.ran.qxlinechart.unittest.QxCommonModleFactory;



/**
 * Created by houqixin on 2017/5/11.
 */
public class QxApplication extends Application {
    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppModleComponent();
    }

    private void initAppModleComponent() {
        //我们创建AppComponent只是用到了QxCommonModleFactory但是从测试的结果来看，QxImageModleFactory中的provide他也会找，
        //也就是说他会找所有他负责的ModleFactory的所有provide直到找到为止

        sAppComponent= DaggerAppComponent.builder().qxCommonModleFactory(new QxCommonModleFactory(this)).build();

    }

    public static AppComponent getAppComponent(){

        return sAppComponent;
    }
}
