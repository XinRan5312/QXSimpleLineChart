package com.ran.qxlinechart;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.View;

/**
 * Created by houqixin on 2017/3/31.
 */
public class MSBaseActivity extends Activity {
    protected <V extends View> V $(@IdRes int resId) {
        return (V) findViewById(resId);
    }
}
