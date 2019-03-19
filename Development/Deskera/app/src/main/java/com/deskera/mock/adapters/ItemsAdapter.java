package com.deskera.mock.adapters;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deskera.mock.DeskeraMockApplication;
import com.deskera.mock.R;
import com.deskera.mock.utils.UtilsHelper;
import com.deskera.mock.entities.Item;
import com.deskera.mock.interfaces.OnItemClickListener;
import com.deskera.mock.viewHolders.ItemsViewHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsViewHolder> {

    List<Item> items;
    private OnItemClickListener<Item> onItemClickListener;

    public ItemsAdapter(List<Item> itemList, OnItemClickListener<Item> itemOnItemClickListener) {
        this.items = itemList;
        this.onItemClickListener = itemOnItemClickListener;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items, parent, false);
        ItemsViewHolder holder = new ItemsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        holder.tvItemName.setText(items.get(position).getItemName());
        holder.tvCategoryName.setText(items.get(position).getCategory().getCategoryName());
        holder.tvItemDescription.setText(items.get(position).getDescription());
        Bitmap textBitmap;
        if (!TextUtils.isEmpty(items.get(position).getItemName())) {
            textBitmap = UtilsHelper.getTextBitmap(items.get(position).getItemName());
        } else {
            textBitmap = UtilsHelper.getTextBitmap("C");
        }
        holder.civItemImage.setImageBitmap(textBitmap);
        holder.ivFavourites.setBackground(items.get(position).getIsFavourite() ?
                DeskeraMockApplication.getContext().
                        getResources().getDrawable(R.drawable.ic_star_grey600_24dp) :
                DeskeraMockApplication.getContext().
                        getResources().getDrawable(R.drawable.ic_star_outline_grey600_24dp));
        holder.ivFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    holder.ivFavourites.setBackground(items.get(position).getIsFavourite() ?
                            DeskeraMockApplication.getContext().
                                    getResources().getDrawable(R.drawable.ic_star_outline_grey600_24dp) :
                            DeskeraMockApplication.getContext().
                                    getResources().getDrawable(R.drawable.ic_star_grey600_24dp));
                    onItemClickListener.onItemClick(items.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
