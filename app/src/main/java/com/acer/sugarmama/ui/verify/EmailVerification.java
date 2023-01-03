package com.acer.sugarmama.ui.verify;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.acer.sugarmama.R;
import com.acer.sugarmama.ui.login.ForgotPassword;
import com.acer.sugarmama.ui.login.LoginActivity;
import com.google.android.material.textfield.TextInputLayout;

public class EmailVerification extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout edtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.btnSendOTP).setOnClickListener(this);
        findViewById(R.id.btnEmailResend).setOnClickListener(this);
        findViewById(R.id.btnEmailClose).setOnClickListener(this);

        edtEmail = findViewById(R.id.edtVerifyEmail);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSendOTP:
                if (!validateEmail()){
                    return;
                }
                Intent fPassIntent = new Intent(this, ForgotPassword.class);
                ActivityOptions fPassOption = ActivityOptions.makeScaleUpAnimation(
                        v, 0, 0, v.getWidth(), v.getHeight()
                );
                startActivity(fPassIntent, fPassOption.toBundle());
                break;
            case R.id.btnEmailClose:
                Intent logBackIntent = new Intent(this, LoginActivity.class);
                ActivityOptions logBackOption = ActivityOptions.makeScaleUpAnimation(
                        v, 0 , 0, v.getWidth(), v.getHeight());
                startActivity(logBackIntent, logBackOption.toBundle());
                break;
            case R.id.btnEmailResend:
                break;
        }

    }
    private boolean validateEmail(){
        String email = edtEmail.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.isEmpty()){
            edtEmail.setError("Field cannot be empty!");
            return false;
        }else if(!email.matches(checkEmail)){
            edtEmail.setError("White space are not allowed!");
            return false;
        }
        else {
            edtEmail.setError(null);
            edtEmail.setErrorEnabled(false);
            return true;
        }
    }
}