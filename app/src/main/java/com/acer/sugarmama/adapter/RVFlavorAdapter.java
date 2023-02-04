package com.acer.sugarmama.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.acer.sugarmama.R;
import com.acer.sugarmama.model.Flavor;
import com.acer.sugarmama.ui.ProductList.ViewAll;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class RVFlavorAdapter extends RecyclerView.Adapter<RVFlavorAdapter.FlavorViewHolder>{

    private Context context;
    private List<Flavor> mList;

    public RVFlavorAdapter() {
    }

    public RVFlavorAdapter(Context context, List<Flavor> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public FlavorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_flavor, parent, false);
        return new FlavorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlavorViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(mList.get(position).getImgUrl()).into(holder.imgFlavor);
        holder.tvFlavorName.setText(mList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewAll.class);
                intent.putExtra("type_key", mList.get(position).getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList == null){
            return 0;
        }
        return mList.size();
    }

    public class FlavorViewHolder extends RecyclerView.ViewHolder{
        private TextView tvFlavorName;
        private ImageView imgFlavor;

        public FlavorViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFlavorName = itemView.findViewById(R.id.rvFlavorText);
            imgFlavor = itemView.findViewById(R.id.rvFlavorImg);
        }
    }
}
