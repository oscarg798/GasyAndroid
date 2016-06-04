package rm.com.gasy.controller;


import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.rm.androidesentials.controllers.abstracts.AbstractController;

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


    public void showAddReportDialog() {

        LayoutInflater layoutInflater =
                getActivity().getLayoutInflater();

        View alertDialogLayout =
                layoutInflater.inflate(R.layout.add_report_dialog, null);

        DialogInterface.OnClickListener positiveButtonOnClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                };

        DialogInterface.OnClickListener negativeButtonOnClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getActivity().getString(R.string.add_report_dialog_title))
                .setCancelable(false)
                .setPositiveButton(getActivity().getString(R.string.accept_label), positiveButtonOnClickListener)
                .setNegativeButton(getActivity().getString(R.string.cancel_label), negativeButtonOnClickListener)
                .setView(alertDialogLayout);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
