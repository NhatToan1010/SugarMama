package com.acer.sugarmama.ui.ProductList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.acer.sugarmama.R;
import com.acer.sugarmama.adapter.RVAllProductAdapter;
import com.acer.sugarmama.model.AllProduct;
import com.acer.sugarmama.model.TopProduct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAll extends AppCompatActivity {
    private List<AllProduct> mList;
    private RVAllProductAdapter mAdapter;
    private RecyclerView rv;
    private FirebaseFirestore db;
    private TextView tittle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        db = FirebaseFirestore.getInstance();

        tittle = (TextView) findViewById(R.id.viewAllTittle);
        String type = getIntent().getStringExtra("type_key");

        mList = new ArrayList<>();
        mAdapter = new RVAllProductAdapter(this, mList);
        rv = findViewById(R.id.rvViewAll);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);

        if(type == null){
            return;
        }

        if (type.equalsIgnoreCase("Cookie")){
            tittle.setText(type);
            db.collection("AllProduct").whereEqualTo("type", "Cookie")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                        AllProduct allProduct = documentSnapshot.toObject(AllProduct.class);
                        mList.add(allProduct);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        if (type.equalsIgnoreCase("Creme")){
            tittle.setText(type);
            db.collection("AllProduct").whereEqualTo("type", "Creme")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                                AllProduct allProduct = documentSnapshot.toObject(AllProduct.class);
                                mList.add(allProduct);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }

        if (type.equalsIgnoreCase("All Product")){
            tittle.setText(type);
            db.collection("AllProduct")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                                AllProduct allProduct = documentSnapshot.toObject(AllProduct.class);
                                mList.add(allProduct);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
    }
}