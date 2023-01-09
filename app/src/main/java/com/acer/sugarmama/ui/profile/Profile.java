package com.acer.sugarmama.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.acer.sugarmama.MainActivity;
import com.acer.sugarmama.R;
import com.acer.sugarmama.SplashScreen;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    private TextView btnSignOut, tvName, tvEmail;
    private ImageView imgAvt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnSignOut = findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(this);
        findViewById(R.id.btnProfileBack).setOnClickListener(this);
        findViewById(R.id.btnEditProfile).setOnClickListener(this);

        tvName = findViewById(R.id.tvProfileName);
        tvEmail = findViewById(R.id.tvProfileEmail);
        imgAvt = findViewById(R.id.imgAvatar);

        setUserProfile();
    }

    private void setUserProfile(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        tvName.setText(name);
        tvEmail.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.img_avt).into(imgAvt);
    }
    @SuppressLint({"NonConstantResourceId", "ResourceAsColor"})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignOut:
                FirebaseAuth.getInstance().signOut();
                Intent splashIntent = new Intent(this, SplashScreen.class);
                startActivity(splashIntent);
                finish();
                break;
            case R.id.btnProfileBack:
                Intent homeIntent = new Intent(this, MainActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.btnEditProfile:
                Intent editIntent = new Intent(this, Edit.class);
                startActivity(editIntent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(this, MainActivity.class);
        startActivity((homeIntent));
    }
}