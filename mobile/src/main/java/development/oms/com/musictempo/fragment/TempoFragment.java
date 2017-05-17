package development.oms.com.musictempo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

import development.oms.com.musictempo.R;
import development.oms.com.musictempo.metronome.Metronome;
import development.oms.com.musictempo.metronome.MetronomeListener;
import development.oms.com.musictempo.metronome.NoteEnum;
import development.oms.com.musictempo.model.TempoModel;
import development.oms.com.musictempo.sqlite.TempoSQLHelper;
import development.oms.com.musictempo.utils.ConstantsKeywords;

/**
 * A simple {@link Fragment} subclass.
 */
public class TempoFragment extends Fragment implements View.OnClickListener, MetronomeListener {

    EditText tempo, title, duration;
    TempoModel model;
    ImageButton begin;
    TempoSQLHelper helper;
    Metronome metronome;
    Spinner notes;

    public TempoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.tempo_fragment, container, false);
        Bundle args = getArguments();

        begin = (ImageButton) rootView.findViewById(R.id.tempo_begin_button);
        tempo = (EditText) rootView.findViewById(R.id.tempo_edit_time);
        title = (EditText) rootView.findViewById(R.id.tempo_edit_name);
        duration = (EditText) rootView.findViewById(R.id.tempo_edit_duration);
        notes = (Spinner) rootView.findViewById(R.id.tempo_note);

        helper = TempoSQLHelper.getInstance();
        metronome = new Metronome(getActivity());
        metronome.setMetronomeListener(this);

        if(args == null){
            model = new TempoModel();
        }else{
            model = args.getParcelable(ConstantsKeywords.TEMPO_DATA);
        }

        begin.setOnClickListener(this);
        setHasOptionsMenu(true);

        ArrayAdapter<NoteEnum> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, NoteEnum.values());
        notes.setAdapter(adapter);

        tempo.setText(String.format(Locale.getDefault(), "%d", model.getTempo()));
        title.setText(model.getTitle());
        duration.setText(String.format(Locale.getDefault(), "%d", model.getDurationInSeconds()));
        notes.setSelection(adapter.getPosition(model.getNote()));

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(metronome.isActive()){
            metronome.stop();
        }
        else {
            if(!metronome.start(model.getTempo(), model.getDuration(), model.getNote())){
                Toast.makeText(getActivity(), R.string.tempo_error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        menuInflater.inflate(R.menu.tempo_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_tempo_save:
                model.setTempo(Long.parseLong(tempo.getText().toString()));
                model.setTitle(title.getText().toString());
                model.setNote((NoteEnum) notes.getSelectedItem());
                model.setDuration(Long.parseLong(duration.getText().toString()));
                long row = helper.addModel(model);
                if(model.getId() == -1){
                    model.setId((int)row);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMetronomeStop() {
        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run() {
                begin.setImageResource(R.drawable.ic_play);
            }
        });
    }

    @Override
    public void onMetronomeStart() {
        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run() {
                begin.setImageResource(R.drawable.ic_pause);
            }
        });

    }
}
