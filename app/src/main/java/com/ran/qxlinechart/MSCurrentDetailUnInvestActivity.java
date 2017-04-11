package com.ran.qxlinechart;

import android.os.Bundle;
import android.widget.TextView;


/**
 * Created by houqixin on 2017/3/31.
 */
public class MSCurrentDetailUnInvestActivity extends MSBaseActivity {
    private TextView mTodayRadoTv;
    private TextView mEvryWanEarningTv;
    private QXLineView mLineChart;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_detail_uninvest);
        mTodayRadoTv=$(R.id.tv_today_earning_value);
        mEvryWanEarningTv=$(R.id.tv_evry_wan_earning);
        mLineChart=$(R.id.uninvset_seven_earning_chart);


    }
}
