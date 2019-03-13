package com.example.kaher.ics45jw19phonebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateRecord extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String name, phone, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_record);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("contacts" );
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        name = b.getString("name");
        phone = b.getString("phone");
        uid = b.getString("uid");
        TextView textName = (TextView)findViewById( R.id.textViewInstructions);
        textName.setText( textName.getText().toString() + " " + name);
    }

    public void update( View view )
    {
        EditText editName = (EditText)findViewById(R.id.editTextName);
        EditText editPhone = (EditText)findViewById(R.id.editTextPhone);
        String oldName = name;
        if( editName.getText().toString().length() > 0 )
            name = editName.getText().toString();
        if( editPhone.getText().toString().length() > 0 )
            phone = editPhone.getText().toString();
        Contact c = new Contact( name, phone, uid );
        myRef.child( uid ).setValue( c );
        Toast.makeText( this, oldName + " has been updated", Toast.LENGTH_LONG ).show();
        Intent intent = new Intent(this, UpdateActivity.class);
        startActivity( intent );


    }

    public void goBack( View view )
    {
        Intent intent = new Intent( this, UpdateActivity.class);
        startActivity( intent );
    }

    public void goHome( View view )
    {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent );
    }
}
