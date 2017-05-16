package development.oms.com.musictempo.navigation;

import android.os.Bundle;

import development.oms.com.musictempo.utils.FragmentEnum;

/**
 * Created by Omar on 15/05/2017.
 */

public interface IFragmentable {
    void ChangeFragment(FragmentEnum newFragment);
    void ChangeFragment(FragmentEnum newFragment, Bundle extras);
}
