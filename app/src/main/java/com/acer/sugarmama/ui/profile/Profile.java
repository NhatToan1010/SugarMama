package com.acer.sugarmama.ui.profile;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.acer.sugarmama.MainActivity;
import com.acer.sugarmama.R;
import com.acer.sugarmama.SplashScreen;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    private TextView btnSignOut, tvName, tvEmail;
    private ImageView imgAvt;
    public  static int MY_REQUEST_CODE = 7;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    /*
    * Init UI
    */
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

        imgAvt.setOnClickListener(this);

        setUserProfile();
    }

    private void setUserProfile(){
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

    /*
    *   OnClick Events
    */
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
            case R.id.imgAvatar:
                checkPermission();
                break;
        }
    }

    /*
    * Upload Avatar Profile
    */
    //Choose Picture From Gallery
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK){
                        Intent intent = result.getData();
                        if (intent == null){
                            return;
                        }
                        Uri uri = intent.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgAvt.setImageBitmap(bitmap);
                            updateAvatar(uri);
                            Glide.with(Profile.this).load(uri).error(R.drawable.img_avt).into(imgAvt);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    // Ask For Permission
    private void checkPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            this.requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    // Move To Gallery
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }else {
                Toast.makeText(this, "Permission denied!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateAvatar(Uri uri){
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Profile.this, "Avatar changed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(this, MainActivity.class);
        startActivity((homeIntent));
        finish();
    }
}