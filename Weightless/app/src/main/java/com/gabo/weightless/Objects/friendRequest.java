package com.gabo.weightless.Objects;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gabo.weightless.Adapters.FriendRequestAdapter;
import com.gabo.weightless.DB.DBHelper;
import com.gabo.weightless.DialogFragment.FriendRequestDialogFragment;
import com.gabo.weightless.R;

public class friendRequest extends AppCompatActivity {
    private ListView listView;
    private String user;
    private DBHelper db;
    private String[] friendRequests;
    private FriendRequestAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        listView = (ListView) findViewById(R.id.listView);

        Intent i  = getIntent();
        user = i.getStringExtra("user");

        db = new DBHelper(this);

        friendRequests = db.getFriendRequests(user);

        adapter = new FriendRequestAdapter(friendRequests, this);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //poner lo de abrir equipo
                db.acceptFriendRequest(user, friendRequests[position]);
                Intent i = new Intent();
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
    }
}
