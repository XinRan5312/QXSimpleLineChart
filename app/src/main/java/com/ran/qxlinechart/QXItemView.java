package com.ran.qxlinechart;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by houqixin on 2017/6/9.
 */
public class QXItemView extends LinearLayout implements View.OnClickListener{

    private ImageView mIcon;

    private TextView mTitle;

    private TextView mDespr;

    private Context mContext;
    private Class<? extends Activity> mCls;

    private Bundle mBundle;

    private boolean mIsResult;


    private void init(Context context) {

        RelativeLayout childView= (RelativeLayout) inflate(context,R.layout.view_item_default,this);

        mIcon=$(childView,R.id.item_icon);

        mTitle=$(childView,R.id.item_title);

        mDespr=$(childView,R.id.item_des);

        mIcon.setOnClickListener(this);


    }
    public void setOnClickIcon(Context context, Class<? extends Activity> cls,boolean isResult){
          setOnClickIcon(context,cls,null,isResult);
    }
    public void setOnClickIcon(Context context, Class<? extends Activity> cls,Bundle bundle){
        setOnClickIcon(context,cls,bundle,false);
    }
    public void setOnClickIcon(Context context, Class<? extends Activity> cls){
        setOnClickIcon(context,cls,null,false);
    }

    public void setOnClickIcon(Context context, Class<? extends Activity> cls,Bundle bundle,boolean isResult){
        this.mContext=context;
        this.mCls=cls;
        this.mBundle=bundle;
        this.mIsResult=isResult;
    }
    public void setTileText(String str){

            mTitle.setText(str);

    }
    public void setDespText(String str){
        mDespr.setText(str);
    }
    public void setIcon(int id){
            mIcon.setImageResource(id);

    }
    public QXItemView(Context context) {
        super(context);
        init(context);
    }


    public QXItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public QXItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private <V extends View> V $(@NonNull View view, @IdRes int id){

        return (V) view.findViewById(id);
    }


    @Override
    public void onClick(View view) {
        if(view==mIcon&&mCls!=null){
            Intent intent=new Intent();
            if(mBundle!=null){

                intent.putExtras(mBundle);
            }
            mContext.startActivity(intent);

        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mContext!=null){
            mContext=null;
            mBundle=null;
            mCls=null;
        }
    }
}
