package com.example.kahermans.phonebookdemo;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private Context mContext;
    private List<Contact> contactList = new ArrayList<Contact>();

    public ContactAdapter( Context context, ArrayList<Contact> list)
    {
        super( context, 0, list);
        mContext = context;
        contactList = list;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        // Associates the list with the XML Layout file "contact_view"
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.contact_view,parent,false);

        // Individually handles each Contact in the contactList
        Contact currentContact = contactList.get(position);

        // Extracts the name of the Contact
        TextView name = (TextView) listItem.findViewById(R.id.textView_Name);
        name.setText(currentContact.getName());

        // Extracts the phone number of the Contact
        TextView number = (TextView) listItem.findViewById(R.id.textView_Number);
        number.setText(currentContact.getNumber());

        return listItem;
    }
}
