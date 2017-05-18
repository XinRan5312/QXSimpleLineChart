package com.ran.qxlinechart;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.ran.qxlinechart.unittest.QXUnitTestActvity;
import com.ran.qxlinechart.unittest.QxBroacastReceiver;
import com.ran.qxlinechart.unittest.QxFragment;
import com.ran.qxlinechart.unittest.QxFramentApp;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.android.controller.FragmentController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowBitmap;
import org.robolectric.shadows.ShadowDialog;
import org.robolectric.shadows.ShadowLinearLayout;
import org.robolectric.shadows.ShadowListView;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.shadows.ShadowLooper;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.shadows.support.v4.SupportFragmentController;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 * Created by houqixin on 2017/5/10.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
//@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class QXUnitTestActvityTest {
    private QXUnitTestActvity mActvity;
    private Button mbtn;
    private TextView mTv;

    /**
     * 线程管理和调度者
     */
    private CountDownLatch runFlag;
    long count;
    @Test
    public void testCountDownLatch(){

        runFlag=new CountDownLatch(1);//表示run+1
      count = runFlag.getCount();//是1

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                //模拟网络请求或者一个耗时业务

                //完成后
                runFlag.countDown();//run-1
                count=runFlag.getCount();//是0
            }
        },6000);

        try {
            /**
             * 一直等待5秒后或者count==0时才会执行下面的Log，要不然就会一直在这里阻塞
             */
            boolean b=runFlag.await(5000, TimeUnit.MILLISECONDS);

            if(b){
                //表示是等待的时间还没到5s，count就等于零了，就不用等待了
            }else{
                //表示count还大于零，但是已经等待的时间大于或者等于5s了，所以也不等待了
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.e("flag","上面的工作终于做完了");
    }


    @Before
    public void setUp() {

        //加上这么一句话无论是测试代码中的log或者被测试中的log都会在控制台输出的
        ShadowLog.stream = System.out;

        Log.d("Test_log", "开启了log日志");


        //相当于启动了actvity 经过了 oncreate，onStart和onResume这三个生命周期
        //一般只是在启动activiy的时候调用
        mActvity = Robolectric.setupActivity(QXUnitTestActvity.class);
        mbtn = (Button) mActvity.findViewById(R.id.btn_changge);
        mTv = (TextView) mActvity.findViewById(R.id.textView);
    }

    @After
    public void tearDown() {
        mActvity = null;
        mbtn = null;
        mTv = null;
    }

    //测试启动初始化是否成功
    @Test
    public void testInit() {
        Assert.assertEquals("actvity packageName is error", "com.ran.qxlinechart", mActvity.getPackageName());
        Assert.assertNotNull("button is null", mbtn);
        Assert.assertNotNull("testView is null", mTv);
        Assert.assertEquals("textView text is error", "text", mTv.getText().toString());
    }

    //测试点击事件是否相应
    @Test
    public void testClickButton() {
        mbtn.performClick();
        Assert.assertEquals("textView text is error", "我被修改了", mTv.getText().toString());
    }

    //测试Actvity生命周期的调用
    @Test
    public void testLifecycleMethods() throws InterruptedException {
        ActivityController<QXUnitTestActvity> controller = Robolectric.buildActivity(QXUnitTestActvity.class);

        QXUnitTestActvity actvity = (QXUnitTestActvity) controller.get();
        //所有的生命周期都可以模仿但是这里的actviy一定是controller里get得到的，
        // 不能用mActvity因为全局的那个是tartUp的更生命周期的这个不是一个
        controller.create();
        Assert.assertEquals("create", actvity.name);

        controller.start();
        Assert.assertEquals("start", actvity.name);

        controller.resume();
        Assert.assertEquals("jump", actvity.name);

        controller.pause();
        Assert.assertEquals("pause", actvity.name);

        controller.stop();
        Assert.assertEquals("stop", actvity.name);

        controller.destroy();

        Assert.assertEquals("destroy", actvity.name);
        mbtn.performClick();
        Assert.assertEquals("textView text is error", "我被修改了", mTv.getText().toString());


    }

    //Fragment是继承app.Fragment 生命周期
    @Test
    public void testFragmentLifecyle() {

        FragmentController<QxFramentApp> controller = Robolectric.buildFragment(QxFramentApp.class);


        QxFramentApp framentApp = controller.get();
        controller.create();////这样一步就走到了onCreatView不仅仅走了onCraete
        Assert.assertEquals("createView", framentApp.name);

        controller.start();
        Assert.assertEquals("start", framentApp.name);

        controller.resume();
        Assert.assertEquals("resume", framentApp.name);

        controller.destroy();
        Assert.assertEquals("destroy", framentApp.name);

    }

    //测试Actvity的跳转
    @Test
    public void testStartActiviy() {
        mbtn.performClick();
        Assert.assertEquals("textView text is error", "我被修改了", mTv.getText().toString());
//        mActvity.name="jump"; 使用这个是错误的

//        ActivityController<QXUnitTestActvity> controller=Robolectric.buildActivity(QXUnitTestActvity.class).create().start();//已经走到resume了
//
//        QXUnitTestActvity actvity= (QXUnitTestActvity) controller.get();
//        actvity.name="jump";也是行不通的
        //只能在自己的生命周期里更改name
        ShadowApplication shadowApplication = ShadowApplication.getInstance();
        //表示shadowApplication.getNextStartedActivity()不能是null（notNullValue()）如果是null就会报错，然后提示"next actvity is null shoud  not jump"
//        Assert.assertThat("next actvity is null shoud  not jump",shadowApplication.getNextStartedActivity(),is(notNullValue()));

        //或许用下面这个来验证 用上面注释的一条验证最简单
        Intent expIntent = new Intent(mActvity, MSCurrentInvestActivity.class);

        Intent act = shadowApplication.getNextStartedActivity();
//如果两者不同就会报错提示
        Assert.assertEquals("不是从mActvity跳转到MSCurrentInvestActivity或者是携带的数据不一样", expIntent.getComponent(), act.getComponent());
    }

    //Fragment是继承v4的 测试Fragment添加初始化是否成功
    @Test
    public void testFragment() {

        QxFragment fragment = new QxFragment();

        //把Fragment添加到Activity中，并且启动Fragment，触发他的生命周期函数OnCreateView

        SupportFragmentTestUtil.startFragment(fragment);
        Bundle bundle = fragment.getArguments();

        Assert.assertThat("fragment not start success", fragment.getView(), is(notNullValue()));
    }

    //测试模拟延迟任务的执行
    @Test
    public void testDelayTask() {
        Assert.assertFalse(mActvity.mCount == 6);//此时没有走线程呢所以不会是6

        mbtn.performClick();

        //利用Looper的影子来安排任务的执行，这也符合Android的架构情况
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();//等待UI delay的Tasks运行完毕然后再往下执行
        //ShadowLooper.idleMainLooper();等待mainLooper执行完毕
        //ShadowLooper.idleMainLooper(1,TimeUnit.SECONDS);等待1秒mainLooper执行完毕
//        ShadowLooper.runMainLooperOneTask();运行mainLooper的一个任务
//        ShadowLooper.runMainLooperToNextTask();
//        ShadowLooper.runUiThreadTasks();//运行Ui上的tasks
        //用ShadowLooper来管理运行Android的线程
        Assert.assertTrue(mActvity.mCount == 6);
    }
//    @Test public void onUnsubscribePostsOffMainThread() throws InterruptedException {
//        ShadowLooper.pauseMainLooper();
//
//        final CountDownLatch latch = new CountDownLatch(1);
//        final AtomicBoolean called = new AtomicBoolean();
//        new Thread(new Runnable() {
//            @Override public void run() {
//                new MainThreadDisposable() {
//                    @Override protected void onDispose() {
//                        called.set(true);
//                    }
//                }.dispose();
//                latch.countDown();
//            }
//        }).start();
//
//        assertTrue(latch.await(1, SECONDS));
//        assertFalse(called.get()); // Callback has not yet run.
//
//        ShadowLooper.runMainLooperOneTask();
//        assertTrue(called.get());
//    }

//    @Test
//    public void directScheduleOnceUsesHook() {
//        final CountingRunnable newCounter = new CountingRunnable();
//        final AtomicReference<Runnable> runnableRef = new AtomicReference<>();
//        RxJavaPlugins.setScheduleHandler(new Function<Runnable, Runnable>() {
//            @Override public Runnable apply(Runnable runnable) {
//                runnableRef.set(runnable);
//                return newCounter;
//            }
//        });
//
//        CountingRunnable counter = new CountingRunnable();
//        scheduler.scheduleDirect(counter);
//
//        // Verify our runnable was passed to the schedulers hook.
//        assertSame(counter, runnableRef.get());
//
//        runUiThreadTasks();
//        // Verify the scheduled runnable was the one returned from the hook.
//        assertEquals(1, newCounter.get());
//        assertEquals(0, counter.get());
//    }
    /**
     * ShadowXX其实就是这个XX的影子，代理或者是复制品  有N个ShadowXX，比如ShadowApplication,ShadowActvity,ShadowToast
     * ShadowListView,ShadowLinearLayout,ShadowBitmap等，也就是说只要Android有的类几乎都有相应的影子Shadow。也就是说只要我们
     * 有了一个类的Shadow我们就相当于有了这个类，而且功能更强大，毕竟是经过了，包装的了，创建一个类的Shadow也很容易：
     * ShadowActivity shadowActivity= Shadows.shadowOf(mActvity);
     * View vie= shadowActivity.getContentView();
     */
    @Test
    public void testBroadCast() {
        ShadowApplication shadowApplication = ShadowApplication.getInstance();
        Intent intent = new Intent("com.wr.qx.ok");
        Assert.assertTrue(shadowApplication.hasReceiverForIntent(intent));//是否已经注册action是"com.wr.qx.ok"的广播

        //模拟注册广播后 激发广播后的操作

        QxBroacastReceiver receiver = new QxBroacastReceiver();
        receiver.onReceive(RuntimeEnvironment.application, intent);

        //检查接到广播后是否启动了context.startActivity(new Intent(context, MSCurrentInvestActivity.class));

        Assert.assertThat("没有收到广播 然后启动actvity", shadowApplication.getNextStartedActivity(), is(notNullValue()));
    }

    @Test
    public void testDialog() {
        mbtn.performClick();
        Dialog dialog = ShadowDialog.getLatestDialog();
        Assert.assertNotNull("dialog not creat", dialog);
        dialog.dismiss();
        Assert.assertTrue(!dialog.isShowing());
    }

    @Test
    public void testAlertDialog() {
        mbtn.performClick();
        AlertDialog alertDialog = ShadowAlertDialog.getLatestAlertDialog();
        Assert.assertNotNull("dialog not creat", alertDialog);
        alertDialog.dismiss();
        Assert.assertTrue(!alertDialog.isShowing());

    }

    @Test
    public void testToast() {
        mbtn.performClick();

        Assert.assertNotNull("not invote Toast", ShadowToast.getLatestToast());
        String toastText = ShadowToast.getTextOfLatestToast();
        Assert.assertEquals("toast error", "hello", toastText);
    }

    //测试模拟资源文件的获取
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Test
    public void testReadResouce() {
        Application application = RuntimeEnvironment.application;
        Drawable drawable = application.getDrawable(R.mipmap.ic_launcher);
    }


}
