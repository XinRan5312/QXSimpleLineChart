package com.ran.qxlinechart;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;

import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Shader;

import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by houqixin on 2017/6/8.
 */
public class QXCicleLineView extends View {


    private Float[] strPercent = new Float[]{7f, 1.4f, 7f, 1.4f, 7f, 1.4f,7f, 1.4f,7f, 1.4f,7f, 1.4f,
            7f, 1.4f,7f, 1.4f,7f, 1.4f,7f, 1.4f,7f, 1.4f,7f, 1.4f};

    private float mRadius; //圆的直径
    // 圆的粗细
    private float mStrokeWidth;
    //圆环的画笔
    private Paint cyclePaint;
    private int[] mColor = new int[]{0xFFF00078, 0xFFCA8EFF,0xFF5B00AE,0xFFFF95CA};
    //View自身的宽和高
    private int mHeight;
    private int mWidth;

    private int defaulColor = 0xFF8E8E8E;

    private int drawColorNum = -1;

    private final int drawItemNum = 10;

    private float drawColorPer = -1;

    private String mValue = "0.0%";

    public QXCicleLineView(Context context) {
        super(context);
        init();
    }

    public QXCicleLineView(Context context, AttributeSet attrs) {

        super(context, attrs);
        init();
    }

    public QXCicleLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

        mRadius=dp2px(24);

        mStrokeWidth=dp2px(3);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //移动画布到圆环的左上角
        canvas.translate(mWidth / 2 - mRadius / 2, mHeight / 2 - mRadius / 2);
        //初始化画笔
        initPaint();
        //画圆环
        drawCycle(canvas);
        Paint paintText = new Paint();
        paintText.setTextSize(dp2px(10));//设置字体大小
        paintText.setTypeface(Typeface.DEFAULT);
        paintText.setColor(0xFF333333);
        canvas.drawText(mValue, mRadius / 2-dp2px(13), mRadius / 2+dp2px(5), paintText);
    }

    private void drawCycle(Canvas canvas) {
        float startPercent = 270;
        float sweepPercent = 0;

        float gasStart = 0;
        int count = 0;

        float gasSweep = 0;
        for (int i = 0; i < strPercent.length; i++) {
//            cyclePaint.setColor(mColor[i]);


            if (i % 2 == 0) {
                if (count == drawColorNum-1) {
                    if (drawColorPer < 1) {
                        count++;
                        startPercent = gasStart + gasSweep;
                        //这里采用比例占100的百分比乘于360的来计算出占用的角度，使用先乘再除可以算出值
                        sweepPercent = (float) (strPercent[i] * 360 * drawColorPer / 100);
                        canvas.drawArc(new RectF(0, 0, mRadius, mRadius), startPercent, sweepPercent, false, cyclePaint);


                        startPercent = startPercent + sweepPercent;
                        Paint paintNo = new Paint();
                        paintNo.setAntiAlias(true);
                        paintNo.setStyle(Paint.Style.STROKE);
                        paintNo.setStrokeWidth(mStrokeWidth);
                        paintNo.setColor(defaulColor);
                        //这里采用比例占100的百分比乘于360的来计算出占用的角度，使用先乘再除可以算出值
                        sweepPercent = (float) (strPercent[i] * 360 * (1 - drawColorPer) / 100);
                        //先把原先没有找一个部分画了
                        canvas.drawArc(new RectF(0, 0, mRadius, mRadius), startPercent, sweepPercent, false, paintNo);
                    } else if (drawColorPer == 1) {
                        count++;
                        startPercent = gasStart + gasSweep;
                        //这里采用比例占100的百分比乘于360的来计算出占用的角度，使用先乘再除可以算出值
                        sweepPercent = strPercent[i] * 360 / 100;
                        canvas.drawArc(new RectF(0, 0, mRadius, mRadius), startPercent, sweepPercent, false, cyclePaint);
                    }
                } else if (count == drawColorNum && drawColorNum < drawItemNum) {
                    Paint paintNo = new Paint();
                    paintNo.setAntiAlias(true);
                    paintNo.setStyle(Paint.Style.STROKE);
                    paintNo.setStrokeWidth(mStrokeWidth);
                    paintNo.setColor(defaulColor);
                    startPercent = gasStart + gasSweep;
                    //这里采用比例占100的百分比乘于360的来计算出占用的角度，使用先乘再除可以算出值
                    sweepPercent = strPercent[i] * 360 / 100;
                    canvas.drawArc(new RectF(0, 0, mRadius, mRadius), startPercent, sweepPercent, false, paintNo);
                } else if(drawColorNum!=-1){
                    count++;

                    if (i > 0) startPercent = gasStart + gasSweep;
                    //这里采用比例占100的百分比乘于360的来计算出占用的角度，使用先乘再除可以算出值
                    sweepPercent = strPercent[i] * 360 / 100;

                    canvas.drawArc(new RectF(0, 0, mRadius, mRadius), startPercent, sweepPercent, false, cyclePaint);
                }else{
                    Paint paintNo = new Paint();
                    paintNo.setAntiAlias(true);
                    paintNo.setStyle(Paint.Style.STROKE);
                    paintNo.setStrokeWidth(mStrokeWidth);
                    paintNo.setColor(defaulColor);
                    if (i > 0) startPercent = gasStart + gasSweep;
                    //这里采用比例占100的百分比乘于360的来计算出占用的角度，使用先乘再除可以算出值
                    sweepPercent = strPercent[i] * 360 / 100;
                    canvas.drawArc(new RectF(0, 0, mRadius, mRadius), startPercent, sweepPercent, false, paintNo);
                }

            } else {

                gasStart = startPercent + sweepPercent;
                gasSweep = strPercent[i] * 360 / 100;
                Paint paintGap = new Paint();
                paintGap.setAntiAlias(true);
                paintGap.setStyle(Paint.Style.STROKE);
                paintGap.setStrokeWidth(2);
                paintGap.setColor(0xFFFFFFFF);
                canvas.drawArc(new RectF(0, 0, mRadius, mRadius), gasStart, gasSweep, false, paintGap);
            }


        }

    }

    public void setValue(float value) {
        if (value < 0 || value > 100) {

            throw new IllegalArgumentException("投资率不能为负数或者大于100!");

        } else if (value == 0) {
            drawColorNum = -1;
            drawColorPer=-1;

        }  else if (value == 100) {
            drawColorNum = drawItemNum;
            drawColorPer = 1;
            mValue="100%";
            invalidate();
        } else {
            float num=drawItemNum*value/100;
            mValue=String.valueOf(value)+"%";

            if(num>0&&num<1){
                drawColorNum=1;
                drawColorPer=num;
            }else{
                String strNum=String.valueOf(num);

                int index = strNum.indexOf(".");
                Float f=Float.parseFloat("0." + strNum.substring(index + 1));

                if (f > 0) {
                    drawColorNum = Integer.parseInt(strNum.substring(0, index))+1;
                    drawColorPer =f;
                } else {
                    drawColorNum = (int) num;
                    drawColorPer = 1;
                }
                invalidate();
            }
        }

    }

    private void initPaint() {
        cyclePaint = new Paint();
        cyclePaint.setAntiAlias(true);
        cyclePaint.setStyle(Paint.Style.STROKE);
        cyclePaint.setStrokeWidth(mStrokeWidth);
//        PathEffect pathEffect=new DashPathEffect(new float[]{49.24f,12.56f,49.24f,12.56f,49.24f,12.56f,49.24f,12.56f,49.24f,12.56f
//                ,49.24f,12.56f,49.24f,12.56f,49.24f,12.56f,49.24f,12.56f,49.24f,12.56f},0);
//
//        cyclePaint.setPathEffect(pathEffect);
        SweepGradient sweepGradient=new SweepGradient(mRadius / 2, mRadius / 2,mColor,null);//梯度渐变
        LinearGradient linearGradient = new LinearGradient(0, 0, 0, 100,
                mColor, null,
                Shader.TileMode.MIRROR);//线性渐变
        cyclePaint.setShader(sweepGradient);


    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */

    private int dp2px(float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
