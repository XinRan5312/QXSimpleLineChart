package com.ran.qxlinechart.car.utils;

import android.content.Context;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houqixin on 2017/6/6.
 * Android高德地图使用之地点关键词的输入提示-InputTips
 */
public class QXInputipsTask implements Inputtips.InputtipsListener {

    private Inputtips mInputtips;

    private static RecomandAdapter mAdapter;

    private static QXInputipsTask mInputTipTask;

    private QXInputipsTask() {

    }

    public static QXInputipsTask getInstance(RecomandAdapter adapter) {
        if (mInputTipTask == null) {
            mInputTipTask = new QXInputipsTask();
        }
        //单例情况，多次进入DestinationActivity传进来的RecomandAdapter对象会不是同一个
        mAdapter = adapter;
        mInputTipTask.setRecommandAdapter(adapter);
        return mInputTipTask;
    }

    public void startSearchTips(Context context, String keyWords, String city) {
        InputtipsQuery query = new InputtipsQuery(keyWords, city);

        mInputtips = new Inputtips(context, query);

        mInputtips.setInputtipsListener(this);
    }

    public void setRecommandAdapter(RecomandAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void onGetInputtips(List<Tip> tips, int resultCode) {
        if (resultCode == AMapException.CODE_AMAP_SUCCESS && tips != null) {
            ArrayList<PositionEntity> positions = new ArrayList<PositionEntity>();
            for (Tip tip : tips) {

                if (tip.getPoint() != null) {

                    positions.add(new PositionEntity(tip.getPoint().getLatitude(), tip.getPoint().getLongitude(), tip.getName(), tip.getAdcode(), "", ""));
                } else {
                    positions.add(new PositionEntity(0, 0, tip.getName(), tip.getAdcode(), "", ""));
                }

            }
            mAdapter.setPositionEntities(positions);
            mAdapter.notifyDataSetChanged();
        }
    }
}
