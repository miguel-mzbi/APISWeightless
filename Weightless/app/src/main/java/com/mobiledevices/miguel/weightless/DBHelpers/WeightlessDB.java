package com.mobiledevices.miguel.weightless.DBHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by miguel on 6/03/17.
 */

public class WeightlessDB extends SQLiteOpenHelper{

    private static final String DB_NAME = "WEIGHTLESS_DB";
    private static final String TABLE_EQUIPMENT = "EQUIPMENT";
    private static final String EQUIPMENT_ID_EQUIPMENT = "ID_EQUIPMENT";
    private static final String EQUIPMENT_NAME = "NAME";
    private static final String TABLE_CATEGORY = "CATEGORY";
    private static final String CATEGORY_ID_EQUIPMENT = "ID_EQUIPMENT";
    private static final String CATEGORY_ID_CATEGORY = "ID_CATEGORY";
    private static final String CATEGORY_NAME = "NAME";
    private static final String TABLE_ITEM = "ITEM";
    private static final String ITEM_ID_EQUIPMENT = "ID_EQUIPMENT";
    private static final String ITEM_ID_CATEGORY = "ID_CATEGORY";
    private static final String ITEM_ID_ITEM = "ID_ITEM";
    private static final String ITEM_NAME = "NAME";
    private static final String ITEM_DESCRIPTION = "DESCRIPTION";
    private static final String ITEM_QUANTITY = "QUANTITY";
    private static final String ITEM_WEIGHT = "WEIGHT";
    private static int DB_VERSION = 1;

    public WeightlessDB(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createEquipment = "CREATE TABLE " + TABLE_EQUIPMENT + "(" +
                                 EQUIPMENT_ID_EQUIPMENT + " INTEGER PRIMARY KEY, " +
                                 EQUIPMENT_NAME + " TEXT);";
        String createCategory = "CREATE TABLE " + TABLE_CATEGORY + "(" +
                                CATEGORY_ID_EQUIPMENT + " INTEGER, " +
                                CATEGORY_ID_CATEGORY + " INTEGER PRIMARY KEY, " +
                                CATEGORY_NAME + " TEXT, " +
                                "FOREIGN KEY(" + CATEGORY_ID_EQUIPMENT + ") " + "REFERENCES " + TABLE_EQUIPMENT + "(" + EQUIPMENT_ID_EQUIPMENT + "));";
        String createItem = "CREATE TABLE " + TABLE_ITEM + "(" +
                            ITEM_ID_EQUIPMENT + " INTEGER, " +
                            ITEM_ID_CATEGORY + " INTEGER, " +
                            ITEM_ID_ITEM + " INTEGER PRIMARY KEY, " +
                            ITEM_NAME + " TEXT, " +
                            ITEM_DESCRIPTION + " TEXT, " +
                            ITEM_QUANTITY + " INTEGER, " +
                            ITEM_WEIGHT + " INTEGER, " +
                            "FOREIGN KEY(" + ITEM_ID_EQUIPMENT + ") " + "REFERENCES " + TABLE_CATEGORY + "(" + CATEGORY_ID_EQUIPMENT + "), " +
                            "FOREIGN KEY(" + ITEM_ID_CATEGORY + ") " + "REFERENCES " + TABLE_CATEGORY + "(" + CATEGORY_ID_CATEGORY + "));";
        db.execSQL(createEquipment);
        db.execSQL(createCategory);
        db.execSQL(createItem);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String[] tablesToDrop = {TABLE_ITEM, TABLE_CATEGORY, TABLE_EQUIPMENT};
        db.execSQL("DROP TABLE IF EXIST ?", tablesToDrop);
        this.onCreate(db);
    }

    public void newEquipment(String name) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(EQUIPMENT_NAME, name);
        db.insert(TABLE_EQUIPMENT, null, content);
    }

    public void newCategory(int equipmentID, String name) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(CATEGORY_ID_EQUIPMENT, equipmentID);
        content.put(CATEGORY_NAME, name);
        db.insert(TABLE_CATEGORY, null, content);
    }

    public void newItem(int equipmentID, int categoryID, String name, String description, int quantity, int weight) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(ITEM_ID_EQUIPMENT, equipmentID);
        content.put(ITEM_ID_CATEGORY, categoryID);
        content.put(ITEM_NAME, name);
        content.put(ITEM_DESCRIPTION, description);
        content.put(ITEM_QUANTITY, quantity);
        content.put(ITEM_WEIGHT, weight);
        db.insert(TABLE_ITEM, null, content);
    }

    public Cursor getEquipments() {

        SQLiteDatabase db = getReadableDatabase();
        String orderBy = EQUIPMENT_ID_EQUIPMENT + " ASC";
        return db.query(TABLE_EQUIPMENT, null, null, null, null, null, orderBy, null);
    }

    public Cursor getCategories(String equipmentID) {

        SQLiteDatabase db = getReadableDatabase();
        String columns[] = {CATEGORY_ID_CATEGORY, CATEGORY_NAME};
        String clause = CATEGORY_ID_EQUIPMENT + " = ?";
        String clauseArgs[] = {equipmentID};
        String orderBy = CATEGORY_ID_EQUIPMENT + " ASC";
        return db.query(TABLE_CATEGORY, columns, clause, clauseArgs, null, null, orderBy, null);
    }

    public Cursor getItems(String equipmentID, String categoryID) {

        SQLiteDatabase db = getReadableDatabase();
        String columns[] = {ITEM_ID_ITEM, ITEM_NAME, ITEM_DESCRIPTION, ITEM_QUANTITY, ITEM_WEIGHT};
        String clause = ITEM_ID_EQUIPMENT + " = ? AND " + ITEM_ID_CATEGORY + " = ?";
        String clauseArgs[] = {equipmentID, categoryID};
        String orderBy = ITEM_ID_EQUIPMENT + " ASC";
        return db.query(TABLE_ITEM, columns, clause, clauseArgs, null, null, orderBy, null);
    }
}
