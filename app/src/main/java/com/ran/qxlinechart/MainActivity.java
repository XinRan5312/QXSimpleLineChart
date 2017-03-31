package com.ran.qxlinechart;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private QXLineView mLineView;
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLineView= (QXLineView) findViewById(R.id.qx_chart);
        mBtn= (Button) findViewById(R.id.btn_changge);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paint pointPaint=new Paint();
                pointPaint.setColor(Color.BLUE);
                pointPaint.setAntiAlias(true);
                pointPaint.setStyle(Paint.Style.STROKE);
                pointPaint.setStrokeWidth(2f);
                mLineView.setPointPaint(pointPaint);
                mLineView.setPointType(QXLineView.PointType.HOLLOW_CIRCLE);
                mLineView.invalidate();
            }
        });
    }
}
