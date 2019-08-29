package com.example.androidthreading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends ListAdapter<Contact, ContactsAdapter.ViewHolder> {
    private static final String LOG_TAG = ContactsAdapter.class.getSimpleName();
    List<Contact> contacts = new ArrayList<>();
    public static final DiffUtil.ItemCallback<Contact> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Contact>() {
                @Override
                public boolean areItemsTheSame(Contact oldItem, Contact newItem) {
                    return oldItem.getId() == newItem.getId();
                }
                @Override
                public boolean areContentsTheSame(Contact oldItem, Contact newItem) {
                    return (oldItem.getName() == newItem.getName() && oldItem.isOnline() == newItem.isOnline());
                }
            };

    public ContactsAdapter() {
        super(DIFF_CALLBACK);
    }

    public ContactsAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public void addMoreContacts(List<Contact> newContacts) {
        contacts.addAll(newContacts);
        submitList(contacts); // DiffUtil takes care of the check
    }

    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_contact, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {
        Contact contact = getItem(position);

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(contact.getName());
        Button button = holder.messageButton;
        button.setText(contact.isOnline() ? "Message" : "Offline");
        button.setEnabled(contact.isOnline());

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public Button messageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            messageButton = (Button) itemView.findViewById(R.id.message_button);
        }
    }
}
