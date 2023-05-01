package edu.uga.cs.rideshare;

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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
private EditText name;

private TextView signIn;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button button;

    public static final String TAG = "SIGN UP ACTIVITY";

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextTextEmailAddress2);
        name = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword2);
        confirmPassword = findViewById(R.id.editTextTextPassword3);
        button = findViewById(R.id.button5);
        signIn = findViewById(R.id.textView33);
        signIn.setPaintFlags(signIn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to sign in
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);

                startActivity(intent);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailString = email.getText().toString();
                String pass = password.getText().toString();
                String confirmPass = confirmPassword.getText().toString();

                if (!pass.equals(confirmPass)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();

                }
                else if (pass.length() < 6) {
                    Toast.makeText(SignUpActivity.this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();

                }
                        else {
                    mAuth.createUserWithEmailAndPassword(emailString, pass)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        // Sign in success

                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        // Add user details to the database
                                        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                                        usersRef.child(user.getUid()).child("email").setValue(emailString);
                                        usersRef.child(user.getUid()).child("name").setValue(name.getText().toString());
                                        usersRef.child(user.getUid()).child("points").setValue(10);
                                        Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);

                                        startActivity(intent);



                                        Toast.makeText(SignUpActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                            Toast.makeText(SignUpActivity.this, "This email is already registered.", Toast.LENGTH_SHORT).show();
                                        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                            Toast.makeText(SignUpActivity.this, "Invalid email format.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                }
            }
        });
    }
}