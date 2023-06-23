package com.example.sahayadriapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                    name.setText(name1);
                    name.setFocusableInTouchMode(false);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });



        pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String website_url="https://chat.openai.com/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(website_url));
                startActivity(intent);
            }
        });


    }
}