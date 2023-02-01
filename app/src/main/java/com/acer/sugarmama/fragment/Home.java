package com.acer.sugarmama.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.acer.sugarmama.MainActivity;
import com.acer.sugarmama.R;
import com.acer.sugarmama.adapter.RVFlavorAdapter;
import com.acer.sugarmama.adapter.RVProductAdapter;
import com.acer.sugarmama.model.Flavor;
import com.acer.sugarmama.model.TopProduct;
import com.acer.sugarmama.ui.profile.Profile;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment implements View.OnClickListener {

    private TextView tvName;
    private ImageView imgAvt;
    private View mView;
    private RecyclerView rvFlavor, rvProduct;
    private RVFlavorAdapter flavorAdapter;
    private RVProductAdapter productAdapter;
    private FirebaseFirestore db;
    private List<Flavor> flavorList;
    private List<TopProduct> productList;

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
        initFlavorAdapter();
        initProductAdapter();

        return mView;
    }
    private void initView(){
        imgAvt = mView.findViewById(R.id.btnProfile);
        tvName = mView.findViewById(R.id.tvHomeName);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String name = user.getDisplayName();
        Uri photoUrl = user.getPhotoUrl();
        db = FirebaseFirestore.getInstance();

        tvName.setText(name);
        Glide.with(this).load(photoUrl).error(R.drawable.img_avt).into(imgAvt);

    }

    private void initFlavorAdapter(){
        flavorList = new ArrayList<>();
        rvFlavor = mView.findViewById(R.id.rvHomeFlavor);
        flavorAdapter = new RVFlavorAdapter(getActivity(), flavorList);
        rvFlavor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvFlavor.setAdapter(flavorAdapter);

        db.collection("Flavor")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Flavor flavor = document.toObject(Flavor.class);
                                flavorList.add(flavor);
                                flavorAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Flavor Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void initProductAdapter(){
        productList = new ArrayList<>();
        rvProduct = mView.findViewById(R.id.rvProduct);
        productAdapter = new RVProductAdapter(getActivity(), productList);
        rvProduct.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvProduct.setAdapter(productAdapter);

        db.collection("TopProduct")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                TopProduct topProduct = document.toObject(TopProduct.class);
                                productList.add(topProduct);
                                productAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Product Error",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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