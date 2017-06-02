package com.ran.qxlinechart;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;

/**
 * Created by houqixin on 2017/3/31.
 */
public class MSBaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected <V extends View> V $(@IdRes int resId) {
        return (V) findViewById(resId);
    }
}
