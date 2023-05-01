package edu.uga.cs.rideshare;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Rides#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Rides extends Fragment implements OfferRideDialog.OfferRideDialogListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static final String TAG = "RIDES FRAGMENT";
    private Context mContext;
    public Rides() {
        // Required empty public constructor
    }

    @Override
    public void updateRide(String rideId, String time, String destination, String date) {
        // update the ride
    System.out.println("updateRide called");


    }


    public void updateRideClick(String rideId, String time, String destination, String date) {
            System.out.println("update ride pressed");
            OfferRideDialog newFragment = new OfferRideDialog();
        System.out.println("OfferRideDialog newFragment = new OfferRideDialog();");
            newFragment.setHostFragment( Rides.this );
        System.out.println("newFragment.setHostFragment( Rides.this );");

            newFragment.show( getParentFragmentManager(), null);
        System.out.println("newFragment.show( getParentFragmentManager(), null);");
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context.getApplicationContext();
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Rides.
     */
    // TODO: Rename and change types and number of parameters
    public static Rides newInstance(String param1, String param2) {
        Rides fragment = new Rides();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ridesView = inflater.inflate(R.layout.fragment_rides, container, false);
        FirebaseSingleton myApp = (FirebaseSingleton) mContext.getApplicationContext();
        FirebaseAuth mAuth = myApp.getFirebaseAuth();

        List<Ride> items = new ArrayList<>();
        // Inflate the layout for this fragment
        RecyclerView recyclerView = ridesView.findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));


//this is where we will call to get the rides objects from the db ->
        Log.d( TAG, "Before calling all requests");

        DatabaseReference ridesRef = FirebaseDatabase.getInstance().getReference("rides");
        Query query = ridesRef.orderByChild("riderId").equalTo(mAuth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d( TAG, "Inside first level of datasnapshot rides");
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.d( TAG, "inside second level of snapshot rides");
                    String id = dataSnapshot.getKey();
                    // Get the ride object using dataSnapshot.getValue(Ride.class)
                    Ride ride = dataSnapshot.getValue(Ride.class);
                    ride.setId(id);
                    Log.d( TAG, id);
                    System.out.print(id);
                    Log.d( TAG, ride.toString());
                    items.add(ride);

                    // Do something with the ride object
                }
                Log.d( TAG, "Creating adapter");
                MyRecyclerAdapter2 adapter = new MyRecyclerAdapter2(mContext, items, "rides", Rides.this);

                // set the adapter on the RecyclerView
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "onCancelled", error.toException());
            }

        });
        List<Ride> items2 = new ArrayList<>();
        // Inflate the layout for this fragment
        RecyclerView recyclerView2 = ridesView.findViewById(R.id.recycler2);

        recyclerView2.setLayoutManager(new LinearLayoutManager(mContext));
        //for drives now
        Query query2 = ridesRef.orderByChild("driverId").equalTo(mAuth.getCurrentUser().getUid());
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.d( TAG, mAuth.getCurrentUser().getUid());

                Log.d( TAG, "Inside first level of datasnapshot rides");
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.d( TAG, "inside second level of snapshot rides");
                    String id = dataSnapshot.getKey();
                    // Get the ride object using dataSnapshot.getValue(Ride.class)
                    Ride ride = dataSnapshot.getValue(Ride.class);
                    ride.setId(id);
                    Log.d( TAG, id);
                    System.out.print(id);
                    Log.d( TAG, ride.toString());
                    items2.add(ride);

                    // Do something with the ride object
                }
                Log.d( TAG, "Creating adapter");
                MyRecyclerAdapter2 adapter2 = new MyRecyclerAdapter2(mContext, items2, "drives", Rides.this);

                // set the adapter on the RecyclerView
                recyclerView2.setAdapter(adapter2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "onCancelled", error.toException());
            }

        });

        return ridesView;
    }



}