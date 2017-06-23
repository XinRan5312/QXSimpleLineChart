package com.ran.qxlinechart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.ran.qxlinechart.adapter.QXExpanableAdapter;
import com.ran.qxlinechart.adapter.QXSimpleExpanbleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QXExpanableListViewActivity extends AppCompatActivity {

    private QXCicleLineView cicleLineView;
    private ExpandableListView expandableListView;
    private QXSimpleExpanbleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qxexpanable_list_view);
        expandableListView= (ExpandableListView) findViewById(R.id.expand_activities_listview);

        List<String> title=new ArrayList<>();

        title.add("jack");
        title.add("mack");
        title.add("join");
        HashMap<String,List<String>> map=new HashMap<>();

       map.put("jack",title);
        map.put("mack",title);
        map.put("join",title);
        adapter=new QXSimpleExpanbleAdapter(this,title,map);

        expandableListView.setAdapter(adapter);


    }
}
