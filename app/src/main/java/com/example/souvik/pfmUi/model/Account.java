package com.example.souvik.pfmUi.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Souvik on 18-Aug-16.
 */
public class Account {

    public static final String TABLE = "accountsTable";

    public static final String COL_ID = "_id";
    public static final String COL_NAME = "accountName";
    public static final String COL_NUMBER = "accountNumber";
    public static final String COL_BALANCE = "accountBalance";


    static String getSql() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE + "("
                + COL_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT,"
                + COL_NAME + " TEXT,"
                + COL_NUMBER + " TEXT UNIQUE NOT NULL,"
                + COL_BALANCE + " REAL" + ")";
    }

    private int bankId;
    private String accountName;
    private String accountNumber;
    private float accountBalance;

    public long save(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(COL_NAME, accountName);
        cv.put(COL_NUMBER, accountNumber);
        cv.put(COL_BALANCE, accountBalance);
        return db.insert(TABLE, null, cv);
 /*       String query = "INSERT INTO "+ Account.TABLE + " ("+
                        COL_NAME +","+COL_NUMBER+","+COL_BALANCE+") VALUES("+
                        accountName+","+accountNumber+","+accountBalance+") ON DUPLICATE KEY UPDATE"+
                        COL_NAME +"="+ accountName+","+
                        COL_NUMBER +"="+ accountNumber+","+
                        COL_BALANCE +"="+ accountBalance ;

        db.rawQuery(query, null);*/
    }


    static public float getAccountTotalBalance(SQLiteDatabase db) {
        String query = "SELECT SUM("+COL_BALANCE+") FROM "+ Account.TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            return cursor.getFloat(0);
        }
        return 0;
    }

    static public void count(SQLiteDatabase db) {
        String countQuery = "SELECT * FROM " + Account.TABLE ;
        Cursor cursor = db.rawQuery(countQuery, null);
        Log.d("SIZE" , cursor.getCount()+"");
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public float getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(float accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
