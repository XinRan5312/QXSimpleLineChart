package com.ran.qxlinechart.unittest;


import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by houqixin on 2017/5/11.
 */
public class QxImageManager {

    private final QxImageLoadTask mImageLoadTask;

    //在构造器加上这个注解可以不在Modle fatory中provide这个 但是如果是单元测试可能会报错
    @Inject
    @Singleton
    public QxImageManager(QxImageLoadTask mImageLoadTask) {
         this.mImageLoadTask=mImageLoadTask;

    }

    public void load() {
        Log.e("Dragger2:", "QxImageManager");
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
