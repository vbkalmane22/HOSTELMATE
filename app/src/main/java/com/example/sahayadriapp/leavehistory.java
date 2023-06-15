package com.example.sahayadriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class leavehistory extends AppCompatActivity {
    BottomNavigationView btm;

    private Button btn;
    public static String USN="com.example.sahyadriapp.USN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leavehistory);
        getSupportActionBar().hide();
        btm = findViewById(R.id.bottom_navigation);
        TextView derived_usn=findViewById(R.id.textView21);
        TextView reason_leave =findViewById(R.id.textView13);
        TextView date_leave=findViewById(R.id.textView14);
        TextView status_leave=findViewById(R.id.textView15);
        Button btn=findViewById(R.id.button9);

    Intent intent = getIntent();
      String leave_history_usn=intent.getStringExtra(AfterLogin.USN);
       derived_usn.setText(leave_history_usn);

        FirebaseFirestore db = FirebaseFirestore.getInstance();





        btm.setSelectedItemId(R.id.leave);

        btm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:startActivity(new Intent(getApplicationContext(), AfterLogin.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.leave:
                        return true;



                }

                return false;
            }
        });




    }
}

