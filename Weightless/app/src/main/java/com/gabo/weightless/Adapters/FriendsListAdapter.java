package com.gabo.weightless.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.gabo.weightless.Objects.Equipment;
import com.gabo.weightless.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by LIC on 08/03/2017.
 */

public class FriendsListAdapter extends BaseAdapter implements ListAdapter{
    private String[] friends;
    private Activity activity;

    public FriendsListAdapter(String[] friends, Activity activity){
        this.friends = friends;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return friends.length;
    }

    @Override
    public Object getItem(int position) {
        return friends[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.friends_row, null);
        }
        TextView friendsName;
        friendsName = (TextView) convertView.findViewById(R.id.friendsName);
        friendsName.setText(friends[position]);

        return convertView;
    }
}
