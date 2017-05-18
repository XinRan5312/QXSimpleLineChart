package com.ran.qxlinechart.powermock;

/**
 * Created by houqixin on 2017/5/18.
 */
public class QXPowerMockDemo {

    private QXPowerMockCommon mPowerMockCommon;
    private QXPowerMockPrivate mMockPrivate;
    String name;


    public QXPowerMockDemo(QXPowerMockCommon powerMockCommon){
        this.mPowerMockCommon=powerMockCommon;

    }

    public void getData(String url){

        QXStringUtil stringUtil=new QXStringUtil();

        if(!mPowerMockCommon.isEmpty(url)){
            name="mPowerMockCommon";
            if(!QXPowerMockStatic.isEmpty(url)){

               name=stringUtil.creatNewString("QXPowerMockStatic");
               // name="QXPowerMockStatic";
            }
        }
    }

    private void setName(String name){

        this.name=name;
    }

    private boolean privateMethodMock(){
        String s=mMockPrivate.getString();
        return s.equals("new");
    }

    public boolean publicMethodMock(){

        return privateMethodMock();
    }

    public void netData(String url){
        mPowerMockCommon.dataFromNet(url,new QXPowerMockCallback() {
            @Override
            public void onOk(String str) {
                name=str;
            }

            @Override
            public void onFail(String error) {

            }
        });
    }

    public interface QXPowerMockCallback{

        void onOk(String str);

        void onFail(String error);
    }
}
