package com.acer.sugarmama.fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.acer.sugarmama.R;
import com.acer.sugarmama.adapter.RVCartAdapter;
import com.acer.sugarmama.model.TopProduct;
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

public class Cart extends Fragment {

    private ImageView avatar;
    private TextView userName, address, subtotal, deliverFee, discount, totalPrice;
    private Button btnContinue;
    private RecyclerView rv;
    private View mView;
    private FirebaseFirestore db;
    private List<TopProduct> listProduct;
    private RVCartAdapter cartAdapter;

    public Cart() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_cart, container, false);

        initView();
//        initProduct();
        return mView;

    }

    private void initView(){
        avatar = mView.findViewById(R.id.cartAvt);
        userName = mView.findViewById(R.id.cartUserName);
        address = mView.findViewById(R.id.cartAddress);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String name = user.getDisplayName();
        Uri photoUrl = user.getPhotoUrl();
        db = FirebaseFirestore.getInstance();

        userName.setText(name);
        Glide.with(this).load(photoUrl).error(R.drawable.img_avt).into(avatar);
    }
    private void initProduct(){
        listProduct = new ArrayList<>();
        cartAdapter = new RVCartAdapter(getActivity(), listProduct);
        rv.findViewById(R.id.rvCartProduct);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(cartAdapter);

        db.collection("TopProduct")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                TopProduct topProduct = document.toObject(TopProduct.class);
                                listProduct.add(topProduct);
                                cartAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Product Error",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}