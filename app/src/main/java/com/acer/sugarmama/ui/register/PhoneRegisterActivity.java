package com.acer.sugarmama.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.acer.sugarmama.ui.verify.OTPVerification;
import com.acer.sugarmama.R;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class PhoneRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout edtPhone;
    private CountryCodePicker countryCodePicker;
    private String _phoneNo;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        countryCodePicker = findViewById(R.id.countryCode);
        findViewById(R.id.btnPhoneBack).setOnClickListener(this);
        findViewById(R.id.btnRegister).setOnClickListener(this);

        edtPhone = findViewById(R.id.edtPhone);

        countryCodePicker.registerCarrierNumberEditText(edtPhone.getEditText());

        edtPhone.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    return;
                }
                edtPhone.setError(null);
                edtPhone.setErrorEnabled(false);
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPhoneBack:
                Intent reg1stIntent = new Intent(this, RegisterActivity.class);
                Pair<View, String> pair = new Pair<>(findViewById(R.id.btnPhoneBack), "transition_reg_back");
                ActivityOptions regOption = ActivityOptions.makeSceneTransitionAnimation(this, pair);
                startActivity(reg1stIntent, regOption.toBundle());
                break;
            case R.id.btnRegister:
                if (!validatePhone()){
                    return;
                }
                String _fullName = getIntent().getStringExtra("fullName");
                String _email = getIntent().getStringExtra("email");
                String _password = getIntent().getStringExtra("password");
                _phoneNo = countryCodePicker.getFullNumberWithPlus().toString().trim();

                Intent otpIntent = new Intent(this, OTPVerification.class);
                otpIntent.putExtra("fullName", _fullName);
                otpIntent.putExtra("email", _email);
                otpIntent.putExtra("password", _password);
                otpIntent.putExtra("phone", _phoneNo);

                ActivityOptions otpOption = ActivityOptions.makeScaleUpAnimation
                        (v, 0,450, v.getWidth(), 250);
                startActivity(otpIntent, otpOption.toBundle());
                break;
        }
    }
    private boolean validatePhone(){
        String phone = edtPhone.getEditText().getText().toString().trim();
        String checkSpace = "(?=.*[0-9])" +
                ".{9,}";
        if(phone.isEmpty()){
            edtPhone.setError(null);
            return false;
        }else if(!phone.matches(checkSpace)){
            edtPhone.setError(null);
            return false;
        }else {
            edtPhone.setError(null);
            edtPhone.setErrorEnabled(false);
            return true;
        }
    }
}