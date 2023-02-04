package com.acer.sugarmama.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.acer.sugarmama.R;
import com.acer.sugarmama.model.TopProduct;
import com.bumptech.glide.Glide;

import java.util.List;

public class RVCartAdapter extends RecyclerView.Adapter<RVCartAdapter.CartViewHolder> {
    private Context context;
    private List<TopProduct> mList;

    public RVCartAdapter(Context context, List<TopProduct> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RVCartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_items_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVCartAdapter.CartViewHolder holder, int position) {
        Glide.with(context).load(mList.get(position).getImgUrl()).into(holder.cartImg);
        holder.cartName.setText(mList.get(position).getName());
        holder.cartDescription.setText((mList.get(position).getDescription()));
        holder.cartPrice.setText((mList.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        if (mList != null){
            return mList.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView cartImg;
        private TextView cartName, cartDescription, cartPrice;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartImg = itemView.findViewById(R.id.cartImg);
            cartDescription = itemView.findViewById(R.id.cartDescription);
            cartPrice = itemView.findViewById(R.id.cartPrice);
            cartName = itemView.findViewById(R.id.cartProductName);
        }
    }
}
