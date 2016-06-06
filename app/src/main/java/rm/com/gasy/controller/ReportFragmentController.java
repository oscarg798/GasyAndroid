package rm.com.gasy.controller;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.rm.androidesentials.controllers.abstracts.AbstractController;
import com.rm.androidesentials.utils.Validation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rm.com.core.model.dto.TankingDTO;
import rm.com.core.model.dto.builders.TankingDTOBuilder;
import rm.com.core.model.dto.utils.AsyncTaskExecutor;
import rm.com.core.model.dto.utils.IAsyncTaskExecutor;
import rm.com.gasy.R;
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
    ITankingDAO tankingDAO;

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
        if (db == null) {
            db = new DatabaseHelper(getActivity()).getReadableDatabase();
        }

       Fragment fragment = ((DashboardActivity) getActivity()).getCurrentFragment();
        if (fragment !=null && fragment instanceof ReportFragment){
            ((ReportFragment) ((DashboardActivity) getActivity()).getCurrentFragment())
                    .setParamatersAndLoadTankingData(tankingDAO, db, null, null);
        }


    }

    private String fillMileage(String mileage) {
        String zeros = "";
        int diference = 3 - mileage.length();
        for (int i = 0; i < diference; i++) {

            zeros += "0";

        }
        mileage = zeros + mileage;
        return mileage;
    }

    private Timestamp getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        calendar = calendar == null ? Calendar.getInstance() : calendar;
        calendar.set(year, month, day);
        return new Timestamp(calendar.getTime().getTime());
    }


    private void validateDialogInputs(EditText etGasStationName, EditText etMileage,
                                      EditText etSpentAmount, final DatePicker dpTankingDate) {
        final String gasStationName = etGasStationName.getText().toString();
        String mileage = etMileage.getText().toString();
        final String spentAmount = etSpentAmount.getText().toString();

        if (!Validation.validateStringThanCanNotBeEmpty(gasStationName)) {
            etGasStationName.setError(getActivity()
                    .getString(R.string.should_add_gas_station_name_label));
            etGasStationName.requestFocus();
            return;
        }

        if (mileage.length() < 3) {
            mileage = fillMileage(mileage);
        }


        if (!Validation.validateStringThanCanNotBeEmpty(mileage)) {
            etMileage.setError(getActivity().getString(R.string.should_enter_mileage_label));
            etMileage.requestFocus();
            return;
        }

        if (!Validation.validateStringThanCanNotBeEmpty(spentAmount)) {
            etSpentAmount.setError(getActivity().getString(R.string.should_enter_spent_amount));
            etSpentAmount.requestFocus();
            return;
        }


        final String finalMileage = mileage;
        AsyncTaskExecutor asyncTaskExecutor = new AsyncTaskExecutor(new IAsyncTaskExecutor() {
            @Override
            public Object execute() {
                List<TankingDTO> tankingDTOList = new ArrayList<>();
                tankingDTOList.add(new TankingDTOBuilder()
                        .withADate(getDateFromDatePicker(dpTankingDate))
                        .withAGasStationName(gasStationName)
                        .withAMileage(Double.parseDouble(finalMileage))
                        .withASpentAmount(Double.parseDouble(spentAmount))
                        .createTakingDTO());

                if (db == null || !db.isOpen()) {
                    db = new DatabaseHelper(getActivity()).getWritableDatabase();
                }

                tankingDAO.insert(db, tankingDAO.getContentValuesFromObject(tankingDTOList));
                return true;
            }

            @Override
            public void onExecuteComplete(Object object) {
                Log.i("INSERTE", "LOGRE INSERTAR");
            }

            @Override
            public void onExecuteFaliure(Exception e) {
                e.printStackTrace();
                Log.i("INSERTE", "NO LOGRE INSERTAR");
            }
        });

        asyncTaskExecutor.execute();
    }


    public void showAddReportDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater =
                getActivity().getLayoutInflater();

        View alertDialogLayout =
                layoutInflater.inflate(R.layout.add_report_dialog, null);

        final EditText etGasStationName = (EditText) alertDialogLayout
                .findViewById(R.id.et_gas_station_name);

        final EditText etMileage = (EditText) alertDialogLayout
                .findViewById(R.id.et_mileage);

        final EditText etSpentAmount = (EditText) alertDialogLayout
                .findViewById(R.id.et_spent_amount);

        final DatePicker dpTankingDate = (DatePicker) alertDialogLayout
                .findViewById(R.id.dp_tanking_date);

        builder.setTitle(getActivity().getString(R.string.add_report_dialog_title))
                .setCancelable(false)
                .setPositiveButton(getActivity().getString(R.string.accept_label), null)
                .setNegativeButton(getActivity().getString(R.string.cancel_label), null)
                .setView(alertDialogLayout);


        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);

                Button negativebutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);


                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        validateDialogInputs(etGasStationName, etMileage,
                                etSpentAmount, dpTankingDate);
                        alertDialog.dismiss();
                    }
                });

                negativebutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });


            }
        });

        alertDialog.show();
    }

    public void tryToCloseDatabase() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }


}
