package com.example.kahermans.phonebookdemo;
// Introducing Google Firebase: A Free Database for your Mobile Apps
// CSTA 2018 Kimberly Hermans
// Update Activity - Updates the phone number of a contact

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {

    // Google Firebase Database Reference
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    // Event Listener that listens to each child in the database
    private ChildEventListener childEventListener;

    // Local data structure that will store all the values from the database
    private ArrayList<Contact> contactList;
    private ArrayList<Contact> searchResults;

    // ArrayAdapter allows the results to be displayed in a list on the app
    private ContactAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Initializes the references to the database and contacts
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("contacts");

        // Initializes the local data structure to store the database
        contactList = new ArrayList<Contact>();

        // Set up an array that will have the contents that you want to display
        searchResults = new ArrayList<Contact>();

        // Sets up the event listener that will specify what happens when access of a node
        // occurs in the database
        childEventListener = new ChildEventListener(){
            // Method is run when any new node is added to the database, and once
            // for every existing node when the activity is loaded
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // Adds the Contact to the local data structure
                contactList.add(dataSnapshot.getValue(Contact.class));
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        // Need to add the event listener to the database
        myRef.addChildEventListener(childEventListener);

        // Sets up the list adapter to read from the results array
        listAdapter = new ContactAdapter( this, searchResults);
        ListView results = findViewById(R.id.listViewResults);
        results.setAdapter(listAdapter);

        // Defines what happens when an item of the listView is clicked
        results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                Contact selectedItem = (Contact) parent.getItemAtPosition(position);

                EditText name = (EditText) findViewById( R.id.editTextName);

                // Extracts phone number
                EditText phone = (EditText) findViewById(R.id.editTextPhone);
                String updatePhone = phone.getText().toString();

                TextView instructions = (TextView)findViewById(R.id.textViewInstructions);

                // Phone number is NOT blank
                if(!updatePhone.equals("")) {
                    // Creates a new contact with the same name and Uid, and new phone
                    Contact add = new Contact(selectedItem.getName(), updatePhone, selectedItem.getUid());
                    // Updates the database
                    myRef.child(selectedItem.getUid()).setValue(add);
                    instructions.setText(add.getName() + " successfully updated.");
                    contactList.remove( selectedItem ); // Removes old contact
                    contactList.add( add ); // Adds updated (new) contact
                    refresh( add.getName()); // Refreshes the listView
                }
                else    // Phone number IS blank
                    instructions.setText("Invalid phone number, please try again.");

                name.setText("");
                phone.setText("");
            }
        });
    }

    // Clears the listAdapter and rebuilds it from contactList
    public void refresh(String update)
    {
        listAdapter.clear();    // clears any content
        for( Contact c: contactList)
        {
            if(c.getName().equalsIgnoreCase(update)) {
                // If the contact name is a match, add the result to the listAdapter for display
                listAdapter.add(c);
            }
        }
    }
    public void updateContact(View view) {
        TextView instructions = (TextView)findViewById(R.id.textViewInstructions);

        listAdapter.clear();    // clears any content
        boolean found = false;
        for (Contact c : contactList) {
            EditText text = (EditText) findViewById(R.id.editTextName);
            String search = text.getText().toString();
            if (c.getName().equalsIgnoreCase(search)) {
                // If the contact name is a match, add the result to the listAdapter for display
                listAdapter.add(c);
                found = true;
            }
        }
        EditText search = (EditText) findViewById(R.id.editTextName);
        EditText phone = (EditText) findViewById( R.id.editTextPhone);
        if (!found) {
            Toast.makeText(this, search.getText().toString() + " not found.", Toast.LENGTH_LONG).show();
            search.setText("");
            phone.setText("");
            instructions.setText("");
        }
        else
            instructions.setText("Select the record that you want to update");
    }

    public void goHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity( intent);
    }
}
