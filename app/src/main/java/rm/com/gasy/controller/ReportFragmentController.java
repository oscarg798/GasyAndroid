package rm.com.gasy.controller;


import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rm.androidesentials.controllers.abstracts.AbstractController;
import com.rm.androidesentials.utils.Validation;

import rm.com.gasy.R;

/**
 * Created by oscargallon on 6/4/16.
 */

public class ReportFragmentController extends AbstractController {

    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    public ReportFragmentController(Activity activity) {
        super(activity);
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


    private void validateDialogInputs(EditText etGasStationName, EditText etMileage,
                                      EditText etSpentAmount) {
        String gasStationName = etGasStationName.getText().toString();
        String mileage = etMileage.getText().toString();
        String spentAmount = etSpentAmount.getText().toString();

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

    }


    public void showAddReportDialog() {

        showAlertDialog("", "");

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


        builder.setTitle(getActivity().getString(R.string.add_report_dialog_title))
                .setCancelable(false)
                .setPositiveButton(getActivity().getString(R.string.accept_label), null)
                .setNegativeButton(getActivity().getString(R.string.cancel_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setView(alertDialogLayout);


        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        validateDialogInputs(etGasStationName, etMileage,
                                etSpentAmount);
                    }
                });


            }
        });

        alertDialog.show();
    }


}
