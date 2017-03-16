package com.gabo.weightless;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.gabo.weightless.Adapters.equipmentListAdapter;
import com.gabo.weightless.Objects.Equipment;

import java.util.ArrayList;

public class EquipmentList extends AppCompatActivity {
    private String user;
    private ListView listView;
    private ArrayList<Equipment> data;
    private DBHelper db;
    private equipmentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_list);

        Intent i = getIntent();
        user = i.getStringExtra("user");

        db = new DBHelper(this);
        data = db.getEquipment(user);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new equipmentListAdapter(data, this);

        listView.setAdapter(adapter);

        Button createEquipment = (Button) findViewById(R.id.createEquipmentButton);

        createEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEquipment(v);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Equipment e = (Equipment) adapter.getItem(position);
                Intent i = new Intent(getApplicationContext(), CategoryList.class);
                i.putExtra("equipment", e.getName());
                i.putExtra("eID", e.getId());
                startActivity(i);
            }
        });
    }

    public void createEquipment(View v){
        Intent i = new Intent(this, createEquipment.class);
        i.putExtra("user",user);
        startActivityForResult(i, 0);
    }

    protected void onActivityResult(int requestedCode, int resultCode, Intent data){
        if(requestedCode == 0 && resultCode == Activity.RESULT_OK){

            adapter = new equipmentListAdapter(db.getEquipment(user), this);

            listView.setAdapter(adapter);
        }
    }
}
