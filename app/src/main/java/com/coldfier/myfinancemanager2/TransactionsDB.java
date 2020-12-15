package com.coldfier.myfinancemanager2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TransactionsDB {

    public static String cardTable;

    public static final String TRANSACTION_DATE = "date";
    public static final String TRANSACTION_PAYMENT = "payment";
    public static final String TRANSACTION_BALANCE = "balance";
    public static final String TRANSACTION_LOCATION = "location";
    public static final String TRANSACTION_CATEGORY = "category";
    public static final String TRANSACTION_ID = "uuid";

    public static final int TRANSACTION_DATABASE_VERSION = 1;

    public static String createDatabase;

    public Context context;

    public TransactionsDB(Context context) {
        this.context = context;
    }

    public TransactionsDB(Card card, Context context) {
        this.context = context;

        cardTable = card.getCardID();

        createDatabase = "create table " + cardTable + "("
                + "_id integer primary key autoincrement, "
                + TRANSACTION_DATE + " text, "
                + TRANSACTION_PAYMENT + " real, "
                + TRANSACTION_BALANCE + " real, "
                + TRANSACTION_LOCATION + " text, "
                + TRANSACTION_CATEGORY + " text, "
                + TRANSACTION_ID + " text" + ");";

        SQLiteDatabase db = new TransactionsDBHelper(context.getApplicationContext(), cardTable, TRANSACTION_DATABASE_VERSION).getWritableDatabase();
        db.close();
    }

    public void addTransaction(Card card, Transaction transaction) {
        cardTable = card.getCardID();
        SQLiteDatabase db = new TransactionsDBHelper(context.getApplicationContext(), cardTable, TRANSACTION_DATABASE_VERSION).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TRANSACTION_DATE, transaction.getDate());
        values.put(TRANSACTION_PAYMENT, transaction.getPayment());
        values.put(TRANSACTION_BALANCE, transaction.getBalance());
        values.put(TRANSACTION_LOCATION, transaction.getLocation());
        values.put(TRANSACTION_CATEGORY, transaction.getCategory());
        values.put(TRANSACTION_ID, transaction.getID());
        db.insert(cardTable, null, values);
        db.close();
    }

    public void deleteTransaction(Card card, Transaction transaction) {
        cardTable = card.getCardID();
        SQLiteDatabase db = new TransactionsDBHelper(context.getApplicationContext(), cardTable, TRANSACTION_DATABASE_VERSION).getWritableDatabase();
        Cursor cursor = db.query(cardTable, null, null,  null, null, null, null);

        int i = 0;
        cursor.moveToFirst();
        while (i < cursor.getCount()) {
            if (transaction.getID().equals(cursor.getString(cursor.getColumnIndex(TRANSACTION_ID)))) {
                db.delete(cardTable, TRANSACTION_ID + " = ?", new String[]{cursor.getString(cursor.getColumnIndex(TRANSACTION_ID))});
                break;
            }
            i++;
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
    }

    public Transaction getTransaction(String transactionID) {
        SQLiteDatabase db = new TransactionsDBHelper(context, cardTable, TRANSACTION_DATABASE_VERSION).getWritableDatabase();
        Cursor cursor = db.query(cardTable, null, null, null, null, null, null);

        Transaction transaction = null;

        int i = 0;
        cursor.moveToFirst();
        while (i < cursor.getCount()) {
            if (transactionID.equals(cursor.getString(cursor.getColumnIndex(TRANSACTION_ID)))) {
                String dateNew = cursor.getString(cursor.getColumnIndex(TRANSACTION_DATE));
                double paymentNew = cursor.getDouble(cursor.getColumnIndex(TRANSACTION_PAYMENT));
                double balanceNew = cursor.getDouble(cursor.getColumnIndex(TRANSACTION_BALANCE));
                String locationNew = cursor.getString(cursor.getColumnIndex(TRANSACTION_LOCATION));
                String categoryNew = cursor.getString(cursor.getColumnIndex(TRANSACTION_CATEGORY));
                String idNew = cursor.getString(cursor.getColumnIndex(TRANSACTION_ID));

                transaction = new Transaction(dateNew, paymentNew, balanceNew, locationNew, categoryNew, idNew);
                break;
            }

            i++;
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return transaction;
    }

    public void updateTransaction(Transaction transaction) {
        SQLiteDatabase db = new TransactionsDBHelper(context, cardTable, TRANSACTION_DATABASE_VERSION).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TRANSACTION_DATE, transaction.getDate());
        values.put(TRANSACTION_PAYMENT, transaction.getPayment());
        values.put(TRANSACTION_BALANCE, transaction.getBalance());
        values.put(TRANSACTION_LOCATION, transaction.getLocation());
        values.put(TRANSACTION_CATEGORY, transaction.getCategory());

        db.update(cardTable, values, TRANSACTION_ID + " = ? ", new String[] {transaction.getID()});
        db.close();
    }

    public List<Transaction> getTransactionsCollection(Card card) {
        cardTable = card.getCardID();

        SQLiteDatabase db = new TransactionsDBHelper(context.getApplicationContext(), cardTable, TRANSACTION_DATABASE_VERSION).getWritableDatabase();
        Cursor cursor = db.query(cardTable, null, null, null, null, null, null);

        List<Transaction> transactionsCollection = new ArrayList<>();

        int i = 0;
        cursor.moveToFirst();
        while (i < cursor.getCount()) {
            String dateNew = cursor.getString(cursor.getColumnIndex(TRANSACTION_DATE));
            float paymentNew = cursor.getFloat(cursor.getColumnIndex(TRANSACTION_PAYMENT));
            float balanceNew = cursor.getFloat(cursor.getColumnIndex(TRANSACTION_BALANCE));
            String locationNew = cursor.getString(cursor.getColumnIndex(TRANSACTION_LOCATION));
            String categoryNew = cursor.getString(cursor.getColumnIndex(TRANSACTION_CATEGORY));
            String idNew = cursor.getString(cursor.getColumnIndex(TRANSACTION_ID));

            Transaction transaction = new Transaction(dateNew, paymentNew, balanceNew, locationNew, categoryNew, idNew);

            transactionsCollection.add(transaction);

            cursor.moveToNext();
            i++;
        }

        cursor.close();
        db.close();

        return transactionsCollection;
    }

    public void deleteTransactionDB(Card card) {
        SQLiteDatabase db = new TransactionsDBHelper(context.getApplicationContext(), card.getCardID(), TRANSACTION_DATABASE_VERSION).getWritableDatabase();
        db.delete(card.getCardID(), null, null);
    }

    private class TransactionsDBHelper extends SQLiteOpenHelper {

        public TransactionsDBHelper(Context context, String name, int version) {
            super(context, name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(createDatabase);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
