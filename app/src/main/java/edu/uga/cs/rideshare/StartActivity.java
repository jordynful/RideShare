package edu.uga.cs.rideshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button loginButton = findViewById(R.id.button2);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CHANGE THIS BACK TO LOGIN BUT RN I CANT
                Intent intent = new Intent(StartActivity.this, LogInActivity.class);
                startActivity(intent);

//                Intent intent = new Intent(StartActivity.this, MainActivity2.class);
//                startActivity(intent);
            }
        });

        Button signupButton = findViewById(R.id.button4);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}