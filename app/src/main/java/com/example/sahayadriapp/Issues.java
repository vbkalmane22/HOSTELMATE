package com.example.sahayadriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Issues extends AppCompatActivity {
  private EditText name,usn,roomno,description;
  private Button subbtn;
    public static final String ISSUE_USN="com.example.sahyadriapp.issueUSN";
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        EditText name= findViewById(R.id.editTextTextPersonName);
        EditText usn= findViewById(R.id.editTextTextPersonName1);
        EditText roomno= findViewById(R.id.editTextTextPersonName21);
        EditText description= findViewById(R.id.editTextTextPersonName2);
        Button subbtn=findViewById(R.id.button);
        Intent intent = getIntent();
        String usn456=intent.getStringExtra(AfterLogin.ISSUE_USN);
        usn.setText(usn456);
        usn.setFocusableInTouchMode(false);

        db = FirebaseFirestore.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("datauser");
        reference.orderByChild("usn").equalTo(usn456).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String name1 = childSnapshot.child("name").getValue(String.class);
                    name.setText(name1);
                    name.setFocusableInTouchMode(false);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });


        subbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = name.getText().toString().trim();
                String usn1 = usn.getText().toString().trim();
                String room1 = roomno.getText().toString().trim();
                String description1 = description.getText().toString().trim();

                Map<String, Object> user = new HashMap<>();
                user.put("name", name1);
                user.put("usn", usn1);
                user.put("room", room1);
                user.put("description", description1);
                db.collection("issues").document(usn1).set(user)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Intent intent=new Intent(Issues.this,Confirmation.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(Issues.this, "Error", Toast.LENGTH_SHORT).show();
                                }

            }
        });


    }
});
    }
}