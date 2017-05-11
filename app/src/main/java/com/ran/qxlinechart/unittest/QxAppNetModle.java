package com.ran.qxlinechart.unittest;

import com.ran.qxlinechart.mockit.QxNetMananger;
import com.ran.qxlinechart.mockit.QxVertifyUrl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by houqixin on 2017/5/11.
 */
@Module
public class QxAppNetModle {

    @Provides
    @Singleton
    public QxNetMananger provideNetManager(){

        return new QxNetMananger();
    }

    @Provides
    @Singleton
    public QxVertifyUrl provideVertifyUrl(){

        return new QxVertifyUrl();
    }
}
