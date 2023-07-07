package com.example.sahayadriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

public class UserProfile extends AppCompatActivity {
    BottomNavigationView btm;
    EditText name_var,usn_var,branch_var;
   public static final String USER_BRANCH = "com.example.sahyadriapp.userbranch";
    public static final String USER_NAME = "com.example.sahyadriapp.userNAME";
    public static final String CHANGE_USN = "com.example.sahyadriapp.changeUSN";
    public static final String CHANGE_NAME = "com.example.sahyadriapp.changeNAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
      //  getSupportActionBar().hide();
       btm = findViewById(R.id.bottom_navigation);
        name_var=findViewById(R.id.editTextTextPersonName);
        usn_var=findViewById(R.id.editTextTextPersonName1);
        branch_var=findViewById(R.id.editTextTextPersonName21);
        Button cp=findViewById(R.id.button17);
        btm.setSelectedItemId(R.id.user);

        Bundle bundle= getIntent().getExtras();
        String user_usn = bundle.getString("USER_USN");
        String user_branch=bundle.getString("USER_BRANCH");
        branch_var.setText(user_branch);
        branch_var.setEnabled(false);
        usn_var.setText(user_usn);
        usn_var.setEnabled(false);
        name_var.setEnabled(false);
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

        btm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.leave:
                        Intent intent = new Intent(getApplicationContext(),leavehistory.class);
                        startActivity(intent);

                    case R.id.home:
                       Intent intent1 = new Intent(getApplicationContext(),AfterLogin.class);
                       startActivity(intent1);


                    case R.id.user:
                        return true;



                }

                return false;
            }
        });

        cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(UserProfile.this,ChangePassword.class);
                intent.putExtra(CHANGE_USN,user_usn);

                startActivity(intent);
                finish();
            }
        });

    }
}