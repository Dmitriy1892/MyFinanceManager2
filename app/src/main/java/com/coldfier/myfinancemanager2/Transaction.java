package com.coldfier.myfinancemanager2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Transaction {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss");

    private String date;
    private double payment;
    private double balance;
    private String location;
    private String category;
    private String id;

    public Transaction(String date, double payment, double balance, String location, String category) {
        this.date = date;
        this.payment = payment;
        this.balance = balance;
        this.location = location;
        this.category = category;
        this.id = "transaction" + UUID.randomUUID().toString().replace("-", "");
    }

    public Transaction(String date, double payment, double balance, String location, String category, String id) {
        this.date = date;
        this.payment = payment;
        this.balance = balance;
        this.location = location;
        this.category = category;
        this.id = id;
    }

    public Transaction() {
        this.id = "transaction" + UUID.randomUUID().toString().replace("-", "");
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }
}
