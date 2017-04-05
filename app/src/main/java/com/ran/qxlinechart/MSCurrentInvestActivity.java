package com.ran.qxlinechart;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by houqixin on 2017/3/31.
 */
public class MSCurrentInvestActivity extends MSBaseActivity {
    private TextView mCurrentBalanceTv;
    private EditText mInvestValueEt;
    private TextView mInvestProtocolTv;
    private TextView mInvestSure;
    private CheckBox mInvestProtocolCb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_invest);
        mCurrentBalanceTv=$(R.id.current_invest_balance_value);
        mInvestValueEt=$(R.id.current_invest_value);
        mInvestProtocolTv=$(R.id.current_invest_protocol);
        mInvestSure=$(R.id.current_invest_sure);
        mInvestProtocolCb=$(R.id.current_invest_protocol_cb);

    }
}
