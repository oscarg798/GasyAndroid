package rm.com.gasy.presentation.activities;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.Serializable;
import java.util.List;

import rm.com.core.model.dto.TankingDTO;
import rm.com.core.utils.GasyUtils;
import rm.com.gasy.R;
import rm.com.gasy.controller.DashBoardActivityController;
import rm.com.gasy.presentation.fragments.ReportFragment;
import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * This is activity represents the app dashboard
 */
@ContentView(R.layout.activity_dashboard)
public class DashboardActivity extends RoboActionBarActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @InjectView(R.id.drawer_layout)
    private DrawerLayout drawer;

    private ActionBarDrawerToggle toggle;

    @InjectView(R.id.nav_view)
    private NavigationView navigationView;

    @InjectView(R.id.toolbar)
    private Toolbar toolbar;

    /**
     * This property contains the fragment that is  shown to the user
     */
    private Fragment currentFragment;

    /**
     * Activity controller
     */
    private DashBoardActivityController dashBoardActivityController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
        initViewComponents();
        onSelectDrawerOption(0);


    }

    /**
     * Init bussines logic components
     */
    private void initComponents() {
        dashBoardActivityController = new DashBoardActivityController(this);
    }

    /**
     * Init view components
     */
    private void initViewComponents() {
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        /**
         * We check if the drawer is open or close, and if  it is  open  we close it
         */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        int selectedOption = 0;
        if (id == R.id.nav_camera) {
            selectedOption = 0;
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        onSelectDrawerOption(selectedOption);

        return true;
    }

    private void onSelectDrawerOption(int option) {
        switch (option) {
            case 0:
                currentFragment = new ReportFragment().newInstance("SSS");
                break;
        }

        changeFragment(currentFragment);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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
                .replace(R.id.nv_frame_layout, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }

    /**
     * We check the result of an activity that has been launched to get a result
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * We check if the activity launched, was {@link AddTankingActivity}
         * and we validate if the user has entered a Tanking report to be display and save it
         */
        if (requestCode == GasyUtils.ADD_REPORT_REQUEST_CODE) {
            if (resultCode == GasyUtils.ADD_RESULT_CODE) {
                List<TankingDTO> tankingDTOList = (List<TankingDTO>)
                        data.getSerializableExtra(getString(R.string.tanking_list_key));
                if (tankingDTOList != null) {
                    dashBoardActivityController.insertTankingOnDataBase(tankingDTOList);
                    if (currentFragment instanceof ReportFragment) {
                        ((ReportFragment) currentFragment).sendSerializableData((Serializable) tankingDTOList);
                    }
                }
            } else {
                Log.i("REPORTE", "SE HA CANCELADO");
            }
        } else if (requestCode == GasyUtils.EDIT_REPORT_REQUEST_CODE) {
            if (resultCode == GasyUtils.EDIT_RESULT_CODE) {
                List<TankingDTO> tankingDTOList = (List<TankingDTO>)
                        data.getSerializableExtra(getString(R.string.tanking_list_key));
                dashBoardActivityController.modifyOrDeleteTankingDTO(tankingDTOList, GasyUtils.EDIT_REPORT_REQUEST_CODE);
                if (currentFragment instanceof ReportFragment) {
                    ((ReportFragment) currentFragment).sendSerializableData((Serializable) tankingDTOList);
                }
            }
        }
    }


    private void sendDataToFragment(List<TankingDTO> tankingDTOList) {

    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }
}