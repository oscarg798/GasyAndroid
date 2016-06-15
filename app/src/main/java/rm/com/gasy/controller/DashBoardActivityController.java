package rm.com.gasy.controller;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

import com.google.inject.Inject;
import com.rm.androidesentials.controllers.abstracts.AbstractController;

import java.util.ArrayList;
import java.util.List;

import rm.com.core.model.dto.TankingDTO;
import rm.com.core.model.dto.utils.Callbacks;
import rm.com.core.utils.GasyUtils;
import rm.com.gasy.model.Tanking;
import rm.com.gasy.persistence.DatabaseHelper;
import rm.com.gasy.persistence.dao.interfaces.ITankingDAO;
import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;

/**
 * Created by oscargallon on 6/6/16.
 * {@link rm.com.gasy.presentation.activities.DashboardActivity} controller
 */
public class DashBoardActivityController extends AbstractController implements Callbacks.IDatabaseOperationCallback {


    @Inject
    private ITankingDAO tankingDAO;

    private SQLiteDatabase db;

    private int executedAction;


    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    public DashBoardActivityController(Activity activity) {
        super(activity);
        /**
         * Inject all the none view elements to the controller
         */
        final RoboInjector injector = RoboGuice.getInjector(getActivity().getApplicationContext());
        injector.injectMembersWithoutViews(this);
        tankingDAO.setCurrentContext(getActivity());
    }

    /**
     * This method sends a {@link TankingDTO} list to be save in the database
     *
     * @param tankingDTOList
     */
    public void insertTankingOnDataBase(final List<TankingDTO> tankingDTOList) {
        Tanking.getInstance().insertTankingIntoDatabase(getDb(), tankingDTOList, tankingDAO);

    }

    public void modifyOrDeleteTankingDTO(List<TankingDTO> tankingDTOs, int action) {
        executedAction = action;
        Tanking.getInstance().editTankingDTO(tankingDTOs, getDb(), this, tankingDAO);
    }

    /**
     * This method close the database instance while it remains opened
     */
    public void tryToCloseDatabase() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    /**
     * This method returns a database instance
     * It checks if the controllers has a open instance for return it, or
     * a new one is created
     *
     * @return database instance.
     */
    private SQLiteDatabase getDb() {
        if (db == null || !db.isOpen()) {
            db = new DatabaseHelper(getActivity()).getReadableDatabase();
        }
        return db;
    }


    @Override
    public void onOperationExecuted(float affectedRows, List<?> objects) {
        switch (executedAction) {
            case GasyUtils.EDIT_REPORT_REQUEST_CODE: {
                break;
            }
        }
    }
}
