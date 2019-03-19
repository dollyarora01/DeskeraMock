package com.deskera.mock.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;

import com.deskera.mock.R;
import com.deskera.mock.entities.Table;
import com.deskera.mock.interfaces.OnItemClickListener;
import com.deskera.mock.interfaces.OnCheckBoxClickListener;
import com.deskera.mock.viewHolders.TableItemsViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TablesAdapter extends RecyclerView.Adapter<TableItemsViewHolder> {

    List<Table> tables;
    List<Table> filteredList;
    OnItemClickListener<Table> onItemClickListener;
    OnCheckBoxClickListener<Table> onCheckBoxClickListener;
    TablesFilter tablesFilter;
    boolean editMode;

    public TablesAdapter(List<Table> tableList, OnItemClickListener<Table> itemOnItemClickListener, OnCheckBoxClickListener<Table> onCheckBoxClickListener) {
        this.tables = tableList;
        this.filteredList = new ArrayList<>(tableList);
        this.onItemClickListener = itemOnItemClickListener;
        this.onCheckBoxClickListener = onCheckBoxClickListener;

    }

    @NonNull
    @Override
    public TableItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_table_item, parent, false);
        TableItemsViewHolder holder = new TableItemsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TableItemsViewHolder holder, int position) {
        holder.tvTableTitle.setText(filteredList.get(position).getName());
        holder.cvTableItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(filteredList.get(position));
                }
            }
        });
        if (getEditMode()) {
            holder.rdTableSelection.setVisibility(View.VISIBLE);
            holder.rdTableSelection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (onCheckBoxClickListener != null) {
                        onCheckBoxClickListener.onCheckBoxClick(filteredList.get(position), isChecked, position);
                    }
                }
            });
        } else {
            holder.rdTableSelection.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public int getPosition(Table table) {
        return filteredList.indexOf(table);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void deleteTableView(Table item, int position) {
        filteredList.remove(item);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, filteredList.size());
    }

    public void insertTableView(Table item) {
        filteredList.add(item);
        notifyItemInserted(filteredList.size());
        notifyDataSetChanged();
    }

    public Filter getFilter() {
        if (tablesFilter == null)
            tablesFilter = new TablesFilter();
        return tablesFilter;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
        notifyDataSetChanged();
    }

    public boolean getEditMode() {
        return editMode;
    }

    class TablesFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = new ArrayList<>(tables);
                results.count = tables.size();
            } else {
                List<Table> filtered = new ArrayList<>();
                for (Table table : tables) {
                    if (!TextUtils.isEmpty(table.getName())) {
                        if (table.getName().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                            filtered.add(table);
                        }
                        Collections.sort(filtered,
                                new Comparator<Table>() {
                                    public int compare(Table t1, Table t2) {
                                        return t1.getName().compareTo(t2.getName());
                                    }
                                });
                    }
                }
                results.values = filtered;
                results.count = filtered.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Table>) results.values;
            notifyDataSetChanged();

        }
    }
}
