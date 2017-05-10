package com.gabo.weightless;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.gabo.weightless.Adapters.FriendsListAdapter;
import com.gabo.weightless.DB.DBHelper;
import com.gabo.weightless.DialogFragment.FriendRequestDialogFragment;
import com.gabo.weightless.DialogFragment.NewFriendDialogFragment;
import com.gabo.weightless.Objects.Category;
import com.gabo.weightless.Objects.friendRequest;

public class Friends extends AppCompatActivity implements NewFriendDialogFragment.NewFriendDialogInterface{

    private ListView lv;
    private DBHelper dbHelper;
    private String user;
    private FriendsListAdapter adapter;
    private String[] friends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        dbHelper = new DBHelper(this);

        lv = (ListView) findViewById(R.id.listView);
        Intent i = getIntent();
        user = i.getStringExtra("user");
        friends = dbHelper.getFriends(user);

        adapter = new FriendsListAdapter(friends, this);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //poner lo de abrir equipo
                String friend = friends[position];
                Intent i = new Intent(getApplicationContext(), EquipmentList.class);
                i.putExtra("user", friend);
                i.putExtra("friend", "1");
                startActivityForResult(i, 0);
            }
        });

        Button addFriendButton = (Button) findViewById(R.id.addFriendButton);
        addFriendButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addNewFriend(v);
            }
        });
        Button viewFriendRquest = (Button) findViewById(R.id.friendRequestButton);
        viewFriendRquest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                viewFriendRequest(v);
            }
        });

    }

    protected void onActivityResult(int requestedCode, int resultCode, Intent data){
        if(requestedCode == 0 && resultCode == Activity.RESULT_OK){
            updateListView();
        }
    }

    public void updateListView(){
        friends = dbHelper.getFriends(user);
        adapter = new FriendsListAdapter(friends, this);
        lv.setAdapter(adapter);
    }

    public void addNewFriend(View v){
        FragmentManager fm = getSupportFragmentManager();
        NewFriendDialogFragment df = new  NewFriendDialogFragment();
        df.show(fm, "Fragment_NewFriend");
    }
    public void viewFriendRequest(View v){
        Intent i = new Intent(this, friendRequest.class);
        i.putExtra("user", user);
        startActivityForResult(i, 0);
    }

    @Override
    public void onFinishNewFriendDialog(String friend) {
        dbHelper.addFriend(user, friend);
        Toast.makeText(this, "Sent friend request to " + friend, Toast.LENGTH_SHORT).show();
    }
}
