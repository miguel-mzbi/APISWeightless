package com.gabo.weightless;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gabo.weightless.Adapters.ItemsListAdapter;
import com.gabo.weightless.DB.DBHelper;
import com.gabo.weightless.DialogFragment.NewCategoryDialogFragment;
import com.gabo.weightless.DialogFragment.NewItemDialogFragment;
import com.gabo.weightless.Objects.Item;

import java.util.ArrayList;

public class ItemList extends AppCompatActivity implements NewItemDialogFragment.ItemDialogInterface{

    private String categoryName;
    private int categoryID;
    private TextView title, totalWeight;
    private ListView lv;
    private ItemsListAdapter adapter;
    private ArrayList<Item> data;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Intent i = getIntent();
        this.categoryID = i.getIntExtra("categoryID", 0);
        this.categoryName = i.getStringExtra("categoryName");
        this.title = (TextView) findViewById(R.id.itemsCategory_tv);
        String t = "Category: " + this.categoryName;
        this.title.setText(t);

        this.db = new DBHelper(getApplicationContext());
        this.data = db.getItems(categoryID);

        String w = "Total Category Weight: " +  Double.toString(db.getCategoryWeight(categoryID));
        this.totalWeight = (TextView) findViewById(R.id.itemTotalWeighOfCategory_tv);
        this.totalWeight.setText(w);

        this.lv = (ListView) findViewById(R.id.items_lv);
        this.adapter = new ItemsListAdapter(data, this);
        this.lv.setAdapter(this.adapter);

        this.lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                db.removeItem((int) id);
                updateListView();
                return true;
            }
        });
        this.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String n = adapter.getItem(position).getName();
                Double w = adapter.getItem(position).getWeight();
                int q = adapter.getItem(position).getQty();
                db.removeItem((int) id);
                FragmentManager fm = getSupportFragmentManager();
                NewItemDialogFragment df = NewItemDialogFragment.newInstance(n, w, q, true);
                df.show(fm, "Fragment_NewItem");
            }
        });
    }

    public void createItem(View v) {

        FragmentManager fm = getSupportFragmentManager();
        NewItemDialogFragment df = NewItemDialogFragment.newInstance("", 0, 0, false);
        df.show(fm, "Fragment_NewItem");
    }

    public void updateListView() {

        this.data = this.db.getItems(this.categoryID);
        this.adapter = new ItemsListAdapter(this.data, this);
        this.lv.setAdapter(this.adapter);
        String w = "Total Category Weight: " +  Double.toString(db.getCategoryWeight(categoryID));
        this.totalWeight.setText(w);
    }


    @Override
    public void onFinishItemDialog(String name, double weight, int quantity) {

        this.db.createItem(this.categoryID, name, weight, quantity);
        this.updateListView();
    }

    @Override
    public void onBackPressed() {

        Intent i  = new Intent();
        setResult(Activity.RESULT_OK, i);
        finish();
    }
}
