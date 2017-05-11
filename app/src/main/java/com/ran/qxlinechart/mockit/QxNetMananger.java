package com.ran.qxlinechart.mockit;

/**
 * Created by houqixin on 2017/5/11.
 */
public class QxNetMananger {

    public void load(String url,QxNetCallback callback){



    }

    public interface QxNetCallback{

        void onSuccess(String msg);

        void onFail(String msg);
    }
}
