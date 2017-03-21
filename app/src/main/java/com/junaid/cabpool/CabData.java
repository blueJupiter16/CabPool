package com.junaid.cabpool;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.IntDef;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Junaid on 14-03-2017.
 */

public class CabData extends AppCompatActivity {

    EditText mOrigin;
    EditText mDestination,mDate,mTime,mDescription;
    Button mButton;
    Cab editableCab = null;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_input_activity_layout);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        Serializable extras = getIntent().getSerializableExtra("Edit_data");
        if(extras != null)
             editableCab = (Cab)extras;

        mOrigin =  (EditText) findViewById(R.id.edit_origin);
        mDate =  (EditText) findViewById(R.id.edit_date);
        mTime = (EditText) findViewById(R.id.edit_time);
        mDestination = (EditText) findViewById(R.id.edit_destination);
        mDescription = (EditText) findViewById(R.id.edit_description);

        mDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Calendar mCalendar = Calendar.getInstance();

                    mDatePickerDialog = new DatePickerDialog(CabData.this,new dateListener(),
                            mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DATE) );
                    mDatePickerDialog.show();
                }
            }
        });

        mTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Calendar mCalendar = Calendar.getInstance();

                    mTimePickerDialog = new TimePickerDialog(CabData.this,new timeListener(),
                            mCalendar.get(Calendar.HOUR_OF_DAY),mCalendar.get(Calendar.MINUTE),false);
                    mTimePickerDialog.show();

                }
            }
        });


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


    /*------------------Listeners-----------------------*/
    private class dateListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            mDate.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
        }
    }

    private class timeListener implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            String AMPM = "";

            if(hourOfDay > 12){
                AMPM = "PM";
                hourOfDay = hourOfDay - 12;
            }
            else {
                AMPM = "AM";
            }
            mTime.setText(hourOfDay+":"+minute + " " +AMPM);
        }
    }


}
