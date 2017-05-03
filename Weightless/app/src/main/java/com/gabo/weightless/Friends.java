package com.gabo.weightless;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.gabo.weightless.DialogFragment.NewFriendDialogFragment;

public class Friends extends AppCompatActivity implements NewFriendDialogFragment.NewFriendDialogInterface{

    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        lv = (ListView) findViewById(R.id.listView);

        Button addFriendButton = (Button) findViewById(R.id.addFriendButton);
        addFriendButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                addNewFriend(v);
            }
        });
    }

    public void updateListView(){

    }

    public void addNewFriend(View v){
        FragmentManager fm = getSupportFragmentManager();
        NewFriendDialogFragment df = new  NewFriendDialogFragment();
        df.show(fm, "Fragment_NewFriend");
    }

    @Override
    public void onFinishNewFriendDialog(String friend) {

    }
}
