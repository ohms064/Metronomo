package development.oms.com.musictempo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import development.oms.com.musictempo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TempoFragment extends Fragment {


    public TempoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tempo_fragment, container, false);
    }

}
