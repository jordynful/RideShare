package edu.uga.cs.rideshare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Home homeFragment = new Home();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, homeFragment)
                .commit();
    }
}