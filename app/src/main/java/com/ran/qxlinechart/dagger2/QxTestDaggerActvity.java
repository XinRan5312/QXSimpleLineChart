package com.ran.qxlinechart.dagger2;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;

import com.ran.qxlinechart.MSBaseActivity;
import com.ran.qxlinechart.QxApplication;
import com.ran.qxlinechart.R;
import com.ran.qxlinechart.unittest.QxImageManager;


import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by houqixin on 2017/5/11.
 */
public class QxTestDaggerActvity extends MSBaseActivity {
    @Inject
    QxImageManager mImageManager;

    /**
     * 这是执行代码后打log的时间顺序：可见QxImageModleFactory会根据我们创建的每一个对象时需要的参数，在自己的提供的
     * provideXX方法中找，直到找到所有的参数，并创建好参数，有可能参数还需要参数，但是我不管，我会找到相应的provideXX就好，
     * 如果找不到，肯定会报错的，所以你要在里面要提供全面，然后最终创建好我们@Inject需要的类型：
     * 比如本例中我们需要的QxImageManager--需要QxImageLoadTask--需要QxImageThirdSDK--需要Context
     * 随意创建过程就是个逆过程Context--创建QxImageThirdSDK--创建QxImageLoadTask--创建QxImageManager，
     * 丛打出的Log就能显示出这一点。
     *
     05-11 13:58:46.451 19053-19053/com.ran.qxlinechart E/Dragger2:: QxCommonModleFactory_cotext
     05-11 13:58:46.451 19053-19053/com.ran.qxlinechart E/Dragger2:: QxImageModleFactory_QxImageThirdSDK
     05-11 13:58:46.451 19053-19053/com.ran.qxlinechart E/Dragger2:: QxImageModleFactory_QxImageLoadTask
     05-11 13:58:46.451 19053-19053/com.ran.qxlinechart E/Dragger2:: QxImageManager

     2.   我们在QxApplication创建AppComponent只是用到了QxCommonModleFactory但是从测试的结果来看，
       QxImageModleFactory中的provide他也会找，也就是说他会找所有他负责的ModleFactory的所有provide直到找到为止

     3.AppComponent负责管理所有的注解到它里面的ModleFactory，而ModleFactory负责提供所有的被别的类依赖的类的获得方法，
     各种provideXX，这样分工很明确，层次也很清晰，最终是我们在用的时候不用关心我直接依赖的类还依赖了谁，我就一个@Inject就可以直接使用

     要不然，我们会写好多多余而又无聊的代码，就像本例中的QxImageManager一样，如果要是自己创建我们要想使用QxImageManager，得先创建
     其它三个对象。
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_test);
        ButterKnife.bind(this);
        QxApplication.getAppComponent().inject(this);
        mImageManager.load();
    }
}
