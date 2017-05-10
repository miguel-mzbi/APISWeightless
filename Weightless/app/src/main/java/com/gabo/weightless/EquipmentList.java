package com.gabo.weightless;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.gabo.weightless.Adapters.EquipmentListAdapter;
import com.gabo.weightless.DB.DBHelper;
import com.gabo.weightless.Objects.Equipment;

import java.util.ArrayList;

public class EquipmentList extends AppCompatActivity {
    private String user;
    private ListView listView;
    private ArrayList<Equipment> data;
    private DBHelper db;
    private EquipmentListAdapter adapter;
    private boolean isFriend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_list);

        Intent i = getIntent();
        user = i.getStringExtra("user");
        if(i.getStringExtra("friend") == null){
            isFriend = false;
        }else{
            isFriend = true;
        }

        db = new DBHelper(this);
        data = db.getEquipment(user);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new EquipmentListAdapter(data, this);

        listView.setAdapter(adapter);

        Button createEquipment = (Button) findViewById(R.id.createEquipmentButton);

            createEquipment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createEquipment(v);
                }
            });



        Button friendsButton = (Button) findViewById(R.id.friendsButton);

        friendsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                friends(v);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Equipment e = (Equipment) adapter.getItem(position);
                Intent i = new Intent(getApplicationContext(), CategoryList.class);
                i.putExtra("equipment", e.getName());
                i.putExtra("eID", e.getId());
                if(isFriend)
                    i.putExtra("friend", "1");
                startActivity(i);
            }
        });
        if(!isFriend){
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    db.removeEquipment((int) id);
                    updateListView();
                    return true;
                }
            });
        }

        ViewGroup layout = (ViewGroup) createEquipment.getParent();
        if(null!=layout && isFriend) {
            layout.removeView(createEquipment);
            layout.removeView(friendsButton);
        }

    }

    public void updateListView() {
        adapter = new EquipmentListAdapter(db.getEquipment(user), this);
        listView.setAdapter(adapter);
    }

    public void createEquipment(View v){
        Intent i = new Intent(this, CreateEquipment.class);
        i.putExtra("user",user);
        startActivityForResult(i, 0);
    }

    protected void onActivityResult(int requestedCode, int resultCode, Intent data){
        if(requestedCode == 0 && resultCode == Activity.RESULT_OK){
            updateListView();
        }
    }

    public void friends(View v){
        Intent i = new Intent(this, Friends.class);
        i.putExtra("user",user);
        startActivityForResult(i, 0);
    }
}
