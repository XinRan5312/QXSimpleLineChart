package com.ran.qxlinechart;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by houqixin on 2017/3/31.
 */
public class MSCurrentTradeInActivity extends MSBaseActivity {
    private TextView mCurrentBalanceTv;
    private EditText mInvestValueEt;
    private TextView mTradeInMaxTv;
    private TextView mTradeInSure;
    private TextView mTradeInEarnHintTv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_trade_in);
        mCurrentBalanceTv=$(R.id.current_trade_in_balance_value);
        mInvestValueEt=$(R.id.current_trade_in_value);
        mTradeInMaxTv=$(R.id.current_trade_in_max);
        mTradeInSure=$(R.id.current_trade_in_sure);
        mTradeInEarnHintTv=$(R.id.current_trade_in_earn_hint);


    }
}
