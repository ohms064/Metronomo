package development.oms.com.musictempo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import development.oms.com.musictempo.R;
import development.oms.com.musictempo.model.TempoModel;
import development.oms.com.musictempo.navigation.IFragmentable;
import development.oms.com.musictempo.sqlite.TempoSQLHelper;
import development.oms.com.musictempo.utils.ConstantsKeywords;
import development.oms.com.musictempo.utils.FragmentEnum;
import development.oms.com.musictempo.viewholder.TempoViewHolder;

/**
 * Created by Omar on 15/05/2017.
 */

public class TempoAdapter extends RecyclerView.Adapter<TempoViewHolder>{
    IFragmentable manager;
    ArrayList<TempoModel> model;
    TempoSQLHelper db;
    private boolean isDeleting;

    public TempoAdapter(IFragmentable manager, ArrayList<TempoModel> model, TempoSQLHelper db){
        this.manager = manager;
        this.model = model;
        this.db = db;
    }

    @Override
    public TempoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context parentContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parentContext);

        View tempoView = inflater.inflate(R.layout.tempo_item, parent, false);

        final TempoViewHolder holder = new TempoViewHolder(tempoView);
        tempoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDeleting){
                    int position = holder.getAdapterPosition();
                    db.remove(model.get(position));
                    model.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                }
                else{
                    Bundle args = new Bundle();
                    args.putParcelable(ConstantsKeywords.TEMPO_DATA, holder.getData());
                    manager.ChangeFragment(FragmentEnum.TEMPO, args);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(TempoViewHolder holder, int position) {
        holder.setData(model.get(position));
    }

    @Override
    public int getItemCount() {
        return model.size();
    }


    public void setDeleting(boolean deleting) {
        isDeleting = deleting;
    }

    public boolean getDeleting(){
        return isDeleting;
    }

    public boolean toggleDeleting(){
        isDeleting = !isDeleting;
        return isDeleting;
    }
}
