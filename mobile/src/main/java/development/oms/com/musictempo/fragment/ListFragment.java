package development.oms.com.musictempo.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import development.oms.com.musictempo.R;
import development.oms.com.musictempo.adapter.TempoAdapter;
import development.oms.com.musictempo.model.TempoModel;
import development.oms.com.musictempo.navigation.IFragmentable;
import development.oms.com.musictempo.sqlite.TempoSQLHelper;
import development.oms.com.musictempo.utils.ConstantsKeywords;
import development.oms.com.musictempo.utils.FragmentEnum;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    RecyclerView recyclerView;
    TempoAdapter adapter;
    TempoSQLHelper sqlHelper;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.list_fragment, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.tempo_list_grid);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        sqlHelper = TempoSQLHelper.getInstance();

    }

    @Override
    public void onResume(){
        super.onResume();
        ArrayList<TempoModel> model = sqlHelper.getModels();
        adapter = new TempoAdapter((IFragmentable) getActivity(), model, sqlHelper);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        menuInflater.inflate(R.menu.list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_list_add:
                ((IFragmentable) getActivity()).ChangeFragment(FragmentEnum.TEMPO);
                return true;
            case R.id.menu_list_delete:
                adapter.toggleDeleting();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
