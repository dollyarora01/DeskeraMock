package com.deskera.mock.viewHolders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.deskera.mock.R;

import org.w3c.dom.Text;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TableItemsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.cvTableItem)
    public CardView cvTableItem;
    @BindView(R.id.tvTableTitle)
    public TextView tvTableTitle;
    @BindView(R.id.cbTableSelection)
    public CheckBox rdTableSelection;

    public TableItemsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
