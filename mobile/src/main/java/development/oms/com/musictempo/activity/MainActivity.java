package development.oms.com.musictempo.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import development.oms.com.musictempo.R;
import development.oms.com.musictempo.navigation.IFragmentable;
import development.oms.com.musictempo.sqlite.TempoSQLHelper;
import development.oms.com.musictempo.utils.ConstantsKeywords;
import development.oms.com.musictempo.utils.FragmentEnum;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener, IFragmentable {

    public FragmentEnum currentFragmentEnum = FragmentEnum.LIST;
    Fragment currentFragment;
    private static final String CURRENT = "current";
    DrawerLayout drawer;
    private boolean changeFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        TempoSQLHelper helper = TempoSQLHelper.createSingleton(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager manager = getSupportFragmentManager();
        manager.addOnBackStackChangedListener(this);


        if(savedInstanceState != null){
            currentFragmentEnum = (FragmentEnum) savedInstanceState.getSerializable(CURRENT);
        }
        else{
            currentFragmentEnum = FragmentEnum.LIST;
            ChangeFragment();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable(CURRENT, currentFragmentEnum);
    }

    @SuppressWarnings("ConstantConditions")
    private void ChangeFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        currentFragment = currentFragmentEnum.getFragment();
        fragmentTransaction.replace(R.id.main_content_frame, currentFragment);
        fragmentTransaction.addToBackStack(currentFragmentEnum.getItemId().toString());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(currentFragmentEnum.getStringId());
    }

    @SuppressWarnings("ConstantConditions")
    private void ChangeFragment(Bundle extras){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        currentFragment = currentFragmentEnum.getFragment();
        currentFragment.setArguments(extras);
        fragmentTransaction.replace(R.id.main_content_frame, currentFragment);
        fragmentTransaction.addToBackStack(currentFragmentEnum.getItemId().toString());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(currentFragmentEnum.getStringId());
    }

    public void ChangeFragment(FragmentEnum newFragment){
        currentFragmentEnum = newFragment;
        ChangeFragment();
    }

    public void ChangeFragment(FragmentEnum newFragment, Bundle extras){
        currentFragmentEnum =  newFragment;
        ChangeFragment(extras);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onBackStackChanged() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        String id = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();

        currentFragmentEnum = FragmentEnum.fromId(Integer.parseInt(id));
        getSupportActionBar().setTitle(currentFragmentEnum.getStringId());
    }
}
