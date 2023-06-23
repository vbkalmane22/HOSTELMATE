package com.example.sahayadriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LogIN extends AppCompatActivity {
    private Button btn1;
    private Button login;
    ProgressDialog progressDialog;
    EditText usn_var,password_var;
public static final String SHARED_PREFS="sharedPrefs";
    public static final String EXTRA_USN="com.example.sahayadriapp.extra.USN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        getSupportActionBar().hide();


        //checkbox();
       Button btn1=findViewById(R.id.button2);
        Button login =findViewById(R.id.button7);
        usn_var=findViewById(R.id.editusn);
        password_var=findViewById(R.id.editTextTextPassword);


     btn1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent=new Intent(LogIN.this,SignUp.class);
             startActivity(intent);
             finish();
         }
     });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usn=usn_var.getText().toString();
                String password=password_var.getText().toString();
                progressDialog=new ProgressDialog(LogIN.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Logging in...");
                progressDialog.show();
                if(!usn.isEmpty())
                {
                    usn_var.setError((null));

                    if(!password.isEmpty())
                    {
                        password_var.setError(null);
                        final String usn_data=usn_var.getText().toString();
                        final String password_data=password_var.getText().toString();

                        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference=firebaseDatabase.getReference("datauser");
                        Query check_username=databaseReference.orderByChild("usn").equalTo(usn_data);

                        check_username.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){

                                    usn_var.setError(null);
                                    String passcheck=snapshot.child(usn_data).child("password").getValue(String.class);
                                    if(passcheck.equals(password_data)){

                                        password_var.setError(null);

                                        if(progressDialog.isShowing())
                                            progressDialog.dismiss();
                                       // Toast.makeText(LogIN.this, "Log In successfull", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(LogIN.this,AfterLogin.class);
                                        intent.putExtra(EXTRA_USN,usn);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        password_var.setError("Wrong Password");
                                        progressDialog.dismiss();
                                    }
                                }
                                else
                                {
                                    usn_var.setError("USN Does not exists");
                                    progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    else {
                        password_var.setError("Please enter password");
                        progressDialog.dismiss();
                    }

                }
                else
                {
                    usn_var.setError("Please enter USN");
                    progressDialog.dismiss();
                }

            }
        });



    }


}