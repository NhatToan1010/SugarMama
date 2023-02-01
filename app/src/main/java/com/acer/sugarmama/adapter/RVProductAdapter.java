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

public class RVProductAdapter extends RecyclerView.Adapter<RVProductAdapter.ProductViewHolder>{

    private Context context;
    private List<TopProduct> mList;

    public RVProductAdapter() {
    }

    public RVProductAdapter(Context context, List<TopProduct> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_items_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Glide.with(context).load(mList.get(position).getImgUrl()).into(holder.imgProduct);
        holder.tvProductName.setText(mList.get(position).getName());
        holder.tvPrice.setText(mList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        if (mList == null){
            return 0;
        }
        return mList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgProduct, imgFavorite, imgAdd;
        private TextView tvProductName, tvPrice;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            imgFavorite = itemView.findViewById(R.id.imgProductFavorite);
            imgAdd = itemView.findViewById(R.id.imgAddProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvProductPrice);
        }
    }
}
