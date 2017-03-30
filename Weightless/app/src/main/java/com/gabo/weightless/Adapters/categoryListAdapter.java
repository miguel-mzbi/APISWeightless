package com.gabo.weightless.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.gabo.weightless.DB.DBHelper;
import com.gabo.weightless.Objects.Category;
import com.gabo.weightless.R;

import java.util.ArrayList;

/**
 * Created by miguel on 15/03/17.
 */

public class CategoryListAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Category> data;
    private Activity activity;

    public CategoryListAdapter(ArrayList<Category> data, Activity activity) {
        this.data = data;
        this.activity = activity;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Category getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DBHelper db = new DBHelper(this.activity);

        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.category_row, null);
        }
        TextView name, weight;
        name = (TextView) convertView.findViewById(R.id.CategoryName);
        weight = (TextView) convertView.findViewById(R.id.CategoryWeight);

        /* To implement after items creation
        weight.setText(Integer.toString(db.getCategoryWeight(data.get(position).getID())));
        */

        String n = data.get(position).getName();
        name.setText(n);
        if(!n.equals("No categories created for this equipment")) {
            String s = Double.toString(data.get(position).getCategoryWeight());
            weight.setText(s);
        }
        return convertView;
    }
}
