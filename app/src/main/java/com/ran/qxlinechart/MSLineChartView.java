package com.ran.qxlinechart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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
 * Created by houqixin on 2017/4/6.
 */
public class MSLineChartView extends View {
    private int mXor;
    private int mYor;
    private int mWidth;
    private int mHeight;
    private int mYSpace = 50;
    private int mXSpace = 50;
    private int mTextSize = 8;
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
    private double mMinY;
    private double mMaxY;
    private double mAverageValue;
    private int mLineNum = 5;
    private float mRato;
    private boolean mIsXGridLine = false;
    private boolean mIsYGridLine = true;

    private boolean mIsFill = true;
    private CurrentDetail mCurrentDetail;
    private List<Double> mXYLineValue;
    private List<String> mXLineValues;

    public void setCurrentDetail(CurrentDetail currentDetail) {
        this.mCurrentDetail = currentDetail;
        invalidate();
    }

    private boolean mIsShowValues = true;
    private PointType mPointType = PointType.HOLLOW_CIRCLE;
    private int mHeughtWidthRate = -1;

    public void setPointType(PointType pointType) {
        this.mPointType = pointType;
    }

    public void setPointPaint(Paint pointPaint) {
        this.mPointPaint = pointPaint;
    }

    private float getRato() {
        return (float) ((mMaxY - mMinY) / mLineNum);
    }

    public void isFillEnable(boolean mIsFill) {
        this.mIsFill = mIsFill;
    }

    public void setFillPaint(Paint mFillPaint) {
        this.mFillPaint = mFillPaint;
    }

    public MSLineChartView(Context context) {
        this(context, null);
    }

    public MSLineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MSLineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.QXChartLineRato);
        mHeughtWidthRate = (int) a.getFloat(R.styleable.QXChartLineRato_height_ratio_with, mHeughtWidthRate);
        a.recycle();
    }

    private void init(Context context) {
        mDefaultPaint = new Paint();
        mDefaultPaint.setColor(Color.parseColor("#CBCADD"));
        mDefaultPaint.setStrokeWidth(3);
        mDefaultPaint.setAntiAlias(true);
        mDefaultPaint.setStyle(Paint.Style.STROKE);

        mDefaultTextPaint = new Paint();
        mDefaultTextPaint.setColor(Color.parseColor("#999998"));
        mDefaultTextPaint.setTextSize(dp2px(mTextSize));//设置字体大小
        mDefaultTextPaint.setTypeface(Typeface.DEFAULT);//设置字体类型

        mDefaultValuePaint = new Paint();
        mDefaultValuePaint.setColor(Color.parseColor("#333092"));
        mDefaultValuePaint.setAntiAlias(true);
        mDefaultValuePaint.setStyle(Paint.Style.STROKE);
        mDefaultValuePaint.setStrokeWidth(3f);


        mDefaultFillPaint = new Paint();
        mDefaultFillPaint.setColor(Color.parseColor("#333092"));
        mDefaultFillPaint.setStrokeWidth(3f);
        mDefaultFillPaint.setStyle(Paint.Style.FILL);
        mDefaultFillPaint.setAlpha(20);


        mDefaultPointPaint = new Paint();
        mDefaultPointPaint.setColor(Color.parseColor("#333092"));
        mDefaultPointPaint.setAntiAlias(true);
        mDefaultPointPaint.setStyle(Paint.Style.STROKE);
        mDefaultPointPaint.setStrokeWidth(6f);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int spceSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize;
        if (mHeughtWidthRate == -1) {
            heightSize = spceSize * 13 / 25;

        } else {
            heightSize = spceSize * spceSize * mHeughtWidthRate;
        }
        setMeasuredDimension(spceSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
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
    private String creatStringDouble(double shu) {
        String str= String.valueOf(shu);
        str=str+0;
        int index= str.indexOf(".");
        return str.substring(0,index+3);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initDatas();
        List<String> yLineValue = new ArrayList<>();
        for (int i = 0; i < mLineNum + 1; i++) {
            yLineValue.add(creatStringDouble(mMinY + mRato * i));
        }
        for (int i = 0; i < mLineNum + 1; i++) {
            canvas.drawText(yLineValue.get(i) + "%", mXor - dp2px(25), mYor - mYSpace * i,
                    mTextPaint == null ? mDefaultTextPaint : mTextPaint);
            if ((i != 0) && mIsYGridLine) {
                canvas.drawLine(mXor, mYor - mYSpace * i, mXor + mWidth,
                        mYor - mYSpace * i, mPaint == null ? mDefaultPaint : mPaint);
            }
        }
        canvas.drawLine(mXor, mYor, mXor + mWidth, mYor, mPaint == null ? mDefaultPaint : mPaint);
        for (int j = 1; j < 7; j++) {
            canvas.drawLine(mXor + mXSpace * j, mYor, mXor + mXSpace * j, mYor + dp2px(5), mPaint == null ? mDefaultPaint : mPaint);
            if (mIsXGridLine) {
                canvas.drawLine(mXor + mXSpace * j, mYor, mXor + mXSpace * j, mYor - mHeight, mPaint == null ? mDefaultPaint : mPaint);
            }
        }
        if (mXLineValues == null) {
            mXLineValues = getDates();
        }
        for (int j = 0; j < mXLineValues.size(); j++) {
            canvas.drawText(mXLineValues.get(j), mXor + mXSpace * j - dp2px(mTextSize) * 1.5f, mYor + dp2px(15),
                    mTextPaint == null ? mDefaultTextPaint : mTextPaint);
        }

        //画曲线
        if (mXYLineValue == null) {
            mXYLineValue = new ArrayList<>();
            mXYLineValue.add(4.53);
            mXYLineValue.add(4.50);
            mXYLineValue.add(4.55);
            mXYLineValue.add(4.57);
            mXYLineValue.add(4.53);
            mXYLineValue.add(4.60);
            mXYLineValue.add(4.62);
        }
        if (mIsFill) {
            Path linePath = new Path();
            linePath.moveTo(mXor, mYor);
            for (int i = 0; i < mXYLineValue.size(); i++) {
                if (i == mXYLineValue.size() - 1) {
                    linePath.lineTo(mXor + mXSpace * i, (float) (mYor - createLineValue(mXYLineValue.get(i))));
                } else {
                    linePath.lineTo(mXor + mXSpace * i, (float) (mYor - createLineValue(mXYLineValue.get(i))));
                }

            }
            linePath.lineTo(mXor + mWidth, mYor);

            canvas.drawPath(linePath, mFillPaint == null ? mDefaultFillPaint : mFillPaint);
            if (mValuePaint == null) {
                mDefaultValuePaint.setStrokeWidth(6);
            } else {
                mValuePaint.setStrokeWidth(6);
            }
        }
        Path linePath1 = new Path();
        for (int i = 0; i < mXYLineValue.size(); i++) {
            if (i == 0) {
                linePath1.moveTo(mXor, (float) (mYor - createLineValue(mXYLineValue.get(i))));
            } else if(i==6){
                linePath1.lineTo(mXor + mXSpace * i-10, (float) (mYor - createLineValue(mXYLineValue.get(i))));
            } else {
                linePath1.lineTo(mXor + mXSpace * i, (float) (mYor - createLineValue(mXYLineValue.get(i))));
            }

        }

        canvas.drawPath(linePath1, mValuePaint == null ? mDefaultValuePaint : mValuePaint);
        for (int i = 0; i < mXYLineValue.size(); i++) {
            //画曲线上的点

            if (mIsShowValues && i == mXLineValues.size() - 1) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_line_chart);

                RectF rectF = new RectF(mXor + mXSpace * i-bitmap.getWidth()-dp2px(1),
                        (float) (mYor - createLineValue(mXYLineValue.get(i))) - bitmap.getHeight()-dp2px(6),
                        mXor + mXSpace * i-dp2px(1),
                        (float) (mYor - createLineValue(mXYLineValue.get(i)))-dp2px(6));
                canvas.drawBitmap(bitmap, null, rectF, mDefaultPaint);
                Paint textPaint = new Paint();
                textPaint.setColor(Color.parseColor("#FFFFFF"));
                textPaint.setTextSize(dp2px(mTextSize));//设置字体大小
                textPaint.setTypeface(Typeface.DEFAULT);//设置字体类型
                canvas.drawText(creatStringDouble(mXYLineValue.get(i))+"%",
                        mXor + mXSpace * i - dp2px(mTextSize) * 1.5f-bitmap.getWidth()/3-dp2px(2),
                        (float) (mYor - createLineValue(mXYLineValue.get(i)) - dp2px(12)),
                        textPaint);

                float xCenter = mXor + mXSpace * i-6;
                float yCenter = (float) (mYor - createLineValue(mXYLineValue.get(i)));
                RectF rectF1 = new RectF(xCenter - dp2px(3), yCenter - dp2px(3), xCenter + dp2px(3), yCenter + dp2px(3));
                switch (mPointType) {
                    case HOLLOW_CIRCLE:
                        mDefaultPointPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
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

    private void initDatas() {
        if (mCurrentDetail != null) {
            generateXYLineValue(mCurrentDetail.last7DaysInterestRates);
            mMinY = mCurrentDetail.minDisplayInterestRate*100;
            mMaxY = mCurrentDetail.maxDisplayInterestRate*100;
            mLineNum = mCurrentDetail.intervalCount;
        } else {
            mMinY = 4;
            mMaxY = 5;
            mLineNum = 5;
        }
        mYSpace = (mHeight - 80) / mLineNum;
        mRato = getRato();
        mXor = dp2px(27);
        mYor = getHeight() - dp2px(27);
        mWidth = getWidth() - dp2px(40);
        mHeight = mYor - dp2px(13);
        mYSpace = mHeight / mLineNum;
        mXSpace = mWidth / 6;
    }


    private void generateXYLineValue(List<CurrentDetail.DayInterestRate> rates) {
        if (rates == null || rates.size() != 7) {
            mCurrentDetail = null;
            mMinY = 4;
            mMaxY = 5;
            mLineNum = 5;
            mYSpace = (mHeight - 80) / mLineNum;
            mRato = getRato();
            mXor = dp2px(27);
            mYor = getHeight() - dp2px(27);
            mWidth = getWidth() - dp2px(40);
            mHeight = mYor - dp2px(13);
            mYSpace = mHeight / mLineNum;
            mXSpace = mWidth / 6;
        } else {
            mXYLineValue = new ArrayList<>();
            mXLineValues = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                mXYLineValue.add((rates.get(i).interestRate)*100);
                mXLineValues.add(creatDateStr(rates.get(i).date));

            }
        }
    }

    private String creatDateStr(Long date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd");
        return formatter.format(date);
    }

    private double createLineValue(double v) {
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
        Long l=currentTime.getTime();
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
