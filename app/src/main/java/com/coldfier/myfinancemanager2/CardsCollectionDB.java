package com.coldfier.myfinancemanager2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CardsCollectionDB {
    public static final String CARDS_TABLE = "cardsDB";
    public static final String CARD_NAME = "cardName";
    public static final String CARD_NUMBER = "cardNumber";
    public static final String CARD_SMS_PATH = "cardSmsPath";
    public static final String CARD_CURRENCY = "cardCurrency";
    public static final String CARD_BALANCE = "cardBalance";
    public static final String CARD_ID = "cardID";


    public static final int CARDS_DB_VERSION = 1;
    public static final String CREATE_CARDS_DB = "create table " + CARDS_TABLE + "("
            + "_id integer primary key autoincrement, "
            + CARD_NAME + " text, "
            + CARD_NUMBER + " integer, "
            + CARD_SMS_PATH + " text, "
            + CARD_CURRENCY + " text, "
            + CARD_BALANCE + " real, "
            + CARD_ID + " text" + ");";

    public Context context;

    public CardsCollectionDB(Context context) {
        this.context = context;
    }

    public void addCard(Card card) {

        SQLiteDatabase db =
                new CardsCollectionDBHelper(context.getApplicationContext()).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CARD_NAME, card.getCardName());
        values.put(CARD_NUMBER,card.getCardNumber());
        values.put(CARD_SMS_PATH, card.getCardSmsPath());
        values.put(CARD_CURRENCY, card.getCardCurrency());
        values.put(CARD_BALANCE, card.getCardBalance());
        values.put(CARD_ID, card.getCardID());
        db.insert(CARDS_TABLE, null, values);
        db.close();

        TransactionsDB cardTransactionsDB = new TransactionsDB(card, context.getApplicationContext());
    }

    public void deleteCard(Card card) {
        SQLiteDatabase db = new CardsCollectionDBHelper(context.getApplicationContext()).getWritableDatabase();
        Cursor cursor = db.query(CARDS_TABLE, null, null, null, null, null, null);

        int i = 0;
        cursor.moveToFirst();
        while(i < cursor.getCount()) {
            if (card.getCardID().equals(cursor.getString(cursor.getColumnIndex(CARD_ID)))) {
                db.delete(CARDS_TABLE, cursor.getString(cursor.getColumnIndex(CARD_ID)), null);

                TransactionsDB transactionsDB = new TransactionsDB(context);
                transactionsDB.deleteTransactionDB(card);
                break;
            }
            i++;
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
    }

    public List<Card> getCardsCollection() {
        SQLiteDatabase db = new CardsCollectionDBHelper(context.getApplicationContext()).getWritableDatabase();
        Cursor cursor = db.query(CARDS_TABLE, null, null, null, null, null, null);

        List<Card> cards = new ArrayList<>();

        int i = 0;
        cursor.moveToFirst();
        while (i < cursor.getCount()){
            String cardNameNew = cursor.getString(cursor.getColumnIndex(CARD_NAME));
            int cardNumberNew = cursor.getInt(cursor.getColumnIndex(CARD_NUMBER));
            int cardSmsPathNew = cursor.getInt(cursor.getColumnIndex(CARD_SMS_PATH));
            String cardCurrencyNew = cursor.getString(cursor.getColumnIndex(CARD_CURRENCY));
            double cardBalanceNew = cursor.getDouble(cursor.getColumnIndex(CARD_BALANCE));
            String cardID = cursor.getString(cursor.getColumnIndex(CARD_ID));
            cards.add(new Card(cardNameNew, cardNumberNew, cardSmsPathNew, cardCurrencyNew, cardBalanceNew, cardID));
            i++;
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return cards;
    }

    public Card getCard(String cardID) {
        SQLiteDatabase db = new CardsCollectionDBHelper(context.getApplicationContext()).getWritableDatabase();
        Cursor cursor = db.query(CARDS_TABLE, null, null, null, null, null, null);

        Card card = null;

        int i = 0;
        cursor.moveToFirst();
        while(i < cursor.getCount()) {
            if (cardID.equals(cursor.getString(cursor.getColumnIndex(CARD_ID)))) {
                String cardNameNew = cursor.getString(cursor.getColumnIndex(CARD_NAME));
                int cardNumberNew = cursor.getInt(cursor.getColumnIndex(CARD_NUMBER));
                int cardSmsPathNew = cursor.getInt(cursor.getColumnIndex(CARD_SMS_PATH));
                String cardCurrencyNew = cursor.getString(cursor.getColumnIndex(CARD_CURRENCY));
                double cardBalanceNew = cursor.getDouble(cursor.getColumnIndex(CARD_BALANCE));
                String cardTransactionsDBNew = cursor.getString(cursor.getColumnIndex(CARD_ID));
                card = new Card(cardNameNew, cardNumberNew, cardSmsPathNew, cardCurrencyNew, cardBalanceNew, cardTransactionsDBNew);
                break;
            }
            i++;
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return card;
    }

    public void updateCard(Card card) {
        SQLiteDatabase db = new CardsCollectionDBHelper(context.getApplicationContext()).getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CARD_NUMBER,card.getCardNumber());
        values.put(CARD_NAME, card.getCardName());
        values.put(CARD_SMS_PATH, card.getCardSmsPath());
        values.put(CARD_CURRENCY, card.getCardCurrency());
        values.put(CARD_BALANCE, card.getCardBalance());
        db.update(CARDS_TABLE, values, CARD_ID + " = ? ", new String[] {card.getCardID()});
        db.close();
    }

    private class CardsCollectionDBHelper extends SQLiteOpenHelper {

        public CardsCollectionDBHelper(@Nullable Context context) {
            super(context, CARDS_TABLE, null, CARDS_DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_CARDS_DB);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
