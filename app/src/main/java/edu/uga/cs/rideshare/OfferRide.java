package edu.uga.cs.rideshare;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OfferRide#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OfferRide extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;

    private String timeString;
    private String dateString;
    private String destinationString;

    private EditText timeEdit;
    private EditText dateEdit;
    private EditText destinationEdit;

    private Button button;
    private String driverName;
    public static final String TAG = "REQUEST RIDE FRAGMENT";
    public OfferRide() {
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
     * @return A new instance of fragment OfferRide.
     */
    // TODO: Rename and change types and number of parameters
    public static OfferRide newInstance(String param1, String param2) {
        OfferRide fragment = new OfferRide();
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

        View offerRideView = inflater.inflate(R.layout.fragment_offer_ride, container, false);

        FirebaseSingleton myApp = (FirebaseSingleton) mContext.getApplicationContext();
        FirebaseAuth mAuth = myApp.getFirebaseAuth();

        timeEdit = offerRideView.findViewById(R.id.editTextTime);
        destinationEdit = offerRideView.findViewById(R.id.editTextDestination);
        dateEdit = offerRideView.findViewById(R.id.editTextDate2);
        button = offerRideView.findViewById(R.id.button6);
        button.setOnClickListener(new View.OnClickListener() {
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

                            driverName = dataSnapshot.getValue(String.class);
                            // Do something with the attribute name here

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle errors here
                        }
                    });
                    timeString = timeEdit.getText().toString();
                    destinationString = destinationEdit.getText().toString();
                    dateString = destinationEdit.getText().toString();


                    Log.d( TAG, "User not null");
//            FirebaseAuth mAuth = FirebaseAuth.getInstance();

                    DatabaseReference myRef = database.getReference("rides");

                    DatabaseReference newChildRef = myRef.push();
                    newChildRef.child("driverId").setValue(mAuth.getCurrentUser().getUid());
                    newChildRef.child("riderId").setValue("");
                    newChildRef.child("driver").setValue(driverName);
                    newChildRef.child("rider").setValue("");
                    newChildRef.child("destination").setValue(destinationString);
                    newChildRef.child("time").setValue(timeString);
                    newChildRef.child("date").setValue(dateString);
                    newChildRef.child("type").setValue("offer");
                    newChildRef.child("points").setValue(5); //can change this
                    newChildRef.child("secured").setValue(false);
                    newChildRef.child("finished").setValue(false);
                    newChildRef.child("driverConfirm").setValue(false);
                    newChildRef.child("riderConfirm").setValue(false);



// The new child node will have a unique key
                    String newChildKey = newChildRef.getKey();

                    Log.d(TAG, newChildKey);
                    Toast.makeText(mContext, "Ride offer made", Toast.LENGTH_SHORT).show();
//toast
                } else {
                    // User is not signed in
                }



                // Do something when the button is clicked
            }
        });

        return offerRideView;
    }
}