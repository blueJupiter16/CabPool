package com.junaid.cabpool;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Junaid on 01-03-2017.
 */

public class AvailableCabs extends Fragment {

    private ArrayList<Cab> dataList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private FloatingActionButton mFloatingActionButton;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private GeometricProgressView mGeometricProgressView;


    public AvailableCabs(){

    }




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.available_cabs_fragment_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        /*
        Cab cab = new Cab();
        cab.setDate("14-03-2017");
        cab.setTime("5:00 PM");
        cab.setDestination("NOIDA");
        cab.setOrigin("SNU");
        cab.setDescription("We have space for two people. reply ASAP");
        cab.setOrganizerName("Junaid Tinwala");
        dataList.add(cab);

        cab.setDate("14adasdas-03-2017");
        cab.setTime("5:asdasdasd00 PM");
        cab.setDestination("NOIDasdasdasA");
        cab.setOrigin("SNU");
        cab.setDescription("We have spadasdace for two people. reply ASAP");
        cab.setOrganizerName("Junaidasdasdas Tinwala");
        dataList.add(cab);*/


        mGeometricProgressView = (GeometricProgressView) view.findViewById(R.id.progressView);
        mGeometricProgressView.setVisibility(View.GONE);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.avialable_cabs_list_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ListAdapter(dataList,this.getClass(),getContext());
        mRecyclerView.setAdapter(mAdapter);

        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.add_cab_FAB);
        mFloatingActionButton.setVisibility(FloatingActionButton.GONE);

        mDatabase.child("cabs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //ArrayList<Cab> cabs = new ArrayList<>();
                displayLoading();
                dataList.clear();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Cab cab = noteDataSnapshot.getValue(Cab.class);
                   // cab.setId();
                   // Log.d("AvailableCabsID",cab.getId()+ " " + noteDataSnapshot.getValue(Cab.class).getId());
                    dataList.add(cab);


                }


               // Log.d("List",dataList.toString());
                mAdapter.notifyDataSetChanged();
                disappearLoading();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    private void displayLoading(){

        mGeometricProgressView.setVisibility(View.VISIBLE);
        mGeometricProgressView.setType(GeometricProgressView.TYPE.TRIANGLE);
        mGeometricProgressView.setNumberOfAngles(3);
        mGeometricProgressView.setColor(Color.parseColor("#3F51B5"));
        mGeometricProgressView.setDuration(1000);
        mGeometricProgressView.setFigurePadding(getResources().getDimensionPixelOffset(R.dimen.cardview_compat_inset_shadow));
    }

    private void disappearLoading(){
        mGeometricProgressView.setVisibility(View.GONE);
    }

}
