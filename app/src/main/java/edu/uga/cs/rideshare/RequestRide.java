package edu.uga.cs.rideshare;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RequestRide#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestRide extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    public static final String TAG = "REQUEST RIDE FRAGMENT";
    public RequestRide() {
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
     * @return A new instance of fragment RequestRide.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestRide newInstance(String param1, String param2) {
        RequestRide fragment = new RequestRide();
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

        View requestRideView = inflater.inflate(R.layout.fragment_request_ride, container, false);

        FirebaseSingleton myApp = (FirebaseSingleton) mContext.getApplicationContext();
        FirebaseAuth mAuth = myApp.getFirebaseAuth();



        if (mAuth.getCurrentUser() != null) {
            Log.d( TAG, "User not null");
//            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("users");
            DatabaseReference currentUserRef = myRef.child(mAuth.getCurrentUser().getUid());
            DatabaseReference pointsRef = currentUserRef.child("points");


        } else {
            // User is not signed in
        }

        return requestRideView;
    }
}