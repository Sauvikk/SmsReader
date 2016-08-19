package com.example.souvik.pfmUi.model;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    public static final String TABLE = "transactionsTable";
    public static final String COL_ID = "transactionId";
    public static final String COL_DESC = "transactionDescription";
    public static final String COL_AMOUNT = "transactionAmount";
    public static final String COL_TYPE = "transactionType";
    public static final String COL_DATE = "transactionDate";
    public static final String COL_BANK_NUMBER = "bankNumber";

    static String getSql() {
        return "CREATE TABLE IF NOT EXISTS " + TABLE + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_DESC + " TEXT,"
                + COL_AMOUNT + " TEXT,"
                + COL_TYPE + " TEXT,"
                + COL_DATE + " DATE,"
                + COL_BANK_NUMBER + " TEXT,"
                + " FOREIGN KEY("+ COL_BANK_NUMBER +") REFERENCES "+Account.TABLE +"("+Account.COL_NUMBER+")"+")";
    }

    private int transactionId;
    private String transactionDescription;
    private String transactionAmount;
    private String transactionType;
    private String transactionDate;
    private String bankNumber;

    public long save(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(COL_DESC, transactionDescription);
        cv.put(COL_AMOUNT, transactionAmount);
        cv.put(COL_TYPE, transactionType);
        cv.put(COL_DATE, transactionDate);
        cv.put(COL_BANK_NUMBER, bankNumber);
        return db.insert(TABLE, null, cv);
    }

    static public List<Transaction> getRecentTransactions(SQLiteDatabase db) {
        List<Transaction> transactionList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Transaction.TABLE + " LIMIT + 5";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction();

                transaction.setTransactionId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                transaction.setTransactionType(cursor.getString(cursor.getColumnIndex(COL_TYPE)));
                transaction.setTransactionDate(cursor.getString(cursor.getColumnIndex(COL_DATE)));
                transaction.setTransactionDescription(cursor.getString(cursor.getColumnIndex(COL_DESC)));
                transaction.setTransactionAmount(cursor.getString(cursor.getColumnIndex(COL_AMOUNT)));

                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }
        return transactionList;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
