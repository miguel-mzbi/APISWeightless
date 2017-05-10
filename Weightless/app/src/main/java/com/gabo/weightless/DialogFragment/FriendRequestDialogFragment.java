package com.gabo.weightless.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.gabo.weightless.Adapters.FriendRequestAdapter;
import com.gabo.weightless.DB.DBHelper;
import com.gabo.weightless.R;



public class FriendRequestDialogFragment extends DialogFragment implements ListView.OnClickListener{

    private ListView listView;
    private DBHelper db;
    private String user;
    private String[] friendRequests;
    private FriendRequestAdapter adapter;
    public static FriendRequestDialogFragment newInstance(String n) {

        FriendRequestDialogFragment f = new FriendRequestDialogFragment();
        Bundle args = new Bundle();
        args.putString("user", n);

        f.setArguments(args);

        return f;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Friend Request");

        LayoutInflater inflater = getActivity().getLayoutInflater();

        Bundle args = getArguments();
        user = getArguments().getString("user");
        View view = inflater.inflate(R.layout.friend_request_dialog, null);
        Log.d("view", view.toString());
        listView = (ListView) view.findViewById(R.id.listView);
        db = new DBHelper(getActivity());
        friendRequests = db.getFriendRequests(user);
        Log.d("friend request length", friendRequests.length + "");
        adapter = new FriendRequestAdapter(friendRequests, getActivity());
        Log.d("adapter", adapter.toString());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //poner lo de abrir equipo
                db.acceptFriendRequest(user, friendRequests[position]);
                FriendRequestDialogFragment.this.getDialog().cancel();
                friendRequests = db.getFriendRequests(user);
                adapter = new FriendRequestAdapter(friendRequests, getActivity());
                listView.setAdapter(adapter);
            }
        });

        /*builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        FriendRequestDialogFragment.this.getDialog().cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        FriendRequestDialogFragment.this.getDialog().cancel();
                    }
                });*/
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        Log.d("holo", "holo");
    }
}
