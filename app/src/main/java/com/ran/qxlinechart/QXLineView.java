package com.ran.qxlinechart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by houqixin on 2017/3/29.
 */
public class QXLineView extends View {
    private int mXor;
    private int mYor;
    private int mWidth;
    private int mHeight;
    private int mYSpace = 50;
    private int mXSpace = 50;
    private int mTextSize = 15;
    private Paint mPaint;
    private Paint mTextPaint;
    private Paint mValuePaint;
    private Paint mDefaultPaint;
    private Paint mDefaultTextPaint;
    private Paint mDefaultValuePaint;
    private Paint mPointPaint;
    private Paint mDefaultPointPaint;
    private Paint mFillPaint;
    private Paint mDefaultFillPaint;
    private List<Float> mValues;
    private float mMinY;
    private float mMaxY;
    private float mAverageValue;
    private int mLineNum = 5;
    private float mRato;
    private boolean mIsXGridLine = false;
    private boolean mIsYGridLine = true;

    private boolean mIsFill = true;

    private boolean mIsShowValues = true;
    private PointType mPointType = PointType.HOLLOW_SQUARE;
    private int mHeughtWidthRate=-1;

    public void setPointType(PointType pointType) {
        this.mPointType = pointType;
    }

    public void setPointPaint(Paint pointPaint) {
        this.mPointPaint = pointPaint;
    }

    private float getRato() {
        return (mMaxY - mMinY) / mLineNum;

    }

    public void isFillEnable(boolean mIsFill) {
        this.mIsFill = mIsFill;
    }

    public void setFillPaint(Paint mFillPaint) {
        this.mFillPaint = mFillPaint;
    }

    public QXLineView(Context context) {
        this(context, null);
    }

    public QXLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QXLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.QXChartLineRato);
        mHeughtWidthRate = (int) a.getFloat(R.styleable.QXChartLineRato_height_ratio_with,mHeughtWidthRate);
        a.recycle();
    }

    private void init(Context context) {
        getDates();
        setData();
        mDefaultPaint = new Paint();
        mDefaultPaint.setColor(Color.GRAY);
        mDefaultPaint.setStrokeWidth(3);
        mDefaultPaint.setAntiAlias(true);
        mDefaultPaint.setStyle(Paint.Style.STROKE);

        mDefaultTextPaint = new Paint();
        mDefaultTextPaint.setTextSize(mTextSize);//设置字体大小
        mDefaultTextPaint.setTypeface(Typeface.DEFAULT);//设置字体类型

        mDefaultValuePaint = new Paint();
        mDefaultValuePaint.setColor(Color.YELLOW);
        mDefaultValuePaint.setAntiAlias(true);
        mDefaultValuePaint.setStyle(Paint.Style.STROKE);
        mDefaultValuePaint.setStrokeWidth(3f);


        mDefaultFillPaint = new Paint();
        mDefaultFillPaint.setColor(Color.YELLOW);
        mDefaultFillPaint.setStrokeWidth(3f);
        mDefaultFillPaint.setStyle(Paint.Style.FILL);
        mDefaultFillPaint.setAlpha(120);

        mDefaultPointPaint = new Paint();
        mDefaultPointPaint.setColor(Color.RED);
        mDefaultPointPaint.setAntiAlias(true);
        mDefaultPointPaint.setStyle(Paint.Style.STROKE);
        mDefaultPointPaint.setStrokeWidth(2f);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int spceSize = MeasureSpec.getSize(widthMeasureSpec);
        if(mHeughtWidthRate==-1){
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((spceSize * 13 / 25), specMode);
        }else{
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((spceSize * mHeughtWidthRate), specMode);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mXor = dp2px(27);
        mYor = getHeight() - dp2px(27);
        mWidth = getWidth() - dp2px(40);
        mHeight = mYor - dp2px(13);
        mYSpace = mHeight / mLineNum;
        mXSpace = mWidth / 6;
    }

    public void setData() {
        mMinY = 4;
        mMaxY = 5;
        mLineNum = 5;
        mYSpace = (mHeight - 80) / mLineNum;
        mRato = getRato();

    }


    public void isXGridLineEnable(boolean mIsXGridLine) {
        this.mIsXGridLine = mIsXGridLine;
    }

    public void isYGridLineEnable(boolean mIsYGridLine) {
        this.mIsYGridLine = mIsYGridLine;
    }

    public void setPaint(Paint paint) {
        this.mPaint = paint;
    }

    public void setTextPaint(Paint textPaint) {
        this.mTextPaint = textPaint;
    }

    public void setValuePaint(Paint valuePaint) {
        this.mValuePaint = valuePaint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        RectF rectF = new RectF(mXor, mYor - bitmap.getHeight(), mXor + bitmap.getWidth(), mYor);
        canvas.drawBitmap(bitmap, null, rectF, mDefaultPaint);
//        RectF rectF1=new RectF(mXor,mYor-3*mYSpace,mXor+mWidth,mYor);
////        canvas.drawArc(rectF1,0,180,false,mDefaultTextPaint);
//        canvas.drawOval(rectF1,mDefaultValuePaint);
        canvas.drawLine(mXor, mYor, mXor, mYor - mHeight, mPaint == null ? mDefaultPaint : mPaint);//y轴
        //画y轴上的刻度
        List<Float> yLineValue = new ArrayList<>();
        yLineValue.add(4f);
        yLineValue.add(4 + mRato);
        yLineValue.add(4 + mRato * 2);
        yLineValue.add(4 + mRato * 3);
        yLineValue.add(4 + mRato * 4);
        yLineValue.add(4 + mRato * 5);
        for (int i = 0; i < mLineNum + 1; i++) {
            //画y轴刻度
            canvas.drawText("" + yLineValue.get(i), mXor - dp2px(15), mYor - mYSpace * i,
                    mTextPaint == null ? mDefaultTextPaint : mTextPaint);
            if ((i != 0) && mIsYGridLine) {
                canvas.drawLine(mXor - 5, mYor - mYSpace * i, mXor + mWidth,
                        mYor - mYSpace * i, mPaint == null ? mDefaultPaint : mPaint);//垂直Y轴的线非x轴
            }
        }
        canvas.drawLine(mXor, mYor, mXor + mWidth, mYor, mPaint == null ? mDefaultPaint : mPaint);//x轴
        //画x轴上的刻度线
        for (int j = 1; j < 7; j++) {
            canvas.drawLine(mXor + mXSpace * j, mYor, mXor + mXSpace * j, mYor + 5, mPaint == null ? mDefaultPaint : mPaint);
            if (mIsXGridLine) {
                canvas.drawLine(mXor + mXSpace * j, mYor, mXor + mXSpace * j, mYor - mHeight, mPaint == null ? mDefaultPaint : mPaint);
                //垂直x轴的线，非Y轴
            }
        }
        //画x轴刻度线上的值
        List<String> xLineValues = getDates();
        for (int j = 0; j < xLineValues.size(); j++) {
            canvas.drawText(xLineValues.get(j), mXor + mXSpace * j - mTextSize * 1.5f, mYor + dp2px(15),
                    mTextPaint == null ? mDefaultTextPaint : mTextPaint);
        }

        //画曲线
        List<Float> xyLineValue = new ArrayList<>();
        xyLineValue.add(4.53f);
        xyLineValue.add(4.50f);
        xyLineValue.add(4.55f);
        xyLineValue.add(4.57f);
        xyLineValue.add(4.53f);
        xyLineValue.add(4.60f);
        xyLineValue.add(4.62f);
        if (mIsFill) {
            Path linePath = new Path();
            linePath.moveTo(mXor + 4, mYor - 2);
            for (int i = 0; i < xyLineValue.size(); i++) {
                linePath.lineTo(mXor + mXSpace * i, mYor - createLineValue(xyLineValue.get(i)));
            }
            linePath.lineTo(mXor + mWidth - 4, mYor - 2);

            canvas.drawPath(linePath, mFillPaint == null ? mDefaultFillPaint : mFillPaint);
            if (mValuePaint == null) {
                mDefaultValuePaint.setStrokeWidth(6);
            } else {
                mValuePaint.setStrokeWidth(6);
            }
        }
        Path linePath1 = new Path();
        for (int i = 0; i < xyLineValue.size(); i++) {
            if (i == 0) {
                linePath1.moveTo(mXor, mYor - createLineValue(xyLineValue.get(i)));
            } else {
                linePath1.lineTo(mXor + mXSpace * i, mYor - createLineValue(xyLineValue.get(i)));
            }

        }

        canvas.drawPath(linePath1, mValuePaint == null ? mDefaultValuePaint : mValuePaint);
        for (int i = 0; i < xyLineValue.size(); i++) {
            //画曲线上的点

            if (mIsShowValues) {
                canvas.drawText("" + xyLineValue.get(i), mXor + mXSpace * i - mTextSize * 1.5f,
                        mYor - createLineValue(xyLineValue.get(i)) - dp2px(8),
                        mTextPaint == null ? mDefaultTextPaint : mTextPaint);

                float xCenter = mXor + mXSpace * i;
                float yCenter = mYor - createLineValue(xyLineValue.get(i));
                RectF rectF1 = new RectF(xCenter - dp2px(2), yCenter - dp2px(2), xCenter + dp2px(2), yCenter + dp2px(2));
                switch (mPointType) {
                    case HOLLOW_CIRCLE:
                        canvas.drawArc(rectF1, 0, 360, false, mPointPaint == null ? mDefaultPointPaint : mPointPaint);
                        break;
                    case SLIDE_CIRCLE:
                        mDefaultPointPaint.reset();
                        mDefaultPointPaint.setColor(Color.RED);
                        mDefaultPointPaint.setStrokeWidth(2f);
                        mDefaultPointPaint.setStyle(Paint.Style.FILL);
                        canvas.drawArc(rectF1, 0, 360, true, mPointPaint == null ? mDefaultPointPaint : mPointPaint);
                        break;
                    case HOLLOW_SQUARE:
                        canvas.drawRect(rectF1, mPointPaint == null ? mDefaultPointPaint : mPointPaint);
                        break;
                    case SLIDE_SQUARE:
                        mDefaultPointPaint.reset();
                        mDefaultPointPaint.setColor(Color.RED);
                        mDefaultPointPaint.setStrokeWidth(2f);
                        mDefaultPointPaint.setStyle(Paint.Style.FILL);
                        canvas.drawRect(rectF1, mPointPaint == null ? mDefaultPointPaint : mPointPaint);
                        break;

                    default:
                        canvas.drawArc(rectF1, 0, 360, false, mPointPaint == null ? mDefaultPointPaint : mPointPaint);
                        break;
                }

            }
        }


    }

    private float createLineValue(float v) {
        return ((v - mMinY) / mRato) * mYSpace;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */

    private int dp2px(float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private List<String> getDates() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        String dateString = formatter.format(currentTime);
        List<String> strs = new ArrayList<>(7);
        String[] strArray = dateString.split("-");
        String strMM = strArray[0];
        String strDD = strArray[1];
        Log.e("dateString:", dateString + strMM + strDD);
        int day1 = strDD.charAt(0);
        int day2 = strDD.charAt(1);
        int day = 0;
        int mon1 = strMM.charAt(0);
        int mon2 = strMM.charAt(1);
        int mon = 0;
        if (day1 == 0) {
            day = day2;
            if (day < 7) {
                if (mon1 == 0) {
                    mon = mon2;
                } else {
                    mon = Integer.valueOf(strMM);
                }
                int i = 6;
                do {
                    strs.add(0, dateString);

                    if (day == 1) {
                        if (mon == 1) {
                            strMM = "12";
                            day = 31;
                        } else if (mon == 3) {
                            strMM = "02";
                            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                            String dateString1 = formatter.format(currentTime);
                            int year = Integer.valueOf(dateString1.split("-")[0]);
                            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                                day = 29;
                            } else {
                                day = 28;
                            }
                        } else if (mon == 5 || mon == 7) {
                            strMM = "0" + (mon - 1);
                            day = 30;
                        } else if (mon == 12) {
                            strMM = "11";
                            day = 30;
                        } else if (mon == 8) {
                            strMM = "07";
                            day = 31;
                        } else if (mon == 10) {
                            strMM = "09";
                            day = 30;
                        } else if (mon == 4 || mon == 6 || mon == 9 || mon == 2) {
                            strMM = "0" + (mon - 1);
                            day = 31;
                        } else if (mon == 11) {
                            strMM = "10";
                            day = 31;
                        }
                    } else {
                        day = day - 1;
                    }
                    if (day < 10) {
                        dateString = strMM + "-0" + day;
                    } else {
                        dateString = strMM + "-" + day;
                    }

                    i--;
                } while (i != -1);

            } else {
                int i = 6;
                do {
                    strs.add(0, dateString);
                    day = day - 1;
                    dateString = strMM + "-" + day;
                    i--;
                } while (i != -1);
            }
        } else {
            day = Integer.valueOf(strDD);
            int i = 6;
            do {
                strs.add(0, dateString);
                day = day - 1;
                if (day < 10) {
                    dateString = strMM + "-0" + day;
                } else {
                    dateString = strMM + "-" + day;
                }
                i--;
            } while (i != -1);
        }
        return strs;
    }


    public enum PointType {
        HOLLOW_CIRCLE,
        SLIDE_CIRCLE,
        HOLLOW_SQUARE,
        SLIDE_SQUARE;
    }
}
