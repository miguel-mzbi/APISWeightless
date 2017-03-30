package com.gabo.weightless.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.gabo.weightless.Objects.Equipment;
import com.gabo.weightless.R;

import java.util.ArrayList;

/**
 * Created by LIC on 08/03/2017.
 */

public class EquipmentListAdapter extends BaseAdapter implements ListAdapter{
    private ArrayList<Equipment> data;
    private Activity activity;

    public EquipmentListAdapter(ArrayList<Equipment> data, Activity activity){
        this.data = data;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.equipment_row, null);
        }
        TextView name, owner;
        name = (TextView) convertView.findViewById(R.id.title);
        owner = (TextView) convertView.findViewById(R.id.owner);
        name.setText(data.get(position).getName());
        owner.setText(data.get(position).getOwner());

        return convertView;
    }
}
