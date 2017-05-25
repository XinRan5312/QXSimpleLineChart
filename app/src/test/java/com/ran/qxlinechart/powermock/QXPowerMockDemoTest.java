package com.ran.qxlinechart.powermock;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.internal.configuration.PowerMockitoInjectingAnnotationEngine;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.powermock.api.mockito.PowerMockito.*;

import static org.junit.Assert.*;

/**
 * Created by houqixin on 2017/5/18.
 *
 * PowerMock相对于Mockito
 * 增加了私有变量
 * 私有方法
 * 静态方法的mock
 * 还有就是方法内部new对象的mock然后截取
 */
@RunWith(PowerMockRunner.class)//表明用 PowerMockerRunner来运行测试用例，否则无法使用PowerMock
@PrepareForTest({QXPowerMockDemo.class, QXPowerMockStatic.class})//所有需要测试的类，列在此处，以逗号分隔,静态方法的类最好也在这里列举
@PowerMockIgnore("javax.management.*")//为了解决使用powermock后，提示classloader错误
public class QXPowerMockDemoTest {

    @Mock
    private QXPowerMockCommon mPowerMockCommon;

    @InjectMocks
    private QXPowerMockDemo mMockDemo;
    private String str="test";
    //final 与普通方法一样mock，但是需要将其所在class添加到@PrepareForTest注解中，我就不写例子了
    @Test
    public void testGetData() throws Exception{
        //mock普通对象的普通方法
        when(mPowerMockCommon.isEmpty(str)).thenReturn(false);
        assertEquals(mPowerMockCommon.isEmpty(str),false);

        //mock对象的静态方法
        mockStatic(QXPowerMockStatic.class);
        when(QXPowerMockStatic.isEmpty(str)).thenReturn(false);
        assertEquals(QXPowerMockStatic.isEmpty(str),false);
//        QXStringUtil stringUtil=mock(QXStringUtil.class);
//        when(stringUtil.creatNewString("QXPowerMockStatic")).thenReturn("QXPowerMockStaticqx");
//        assertEquals("QXPowerMockStaticqx",stringUtil.creatNewString("QXPowerMockStatic"));

        //窃取方法内部new的新对象然后改变其属性值 比如方法中new一个List我们也可以截取给其赋值
        QXStringUtil stringUtil=new QXStringUtil();
            stringUtil.name="qx";

        whenNew(QXStringUtil.class).withNoArguments().thenReturn(stringUtil);

        assertEquals(stringUtil.creatNewString("wr"),"wrqx");
        mMockDemo.getData(str);
        assertEquals("no eq ",mMockDemo.name,"QXPowerMockStaticqx");


    }

    //测试私有方法
    @Test
    public void testSetName() throws Exception {
        Whitebox.invokeMethod(mMockDemo,"setName","wr");

        assertEquals("wr",mMockDemo.name);
    }

    //mock 私有方法然后调用

    @Test
    public void testPrivateMethod() throws Exception{
        //mock私有方法时对象也要变成mock的不能是全局的 测试私有方法和mock私有方法是不一样的

        QXPowerMockPrivate powerMockPrivate=mock(QXPowerMockPrivate.class);

       QXPowerMockDemo demo=mock(QXPowerMockDemo.class);

        Whitebox.setInternalState(mMockDemo,"mMockPrivate",powerMockPrivate);//给私有变量赋值可以用全局的但是要统一

        //when(powerMockPrivate.getString()).thenReturn("old");//测试mock的私有属性对象

//        when(mMockDemo,"privateMethodMock").thenReturn(false);//测试mock的私有方法 不用mock自己mock的用全局的会报错的
        when(demo,"privateMethodMock").thenReturn(false);//测试mock的私有方法 用mock自己mock的不用全局的
//        assertEquals(mMockDemo.publicMethodMock(),false);
        assertEquals(demo.publicMethodMock(),false);



    }
//final 与普通方法一样mock，但是需要将其所在class添加到@PrepareForTest注解中，我就不写例子了


    @Test
    public void testAnser() throws Exception {//窃取回调接口测试失败 暂时走不通看来只能用Mockito了

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {

                Object[] agrments = invocation.getArguments();

                QXPowerMockDemo.QXPowerMockCallback callback= (QXPowerMockDemo.QXPowerMockCallback) agrments[1];

                callback.onOk("OKOK");
                return str;
            }
        }).when(mPowerMockCommon).dataFromNet(str,mock(QXPowerMockDemo.QXPowerMockCallback.class));

        mMockDemo.netData(str);
        assertEquals("OKOK",mMockDemo.name);
    }
}