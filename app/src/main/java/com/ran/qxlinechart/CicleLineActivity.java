package com.ran.qxlinechart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CicleLineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cicle_line);

        QXCicleLineView cicleLineView= (QXCicleLineView) findViewById(R.id.cicle_line);

        cicleLineView.setValue(78);



    }
}
