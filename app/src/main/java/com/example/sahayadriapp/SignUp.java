package com.example.sahayadriapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    private Button btn1;
    private Button signup;
    EditText name_var,usn_var,password_var;
    public static final String EXTRA_NAME="com.example.sahayadriapp.extra.NAME";
     String USN_PATTERN = "^4SF[0-9]{2}[A-Za-z]{2}[0-9]{3}$";
     String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$";


    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        //getSupportActionBar().hide();



        Button signup =findViewById(R.id.button7);
        name_var=findViewById(R.id.editname);
        usn_var=findViewById(R.id.editTextTextPersonName5);
        password_var=findViewById(R.id.editTextTextPassword);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=name_var.getText().toString();
                String usn=usn_var.getText().toString();
                String password=password_var.getText().toString();
                if(!name.isEmpty()){
                    name_var.setError((null));
                    if(!usn.isEmpty()){
                        usn_var.setError(null);
                        if(!password.isEmpty())
                        {
                            password_var.setError(null);
                            if(validateUSN(usn))
                            {
                                if (validatePassword(password)) {
                                    firebaseDatabase = FirebaseDatabase.getInstance();
                                    reference = firebaseDatabase.getReference("datauser");
                                    String name_s = name_var.getText().toString();
                                    String usn_s = usn_var.getText().toString();
                                    String password_s = password_var.getText().toString();

                                    storingdata storingdatass = new storingdata(name_s, usn_s, password_s);
                                    reference.child(usn_s).setValue(storingdatass);
                                    Toast.makeText(SignUp.this, "Successfull", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUp.this, LogIN.class);
                                    intent.putExtra(EXTRA_NAME, name);

                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    password_var.setError("Enter Correct Password");
                                }
                            }
                            else
                            {
                                usn_var.setError("Enter Correct USN");
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
                else
                {
                    name_var.setError("Please Enter the name");
                }
            }
        });

    }

    private boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher =pattern.matcher(password);
        return matcher.matches();
    }

    private boolean validateUSN(String usn) {
        Pattern pattern = Pattern.compile(USN_PATTERN);
        Matcher matcher =pattern.matcher(usn);
        return matcher.matches();
    }


}