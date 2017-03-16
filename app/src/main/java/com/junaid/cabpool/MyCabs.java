package com.junaid.cabpool;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by Junaid on 01-03-2017.
 */

public class MyCabs extends Fragment {

    private ArrayList<Cab> dataList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private FloatingActionButton mFloatingActionButton;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private DBHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.available_cabs_fragment_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new DBHelper(getActivity());
        /*Cab cab = new Cab();
        cab.setDate("14-03-2017");
        cab.setTime("5:00 PM");
        cab.setDestination("NOIDA");
        cab.setOrigin("SNU");
        cab.setDescription("We have space for two people. reply ASAP");
        cab.setOrganizerName("Junaid Tinwala");*/

       // dataList.add(cab);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.avialable_cabs_list_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ListAdapter(dataList,this.getClass(),getContext());
        mRecyclerView.setAdapter(mAdapter);

        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.add_cab_FAB);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),CabData.class);
                startActivity(intent);
            }
        });


        mDatabase.child("cabs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //ArrayList<Cab> cabs = new ArrayList<>();
                dataList.clear();

                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Cab cab = noteDataSnapshot.getValue(Cab.class);
                   // Log.d("MyCabsId",cab.getId()+ " " + noteDataSnapshot.getValue().toString());
                    if(db.findID(cab.getId()))
                        dataList.add(cab);

                }


               // Log.d("List",dataList.toString());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
}
