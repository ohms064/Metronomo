package development.oms.com.musictempo.viewholder;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import development.oms.com.musictempo.R;
import development.oms.com.musictempo.fragment.ListFragment;
import development.oms.com.musictempo.model.TempoModel;
import development.oms.com.musictempo.navigation.IFragmentable;
import development.oms.com.musictempo.utils.ConstantsKeywords;
import development.oms.com.musictempo.utils.FragmentEnum;

/**
 * Created by Omar on 15/05/2017.
 */

public class TempoViewHolder extends RecyclerView.ViewHolder{

    TextView tempo, title;
    TempoModel tempoData;

    public TempoViewHolder(View itemView){
        super(itemView);
        tempo = (TextView) itemView.findViewById(R.id.tempo_item_tempo);
        title = (TextView) itemView.findViewById(R.id.tempo_item_title);
        tempoData = new TempoModel();
    }

    public void setData(TempoModel newTempo){
        tempoData = newTempo;

        tempo.setText(String.format(Locale.getDefault(), "%f", tempoData.getTempo()));
        title.setText(tempoData.getTitle());

    }

    public TempoModel getData(){
        return tempoData;
    }

}
