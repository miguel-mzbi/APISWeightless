package com.gabo.weightless;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.gabo.weightless.Adapters.categoryListAdapter;
import com.gabo.weightless.DialogFragment.NewCategoryDialogFragment;
import com.gabo.weightless.Objects.Category;

import java.util.ArrayList;

public class CategoryList extends AppCompatActivity implements NewCategoryDialogFragment.CategoryDialogListener{

    private DBHelper db;
    private ListView categoryLV;
    private String equipment;
    private int equipmentID;
    private categoryListAdapter adapter;
    private ArrayList<Category> data;
    private TextView title;
    private Button createCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        Intent i = getIntent();
        equipment = i.getStringExtra("equipment");
        equipmentID = i.getIntExtra("eID", 0);
        title = (TextView) findViewById(R.id.CategoryTitle);
        String titleText = "Equipment: " + equipment;
        title.setText(titleText);

        db = new DBHelper(this);
        data = db.getCategories(equipmentID);

        categoryLV = (ListView) findViewById(R.id.categoryListView);
        adapter = new categoryListAdapter(data, this);

        categoryLV.setAdapter(adapter);

    }

    public void newCategoryClicked(View v) {

        FragmentManager fm = getSupportFragmentManager();
        NewCategoryDialogFragment df = new NewCategoryDialogFragment();
        df.show(fm, "Fragment_NewCategory");
    }

    @Override
    public void onFinishCategoryDialog(String inputText) {

        db.createCategory(equipmentID, inputText);
        adapter = new categoryListAdapter(db.getCategories(equipmentID), this);
        categoryLV.setAdapter(adapter);
    }
}
