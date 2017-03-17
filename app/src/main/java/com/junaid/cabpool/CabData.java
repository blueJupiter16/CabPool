package com.junaid.cabpool;


import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

/**
 * Created by Junaid on 14-03-2017.
 */

public class CabData extends AppCompatActivity {

    TextView mOrigin;
    TextView mDestination,mDate,mTime,mDescription;
    Button mButton;
    Cab editableCab = null;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_input_activity_layout);

        Serializable extras = getIntent().getSerializableExtra("Edit_data");
        if(extras != null)
             editableCab = (Cab)extras;

        mOrigin =  (TextView) findViewById(R.id.edit_origin);
        mDate =  (TextView) findViewById(R.id.edit_date);
        mTime = (TextView) findViewById(R.id.edit_time);
        mDestination = (TextView) findViewById(R.id.edit_destination);
        mDescription = (TextView) findViewById(R.id.edit_description);

        if(editableCab != null){
            mOrigin.setText(editableCab.getOrigin());
            mDate.setText(editableCab.getDate());
            mDescription.setText(editableCab.getDescription());
            mTime.setText(editableCab.getTime());
            mDestination.setText(editableCab.getDestination());
            Log.d("check0",editableCab.getId());
        }

        mButton = (Button) findViewById(R.id.save_data);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveCabState();

            }
        });


    }



    private void saveCabState(){
        if(editableCab == null) {
            saveCab();
//            Log.d("check",editableCab.getId());
        }
        else {
            editCab(editableCab);
        }

        //Log.d("Database",db.getAllContacts().toString());
        Intent i = new Intent(getBaseContext(),MainActivity.class);
        startActivity(i);
        Toast.makeText(getBaseContext(),"Saved",Toast.LENGTH_SHORT).show();

    }

    private void editCab(Cab cab) {

        cab.setOrganizerName(MainActivity.mOrganizerName);
        cab.setEmail(MainActivity.mEmail);
        cab.setDescription(mDescription.getText().toString());
        cab.setOrigin(mOrigin.getText().toString());
        cab.setTime(mTime.getText().toString());
        cab.setDestination(mDestination.getText().toString());
        cab.setDate(mDate.getText().toString());
        mDatabase.child("cabs").child(cab.getId()).setValue(cab);
        finish();
        Log.d("edit_cab",cab.getId());
    }

    private void saveCab() {
        Cab cab = new Cab();

        cab.setOrganizerName(MainActivity.mOrganizerName);
        cab.setEmail(MainActivity.mEmail);
        cab.setDescription(mDescription.getText().toString());
        cab.setOrigin(mOrigin.getText().toString());
        cab.setTime(mTime.getText().toString());
        cab.setDestination(mDestination.getText().toString());
        cab.setDate(mDate.getText().toString());
        cab.setId(mDatabase.child("cabs").push().getKey());

        mDatabase.child("cabs").child(cab.getId()).setValue(cab);
        finish();

        DBHelper db = new DBHelper(getApplicationContext());
        db.insertID(cab.getId());
    }


}
