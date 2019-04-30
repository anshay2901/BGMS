package com.example.ansha.bgms;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText _email, _pass, _dob, _name;
    Button _register_btn, _pick_dob;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        _email = findViewById(R.id.email);
        _pass = findViewById(R.id.pass);
        _register_btn = findViewById(R.id.register_btn);
        _pick_dob = findViewById(R.id.pickDOB);
        _dob = findViewById(R.id.dob);
        _name = findViewById(R.id.name);

        auth = FirebaseAuth.getInstance();



        //Open Calender
        _pick_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");

            }
        });

        //Register
        _register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_pass.getText().toString().equals("") || _email.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    String email = _email.getText().toString().trim();
                    String pass = _pass.getText().toString().trim();
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {

                                final User user = new User (_name.getText().toString(),
                                        _email.getText().toString(),
                                        _dob.getText().toString());
                                //User Node
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getApplicationContext(), "User Created Successfully :)", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "User Not Created :(", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                //All users node
                                FirebaseDatabase.getInstance().getReference("bgms-86173").child("AllUsers")
                                        .setValue(user.getName()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Log.i("user stored", user.getName());
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), "error creating all users node", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                            else {
                                Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

    }
    //Calender Set
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.getTime());
        _dob.setText(currentDateString);

    }
}
/*
todo:
 */