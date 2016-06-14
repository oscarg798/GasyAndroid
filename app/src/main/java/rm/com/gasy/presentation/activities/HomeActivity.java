package rm.com.gasy.presentation.activities;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import rm.com.gasy.R;
import rm.com.gasy.presentation.fragments.LoginFragment;
import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_home)
public class HomeActivity extends RoboActionBarActivity {

    private Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentFragment = new LoginFragment();
        changeFragment(currentFragment);
    }


    /**
     * This method change the fragment displayed
     *
     * @param fragment
     */
    private void changeFragment(Fragment fragment) {
        this.currentFragment = fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.ha_frame, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }


}
