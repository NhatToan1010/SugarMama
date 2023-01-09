package com.acer.sugarmama.ui.verify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.acer.sugarmama.R;
import com.acer.sugarmama.ui.login.ForgotPassword;
import com.acer.sugarmama.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class EmailVerification extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout edtEmail;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        findViewById(R.id.btnSendOTP).setOnClickListener(this);
        findViewById(R.id.btnEmailResend).setOnClickListener(this);
        findViewById(R.id.btnEmailClose).setOnClickListener(this);

        edtEmail = findViewById(R.id.edtVerifyEmail);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSendOTP:
                if (!validateEmail()){
                    return;
                }
                sendPasswordToEmail();
//                Intent fPassIntent = new Intent(this, ForgotPassword.class);
//                ActivityOptions fPassOption = ActivityOptions.makeScaleUpAnimation(
//                        v, 0, 0, v.getWidth(), v.getHeight()
//                );
//                startActivity(fPassIntent, fPassOption.toBundle());
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
    private void sendPasswordToEmail(){
        progressDialog.show();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = edtEmail.getEditText().getText().toString().trim();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(EmailVerification.this, "Email sent!",
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(EmailVerification.this, "Email sent failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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