package com.deskera.mock.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.deskera.mock.R;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ItemsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.cvItems)
    public CardView cvItems;
    @BindView(R.id.civItemImage)
    public CircleImageView civItemImage;
    @BindView(R.id.tvItemName)
    public TextView tvItemName;
    @BindView(R.id.tvItemDescription)
    public TextView tvItemDescription;
    @BindView(R.id.tvCategoryName)
    public TextView tvCategoryName;
    @BindView(R.id.ivFavourites)
    public ImageView ivFavourites;

    public ItemsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
