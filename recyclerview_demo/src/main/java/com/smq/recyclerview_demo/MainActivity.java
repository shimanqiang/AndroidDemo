package com.smq.recyclerview_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.smq.recyclerview_demo.listview.ListViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_listview:
                Intent listviewIntent = new Intent(this, ListViewActivity.class);
                startActivity(listviewIntent);
                break;

            case R.id.btn_gridview:
                break;
        }
    }
}
