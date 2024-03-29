package com.example.sahayadriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PayFees extends AppCompatActivity {
  private Button pay_now,get_details;
    private EditText name, usn, room_no, pending_fees;
    public static final String FEES_USN="com.example.sahyadriapp.feeUSN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_fees);
        pay_now=findViewById(R.id.button2);
        get_details=findViewById(R.id.button);
        name=findViewById(R.id.editTextTextPersonName);
        usn=findViewById(R.id.editTextTextPersonName1);
        room_no=findViewById(R.id.editTextTextPersonName21);
        pending_fees=findViewById(R.id.editTextTextPersonName2);

        Intent intent = getIntent();
        String usn45=intent.getStringExtra(FEES_USN);
        usn.setText(usn45);
        usn.setFocusableInTouchMode(false);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("datauser");
        reference.orderByChild("usn").equalTo(usn45).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String name1 = childSnapshot.child("name").getValue(String.class);
                    String room_no1=childSnapshot.child("room_no").getValue(String.class);
                    name.setText(name1);
                    room_no.setText(room_no1);
                    name.setFocusableInTouchMode(false);



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });

       get_details.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DatabaseReference reference = FirebaseDatabase.getInstance().getReference("datauser");
               reference.orderByChild("usn").equalTo(usn45).addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                           String pending_fees1 = childSnapshot.child("pending_fees").getValue(String.class);
                           String room_no1=childSnapshot.child("room_no").getValue(String.class);
                           pending_fees.setText(pending_fees1);
                           room_no.setText(room_no1);


                       }
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {
                       // Handle error
                   }
               });

           }
       });



        pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String website_url="https://www.aptraedu.com/Sahyadri/index";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(website_url));
                startActivity(intent);
            }
        });


    }
}