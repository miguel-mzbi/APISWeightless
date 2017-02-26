package com.mobiledevices.miguel.prototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CategoryActivity extends AppCompatActivity {

    TextView equipmentName;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Intent i = getIntent();
        equipmentName = (TextView) findViewById(R.id.tview_title_category);
        equipmentName.setText(i.getStringExtra("categoryName"));
    }
}
