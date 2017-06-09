package com.ran.qxlinechart.car.utils;

import android.content.Context;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houqixin on 2017/6/5.
 */
public class QXPoiSearchTask implements PoiSearch.OnPoiSearchListener {

    private Context mContext;

    private RecomandAdapter mRecommandAdapter;
    private PoiSearch.Query query;

    public QXPoiSearchTask(Context context, RecomandAdapter recommandAdapter) {
        this.mContext = context;
        this.mRecommandAdapter = recommandAdapter;
    }

    public void startSearch(String keyWord, String city) {

        query=new PoiSearch.Query(keyWord,"餐饮",city);

        query.setPageSize(10);
        query.setPageNum(1);

        PoiSearch poiSearch=new PoiSearch(mContext,query);

        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
      if(poiResult!=null&&i== AMapException.CODE_AMAP_SUCCESS){

          if(query.equals(poiResult.getQuery())){//保证是统一个query

              ArrayList<PoiItem> listItem = poiResult.getPois();

              if(listItem==null||listItem.isEmpty()){

                  return;
              }
              List<PositionEntity> entities=new ArrayList<PositionEntity>();
              for(PoiItem poiItem:listItem){
                  PositionEntity entity=new PositionEntity(poiItem.getLatLonPoint().getLatitude(),
                          poiItem.getLatLonPoint().getLongitude(),poiItem.getTitle()
                          ,poiItem.getCityName(),"","");
                  entities.add(entity);
              }
              mRecommandAdapter.setPositionEntities(entities);
              mRecommandAdapter.notifyDataSetChanged();

          }
      }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
