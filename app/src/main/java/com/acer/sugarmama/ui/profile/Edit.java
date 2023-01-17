package com.acer.sugarmama.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.acer.sugarmama.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Edit extends AppCompatActivity {
    private TextInputLayout edtName, edtEmail, edtPhone, edtAddress;
    private ImageView btnSave;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edtName = findViewById(R.id.edtEditName);
        edtEmail = findViewById(R.id.edtEditEmail);
        edtPhone = findViewById(R.id.edtEditPhone);
        edtAddress = findViewById(R.id.edtEditAddress);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void setUserProfile(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();

        edtName.getEditText().setText(name);
        edtEmail.getEditText().setText(email);
    }
}