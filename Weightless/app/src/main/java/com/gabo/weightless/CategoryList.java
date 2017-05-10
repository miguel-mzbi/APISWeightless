package com.gabo.weightless;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.gabo.weightless.Adapters.CategoryListAdapter;
import com.gabo.weightless.Adapters.EquipmentListAdapter;
import com.gabo.weightless.DB.DBHelper;
import com.gabo.weightless.DialogFragment.NewCategoryDialogFragment;
import com.gabo.weightless.Objects.Category;

import java.util.ArrayList;

public class CategoryList extends AppCompatActivity implements NewCategoryDialogFragment.CategoryDialogListener{

    private DBHelper db;
    private ListView categoryLV;
    private String equipment;
    private int equipmentID;
    private CategoryListAdapter adapter;
    private ArrayList<Category> data;
    private TextView title;
    private boolean isFriend;

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

        if(i.getStringExtra("friend") == null){
            isFriend = false;
        }else{
            isFriend = true;
        }

        db = new DBHelper(this);
        data = db.getCategories(equipmentID);

        categoryLV = (ListView) findViewById(R.id.categoryListView);
        adapter = new CategoryListAdapter(data, this);

        categoryLV.setAdapter(adapter);
        categoryLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category c = adapter.getItem(position);
                Intent i = new Intent(getApplicationContext(), ItemList.class);
                i.putExtra("categoryName", c.getName());
                i.putExtra("categoryID", c.getID());
                if(isFriend)
                    i.putExtra("friend", "1");
                startActivityForResult(i, 0);
            }
        });
        if(!isFriend){
            categoryLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    db.removeCategory((int) id);
                    updateListView();
                    return true;
                }
            });
        }

        Button addCategoryButton = (Button) findViewById(R.id.addCategoryButton);
        ViewGroup layout = (ViewGroup) addCategoryButton.getParent();
        if(null!=layout && isFriend) {
            layout.removeView(addCategoryButton);
        }
     }

    public void newCategoryClicked(View v) {

        FragmentManager fm = getSupportFragmentManager();
        NewCategoryDialogFragment df = new NewCategoryDialogFragment();
        df.show(fm, "Fragment_NewCategory");
    }

    public void updateListView() {

        data = db.getCategories(equipmentID);
        adapter = new CategoryListAdapter(data, this);
        categoryLV.setAdapter(adapter);
    }

    @Override
    public void onFinishCategoryDialog(String inputText) {

        db.createCategory(equipmentID, inputText);
        this.updateListView();
    }

    protected void onActivityResult(int requestedCode, int resultCode, Intent data){

        if(requestedCode == 0 && resultCode == Activity.RESULT_OK){
            this.updateListView();
        }
    }
}
