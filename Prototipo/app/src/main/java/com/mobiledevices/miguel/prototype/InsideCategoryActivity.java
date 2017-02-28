package com.mobiledevices.miguel.prototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.mobiledevices.miguel.prototype.adapters.Item;
import com.mobiledevices.miguel.prototype.adapters.ItemAdapter;

import java.util.ArrayList;

public class InsideCategoryActivity extends AppCompatActivity {

    TextView categoryTitle;
    ListView itemList;
    ItemAdapter itemAdapter;
    ArrayList<Item> items = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_category);

        Intent i = getIntent();
        items.add(new Item("Klymit SB", "Sleeping Bag w/Isolation", 1, 710, "g"));
        items.add(new Item("Klymit SB", "Sleeping Bag", 1, 1400, "g"));
        items.add(new Item("Mylar", "Heat Retention", 1, 150, "g"));

        categoryTitle = (TextView) findViewById(R.id.tview_title_inside);
        categoryTitle.setText(i.getStringExtra("categoryName"));
        itemList = (ListView) findViewById(R.id.listView_items_inside);
        itemAdapter = new ItemAdapter(this, R.layout.item_row, items);
        itemList.setAdapter(itemAdapter);

    }
}
