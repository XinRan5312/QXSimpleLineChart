package com.ran.qxlinechart;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by houqixin on 2017/3/31.
 */
public class MSCurrentDetailInvestedActivity extends MSBaseActivity {
    private TextView mYestodayEarnedTv;
    private TextView mEarnedAllTv;
    private TextView mCurrentAssetTv;
    private TextView mInvestTv;
    private TextView mCommingMoneyTv;
    private TextView mEarnedNotCloseTv;
    private ImageView mCurrentVulueEyeImg;
    private QXLineView mLineChart;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_detail_invest);
        mYestodayEarnedTv=$(R.id.current_yestoday_earned_value);
        mEarnedAllTv=$(R.id.current_earned_all_value);
        mLineChart=$(R.id.invested_seven_earning_chart);

        mCurrentAssetTv=$(R.id.current_all_asset_value);
        mEarnedNotCloseTv=$(R.id.current_earned_not_close);
        mCommingMoneyTv=$(R.id.current_comming_value);
        mInvestTv=$(R.id.current_invested_value);
        mCurrentVulueEyeImg=$(R.id.current_all_asset_eye);
    }
}
