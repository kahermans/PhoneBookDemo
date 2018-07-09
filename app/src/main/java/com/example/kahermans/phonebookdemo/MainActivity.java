package com.example.kahermans.phonebookdemo;
// Introducing Google Firebase: A Free Database for your Mobile Apps
// CSTA 2018 Kimberly Hermans
// Main Activity: Directs each button to their respective activity

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addContact( View view)
    {
        Intent intent = new Intent( this, AddActivity.class);
        startActivity(intent);
    }

    public void viewContacts(View view)
    {
        Intent intent = new Intent(this, ViewActivity.class);
        startActivity( intent );
    }

    public void searchContacts(View view)
    {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity( intent );
    }

    public void removeContact(View view )
    {
        Intent intent = new Intent(this, RemoveActivity.class);
        startActivity( intent);
    }

    public void updateContact(View view )
    {
        Intent intent = new Intent( this, UpdateActivity.class);
        startActivity(intent);
    }
}
