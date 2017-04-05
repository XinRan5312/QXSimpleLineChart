package com.ran.qxlinechart;

import android.os.Bundle;
import android.widget.TextView;


/**
 * Created by houqixin on 2017/3/31.
 */
public class MSCurrentDetailInvestedActivity extends MSBaseActivity {
    private TextView mYestodayEarnedTv;
    private TextView mEarnedAllTv;
    private QXLineView mLineChart;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_detail_invest);
        mYestodayEarnedTv=$(R.id.current_yestoday_earned_value);
        mEarnedAllTv=$(R.id.current_earned_all_value);
        mLineChart=$(R.id.invested_seven_earning_chart);

    }
}
