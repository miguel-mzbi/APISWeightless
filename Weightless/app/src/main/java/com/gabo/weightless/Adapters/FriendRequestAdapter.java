package com.gabo.weightless.Adapters;

import android.app.Activity;
import android.util.Log;
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

public class FriendRequestAdapter extends BaseAdapter implements ListAdapter{
    private String[] friendRequests;
    private Activity activity;

    public FriendRequestAdapter(String[] friendRequests, Activity activity){
        this.friendRequests = friendRequests;
        this.activity = activity;
        Log.d("a", "a");
    }


    @Override
    public int getCount() {
        Log.d("count", friendRequests.length + ""); return friendRequests.length;
    }

    @Override
    public Object getItem(int position) {
        Log.d("item", friendRequests[position]); return friendRequests[position];
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
        Log.d("da", friendRequests[position]);
        friendsName.setText(friendRequests[position]);

        return convertView;
    }
}
