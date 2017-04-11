package com.ran.qxlinechart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private MSLineChartView mLineView;
    private Button mBtn;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLineView = (MSLineChartView) findViewById(R.id.qx_chart);
        mBtn = (Button) findViewById(R.id.btn_changge);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paint pointPaint = new Paint();
                pointPaint.setColor(Color.BLUE);
                pointPaint.setAntiAlias(true);
                pointPaint.setStyle(Paint.Style.STROKE);
                pointPaint.setStrokeWidth(2f);
                mLineView.setPointPaint(pointPaint);
                mLineView.setPointType(MSLineChartView.PointType.HOLLOW_CIRCLE);
                mLineView.invalidate();
            }
        });
        tv = (TextView) findViewById(R.id.textView);
        tv.setText("《" + "你好" + "》" + "。"+"&#160");
        Log.e("QQ",""+tv.getText().toString().length());
      createStr(tv);
    }

    private void createStr(TextView view) {
        Map<String,Integer> map=new HashMap<>();
        int from=7;
        int end=7+"《购买写协议》,".length();

        SpannableString ss = new SpannableString("已经阅读并许可"+"《购买写协议》,"+"《赎回协议》,"+"打电话，发短信，发邮件，Go百度,金卡价开发机安检费，骄傲卡夫卡爱饭饭安克发卡机开发卡卡就爱看积分卡开发法可减肥咖啡");
        ss.setSpan(new URLSpanNoUnderline("http://www.baidu.com/你好"), from, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        from=end;
        end=from+"《赎回协议》,".length();
        ss.setSpan(new URLSpanNoUnderline("http://www.baidu.com/规则"), from, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ss.setSpan(new URLSpanNoUnderline("smsto:18565554482"), 4, 7,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ss.setSpan(new URLSpanNoUnderline("mailto:584991843@qq.com"), 8, 11,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ss.setSpan(new URLSpanNoUnderline("http://www.baidu.com"), 12, 16,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(ss);
        view.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private static class URLSpanNoUnderline extends URLSpan {
        public URLSpanNoUnderline(String url) {
            super(url);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);//去掉下划线
        }
    @Override
    public void onClick(View widget) {
       // super.onClick(widget);重写onClick方法把父类跳转方式重写
        String urlAndTile = getURL();
        int index = urlAndTile.lastIndexOf("/");
        String url = urlAndTile.substring(0, index);
        String title = urlAndTile.substring(index + 1);
        Context context=widget.getContext();
        startWebActvity(context,title, url);
    }
    }
    private static void startWebActvity(Context context,String title, String url) {
        Log.e("UrlAndTitle:",url+"="+title);
        context.startActivity(new Intent(context,MSCurrentInvestActivity.class));
    }
}
