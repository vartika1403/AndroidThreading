package com.example.androidthreading;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class ContactDiffCallback extends DiffUtil.Callback {
    private List<Contact> oldList;
    private List<Contact> newList;


    public ContactDiffCallback(List<Contact>oldList,List<Contact>newList) {
        this.oldList =oldList;
        this.newList = newList;
    }
    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Contact oldContact = oldList.get(oldItemPosition);
        Contact newContact = newList.get(newItemPosition);

        if (oldContact.getName().equalsIgnoreCase(newContact.getName()) && oldContact.isOnline() == newContact.isOnline()) {
            return true;
        }
        return false;
    }
}
