package com.mobiledevices.miguel.prototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    TextView equipmentName;
    Button addCategory;
    ListView itemsList;
    ArrayList<String> categories = new ArrayList<String>(); // This ArrayList will be deleted after. Should be stored in DB.
    ArrayAdapter<String> categoriesAdapter; // This will be a cursorAdapter for DB

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Intent i = getIntent();
        equipmentName = (TextView) findViewById(R.id.tview_title_category);
        equipmentName.setText(i.getStringExtra("categoryName"));
        addCategory = (Button) findViewById(R.id.button_addCategory_category);
        itemsList = (ListView) findViewById(R.id.listView_categories_category);
        categories.add("Sleep");
        categories.add("Water & Food");
        categories.add("Shoes");
        categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
        itemsList.setAdapter(categoriesAdapter);
    }
}
