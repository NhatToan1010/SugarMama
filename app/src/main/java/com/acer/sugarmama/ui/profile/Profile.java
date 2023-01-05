package com.acer.sugarmama.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.acer.sugarmama.R;
import com.acer.sugarmama.SplashScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    private TextView btnSignOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnSignOut = findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignOut:
                FirebaseAuth.getInstance().signOut();
                Intent splashIntent = new Intent(this, SplashScreen.class);
                startActivity(splashIntent);
                finish();
                break;
        }
    }
}