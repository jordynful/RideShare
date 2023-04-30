package edu.uga.cs.rideshare;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {
    private EditText mEmailEditText;
    public static final String TAG = "LOGIN ACTIVITY";
    private EditText mPasswordEditText;
    private TextView signUp;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mEmailEditText = findViewById(R.id.editTextTextEmailAddress);
        mPasswordEditText = findViewById(R.id.editTextTextPassword);
        signUp = findViewById(R.id.textView34);
        signUp.setPaintFlags(signUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to sign in
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);

                startActivity(intent);

            }
        });
        Button loginButton = findViewById(R.id.button3);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseSingleton myApp = (FirebaseSingleton) getApplicationContext();
                FirebaseAuth mAuth = myApp.getFirebaseAuth();

//                mAuth = FirebaseAuth.getInstance();
                // Get the email and password from the EditText views
                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();


                mAuth.signInWithEmailAndPassword( email, password )
                        .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
// Sign in success
                                    Log.d( TAG, "signInWithEmail:success" );
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(LogInActivity.this, MainActivity2.class);

                                    startActivity(intent);
                                }
                                else {
// If sign in fails
                                    Log.d( TAG, "signInWithEmail:failure", task.getException() );
                                    Toast.makeText(LogInActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                // Send the email and password to your database to check if they are valid
                // Here you would call a method or function that handles the database logic
                // and checks if the email and password are valid.
            }
        });

        }
    public FirebaseAuth getFirebaseAuthInstance() {
        return mAuth;
    }
}