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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentOffers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentOffers extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private MyRecyclerAdapter mAdapter;
    private List<Ride> mItems;
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = "CURRENT OFFERS FRAGMENT";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    public CurrentOffers() {
        // Required empty public constructor
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
     * @return A new instance of fragment CurrentRequests.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrentOffers newInstance(String param1, String param2) {
        CurrentOffers fragment = new CurrentOffers();
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

        View currentOffers = inflater.inflate(R.layout.fragment_current_offers, container, false);
        List<Ride> items = new ArrayList<>();
        // Inflate the layout for this fragment
        RecyclerView recyclerView = currentOffers.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));


//this is where we will call to get the rides objects from the db ->
        Log.d( TAG, "Before calling all offers");


        DatabaseReference ridesRef = FirebaseDatabase.getInstance().getReference("rides");
        Query query = ridesRef.orderByChild("type").equalTo("offer").orderByChild("secured").equalTo(true);;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d( TAG, "Inside first level of datasnapshot");
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.d( TAG, "inside second level of snapshot");
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

                MyRecyclerAdapter adapter = new MyRecyclerAdapter(mContext, items, "offer");

                // set the adapter on the RecyclerView
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", "onCancelled", error.toException());
            }

        });

        // create a new list to hold the items


        // create a new adapter and pass in the list of items

        return currentOffers;
    }
}