package com.acer.sugarmama;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout edtFullName, edtEmail, edtPassword, edtConfirm;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.btnNext).setOnClickListener(this);
        findViewById(R.id.tvLogin).setOnClickListener(this);

        edtFullName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtRegEmail);
        edtPassword = findViewById(R.id.edtRegPassword);
        edtConfirm = findViewById(R.id.edtRepeat);

        setErrorToFalse();
    }

    private void setErrorToFalse(){
        edtFullName.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    return;
                }
                edtFullName.setError(null);
                edtFullName.setErrorEnabled(false);
            }
        });
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
        edtConfirm.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    return;
                }
                edtConfirm.setError(null);
                edtConfirm.setErrorEnabled(false);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNext:
                String _name = edtFullName.getEditText().getText().toString().trim();
                String _email = edtEmail.getEditText().getText().toString().trim();
                String _password = edtPassword.getEditText().getText().toString().trim();
                String _confirm = edtConfirm.getEditText().getText().toString().trim();
                if(!validateName(_name) | !validateEmail(_email) | !validatePassword(_password) | !validateConfirm(_confirm)){
                    return;
                }
                Intent phoneIntent = new Intent(this, PhoneRegisterActivity.class);
                phoneIntent.putExtra("fullName", _name);
                phoneIntent.putExtra("email", _email);
                phoneIntent.putExtra("password", _password);
                Pair<View, String> phonePair = new Pair<>(findViewById(R.id.btnNext), "transition_btn_next");
                ActivityOptions phoneOption = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, phonePair);
                startActivity(phoneIntent, phoneOption.toBundle());
                break;
            case R.id.tvLogin:
                Intent logIntent = new Intent(this, LoginActivity.class);
                ActivityOptions logOptions = ActivityOptions.makeScaleUpAnimation(v, 0, 0, v.getWidth(), v.getHeight());
                startActivity(logIntent, logOptions.toBundle());
                break;
        }
    }

    private boolean validateName(String name){
        if(name.isEmpty()){
            edtFullName.setError("Field cannot be empty!");
            return false;
        }else {
            edtFullName.setError(null);
            edtFullName.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateEmail(String email){
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(email.isEmpty()){
            edtEmail.setError("Field cannot be empty!");
            return false;
        }else if(!email.matches(checkEmail)){
            edtEmail.setError("No white space allowed!");
            return false;
        }
        else {
            edtEmail.setError(null);
            edtEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword(String password){
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
    private boolean validateConfirm(String password){
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
            edtConfirm.setError("Field cannot be empty!");
            return false;
        }else if(!password.matches(checkPass)){
            edtConfirm.setError("Something went wrong!");
            edtConfirm.setErrorEnabled(false);
            return false;
        }
        else {
            edtConfirm.setError(null);
            edtConfirm.setErrorEnabled(false);
            return true;
        }
    }
    @Override
    public void onBackPressed(){
        Intent a = new Intent(this, MainActivity.class);
        startActivity(a);
    }
}