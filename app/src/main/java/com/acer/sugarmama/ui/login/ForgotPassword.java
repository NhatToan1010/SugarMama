package com.acer.sugarmama.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.acer.sugarmama.CheckTheInternet;
import com.acer.sugarmama.ui.verify.EmailVerification;
import com.acer.sugarmama.R;
import com.google.android.material.textfield.TextInputLayout;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout edtPassword, edtConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.btnFPassBack).setOnClickListener(this);
        findViewById(R.id.btnFPass).setOnClickListener(this);

        edtPassword = findViewById(R.id.edtNewPass);
        edtConfirm = findViewById(R.id.edtConfirmPass);
        setErrorToFalse();
    }


    /*
    * OnClick Events
    */
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFPassBack:
                Intent eBackIntent = new Intent(this, EmailVerification.class);
                Pair<View, String> pair = new Pair<>(findViewById(R.id.btnFPassBack), "transition_email_back");
                ActivityOptions eBackOption = ActivityOptions.makeSceneTransitionAnimation(this, pair);
                startActivity(eBackIntent, eBackOption.toBundle());
                break;
            case R.id.btnFPass:
                checkTheInternet();
                if(!validatePassword() | !validateConfirm()){
                    return;
                }
                Toast.makeText(this, "Password reset!",
                        Toast.LENGTH_SHORT).show();
                Intent logBackIntent = new Intent(this, LoginActivity.class);
                ActivityOptions logBackOption = ActivityOptions.makeScaleUpAnimation(
                        v, 0 , 0, v.getWidth(), v.getHeight());
                startActivity(logBackIntent, logBackOption.toBundle());
                break;
        }
    }
    /*
    * Check Internet Connection
    */
    private void checkTheInternet() {
        CheckTheInternet checkTheInternet = new CheckTheInternet();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!checkTheInternet.isConnected(this)){
                return;
            }
        }
    }
    /*
    * Validate data
    */
    private void setErrorToFalse(){
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
    private boolean validateConfirm(){
        String password = edtConfirm.getEditText().getText().toString().trim();
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
}