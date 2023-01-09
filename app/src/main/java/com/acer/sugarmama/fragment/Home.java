package com.acer.sugarmama.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.acer.sugarmama.MainActivity;
import com.acer.sugarmama.R;
import com.acer.sugarmama.ui.profile.Profile;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends Fragment implements View.OnClickListener {

    private TextView tvName;
    private ImageView imgAvt;
    private View mView;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        initView();
        initListener();

        return mView;
    }
    private void initView(){
        imgAvt = mView.findViewById(R.id.btnProfile);
        tvName = mView.findViewById(R.id.tvHomeName);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String name = user.getDisplayName();
        Uri photoUrl = user.getPhotoUrl();

        tvName.setText(name);
        Glide.with(this).load(photoUrl).error(R.drawable.img_avt).into(imgAvt);
    }

    private void initListener(){
        mView.findViewById(R.id.btnProfile).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnProfile:
                Intent profileIntent = new Intent(getActivity(), Profile.class);
                startActivity(profileIntent);
                break;
        }
    }
}