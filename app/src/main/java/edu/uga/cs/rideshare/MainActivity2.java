package edu.uga.cs.rideshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity2 extends AppCompatActivity {
private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        button = findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code to execute when the button is clicked
                FirebaseSingleton myApp = (FirebaseSingleton) getApplicationContext();
                FirebaseAuth mAuth = myApp.getFirebaseAuth();
                mAuth.signOut();
                Intent intent = new Intent(MainActivity2.this, StartActivity.class);
                startActivity(intent);


            }
        });

//        Home homeFragment = new Home();
//        RequestRide requestRideFragment = new RequestRide();
//        OfferRide offerRideFragment = new OfferRide();
//        CurrentRequests currentRequestsFragment = new CurrentRequests();
//        CurrentOffers currentOffersFragment = new CurrentOffers();
        Rides ridesFragment = new Rides();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, ridesFragment)
                .commit();
    }
}