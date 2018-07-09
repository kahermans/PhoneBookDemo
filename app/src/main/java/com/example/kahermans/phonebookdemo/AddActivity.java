package com.example.kahermans.phonebookdemo;

// Introducing Google Firebase: A Free Database for your Mobile Apps
// CSTA 2018 Kimberly Hermans
// Add Activity - Allows users to add contacts to the phone book

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddActivity extends AppCompatActivity {

    // Google Firebase Database References
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Initializes the references to the database and contacts
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("contacts");
    }

    public void addContact(View view)
    {
        EditText editName = findViewById(R.id.editTextName);
        String name = editName.getText().toString();
        EditText editNumber = findViewById(R.id.editTextPhone);
        String phone = editNumber.getText().toString();

        if( name.length() > 0 )
        {
            String key = myRef.push().getKey(); // Generates unique random key
            Contact c = new Contact(name, phone, key);
            myRef.child(key).setValue(c);   // Adds new Contact to the Database
            Toast.makeText(this, c.getName() + " successfully added.", Toast.LENGTH_LONG).show();
        }

        // Resets fields
        editName.setText("");
        editNumber.setText("");
    }

    public void goHome( View view )
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity( intent);
    }
}
