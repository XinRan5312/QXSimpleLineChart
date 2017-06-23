package com.ran.qxlinechart.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import com.ran.qxlinechart.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by houqixin on 2017/6/12.
 */
public class QXExpanableAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> groupTitle; //子项是一个map，key是group的id，每一个group对应一个ChildItem的
    private HashMap<Integer, List<String>> mChildrenMap;// childMap

    public QXExpanableAdapter(Context mContext, List<String> groupTitle,HashMap<Integer, List<String>> map) {
        this.mContext = mContext;
        this.groupTitle = groupTitle;
        this.mChildrenMap=map;
    }

    @Override
    public int getGroupCount() {
        Log.e("sizeGroonp:",""+groupTitle.size());
        return groupTitle.size();
    }

    @Override
    public int getChildrenCount(int i) {
        Log.e("sizechildren:",""+mChildrenMap.get(i).size());
        return mChildrenMap.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return groupTitle.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mChildrenMap.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getGroupType(int groupPosition) {
        return super.getGroupType(groupPosition);
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return super.getChildType(groupPosition, childPosition);
    }

    @Override
    public int getChildTypeCount() {
        return super.getChildTypeCount();
    }

    @Override
    public int getGroupTypeCount() {
        return 3;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        Log.e("ee:","ggg"+R.layout.home_new_user_loan);
        int layId=-1;
        if(i==0){
          layId=R.layout.group_one;

        }else if(i==1){
            layId=R.layout.group_two;
        }else if(i==2){
            layId=R.layout.group_three;
        }
        if(layId!=-1){
            view=View.inflate(mContext,layId,null);
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        Log.e("ee:","2222"+R.layout.home_new_user_loan);
            if (i == 0) {
                view = View.inflate(mContext, R.layout.home_new_user_loan, null);
                Log.e("ee:",""+R.layout.home_new_user_loan);

            } else if (i == 1) {
                view = View.inflate(mContext, R.layout.home_recommander_item, null);
            } else if (i == 2) {
                view = View.inflate(mContext, R.layout.home_recommander_item, null);
            }

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
