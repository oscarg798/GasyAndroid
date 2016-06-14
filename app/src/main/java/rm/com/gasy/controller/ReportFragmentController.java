package rm.com.gasy.controller;


import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;

import com.google.inject.Inject;
import com.rm.androidesentials.controllers.abstracts.AbstractController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rm.com.core.model.dto.TankingDTO;
import rm.com.core.model.dto.utils.Callbacks;
import rm.com.gasy.model.Tanking;
import rm.com.gasy.persistence.DatabaseHelper;
import rm.com.gasy.persistence.dao.interfaces.ITankingDAO;
import rm.com.gasy.presentation.activities.DashboardActivity;
import rm.com.gasy.presentation.fragments.ReportFragment;
import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;

/**
 * Created by oscargallon on 6/4/16.
 */

public class ReportFragmentController extends AbstractController implements Callbacks.IDatabaseOperationCallback {

    private SQLiteDatabase db;

    private Calendar calendar;

    private Fragment fragment;

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
        tankingDAO.setCurrentContext(getActivity());
    }

    public void loadTankingDataFromDataBase() {
        Fragment fragment = ((DashboardActivity) getActivity()).getCurrentFragment();
        if (fragment != null && fragment instanceof ReportFragment) {
            ((ReportFragment) ((DashboardActivity) getActivity()).getCurrentFragment())
                    .setParamatersAndLoadTankingData(tankingDAO, getDb(), null, null);
        }
    }

    public void deleteTanking(List<TankingDTO> tankingDTOList) {
        Tanking.getInstance().deleteTankigDTO(tankingDTOList, db, this, tankingDAO);
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

    @Override
    public void onOperationExecuted(float affectedRows, List<?> objects) {
        if (affectedRows > 0) {
            ((ReportFragment) getFragment1()).tankingDTODeleted();
        }
    }


    public Fragment getFragment1() {
        return fragment;
    }

    public void setFragment1(Fragment fragment) {
        this.fragment = fragment;
    }
}
