package com.junaid.cabpool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Junaid on 10-03-2017.
 */
 class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ArrayList<Cab> mList = null;
    private Class callingClass;
    private Context mContext;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;


    /*------------------View Holder---------------*/
     static class ViewHolder extends RecyclerView.ViewHolder{

         TextView mOrigin;
         TextView mDestination,mDate,mTime,mName,mDescription,mOptions;


         ViewHolder(View v) {
            super(v);

            mOrigin =  (TextView) v.findViewById(R.id.card_origin);
            mDate =  (TextView) v.findViewById(R.id.card_date);
            mTime = (TextView) v.findViewById(R.id.card_time);
            mDestination = (TextView) v.findViewById(R.id.card_destination);
            mDescription = (TextView) v.findViewById(R.id.card_description);
            mName = (TextView) v.findViewById(R.id.card_name);
            mOptions = (TextView) v.findViewById(R.id.card_options);


        }
    }

    /*-----------------Constructor------------------*/
    public ListAdapter(ArrayList<Cab> list,Class mClass,Context context){
        mList = list;

        this.callingClass = mClass;
        this.mContext = context;

    }



    /*----------------Abstract Methods---------------------*/
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, final int position) {

        Cab cab = mList.get(position);

        holder.mDate.setText(cab.getDate());
        holder.mDestination.setText(cab.getDestination());
        holder.mTime.setText(cab.getTime());
        holder.mOrigin.setText(cab.getOrigin());
        holder.mDescription.setText(cab.getDescription());
        holder.mName.setText(MainActivity.mOrganizerName);

        setAnimation(holder.itemView,position);

       // Log.d("ListTokens",cab.getId() + " " + mList.get(position).getId());

        holder.mOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 /*----------------Displaying Menu--------------*/
                if(MyCabs.class == callingClass)
                    createMyCabsMenu(v,position);
                else
                    createAvailableCabsMenu(v,position);
            }
        });




    }

    @Override
    public int getItemCount() { return mList.size();  }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);

            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    private void createAvailableCabsMenu(final View v,final int position){

        PopupMenu popupMenu = new PopupMenu(mContext,v);
        popupMenu.inflate(R.menu.available_cabs_options_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.email_menu_item:
                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ mList.get(position).getEmail() });
                        email.putExtra(Intent.EXTRA_SUBJECT, "cabpool");


                       //need this to prompts email client only
                        email.setType("message/rfc822");

                        mContext.startActivity(Intent.createChooser(email, "Choose an Email client :"));
                        break;


                }
                return false;
            }
        });
        //displaying the popup
        popupMenu.show();

    }


    private void createMyCabsMenu(final View v, final int position){

        final DBHelper db = new DBHelper(v.getContext());


        PopupMenu popupMenu = new PopupMenu(mContext,v);
        popupMenu.inflate(R.menu.my_cabs_options_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit_menu_item:
                        Intent i = new Intent(v.getContext(),CabData.class);
                        i.putExtra("Edit_data",mList.get(position));
                        v.getContext().startActivity(i);
                        break;
                    case R.id.delete_menu_item:
                        Cab cab = mList.get(position);
                        //Log.d("Click",position+" "+mList.get(position).getId() + " "+cab.getDescription());
                        mDatabase.child("cabs").child(cab.getId()).removeValue();
                        db.deleteID(cab.getId());
                        notifyDataSetChanged();
                        break;

                }
                return false;
            }
        });
        //displaying the popup
        popupMenu.show();
    }
}
