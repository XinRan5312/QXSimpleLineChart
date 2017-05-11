package com.ran.qxlinechart.unittest;

import android.content.Context;
import android.util.Log;

import dagger.Module;
import dagger.Provides;

/**
 * Created by houqixin on 2017/5/11.
 */

@Module
public class QxCommonModleFactory {//基础相关的被别人依赖的类的生产工厂
    private Context mContext;
    public QxCommonModleFactory(Context context){
        this.mContext=context;
    }
    @Provides
    public Context provideContext(){
        Log.e("Dragger2:","QxCommonModleFactory_cotext");
        return mContext;
    }
}
