package com.example.kaher.ics45jw19phonebook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private ChildEventListener childEventListener;

    private ArrayList< Contact > contactList;
    private ArrayList< Contact > searchResults;

    private ContactAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference( "contacts" );

        contactList = new ArrayList< Contact >();
        searchResults = new ArrayList< Contact >();

        childEventListener = new ChildEventListener(){

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                contactList.add( dataSnapshot.getValue( Contact.class ) );
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        myRef.addChildEventListener( childEventListener );

        // Set up the list adapter to read from the results array
        listAdapter = new ContactAdapter( this, searchResults );
        ListView results = findViewById( R.id.listViewResults );
        results.setAdapter( listAdapter );

        results.setOnItemClickListener( new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact selectedItem = ( Contact )parent.getItemAtPosition( position );
                Bundle b = new Bundle();
                b.putString( "uid", selectedItem.getUid() );
                b.putString( "name", selectedItem.getName());
                b.putString("number", selectedItem.getNumber());
                Intent intent = new Intent( view.getContext(), UpdateRecord.class);
                intent.putExtras( b );
                startActivity( intent );



            }
        } );

    }

    public void search( View view )
    {
        listAdapter.clear();
        boolean found = false;
        for( Contact c: contactList )
        {
            EditText text = (EditText)findViewById( R.id.editTextName );
            String search = text.getText().toString();
            if( c.getName().equalsIgnoreCase( search ) )
            {
                listAdapter.add( c );
                found = true;
            }
        }

        EditText search = (EditText) findViewById( R.id.editTextName );
        if( !found )
            Toast.makeText( this, search.getText().toString() + " not found.", Toast.LENGTH_LONG ).show();
        search.setText("");
    }

    public void goHome( View view )
    {
        Intent intent = new Intent( this, MainActivity.class );
        startActivity( intent );
    }
}
