package com.mobiledevices.miguel.weightless;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobiledevices.miguel.weightless.DBHelpers.WeightlessDB;

public class MainActivity extends AppCompatActivity {

    WeightlessDB DB;
    Button eb, cb, ib;
    TextView ev, cv, iv;
    EditText ce, ie1, ie2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eb = (Button) findViewById(R.id.button);
        cb = (Button) findViewById(R.id.button2);
        ib = (Button) findViewById(R.id.button3);
        ev = (TextView) findViewById(R.id.textView);
        cv = (TextView) findViewById(R.id.textView2);
        iv = (TextView) findViewById(R.id.textView3);
        ce = (EditText) findViewById(R.id.editText);
        ie1 = (EditText) findViewById(R.id.editText2);
        ie2 = (EditText) findViewById(R.id.editText3);

        DB = new WeightlessDB(getApplicationContext());

        DB.newEquipment("Equipo 1"); // 1
        DB.newEquipment("Equipo 2"); // 2

        DB.newCategory(1, "Categoria 1"); // 1
        DB.newCategory(1, "Categoria 2"); // 2
        DB.newCategory(1, "Categoria 3"); // 3
        DB.newCategory(1, "Categoria 4"); // 4
        DB.newCategory(2, "Categoria 99"); // 5
        DB.newCategory(2, "Categoria 98"); // 6
        DB.newCategory(2, "Categoria 97"); // 7

        DB.newItem(1, 1, "item1", "desc", 2, 3);
        DB.newItem(1, 1, "item2", "desc", 1, 1);
        DB.newItem(1, 1, "item3", "desc", 5, 1);
        DB.newItem(1, 2, "item10", "desc", 1, 10);
        DB.newItem(1, 2, "item11", "desc", 9, 10);
        DB.newItem(1, 2, "item12", "desc", 7, 10);
        DB.newItem(2, 5, "item50", "desc", 5, 4);
        DB.newItem(2, 6, "item60", "desc", 1, 2);

    }

    public void clickEquipment(View v) {

        Cursor c = DB.getEquipments();
        String out = "";
        while(c.moveToNext()) {
            out += c.getInt(0) + " - ";
            out += c.getString(1) + " | ";
        }
        ev.setText(out);
    }

    public void clickCategory(View v) {

        Cursor c = DB.getCategories(ce.getText().toString());
        String out = "";
        while(c.moveToNext()) {
            out += c.getInt(0) + " - ";
            out += c.getString(1) + " | ";
        }
        cv.setText(out);
    }

    public void clickItem(View v) {

        Cursor c = DB.getItems(ie1.getText().toString(), ie2.getText().toString());
        String out = "";
        while(c.moveToNext()) {
            out += c.getInt(0) + " - ";
            out += c.getString(1) + " - ";
            out += c.getString(2) + " - ";
            out += c.getInt(3) + " - ";
            out += c.getInt(4) + " | ";
        }
        iv.setText(out);
    }
}
