package com.ran.qxlinechart.unittest;

import com.ran.qxlinechart.MSBaseActivity;
import com.ran.qxlinechart.dagger2.QxTestDaggerActvity;
import com.ran.qxlinechart.mockit.QxMockitActvity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by houqixin on 2017/5/11.
 */
@Component(modules = {QxCommonModleFactory.class,QxImageModleFactory.class,QxAppNetModle.class})
@Singleton //必须注明是单例，要么会报错，强制把这个管理ModleFactory的类设置成单例，也是全局就他一个，都在用，是单例才是最好的选择
public interface AppComponent {

    //void inject(MSBaseActivity actvity);  MSBaseActivity虽然是所有的Actvity的父类但是在这里它只能接收自己，
    // 子类自己也只能接收自己，乱接会报错的，因为DaggerAppComponent就收到你的真实类，会把你要的@Inject 类生产完成后给你
    //乱接就给错了
    void inject(QxTestDaggerActvity actvity);
    void inject(QxMockitActvity actvity);
}
