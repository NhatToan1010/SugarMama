package com.acer.sugarmama.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.acer.sugarmama.CheckTheInternet;
import com.acer.sugarmama.SplashScreen;
import com.acer.sugarmama.ui.verify.EmailVerification;
import com.acer.sugarmama.MainActivity;
import com.acer.sugarmama.R;
import com.acer.sugarmama.ui.register.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout edtEmail, edtPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btnLoginBack).setOnClickListener(this);
        findViewById(R.id.tvRegister).setOnClickListener(this);
        findViewById(R.id.btnForgot).setOnClickListener(this);
        findViewById(R.id.btnLogin).setOnClickListener(this);

        edtEmail = findViewById(R.id.edtLoginEmail);
        edtPassword = findViewById(R.id.edtLoginPassword);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");

        setErrorToFalse();
    }



    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLoginBack:
                Intent intent = new Intent(this, SplashScreen.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tvRegister:
                Intent regIntent = new Intent(this, RegisterActivity.class);
                ActivityOptions regOptions = ActivityOptions.makeScaleUpAnimation
                        (v, 0, 0, v.getWidth(), v.getHeight());
                startActivity(regIntent, regOptions.toBundle());
                finish();
                break;
            case R.id.btnForgot:
                Intent verifyIntent = new Intent(this, EmailVerification.class);
                ActivityOptions verifyOption = ActivityOptions.makeScaleUpAnimation(
                        v, 0, 0, v.getWidth(), v.getHeight());
                startActivity(verifyIntent, verifyOption.toBundle());
                finish();
                break;
            case R.id.btnLogin:
                checkTheInternet();
                String email = edtEmail.getEditText().getText().toString().trim();
                String password = edtPassword.getEditText().getText().toString().trim();
                if(!validateEmail(email) | !validatePassword(password)){
                    return;
                }
                loginWithEmail(email, password);
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
    * Check Login User
    */

    private void loginWithEmail(String email, String password){
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(homeIntent);
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "User did not exit",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    /*
    * Validate data
    */

    private boolean validateEmail(String email){
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.isEmpty()){
            edtEmail.setError("Field cannot be empty!");
            return false;
        }else if(!email.matches(checkEmail)){
            edtEmail.setError("White space are not allowed!");
            return false;
        }
        return true;
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
        return true;
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
}