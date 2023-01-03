package com.acer.sugarmama.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
    private RelativeLayout progressBar;
    private FirebaseAuth mAuth;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btnLoginBack).setOnClickListener(this);
        findViewById(R.id.tvRegister).setOnClickListener(this);
        findViewById(R.id.btnForgot).setOnClickListener(this);
        findViewById(R.id.btnLogin).setOnClickListener(this);

        progressBar = findViewById(R.id.progressbarLogin);
        progressBar.setVisibility(View.INVISIBLE);

        edtEmail = findViewById(R.id.edtLoginEmail);
        edtPassword = findViewById(R.id.edtLoginPassword);

        setErrorToFalse();
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
                if (checkTheInternet()){
                    return;
                }
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
    private boolean checkTheInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((mobileConn != null && mobileConn.isConnected()) || (wifiConn != null && wifiConn.isConnected())){
            return true;
        }
        else {
            Toast.makeText(this, "Internet Connection Required!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /*
    * Check Login User
    */

    private void loginWithEmail(String email, String password){
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "Login Success!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
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