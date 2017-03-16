package com.gabo.weightless;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gabo.weightless.Objects.Category;
import com.gabo.weightless.Objects.Equipment;

import java.util.ArrayList;

/**
 * Created by LIC on 08/03/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String USERTABLE = "USERS";
    private static final String FRIENDTABLE = "FRIENDS";
    private static final String EQUIPMENTTABLE = "EQUIPMENTS";
    private static final String CATEGORYTABLE = "CATEGORIES";
    private static final String ITEMTABLE = "ITEMS";

    private static final String DATABASE = "weightless.db";
    private static final int VERSION = 1;

    //USER TABLE
    private static final String C_ID = "id";
    private static final String C_USER = "user";
    private static final String C_MAIL = "mail";
    private static final String C_PSWD = "password";

    //FRIENDS
    private static final String C_USER1 = "user1";
    private static final String C_USER2 = "user2";

    //EQUIPMENT TABLE
    private static final String C_NAME = "name";
    private static final String C_OWNER = "owner";

    //CATEGORY TABLE
    private static final String C_EQUIPMENTID = "equipmentID";

    //ITEM TABLE
    private static final String C_QUANTITY = "quantity";
    private static final String C_WEIGHT = "weight";
    private static final String C_CATEGORYID = "categoryID";

    public DBHelper(Context context){
        super(context, DATABASE, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String creationQueryUser ="CREATE TABLE " + USERTABLE + "" +
                " (" + C_ID + " INTEGER PRIMARY KEY, " +
                 C_USER + " TEXT, " +
                C_MAIL + " TEXT, " +
                C_PSWD + " TEXT) ";
        String creationQueryFriends = "CREATE TABLE " + FRIENDTABLE + "" +
                " (" + C_ID + " INTEGER PRIMARY KEY, " +
                C_USER1 + " TEXT, " +
                C_USER2 + " TEXT) ";
        String creationQueryEquipment = "CREATE TABLE " + EQUIPMENTTABLE +
                " (" + C_ID + " INTEGER PRIMARY KEY, " +
                C_NAME + " TEXT, " +
                C_OWNER + " TEXT) ";
        String creationQueryCategories = "CREATE TABLE " + CATEGORYTABLE +
                " (" + C_ID + " INTEGER PRIMARY KEY, " +
                C_NAME + " TEXT, " +
                C_EQUIPMENTID + " INTEGER) ";
        String creationQueryItems = "CREATE TABLE " + ITEMTABLE +
                " (" + C_ID + " INTEGER PRIMARY KEY, " +
                C_NAME + " TEXT, " +
                C_QUANTITY + " INTEGER, " +
                C_WEIGHT + " REAL, " +
                C_CATEGORYID + " INTEGER) ";

        db.execSQL(creationQueryUser);
        db.execSQL(creationQueryFriends);
        db.execSQL(creationQueryEquipment);
        db.execSQL(creationQueryCategories);
        db.execSQL(creationQueryItems);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String[] tables = {USERTABLE, FRIENDTABLE, EQUIPMENTTABLE, CATEGORYTABLE, ITEMTABLE};

        db.execSQL("DROP TABLE IF EXISTS ?", tables);
        onCreate(db);
    }
    public void createUser(String user, String email, String password){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(C_USER, user);
        cv.put(C_MAIL, email);
        cv.put(C_PSWD, password);
        db.insert(USERTABLE, null, cv);
    }
    public boolean userExists(String user){
        SQLiteDatabase db = getWritableDatabase();
        String selection = C_USER + " = ?";
        String[] params = {user};
        Cursor c = db.query(USERTABLE, null, selection, params, null, null, null);
        if(c.getCount() == 0){
            return false;
        }else{
            return true;
        }
    }
    public boolean userValidation(String user, String password){
        SQLiteDatabase db = getWritableDatabase();

        String selection = C_USER + " = ?";
        String[] params = {user};
        Cursor c = db.query(USERTABLE, null, selection, params, null, null, null);
        c.moveToFirst();
        Log.d("password", c.getString(3));
        if(c.getString(3).compareTo(password)==0){
            return true;
        }else{
            return false;
        }

    }
    public void createEquipment(String user, String name){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(C_NAME, name);
        cv.put(C_OWNER, user);
        db.insert(EQUIPMENTTABLE, null, cv);
    }
    public boolean equipmentExists(String name, String user){
        SQLiteDatabase db = getWritableDatabase();
        String selection = C_NAME + " = ? AND " + C_OWNER + " = ?";
        String[] params = {name, user};
        Cursor c = db.query(EQUIPMENTTABLE, null, selection, params, null, null, null);
        if(c.getCount() == 0){
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<Equipment> getEquipment(String user){
        SQLiteDatabase db = getWritableDatabase();

        String selection = C_OWNER + " = ?";
        String[] params = {user};

        Cursor c = db.query(EQUIPMENTTABLE, null, selection, params, null, null, null);
        if(c.getCount() == 0){
            Equipment defaultEquip = new Equipment(0, "You don't have any Equipment Created", "");
            ArrayList<Equipment> r = new ArrayList<Equipment>();
            r.add(defaultEquip);
            return r;
        }else{
            Equipment tmp;
            ArrayList<Equipment> r = new ArrayList<Equipment>();

            c.moveToFirst();
            while(!c.isAfterLast()){
                tmp = new Equipment(c.getInt(0), c.getString(1), c.getString(2));
                r.add(tmp);
                c.moveToNext();
            }

            return r;
        }
    }

    public void createCategory(int eID, String name) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(C_EQUIPMENTID, eID);
        cv.put(C_NAME, name);
        db.insert(CATEGORYTABLE, null, cv);
    }

    public ArrayList<Category> getCategories(int eID) {
        SQLiteDatabase db = getWritableDatabase();

        String eIDString = Integer.toString(eID);
        String selection = C_EQUIPMENTID + " = ?";
        String[] params = {eIDString};

        Cursor c = db.query(CATEGORYTABLE, null, selection, params, null, null, null);

        ArrayList<Category> toReturn = new ArrayList<Category>();

        if(c.getCount() == 0) {
            Category defaultCategory = new Category(0, "No categories created for this equipment", 0);
            toReturn.add(defaultCategory);
            return toReturn;
        }
        else {
            c.moveToFirst();

            while(!c.isAfterLast()) {
                toReturn.add(new Category(c.getInt(0), c.getString(1), c.getInt(2)));
                c.moveToNext();
            }

            return toReturn;
        }
    }

    public int getCategoryWeight(int cID) {
        return 0;
    }
}