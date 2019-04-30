package com.example.ansha.bgms;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText _emailid, _password;
    Button _login;
    ProgressBar _login_progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), UserHome.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();


        _emailid = findViewById(R.id.emailid);
        _password = findViewById(R.id.password);
        _login = findViewById(R.id.login);
        _login_progressBar = findViewById(R.id.login_progressBar);
        _login_progressBar.setVisibility(View.INVISIBLE);

        _login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _login_progressBar.setVisibility(View.VISIBLE);
                _login.setVisibility(View.INVISIBLE);
                String emailid = _emailid.getText().toString().trim();
                String password = _password.getText().toString().trim();
                Log.i("username", emailid);
                Log.i("password", password);
                if(emailid.equals("admin") && password.equals("1234")) {
                    startActivity(new Intent(getApplicationContext(), AdminHome.class));
                    _login_progressBar.setVisibility(View.INVISIBLE);
                    _login.setVisibility(View.VISIBLE);
                }
                else {
                    check_user(emailid, password);
                    _login_progressBar.setVisibility(View.INVISIBLE);
                    _login.setVisibility(View.VISIBLE);
                }
               // _login_progressBar.setVisibility(View.INVISIBLE);
               // _login.setVisibility(View.VISIBLE);
            }

            private void check_user(String emailid, String password) {
                if(emailid.equals("") || password.equals("")) {

                    Toast.makeText(getApplicationContext(), "Empty Fields..", Toast.LENGTH_SHORT).show();

                }
                else {
                    firebaseAuth.signInWithEmailAndPassword(emailid, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(), UserHome.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    });
                }
            }
        });
    }
}
