package com.gabo.weightless.Adapters;

import android.app.Activity;
import android.content.ClipData;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.gabo.weightless.Objects.Item;
import com.gabo.weightless.R;

import java.util.ArrayList;

/**
 * Created by MiguelAngel on 29/03/2017.
 */

public class ItemsListAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Item> data;
    private Activity activity;

    public ItemsListAdapter(ArrayList<Item> d, Activity a) {
        this.activity = a;
        this.data = d;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Item getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.item_row, null);
        }

        TextView name, weight, qty;

        name = (TextView) convertView.findViewById(R.id.itemName_tv);
        String n = this.getItem(position).getName();
        name.setText(n);

        if(!n.equals("No items created for this category")) {
            qty = (TextView) convertView.findViewById(R.id.itemQty_tv);
            weight = (TextView) convertView.findViewById(R.id.itemWght_tv);

            String s = "Weight: " + Double.toString(this.getItem(position).getWeight());
            qty.setText(s);
            s = "Quantity: " + Integer.toString(this.getItem(position).getQty());
            weight.setText(s);
        }

        return convertView;
    }
}
