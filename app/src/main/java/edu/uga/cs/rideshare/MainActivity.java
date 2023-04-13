package edu.uga.cs.rideshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "RideShare";
    private FirebaseAuth mAuth;
private ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        constraintLayout = findViewById(R.id.constraintLayout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation fadeOut = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);
                constraintLayout.startAnimation(fadeOut);

                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
            }
        }, 2000);






        mAuth = FirebaseAuth.getInstance();
        String email = "dawg@mail.com";
        String password = "password";



        mAuth.signInWithEmailAndPassword( email, password )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
// Sign in success
                            Log.d( TAG, "signInWithEmail:success" );
                            FirebaseUser user = mAuth.getCurrentUser();
                        }
                        else {
// If sign in fails
                            Log.d( TAG, "signInWithEmail:failure", task.getException() );
                        }
                    }
                });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //this key doesnt exist in the db rn
        DatabaseReference myRef = database.getReference( "message" );

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
// This method is called once, initially, and when data is updated
                String message = dataSnapshot.getValue(String.class);
    //could set text here
            }
            @Override
            public void onCancelled( DatabaseError error ) {
// Failed to read value
                Log.d( TAG, "Failed to read value.", error.toException() );
            }
        });


    }
}