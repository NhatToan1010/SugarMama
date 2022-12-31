package com.acer.sugarmama;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout edtEmail, edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.btnLoginBack).setOnClickListener(this);
        findViewById(R.id.tvRegister).setOnClickListener(this);
        findViewById(R.id.btnForgot).setOnClickListener(this);
        findViewById(R.id.btnLogin).setOnClickListener(this);

        edtEmail = findViewById(R.id.edtLoginEmail);
        edtPassword = findViewById(R.id.edtLoginPassword);

        setErrorToFalse();
    }

    private void setErrorToFalse(){
        edtEmail.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    return;
                }
                edtEmail.setError(null);
                edtEmail.setErrorEnabled(false);
            }
        });
        edtPassword.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    return;
                }
                edtPassword.setError(null);
                edtPassword.setErrorEnabled(false);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoginBack:
                Intent intent = new Intent(this, MainActivity.class);
                Pair<View, String> pair = new Pair<>(findViewById(R.id.btnLoginBack), "transition_login_back");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pair);
                startActivity(intent, options.toBundle());
                break;
            case R.id.tvRegister:
                Intent regIntent = new Intent(this, RegisterActivity.class);
                ActivityOptions regOptions = ActivityOptions.makeScaleUpAnimation
                        (v, 0, 0, v.getWidth(), v.getHeight());
                startActivity(regIntent, regOptions.toBundle());
                break;
            case R.id.btnForgot:
                Intent verifyIntent = new Intent(this, EmailVerification.class);
                ActivityOptions verifyOption = ActivityOptions.makeScaleUpAnimation(
                        v, 0, 0, v.getWidth(), v.getHeight());
                startActivity(verifyIntent, verifyOption.toBundle());
                break;
            case R.id.btnLogin:
                if(!validateEmail() | !validatePassword()){
                    return;
                }
                Toast.makeText(this, "Success!",
                        Toast.LENGTH_SHORT).show();
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
    private boolean validatePassword(){
        String password = edtPassword.getEditText().getText().toString().trim();
        String checkPass = "^" +
                "(?=.*[0-9])" +     // At least 1 digit
                "(?=.*[a-z])" +     // At least 1 lower
                "(?=.*[A-Z])" +     // At least 1 upper
                "(?=.*[a-zA-Z])" +  // Any letter
                //"(?=.*[@#$%^&+=])" +// At least 1 special letter
                "(?=\\S+$)" +       // No white space
                ".{4,}" +           // At least 4 characters
                "$";
        if (password.isEmpty()){
            edtPassword.setError("Field cannot be empty!");
            return false;
        }else if(!password.matches(checkPass)){
            edtPassword.setError("Something went wrong!");
            edtPassword.setErrorEnabled(false);
            return false;
        }
        else {
            edtPassword.setError(null);
            edtPassword.setErrorEnabled(false);
            return true;
        }
    }
}