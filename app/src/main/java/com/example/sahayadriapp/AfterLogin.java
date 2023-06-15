package com.example.sahayadriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class AfterLogin extends AppCompatActivity {
    private Button applyleave;
    private Button payfees;
    private Button notices;
    private Button issues;
    private TextView textname;
    private TextView textusn;
    private TextView textbranch;
     BottomNavigationView btm;
    public static final String EXTRA_NAME="com.example.sahayadriapp.extra.name";
    public static final String EXTRA_USN="com.example.sahayadriapp.extra.USN";
    public static String USN="com.example.sahyadriapp.USN";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        getSupportActionBar().hide();
         Button applyleave=findViewById(R.id.button3);
        Button payfees=findViewById(R.id.button4);
        Button notices=findViewById(R.id.button6);
        Button issues=findViewById(R.id.button5);
        TextView textname=findViewById(R.id.textView2);
        TextView textusn=findViewById(R.id.textView3);
        TextView textbranch=findViewById(R.id.textView4);
        btm = findViewById(R.id.bottom_navigation);

        Intent intent=getIntent();
        String usn2= intent.getStringExtra(LogIN.EXTRA_USN);
        textusn.setText(usn2);

        String leave_history_usn=textusn.getText().toString();

        if(usn2.contains("CS")|| usn2.contains("cs"))
        {
            textbranch.setText("BE/CSE");
        }
        else if(usn2.contains("EC") || usn2.contains("ec"))
        {
            textbranch.setText("BE/ECE");
        }
        else if(usn2.contains("ME") || usn2.contains("me"))
        {
            textbranch.setText("BE/ME");
        }
        else if(usn2.contains("IS") || usn2.contains("is"))
        {
            textbranch.setText("BE/ISE");
        }
        else{
            textbranch.setText("MBA");
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("datauser");
        reference.orderByChild("usn").equalTo(usn2).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String name1 = childSnapshot.child("name").getValue(String.class);
                    textname.setText(name1);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });



         String name11=textname.getText().toString();
        String usn11=textusn.getText().toString();
        applyleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AfterLogin.this,Applyfroleave.class);

                intent.putExtra(EXTRA_USN,usn11);
                startActivity(intent);
            }
        });

        payfees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AfterLogin.this,PayFees.class);
                startActivity(intent);
            }
        });


        issues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AfterLogin.this,Issues.class);
                startActivity(intent);
            }
        });


        notices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AfterLogin.this,Notices.class);
                startActivity(intent);
            }
        });
        String usn12=textusn.getText().toString();

        btm.setSelectedItemId(R.id.home);
        btm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.leave:
                       Intent intent = new Intent(getApplicationContext(),leavehistory.class);
                       intent.putExtra(USN,leave_history_usn);
                       startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;



                }

                return false;
            }
        });

    }
}