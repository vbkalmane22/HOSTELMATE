package com.example.sahayadriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

    public static final String CHANGE_USN = "com.example.sahyadriapp.changeUSN";
Button verify_btn,reset_btn;
TextView name_var;
    String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$";
    ProgressDialog progressDialog;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    DatabaseReference reference2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
 name_var=findViewById(R.id.textView12);
        usn_var=findViewById(R.id.editusn);
        password_var=findViewById(R.id.editTextPassword);
        password2_var=findViewById(R.id.editTextPassword2);
        verify_btn=findViewById(R.id.button7);
        reset_btn=findViewById(R.id.button8);

        reset_btn.setEnabled(false);
        password2_var.setEnabled(false);

        Intent intent1 = getIntent();
        String user_usn = intent1.getStringExtra(UserProfile.CHANGE_USN);
        usn_var.setText(user_usn);
        usn_var.setEnabled(false);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("datauser");
        reference.orderByChild("usn").equalTo(user_usn).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String name1 = childSnapshot.child("name").getValue(String.class);
                    name_var.setText(name1);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });

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

               String name=name_var.getText().toString();
               String usn=usn_var.getText().toString();
               String password=password2_var.getText().toString();

               progressDialog=new ProgressDialog(ChangePassword.this);
               progressDialog.setCancelable(false);
               progressDialog.setMessage("Resetting...");
               progressDialog.show();

               if(!name.isEmpty()){
                   name_var.setError((null));
                   if(!usn.isEmpty()){
                       usn_var.setError(null);
                       if(!password.isEmpty())
                       {
                           password2_var.setError(null);

                               if (validatePassword(password)) {
                                   firebaseDatabase = FirebaseDatabase.getInstance();
                                   reference2 = firebaseDatabase.getReference("datauser");
                                   String name_s = name_var.getText().toString();
                                   String usn_s = usn_var.getText().toString();
                                   String password_s = password2_var.getText().toString();

                                   storingdata storingdatass = new storingdata(name_s, usn_s, password_s);
                                   reference2.child(usn_s).setValue(storingdatass);
                                   Toast.makeText(ChangePassword.this, "Successfully Changed", Toast.LENGTH_SHORT).show();
                                   progressDialog.dismiss();
                                   reset_btn.setEnabled(false);

                               }
                               else
                               {
                                   password2_var.setError("Enter Correct Password");
                                   progressDialog.dismiss();
                               }


                       }
                       else
                       {
                           password2_var.setError("Please enter password");
                           progressDialog.dismiss();
                       }
                   }
                   else
                   {
                       usn_var.setError("Please enter USN");
                       progressDialog.dismiss();
                   }
               }
               else
               {
                   name_var.setError("Please Enter the name");
                   progressDialog.dismiss();
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

