package com.ran.qxlinechart.unittest;

import android.util.Log;



/**
 * Created by houqixin on 2017/5/11.
 */
public class QxImageLoadTask {

    private final QxImageThirdSDK qxImageThirdSDK;

    public QxImageLoadTask(QxImageThirdSDK qxImageThirdSDK){
        this.qxImageThirdSDK=qxImageThirdSDK;
    }

    public void loadImage(){
        Log.e("Dragger2:","QxImageLoadTask");
    }
}
