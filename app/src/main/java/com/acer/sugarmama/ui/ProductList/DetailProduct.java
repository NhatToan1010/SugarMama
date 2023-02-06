package com.acer.sugarmama.ui.ProductList;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.acer.sugarmama.R;
import com.acer.sugarmama.model.AllProduct;
import com.acer.sugarmama.model.TopProduct;
import com.acer.sugarmama.ui.profile.Profile;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import kotlinx.coroutines.channels.ProduceKt;

public class DetailProduct extends AppCompatActivity {
    private int totalQuantity = 1;
    private TextView name, price, rating, description, tvTop, quantity;
    private ImageView productImage, addItem, removeItem;
    private AllProduct allProduct;
    private TopProduct topProduct;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        initView();
        setData();
        setQuantity();
    }

    private void setQuantity() {
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQuantity >= 1){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity > 1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });
    }

    private void initView(){
        addItem = findViewById(R.id.detailAdd);
        removeItem = findViewById(R.id.detailRemove);
        quantity = findViewById(R.id.detailQuantity);
        name = findViewById(R.id.detailProductName);
        description = findViewById(R.id.detailDescription);
        price = findViewById(R.id.detailPrice);
        rating = findViewById(R.id.detailRating);
        productImage = findViewById(R.id.detailImage);
        tvTop = findViewById(R.id.detailTop);
    }

    private void setData(){
        final  Object object = getIntent().getSerializableExtra("detail_key");
        if (object instanceof AllProduct){
            allProduct = (AllProduct) object;
        }else if(object instanceof TopProduct){
            topProduct = (TopProduct) object;
        }
        if (allProduct != null){
            tvTop.setVisibility(View.INVISIBLE);
            name.setText(allProduct.getName());
            description.setText(allProduct.getDescription());
            price.setText(allProduct.getPrice());
            rating.setText(allProduct.getRating());
            Glide.with(this).load(allProduct.getImgUrl()).into(productImage);
        }else if(topProduct != null){
            name.setText(topProduct.getName());
            description.setText(topProduct.getDescription());
            price.setText(topProduct.getPrice());
            rating.setText(topProduct.getRating());
            Glide.with(this).load(topProduct.getImgUrl()).into(productImage);
        }
    }
}