package com.mobiledevices.miguel.prototype.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobiledevices.miguel.prototype.R;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, int textViewResourceId) {

        super(context, textViewResourceId);
    }

    public ItemAdapter(Context context, int resource, List<Item> items) {

        super(context, resource, items);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v == null) {

            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.item_row, null);
        }

        Item p = getItem(position);
        if(p != null) {
            TextView itemName = (TextView) v.findViewById(R.id.tview_itemName_itemRow);
            TextView itemDesc = (TextView) v.findViewById(R.id.tview_itemDesc_itemRow);
            TextView itemQty = (TextView) v.findViewById(R.id.tview_itemQty_itemRow);
            TextView itemWeight = (TextView) v.findViewById(R.id.tview_itemWeight_itemRow);
            TextView itemMeasure = (TextView) v.findViewById(R.id.tview_itemMeasure_itemRow);

            if(itemName != null) {

                itemName.setText(p.getName());
            }

            if(itemDesc != null) {

                itemDesc.setText(p.getDesc());
            }

            if(itemQty != null) {

                itemQty.setText(p.getQty());
            }

            if(itemWeight != null) {

                itemWeight.setText(p.getWeight());
            }

            if(itemMeasure != null) {

                itemMeasure.setText(p.getMeasure());
            }
        }

        return v;
    }
}
