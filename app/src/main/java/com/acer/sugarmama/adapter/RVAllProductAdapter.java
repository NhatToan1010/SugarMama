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
import com.acer.sugarmama.model.AllProduct;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class RVAllProductAdapter extends RecyclerView.Adapter<RVAllProductAdapter.AllProductViewHolder> {
    private Context context;
    private List<AllProduct> mList;

    public RVAllProductAdapter(Context context, List<AllProduct> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RVAllProductAdapter.AllProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_items_viewall, parent, false);
        return new AllProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAllProductAdapter.AllProductViewHolder holder, int position) {
        Glide.with(context).load(mList.get(position).getImgUrl()).into(holder.productImg);
        holder.name.setText(mList.get(position).getName());
        holder.description.setText(mList.get(position).getDescription());
        holder.rating.setText(mList.get(position).getRating());
        holder.price.setText(mList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        if(mList != null){
            return mList.size();
        }
        return 0;
    }
    public class AllProductViewHolder extends RecyclerView.ViewHolder{
        private ImageView productImg;
        private TextView name, description, rating, price;
        public AllProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImg = itemView.findViewById(R.id.viewAllImg);
            description = itemView.findViewById(R.id.viewAllDescription);
            price = itemView.findViewById(R.id.viewAllPrice);
            name = itemView.findViewById(R.id.viewAllName);
            rating = itemView.findViewById(R.id.viewAllRating);
        }
    }
}
