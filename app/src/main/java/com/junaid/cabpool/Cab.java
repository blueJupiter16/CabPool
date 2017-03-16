package com.junaid.cabpool;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Time;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Junaid on 27-02-2017.
 */

public class Cab {

    private String mOrganizerName;
    private String mDestination;
    private String mOrigin;
    private String mDate;
    private String mTime;
    private String mEmail;
    private String mDescription;
    private String mId;

    /*-------------------------Constructors-------------------------*/


    public Cab() {
        mId = UUID.randomUUID().toString();
    }

    public Cab(String organizerName, String destination, String date, String time, String email,
               String description,String origin) {


        mOrganizerName = organizerName;
        mDestination = destination;
        mDate = date;
        this.mTime = time;
        mEmail = email;
        mDescription = description;
        mOrigin = origin;


    }

    /*-------------------------Getters------------------------------*/

    public String getId() {
        return mId;
    }

    public String getOrigin() {
        return mOrigin;
    }

    public String getOrganizerName() {
        return mOrganizerName;
    }

    public String getDestination() {
        return mDestination;
    }

    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getDescription() {
        return mDescription;
    }

    /*----------------------------------Setters-----------------------------*/

    public void setOrganizerName(String organizerName) {
        mOrganizerName = organizerName;

    }
    public void setId(String ID){}

    public void setDestination(String destination) {
        mDestination = destination;
    }

    public void setOrigin(String origin) {
        mOrigin = origin;
    }

    public void setDate(String date) { mDate = date; }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}

