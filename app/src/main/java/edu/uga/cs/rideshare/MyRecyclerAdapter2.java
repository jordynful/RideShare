package edu.uga.cs.rideshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyRecyclerAdapter2 extends RecyclerView.Adapter<MyRecyclerAdapter2.MyViewHolder> {

    private Context mContext;
    private List<Ride> mItems;
    private Rides ridesFrag;
    private String mType;

    public MyRecyclerAdapter2(Context context, List<Ride> items, String type, Rides rideF) {
        mContext = context;
        mItems = items;
        mType = type;
        ridesFrag = rideF;
    }


    public void deleteItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item2_layout, parent, false);
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
        int positionSub = position;
        Ride item = mItems.get(position);
        Date now = new Date();
        String dateString = "2023/04/26 12:00:00";
        String dateString2 = item.getDate() + " " + item.getTime() + ":00";// replace with your date string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());

        try {
            Date dateToCompare = dateFormat.parse(dateString2);
            System.out.println(dateToCompare);
            int comparison = dateToCompare.compareTo(now);
            System.out.println(comparison);
            if (comparison > 0) {
                if (item.isSecured() == false) {
                    holder.button.setText("DELETE");
                    mType = "delete";
                }
                else {
                    // dateToCompare is before now, make button invisible
                    //make it a delete button here
                    holder.button.setVisibility(View.INVISIBLE);
                    holder.update.setVisibility(View.INVISIBLE);
                }
//                holder.button.setVisibility(View.INVISIBLE);
            } else {
                if (item.isSecured() == true) {
                    holder.update.setVisibility(View.INVISIBLE);
                    // dateToCompare is now or later, enable and show the button
                    if (mType == "rides" && item.isRiderConfirm() == false) {


                        holder.button.setEnabled(true);
                        holder.button.setVisibility(View.VISIBLE);

                    }
                    else if (mType == "drives" && item.isRiderConfirm() == false) {
                        holder.button.setEnabled(true);
                        holder.button.setVisibility(View.VISIBLE);
                    }
                    else {
                        holder.button.setVisibility(View.INVISIBLE);
                    }
                }
                else {
                    //make it a delte button here
                    holder.button.setText("DELETE");
                    mType = "delete";
//                    holder.button.setVisibility(View.INVISIBLE);
                }
            }


        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        System.out.print("We in the adapter nowwwww " + item.toString());
        holder.time.setText(item.getTime());

        holder.destination.setText(item.getDestination());
        holder.date.setText(item.getDate());
        holder.points.setText(String.valueOf(item.getPoints()));

        holder.secured.setText(item.isSecured() ? "TRUE" : "FALSE");


        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rides ridesFragment = ridesFrag;

                String rideId = item.getId();
                System.out.println("ride ID in update onclick listener");
                System.out.println(rideId);
                String time = item.getTime();
                String destination = item.getDestination();
                String date = item.getDate();
                System.out.println("update ride presed in adapter");
                ridesFragment.updateRideClick(rideId, time, destination, date );

                // Code to execute when the button is clicked
            }
        });
        if (mType == "rides") {

            holder.email.setText(item.getDriverName());
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //set riderconfirm to true
                    DatabaseReference ridesRef = FirebaseDatabase.getInstance().getReference("rides");
                    DatabaseReference currentUserRef = ridesRef.child(item.getId());
                    System.out.println("line 103");
                    System.out.println(item.getId());

                    currentUserRef.child("riderConfirm").setValue(true);
                    //take points if driverconfirm true
                    if (item.isDriverConfirm() == true) {

                        DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference("users");
                        DatabaseReference user = myRef2.child(mAuth.getCurrentUser().getUid());
                        DatabaseReference points = user.child("points");
                        points.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
// This method is called once, initially, and when data is updated
                                int pointsCurr = dataSnapshot.getValue(Integer.class);
                                int addPoints = item.getPoints();
                                int newValue = pointsCurr - addPoints;
                                user.child("points").setValue(newValue);

                                //could set text here
                            }
                            @Override
                            public void onCancelled( DatabaseError error ) {
// Failed to read value

                            }



                        });

                        //take away
                        DatabaseReference user2 = myRef2.child(item.getDriverId());
                        DatabaseReference points2 = user2.child("points");

                        points2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
// This method is called once, initially, and when data is updated
                                int pointsCurr = dataSnapshot.getValue(Integer.class);
                                int addPoints = item.getPoints();
                                int newValue = pointsCurr + addPoints;
                                user2.child("points").setValue(newValue);

                                //could set text here
                            }
                            @Override
                            public void onCancelled( DatabaseError error ) {
// Failed to read value

                            }



                        });
                        //take points if drivr confirm & add points to driver

                    }


                    Toast.makeText(mContext, "Ride Confirmed", Toast.LENGTH_SHORT).show();
                    holder.button.setVisibility(View.INVISIBLE);
                    // Perform desired action when button is clicked
                }
            });


        }
        else if (mType == "drives") {

            holder.email.setText(item.getRiderName());

            holder.emailTitle.setText("RIDER EMAIL");

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //set driverConfirm to true
                    DatabaseReference ridesRef = FirebaseDatabase.getInstance().getReference("rides");
                    DatabaseReference currentUserRef = ridesRef.child(item.getId());
                    currentUserRef.child("driverConfirm").setValue(true);
                    if (item.isRiderConfirm() == true) {
                        //add points if  riderconfirm true & take points from rider
                        DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference("users");
                        DatabaseReference user = myRef2.child(mAuth.getCurrentUser().getUid());
                        DatabaseReference points = user.child("points");
                        points.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
// This method is called once, initially, and when data is updated
                                int pointsCurr = dataSnapshot.getValue(Integer.class);
                                int addPoints = item.getPoints();
                                int newValue = pointsCurr + addPoints;
                                user.child("points").setValue(newValue);

                                //could set text here
                            }
                            @Override
                            public void onCancelled( DatabaseError error ) {
// Failed to read value

                            }



                        });

                        //take away
                        DatabaseReference user2 = myRef2.child(item.getRiderId());
                        DatabaseReference points2 = user2.child("points");
                        points2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
// This method is called once, initially, and when data is updated
                                int pointsCurr = dataSnapshot.getValue(Integer.class);
                                int addPoints = item.getPoints();
                                int newValue = pointsCurr - addPoints;
                                user2.child("points").setValue(newValue);

                                //could set text here
                            }
                            @Override
                            public void onCancelled( DatabaseError error ) {
// Failed to read value

                            }



                        });

                    }

                    // Perform desired action when button is clicked
                    Toast.makeText(mContext, "Drive Confirmed", Toast.LENGTH_SHORT).show();
                    holder.button.setVisibility(View.INVISIBLE);
                }
            });
        }


        else {
            //mType == delete
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //set driverConfirm to true
                    DatabaseReference ridesRef = FirebaseDatabase.getInstance().getReference("rides");
                    DatabaseReference currentRideRef = ridesRef.child(item.getId());
                    currentRideRef.removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // node was successfully deleted
                                    //toast here
                                    Toast.makeText(mContext, "Ride deleted", Toast.LENGTH_SHORT).show();

                                    deleteItem(positionSub);

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // handle the error case
                                }
                            });
                    // Perform desired action when button is clicked
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView time;
        public TextView secured;
        public TextView destination;
        public TextView date;
        public Button update;
        public Button button;
        public TextView points;
        public TextView personId;
        public TextView email;

        public TextView personIdTitle;
        public TextView emailTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.textView24);
            secured = itemView.findViewById(R.id.textView25);
            destination = itemView.findViewById(R.id.textView23);
            date = itemView.findViewById(R.id.textView32);
            button = itemView.findViewById(R.id.button7);
            update = itemView.findViewById(R.id.button8);
            points = itemView.findViewById(R.id.textView36);

            email = itemView.findViewById(R.id.textView40);

            emailTitle = itemView.findViewById(R.id.textView39);



        }
    }
}
