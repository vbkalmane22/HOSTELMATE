package com.example.sahayadriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePassword extends AppCompatActivity {
EditText usn_var,password_var,password2_var;
Button verify_btn,reset_btn;
    String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$";
    ProgressDialog progressDialog;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        usn_var=findViewById(R.id.editusn);
        password_var=findViewById(R.id.editTextPassword);
        password2_var=findViewById(R.id.editTextPassword2);
        verify_btn=findViewById(R.id.button7);
        reset_btn=findViewById(R.id.button8);

        reset_btn.setEnabled(false);
        password2_var.setEnabled(false);

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usn=usn_var.getText().toString();
                String password=password_var.getText().toString();
                progressDialog=new ProgressDialog(ChangePassword.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Verifying...");
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
                                         Toast.makeText(ChangePassword.this, "Verified..Enter the new password", Toast.LENGTH_SHORT).show();
                                        password2_var.setEnabled(true);
                                        verify_btn.setEnabled(false);
                                       // reset_btn.setEnabled(true);
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

        reset_btn.setEnabled(true);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password2=password2_var.getText().toString();
                String usn=usn_var.getText().toString();
                String password=password_var.getText().toString();
                if(!password2.isEmpty()){
                    password2_var.setError((null));
                    if(!usn.isEmpty()){
                        usn_var.setError(null);
                        if(!password.isEmpty())
                        {
                            password_var.setError(null);
                            if (validatePassword(password2))
                            {
                                    firebaseDatabase = FirebaseDatabase.getInstance();
                                    reference = firebaseDatabase.getReference("datauser");

                                    String usn_s = usn_var.getText().toString();
                                    String password2_s = password2_var.getText().toString();

                                   stroingdatas stroingdatas = new stroingdatas(usn_s,password2_s);
                                    reference.child(usn_s).setValue(stroingdatas);
                                    Toast.makeText(ChangePassword.this, "Successfully Changed", Toast.LENGTH_SHORT).show();


                            }
                                else
                                {
                                    password2_var.setError("Enter Correct Password");
                                }



                        }
                        else
                        {
                            password_var.setError("Please enter password");
                        }
                    }
                    else
                    {
                        usn_var.setError("Please enter USN");
                    }
                }

            }
        });



    }
private boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher =pattern.matcher(password);
        return matcher.matches();
        }


    }

