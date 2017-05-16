package development.oms.com.musictempo.utils;

import android.support.v4.app.Fragment;

import development.oms.com.musictempo.R;
import development.oms.com.musictempo.fragment.ListFragment;
import development.oms.com.musictempo.fragment.TempoFragment;

/**
 * Created by Omar on 15/05/2017.
 */

public enum FragmentEnum {
    TEMPO(1, TempoFragment.class, R.string.tempo_fragment_title),
    LIST(2,ListFragment.class, R.string.list_fragment_title);

    private Integer itemId;
    private Class fragment;
    private int stringId;

    FragmentEnum(int value, Class fragment, int stringId) {
        itemId = value;
        this.fragment = fragment;
        this.stringId = stringId;
    }

    public static FragmentEnum fromId(int i){
        for(FragmentEnum b : FragmentEnum.values()){
            if(b.getItemId() == i) {return b;}
        }
        return null;
    }

    public Integer getItemId() {
        return itemId;
    }

    public Fragment getFragment() {
        try {
            return (Fragment) fragment.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getStringId() {
        return stringId;
    }
}
