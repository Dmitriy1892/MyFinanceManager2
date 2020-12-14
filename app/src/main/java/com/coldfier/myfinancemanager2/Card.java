package com.coldfier.myfinancemanager2;

import java.util.UUID;

public class Card {

    private String cardName;
    private int cardNumber;
    private int cardSmsPath = 0;
    private String cardCurrency;
    private double cardBalance;
    private String cardID;

    public Card(String cardName, int cardNumber, String cardCurrency, double cardBalance) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.cardCurrency = cardCurrency;
        this.cardBalance = cardBalance;
        this.cardID = "card" + UUID.randomUUID().toString().replace("-", "");
    }

    public Card(String cardName, int cardNumber, int cardSmsPath, String cardCurrency, double cardBalance, String cardID) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.cardSmsPath = cardSmsPath;
        this.cardCurrency = cardCurrency;
        this.cardBalance = cardBalance;
        this.cardID = cardID;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCardSmsPath() {
        return cardSmsPath;
    }

    public void setCardSmsPath(int cardSmsPath) {
        this.cardSmsPath = cardSmsPath;
    }

    public String getCardCurrency() {
        return cardCurrency;
    }

    public void setCardCurrency(String cardCurrency) {
        this.cardCurrency = cardCurrency;
    }

    public double getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(double cardBalance) {
        this.cardBalance = cardBalance;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }
}
