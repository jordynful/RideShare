package edu.uga.cs.rideshare;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

    private Context mContext;
    public static final String TAG = "RECYCLER ADAPTER";
    private List<Ride> mItems;

    private String mType;

    private String driverName;
    private String riderName;
    private String driverEmail;
    private String riderEmail;

    public MyRecyclerAdapter(Context context, List<Ride> items, String type) {
        mContext = context;
        mItems = items;
        mType = type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_layout, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FirebaseSingleton myApp = (FirebaseSingleton) mContext.getApplicationContext();
        FirebaseAuth mAuth = myApp.getFirebaseAuth();
        Ride item = mItems.get(position);
//        System.out.print("We in the adapter nowwwww " + item.toString());
        holder.time.setText(item.getTime());

        holder.destination.setText(item.getDestination());
        holder.date.setText(item.getDate());

        if (mType.compareTo("request") == 0) {


            holder.driver.setText(item.getRider());
            holder.driverTag.setText("RIDER");
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mAuth.getCurrentUser() != null) {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef2 = database.getReference("users");
                        DatabaseReference currentUserRef = myRef2.child(mAuth.getCurrentUser().getUid());



                        DatabaseReference userName = currentUserRef.child("name");
                        DatabaseReference userEmail = currentUserRef.child("email");
                        userName.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                driverName = dataSnapshot.getValue(String.class);
                                // Do something with the attribute name here

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Handle errors here
                            }
                        });

                        userEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                driverEmail = dataSnapshot.getValue(String.class);
                                // Do something with the attribute name here

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Handle errors here
                            }
                        });



                        Log.d( TAG, "User not null");
//            FirebaseAuth mAuth = FirebaseAuth.getInstance();

                        DatabaseReference myRef = database.getReference("rides");

                        DatabaseReference rideRef = myRef.child(item.getId());
                        rideRef.child("driver").setValue(driverName);
                        rideRef.child("driverName").setValue(driverEmail);
                        rideRef.child("driverId").setValue(mAuth.getCurrentUser().getUid());
                        rideRef.child("secured").setValue(true);

                        Toast.makeText(mContext, "Ride request accepted", Toast.LENGTH_SHORT).show();
//toast
                    } else {
                        // User is not signed in
                    }
                    // Perform desired action when button is clicked
                }
            });
        }
        else {
            holder.driver.setText(item.getDriver());
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mAuth.getCurrentUser() != null) {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef2 = database.getReference("users");
                        DatabaseReference currentUserRef = myRef2.child(mAuth.getCurrentUser().getUid());



                        DatabaseReference userName = currentUserRef.child("name");
                        userName.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                riderName = dataSnapshot.getValue(String.class);

                                // Do something with the attribute name here

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Handle errors here
                            }
                        });
                        DatabaseReference userEmail = currentUserRef.child("email");
                        userEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                driverEmail = dataSnapshot.getValue(String.class);

                                // Do something with the attribute name here

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Handle errors here
                            }
                        });



                        Log.d( TAG, "User not null");
//            FirebaseAuth mAuth = FirebaseAuth.getInstance();

                        DatabaseReference myRef = database.getReference("rides");

                        DatabaseReference rideRef = myRef.child(item.getId());
                        System.out.println(riderName);
                        rideRef.child("rider").setValue(riderName);
                        rideRef.child("riderName").setValue(riderEmail);

                        rideRef.child("riderId").setValue(mAuth.getCurrentUser().getUid());
                        rideRef.child("secured").setValue(true);

                        Toast.makeText(mContext, "Ride offer accepted", Toast.LENGTH_SHORT).show();
//toast
                    } else {
                        // User is not signed in
                    }
                    // Perform desired action when button is clicked
                }
            });
        }

    }

    @Override
    public int getItemCount() {

        if (mItems != null) {
            return mItems.size();
        }
        else {
            return 0;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public TextView driver;
        public TextView destination;
        public TextView date;
        public TextView driverTag;
        public Button button;

        public MyViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.textView16);
            driver = itemView.findViewById(R.id.textView14);
            destination = itemView.findViewById(R.id.textView12);
            date = itemView.findViewById(R.id.textView27);
            button = itemView.findViewById(R.id.item_button);
            driverTag = itemView.findViewById(R.id.textView13);
        }
    }
}
