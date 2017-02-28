package com.mobiledevices.miguel.prototype;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> equipments = new ArrayList<String>(); // This ArrayList will be deleted after. Should be stored in DB.
    ArrayAdapter<String> equipmentsAdapter; // This will be a cursorAdapter for DB
    Button createEquipment, openEquipment;
    EditText equipmentName;
    Spinner selectEquipment;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        equipments.add("Equipment A");
        equipments.add("Equipment B");
        equipments.add("Equipment C");
        createEquipment = (Button) findViewById(R.id.button_create_equipment);
        openEquipment = (Button) findViewById(R.id.button_open_equipment);
        equipmentName = (EditText) findViewById(R.id.tedit_equipmentName_equipment);
        selectEquipment = (Spinner) findViewById(R.id.spinner_select_equipment);
        equipmentsAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, equipments);
        equipmentsAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        selectEquipment.setAdapter(equipmentsAdapter);
    }

    public void openEquipmentClick(View v) {

        Intent i = new Intent(this, CategoryActivity.class);
        i.putExtra("categoryName", selectEquipment.getSelectedItem().toString());
        startActivity(i);
    }

    public void createEquipmentClick(View v) {

        equipments.add(equipmentName.getText().toString());
        equipmentsAdapter.notifyDataSetChanged();
    }
}
