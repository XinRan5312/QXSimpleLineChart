package com.ran.qxlinechart.softwidow;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ran.qxlinechart.R;

public class QXMainKeyBordActivity extends QXKeyBordBaseActvity {

    private EditText mPhone;
    private View mBtnCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qxmain_key_bord);
        mPhone = (EditText) findViewById(R.id.et_phone);
        mBtnCode = findViewById(R.id.btn_code);
    }

    @Override
    public View[] filterViewByIds() {
        View[] views = {mPhone, mBtnCode};
        return views;
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        int[] ids = {R.id.et_phone, R.id.et_check_code, R.id.et_city_code};
        return ids;
    }

}
