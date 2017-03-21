package com.junaid.cabpool;

import android.content.Intent;
import android.graphics.Color;
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

import net.bohush.geometricprogressview.GeometricProgressView;

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
    private GeometricProgressView mGeometricProgressView;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private DBHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.available_cabs_fragment_layout,container,false);
        mGeometricProgressView = (GeometricProgressView) v.findViewById(R.id.progressView);
        mGeometricProgressView.setVisibility(View.GONE);
        displayLoading();

        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new DBHelper(getActivity());



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

