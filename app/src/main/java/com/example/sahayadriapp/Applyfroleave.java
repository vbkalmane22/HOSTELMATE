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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Applyfroleave extends AppCompatActivity {
    private EditText name, usn, reason, parent_contact, dateoa;
    private Button btn;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    public static final String EXTRA_NAME="com.example.sahayadriapp.extra.name";
    public static final String EXTRA_USN="com.example.sahayadriapp.extra.USN";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applyfroleave);
        EditText name = findViewById(R.id.editTextTextPersonName);
        EditText usn = findViewById(R.id.editTextTextPersonName1);
        EditText reason = findViewById(R.id.editTextTextPersonName21);
        EditText parent_contact = findViewById(R.id.editTextTextPersonName2);
        EditText dateoa = findViewById(R.id.editTextDate);
        Button btn = findViewById(R.id.button);


        db = FirebaseFirestore.getInstance();
        Intent intent=getIntent();

        String usn2=intent.getStringExtra(AfterLogin.EXTRA_USN);
        usn.setText(usn2);
        usn.setFocusableInTouchMode(false);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog=new ProgressDialog(Applyfroleave.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Submitting the data...");
                progressDialog.show();

                String name1 = name.getText().toString().trim();
                String usn1 = usn.getText().toString().trim();
                String reason1 = reason.getText().toString().trim();
                String parent_contact1 = parent_contact.getText().toString().trim();
                String dateoa1 = dateoa.getText().toString().trim();
                  if(!name1.isEmpty())
                  {
                      name.setError((null));
                  }
                if(!usn1.isEmpty())
                {
                    usn.setError((null));
                }
                if(!reason1.isEmpty())
                {
                    reason.setError((null));
                }
                if(!parent_contact1.isEmpty())
                {
                    parent_contact.setError((null));
                }
                if(!dateoa1.isEmpty()) {
                    dateoa.setError((null));

                    Map<String, Object> user = new HashMap<>();
                    user.put("name", name1);
                    user.put("usn", usn1);
                    user.put("reason", reason1);
                    user.put("parent_contact", parent_contact1);
                    user.put("date_of_apply", dateoa1);
                    user.put("status", "PENDING");
                    user.put("value","1");

                    db.collection("leaves").document(usn1).set(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        if(progressDialog.isShowing())
                                            progressDialog.dismiss();
                                        Intent intent = new Intent(Applyfroleave.this, Confirmation.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(Applyfroleave.this, "Error", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                }






            }
        });
    }
}