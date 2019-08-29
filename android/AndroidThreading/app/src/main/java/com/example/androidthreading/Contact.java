package com.example.androidthreading;

import java.util.ArrayList;

public class Contact {
    private int id;
    private String name;
    private boolean isOnline;

    public Contact(String name, boolean isOnline) {
        this.name = name;
        this.isOnline = isOnline;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public boolean isOnline() {
        return isOnline;
    }
    private static int lastContactId = 0;

    public static ArrayList<Contact> createContactsList(int numContacts) {
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Contact("Person " + ++lastContactId, i <= numContacts / 2));
        }

        return contacts;
    }
}
