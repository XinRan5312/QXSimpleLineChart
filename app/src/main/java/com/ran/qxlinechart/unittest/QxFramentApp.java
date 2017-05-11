package com.ran.qxlinechart.unittest;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ran.qxlinechart.R;

/**
 * Created by houqixin on 2017/5/10.
 */
public class QxFramentApp extends Fragment {
    public String name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name="create";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        name="createView";
        return inflater.inflate(R.layout.activity_current_detail_invest,container,false);
    }

    @Override
    public void onAttach(Context context) {
        name="attachView";
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        name="resume";
    }

    @Override
    public void onPause() {
        super.onPause();
        name="pause";
    }

    @Override
    public void onStop() {
        super.onStop();
        name="stop";
    }

    @Override
    public void onStart() {
        super.onStart();
        name="start";
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        name="destroy";
    }
}
