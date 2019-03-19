package com.deskera.mock.views.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.collection.SparseArrayCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deskera.mock.DeskeraMockApplication;
import com.deskera.mock.R;
import com.deskera.mock.utils.UtilsHelper;
import com.deskera.mock.adapters.TablesAdapter;
import com.deskera.mock.entities.Table;
import com.deskera.mock.interfaces.InteractionListener;
import com.deskera.mock.interfaces.OnItemClickListener;
import com.deskera.mock.interfaces.OnCheckBoxClickListener;
import com.deskera.mock.viewModels.TablesViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TableFragment extends Fragment implements InteractionListener<Table> {

    //region Controls
    @BindView(R.id.svTable)
    SearchView svTable;

    @BindView(R.id.rvTables)
    RecyclerView rvTables;

    @BindView(R.id.btnDelete)
    Button btnDelete;

    private TextView tvEditDone;
    private TextView tvTableTitle;
    private ImageView ivAddTable;
    private TextView tvBack;
    private TextView tvTableSave;
    private Toolbar profileToolbar;
    //end region

    //region Variables
    TablesAdapter tablesAdapter;
    TablesViewModel tablesViewModel;
    SparseArrayCompat<Table> tablesToBeDeleted;

    //end region
    public TableFragment() {
        // Required empty public constructor
    }

    //region Events
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tablesViewModel = ViewModelProviders.of(this).get(TablesViewModel.class);
        setupToolBar();
        getTables(1L);
    }

    @Override
    public void onInteraction(Table value) {
        tablesViewModel.updateTable(value);
        tablesAdapter.notifyItemChanged(tablesAdapter.getPosition(value));
    }

    //end region

    //region Function
    private void setupToolBar() {
        profileToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        tvTableTitle = (TextView) profileToolbar.findViewById(R.id.tvTableTitle);
        tvBack = (TextView) profileToolbar.findViewById(R.id.tvBack);
        tvTableSave = (TextView) profileToolbar.findViewById(R.id.tvTableSave);
        ivAddTable = (ImageView) profileToolbar.findViewById(R.id.ivAddTable);
        tvEditDone = (TextView) profileToolbar.findViewById(R.id.tvEditDone);

        tvBack.setVisibility(View.GONE);
        tvEditDone.setVisibility(View.VISIBLE);
        tvTableSave.setVisibility(View.GONE);
        ivAddTable.setVisibility(View.VISIBLE);
        svTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svTable.setIconified(false);
            }
        });
    }

    private void getTables(Long userId) {
        tablesViewModel.getTables(userId).subscribe(new Subscriber<List<Table>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Table> tableList) {
                setDisplay(tableList);
                setupSearch();
                setTableActions();
            }
        });
    }

    private void setDisplay(List<Table> tables) {
        rvTables.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DeskeraMockApplication.getContext());
        rvTables.setLayoutManager(layoutManager);
        tablesAdapter = new TablesAdapter(tables, new OnItemClickListener<Table>() {
            @Override
            public void onItemClick(Table table) {
                Fragment fragment;
                FragmentTransaction transaction;
                fragment = new TableViewFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(getResources().getString(R.string.title_table), table);
                fragment.setArguments(bundle);
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.flMasterTable, fragment, getResources().getString(R.string.fragment_view_details));
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }, new OnCheckBoxClickListener<Table>() {
            @Override
            public void onCheckBoxClick(Table item, boolean isChecked, int position) {
                if (tablesToBeDeleted == null)
                    tablesToBeDeleted = new SparseArrayCompat<>();
                if (isChecked) {
                    tablesToBeDeleted.append(position, item);
                    btnDelete.setVisibility(View.VISIBLE);
                    tvTableTitle.setText(tablesToBeDeleted.size() + " selected");
                } else {
                    tablesToBeDeleted.remove(position);
                    btnDelete.setVisibility(tablesToBeDeleted.size() > 0 ? View.VISIBLE : View.GONE);
                    tvTableTitle.setText("Tables");
                }
            }
        });
        tablesAdapter.setEditMode(false);
        rvTables.setAdapter(tablesAdapter);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int index = 0; index < tablesToBeDeleted.size(); index++) {
                    tablesViewModel.deleteTable(tablesToBeDeleted.get(tablesToBeDeleted.keyAt(index)));
                    tablesAdapter.deleteTableView(tablesToBeDeleted.get(tablesToBeDeleted.keyAt(index)), tablesToBeDeleted.keyAt(index));
                }
                tablesToBeDeleted = null;
                toggleState(tablesAdapter.getEditMode());
            }
        });
    }

    private void setupSearch() {
        svTable.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                svTable.onActionViewCollapsed();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tablesAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void setTableActions() {
        tvEditDone = (TextView) profileToolbar.findViewById(R.id.tvEditDone);
        ImageView ivAddTable = (ImageView) profileToolbar.findViewById(R.id.ivAddTable);
        tvEditDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleState(tablesAdapter.getEditMode());
            }
        });
        ivAddTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Table table = new Table();
                table.setName("Fruit " + UtilsHelper.getRandomNumber());
                table.setUserId(1L);
                tablesViewModel.insertTable(table);
                tablesAdapter.insertTableView(table);
                Toast.makeText(DeskeraMockApplication.getContext(), table.getName() + " inserted", Toast.LENGTH_SHORT).show();
                rvTables.scrollToPosition(tablesAdapter.getItemCount());
            }
        });
    }

    private void toggleState(boolean isEdit) {
        if (isEdit) {
            tablesAdapter.setEditMode(false);
            btnDelete.setVisibility(View.GONE);
            tvEditDone.setText(getResources().getString(R.string.button_edit));
            tvTableTitle.setText(getResources().getString(R.string.title_activity_table));
        } else {
            tvEditDone.setText(getResources().getString(R.string.button_done));
            tablesAdapter.setEditMode(true);
        }
    }

    //end region


}
