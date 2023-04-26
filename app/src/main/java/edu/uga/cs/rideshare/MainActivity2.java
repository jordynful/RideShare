package edu.uga.cs.rideshare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        Home homeFragment = new Home();
//        RequestRide requestRideFragment = new RequestRide();
//        OfferRide offerRideFragment = new OfferRide();
//        CurrentRequests currentRequestsFragment = new CurrentRequests();
        CurrentOffers currentOffersFragment = new CurrentOffers();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, currentOffersFragment)
                .commit();
    }
}