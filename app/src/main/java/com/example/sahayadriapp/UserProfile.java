package com.example.sahayadriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.auth.User;

public class UserProfile extends AppCompatActivity {
    BottomNavigationView btm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
      //  getSupportActionBar().hide();
        btm = findViewById(R.id.bottom_navigation);
        Button cp=findViewById(R.id.button17);
        btm.setSelectedItemId(R.id.user);
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
                startActivity(intent);
                finish();
            }
        });

    }
}