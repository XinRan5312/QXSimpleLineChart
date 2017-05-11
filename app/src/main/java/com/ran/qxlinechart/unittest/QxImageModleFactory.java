package com.ran.qxlinechart.unittest;

import android.content.Context;
import android.util.Log;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by houqixin on 2017/5/11.
 */
@Module
public class QxImageModleFactory {//图片相关的被别人依赖的类的生产工厂

    @Provides
    @Singleton
    public QxImageThirdSDK provideImageThirdSDK(Context context){
        Log.e("Dragger2:","QxImageModleFactory_QxImageThirdSDK");
        return new QxImageThirdSDK(context);
    }

    @Provides
    @Singleton
    public QxImageLoadTask provideImageLoadTask(QxImageThirdSDK thirdSDK){
        Log.e("Dragger2:","QxImageModleFactory_QxImageLoadTask");
        return new QxImageLoadTask(thirdSDK);
    }
    //因为在QxImageManager的构造器上加上了@Inject和@Singleton，所以可以不提供这个类的提供者，系统会根据注解自动提供。
    // 但是如果是单元测试可能会报错
//    @Singleton
//    @Provides
//    public QxImageManager provideImageManager(QxImageLoadTask loadTask){
//
//        return new QxImageManager(loadTask);
//    }
}
