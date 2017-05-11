package com.ran.qxlinechart.unittest;

import com.ran.qxlinechart.dagger2.QxTestDaggerActvity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by houqixin on 2017/5/11.
 */
@Component(modules = {QxCommonModleFactory.class,QxImageModleFactory.class})
@Singleton //必须注明是单例，要么会报错，强制把这个管理ModleFactory的类设置成单例，也是全局就他一个，都在用，是单例才是最好的选择
public interface AppComponent {

    void inject(QxTestDaggerActvity actvity);
}
