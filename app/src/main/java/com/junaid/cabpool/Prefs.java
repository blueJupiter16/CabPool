package com.junaid.cabpool;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by Junaid on 15-03-2017.
 */

public class Prefs {

    private static final String PREFS_NAME="prefs";
    private static  int CAB_COUNT = 0;
    private SharedPreferences sharedpreferences;




    public Prefs(Context context){

        sharedpreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void addData(String ID){

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Integer.toString(CAB_COUNT),ID);
        editor.apply();
        CAB_COUNT++;


    }

    public ArrayList<String> getData(){
        ArrayList<String> list = new ArrayList<>();
        String s;
        for(int i =0;i<CAB_COUNT;i++){
            s = sharedpreferences.getString(i+"",null);
            if(s != null)
                list.add(s);
        }

        return list;
    }

    public void deleteData(String ID){

        SharedPreferences.Editor editor = sharedpreferences.edit();
        for(int i =0;i<CAB_COUNT;i++){
            if(sharedpreferences.getString(i+"",null) != null){
                if(ID.equals(sharedpreferences.getString(i+"",null))){
                    editor.remove(i+"");
                }

            }
        }
        editor.apply();
    }




}
