package com.acer.sugarmama.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.acer.sugarmama.MainActivity;
import com.acer.sugarmama.R;
import com.acer.sugarmama.database.User;
import com.acer.sugarmama.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


    /*
    * OnClick Events
    */

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNext:
                if (!checkTheInternet()){
                    return;
                }
                String _name = edtFullName.getEditText().getText().toString().trim();
                String _email = edtEmail.getEditText().getText().toString().trim();
                String _password = edtPassword.getEditText().getText().toString().trim();
                String _confirm = edtConfirm.getEditText().getText().toString().trim();
                if(!validateName(_name) | !validateEmail(_email) | !validatePassword(_password) | !validateConfirm(_confirm)){
                    return;
                }
                if(!_password.equals(_confirm)){
                    edtConfirm.setError("Password must be right!");
                    return;
                }
                User newUser = new User(_name, _email, _password);
                registerWithEmail(newUser);
                break;
            case R.id.tvLogin:
                Intent logIntent = new Intent(this, LoginActivity.class);
                ActivityOptions logOptions = ActivityOptions.makeScaleUpAnimation(v, 0, 0, v.getWidth(), v.getHeight());
                startActivity(logIntent, logOptions.toBundle());
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

        if ((mobileConn != null && mobileConn.isConnected()) || (wifiConn != null && wifiConn.isConnected())) {
            return true;
        } else {
            Toast.makeText(this, "Internet Connection Required!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /*
    * Create New User
    */

    private void registerWithEmail(User user){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Users");

                            reference.setValue(user);
                            Toast.makeText(RegisterActivity.this, "Register success!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Register failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    /*
    * Validate data
    */

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
}