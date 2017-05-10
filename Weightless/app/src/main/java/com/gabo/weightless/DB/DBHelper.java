package com.gabo.weightless.DB;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gabo.weightless.Objects.Category;
import com.gabo.weightless.Objects.Equipment;
import com.gabo.weightless.Objects.Item;

import java.io.Console;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private static final String SHAREDEQUIPMENTTABLE = "SHAREDEQUIPMENT";

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
    private static final String C_STATUS = "status";

    //EQUIPMENT TABLE
    private static final String C_NAME = "name";
    private static final String C_OWNER = "owner";

    //CATEGORY TABLE
    private static final String C_EQUIPMENTID = "equipmentID";

    //ITEM TABLE
    private static final String C_QUANTITY = "quantity";
    private static final String C_WEIGHT = "weight";
    private static final String C_CATEGORYID = "categoryID";

    //SHAREDEQUIPMENT
    //usar C_OWNER
    private static final String C_FRIEND = "friend";
    // usar C_EQUIPMENTID

    Context context;

    public DBHelper(Context context){
        super(context, DATABASE, null, VERSION);
        this.context = context;
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
                C_USER2 + " TEXT, " +
                C_STATUS + " INTEGER) ";
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
        String creationQuerySharedEquipment = "CREATE TABLE " + SHAREDEQUIPMENTTABLE +
                " (" + C_ID + " INTEGER PRIMARY KEY, " +
                C_OWNER + " TEXT, " +
                C_FRIEND + " TEXT, " +
                C_EQUIPMENTID + " INTEGER) ";

        db.execSQL(creationQueryUser);
        db.execSQL(creationQueryFriends);
        db.execSQL(creationQueryEquipment);
        db.execSQL(creationQueryCategories);
        db.execSQL(creationQueryItems);
        db.execSQL(creationQuerySharedEquipment);
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
    public boolean emailExists(String email){
        SQLiteDatabase db = getWritableDatabase();
        String selection = C_MAIL + " = ?";
        String[] params = {email};
        Cursor c = db.query(USERTABLE, null, selection, params, null, null, null);
        if(c.getCount() == 0){
            return false;
        }else{
            return true;
        }
    }
    public boolean userExists(String user){
        SQLiteDatabase db = getWritableDatabase();
        String selection = C_USER + "= ?";
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

        String selection = C_MAIL + " = ?";
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
    public String getUserFromMail(String mail){
        SQLiteDatabase db = getWritableDatabase();
        String selection = C_MAIL + "= ?";
        String[] params = {mail};
        Cursor c = db.query(USERTABLE, null, selection, params, null, null, null);
        c.moveToFirst();
        return c.getString(1);
    }
    public void createEquipment(String email, String name){
        SQLiteDatabase db = getWritableDatabase();
        //String user = getUserFromMail(email);
        ContentValues cv = new ContentValues();
        cv.put(C_NAME, name);
        cv.put(C_OWNER, email);
        db.insert(EQUIPMENTTABLE, null, cv);
    }
    public boolean equipmentExists(String name, String email){
        SQLiteDatabase db = getWritableDatabase();
        //String user = getUserFromMail(email);
        String selection = C_NAME + " = ? AND " + C_OWNER + " = ?";
        String[] params = {name, email};
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
            Equipment defaultEquip = new Equipment(0, "You don't have any Equipment Created", "", 0);
            ArrayList<Equipment> r = new ArrayList<Equipment>();
            r.add(defaultEquip);
            return r;
        }else{
            Equipment tmp;
            ArrayList<Equipment> r = new ArrayList<Equipment>();

            c.moveToFirst();
            while(!c.isAfterLast()) {
                tmp = new Equipment(c.getInt(0), c.getString(1), c.getString(2), this.getEquipmentWeight(c.getInt(0)));
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
            Category defaultCategory = new Category(0, "No categories created for this equipment", 0, 0);
            toReturn.add(defaultCategory);
            return toReturn;
        }
        else {
            c.moveToFirst();

            while(!c.isAfterLast()) {
                toReturn.add(new Category(c.getInt(0), c.getString(1), c.getInt(2), getCategoryWeight(c.getInt(0))));
                c.moveToNext();
            }

            return toReturn;
        }
    }

    public double getCategoryWeight(int cID) {

        SQLiteDatabase db = getReadableDatabase();

        String selection = C_CATEGORYID + " = ?";
        String[] params = {Integer.toString(cID)};

        Cursor c = db.query(ITEMTABLE, null, selection, params, null, null, null);

        double toReturn = 0;

        if(c.getCount() == 0) {
            return toReturn;
        }
        else {
            c.moveToFirst();

            while(!c.isAfterLast()) {
                toReturn += this.round(c.getDouble(3)*c.getInt(2), 2);
                c.moveToNext();
            }

            return toReturn;
        }
    }

    public void createItem(int cID, String name, double w, int q) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(C_NAME, name);
        cv.put(C_WEIGHT, w);
        cv.put(C_QUANTITY, q);
        cv.put(C_CATEGORYID, cID);
        db.insert(ITEMTABLE, null, cv);
    }

    public ArrayList<Item> getItems(int cID) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = C_CATEGORYID + " = ?";
        String[] params = {Integer.toString(cID)};

        Cursor c = db.query(ITEMTABLE, null, selection, params, null, null, null);

        ArrayList<Item> toReturn = new ArrayList<Item>();

        if(c.getCount() == 0) {
            Item defaultItem = new Item("No items created for this category", 0, 0, 0, 0);
            toReturn.add(defaultItem);
            return toReturn;
        }
        else {
            c.moveToFirst();

            while(!c.isAfterLast()) {
                toReturn.add(new Item(c.getString(1), c.getDouble(3), c.getInt(2), c.getInt(0), c.getInt(4)));
                c.moveToNext();
            }

            return toReturn;
        }
    }

    public void removeItem(int iID) {

        SQLiteDatabase db = getWritableDatabase();
        db.delete(ITEMTABLE, C_ID + " = " + iID, null);
    }

    public void removeCategory(int cID) {

        SQLiteDatabase db = getWritableDatabase();

        String selectionItems = C_CATEGORYID + " = ?";
        String[] paramsItems = {Integer.toString(cID)};

        Cursor c = db.query(ITEMTABLE, null, selectionItems, paramsItems, null, null, null);

        if(c.getCount() != 0) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                db.delete(ITEMTABLE, C_ID + " = " + c.getInt(0), null);
                c.moveToNext();
            }
        }

        db.delete(CATEGORYTABLE, C_ID + " = " + cID, null);
    }

    public void removeEquipment(int eID) {

        SQLiteDatabase db = getWritableDatabase();

        String selectionCategories = C_EQUIPMENTID + " = ?";
        String[] paramsCategories = {Integer.toString(eID)};

        Cursor c = db.query(CATEGORYTABLE, null, selectionCategories, paramsCategories, null, null, null);
        ArrayList<Integer> categoryToRemove = new ArrayList<>(c.getCount());

        if(c.getCount() != 0) {
            c.moveToFirst();
            while(!c.isAfterLast()) {
                categoryToRemove.add(c.getInt(0));
                c.moveToNext();
            }
        }

        db.delete(EQUIPMENTTABLE, C_ID + " = " + eID, null);
        db.close();

        for(int i = 0; i < categoryToRemove.size(); i++) {
            this.removeCategory(categoryToRemove.get(i));
        }
    }

    public double getEquipmentWeight(int eID) {

        SQLiteDatabase db = getReadableDatabase();

        String selection = C_EQUIPMENTID + " = ?";
        String[] params = {Integer.toString(eID)};

        Cursor c = db.query(CATEGORYTABLE, null, selection, params, null, null, null);

        double toReturn = 0;

        if(c.getCount() == 0) {
            return toReturn;
        }
        else {
            c.moveToFirst();

            while(!c.isAfterLast()) {
                toReturn += this.getCategoryWeight(c.getInt(0));
                c.moveToNext();
            }

            return toReturn;
        }
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal number = new BigDecimal(value);
        number = number.setScale(places, RoundingMode.HALF_UP);
        return number.doubleValue();
    }

    public String[] getFriends(String user) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = C_USER1 + " = ? AND " + C_STATUS + " = 1";
        String[] params = {user};

        Cursor c = db.query(FRIENDTABLE, null, selection, params, null, null, null);

        c.moveToFirst();

        if(c.getCount() > 0){
            String[] friends = new String[c.getCount()];
            for(int i = 0; i < c.getCount(); i++){
                friends[i] = c.getString(2);
                c.moveToNext();
            }
            return friends;
        }else{
            String[] friends = {"You don't have any friends"};
            return friends;
        }

    }
    public String[] getFriendRequests(String user){
        SQLiteDatabase db = getReadableDatabase();
        String selection = C_USER1 + " = ? AND " + C_STATUS + " = 0";
        String[] params = {user};

        Cursor c = db.query(FRIENDTABLE, null, selection, params, null, null, null);

        c.moveToFirst();

        if(c.getCount() > 0){
            String[] friends = new String[c.getCount()];
            for(int i = 0; i < c.getCount(); i++){
                friends[i] = c.getString(2);
                c.moveToNext();
            }

            return friends;
        }else{
            String[] friends = {"You don't have any friend requests"};
            return friends;
        }
    }
    public void addFriend(String user1, String user2){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(C_USER1, user1);
        cv.put(C_USER2, user2);
        cv.put(C_STATUS, 0);
        db.insert(FRIENDTABLE, null, cv);
        ContentValues cv2 = new ContentValues();
        cv2.put(C_USER1, user2);
        cv2.put(C_USER2, user1);
        cv2.put(C_STATUS, 0);
        db.insert(FRIENDTABLE, null, cv2);
    }
    public void acceptFriendRequest(String user1, String user2){
        SQLiteDatabase db = getWritableDatabase();

        String selection = C_USER1 + " = ? AND " + C_USER2 + " = ?";
        String[] params = {user1, user2};

        db.delete(FRIENDTABLE, selection, params);
        String[] params2 = {user2, user1};

        db.delete(FRIENDTABLE, selection, params2);

        ContentValues cv = new ContentValues();
        cv.put(C_USER1, user1);
        cv.put(C_USER2, user2);
        cv.put(C_STATUS, 1);
        db.insert(FRIENDTABLE, null, cv);
        ContentValues cv2 = new ContentValues();
        cv2.put(C_USER1, user2);
        cv2.put(C_USER2, user1);
        cv2.put(C_STATUS, 1);
        db.insert(FRIENDTABLE, null, cv2);

    }
}