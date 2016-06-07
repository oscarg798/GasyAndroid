package rm.com.gasy.controller;


import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;

import com.google.inject.Inject;
import com.rm.androidesentials.controllers.abstracts.AbstractController;

import java.util.Calendar;

import rm.com.gasy.persistence.DatabaseHelper;
import rm.com.gasy.persistence.dao.interfaces.ITankingDAO;
import rm.com.gasy.presentation.activities.DashboardActivity;
import rm.com.gasy.presentation.fragments.ReportFragment;
import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;

/**
 * Created by oscargallon on 6/4/16.
 */

public class ReportFragmentController extends AbstractController {

    private SQLiteDatabase db;

    private Calendar calendar;

    @Inject
    private ITankingDAO tankingDAO;

    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    public ReportFragmentController(Activity activity) {
        super(activity);
        /**
         * We must to inject all the no view elements
         */
        final RoboInjector injector = RoboGuice.getInjector(getActivity().getApplicationContext());
        injector.injectMembersWithoutViews(this);
    }

    public void loadTankingDataFromDataBase() {
        Fragment fragment = ((DashboardActivity) getActivity()).getCurrentFragment();
        if (fragment != null && fragment instanceof ReportFragment) {
            ((ReportFragment) ((DashboardActivity) getActivity()).getCurrentFragment())
                    .setParamatersAndLoadTankingData(tankingDAO, getDb(), null, null);
        }
    }




    public void tryToCloseDatabase() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    public SQLiteDatabase getDb() {
        if (db == null) {
            db = new DatabaseHelper(getActivity()).getReadableDatabase();
        }
        return db;
    }
}
