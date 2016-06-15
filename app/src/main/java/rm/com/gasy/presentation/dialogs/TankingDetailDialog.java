package rm.com.gasy.presentation.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

import rm.com.core.model.dto.TankingDTO;
import rm.com.gasy.R;

/**
 * Created by ogallonr on 14/06/2016.
 */

public class TankingDetailDialog extends DialogFragment {


    private TextView tvTankingDate;

    private TextView tvGasStationName;

    private TextView tvMileage;

    private TextView tvSpentAmount;

    private String dialogTitle;

    private String buttonText;

    private TankingDTO tankingDTO;

    private SimpleDateFormat simpleDateFormat;

    public static TankingDetailDialog getInstace(String dialogTitle, String buttonText, TankingDTO tankingDTO) {
        TankingDetailDialog tankingDetailDialog = new TankingDetailDialog();
        Bundle bundle = new Bundle();
        bundle.putString("title", dialogTitle);
        bundle.putString("button_text", buttonText);
        bundle.putSerializable("tanking", tankingDTO);
        tankingDetailDialog.setArguments(bundle);
        return tankingDetailDialog;
    }

    public TankingDetailDialog() {
        super();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        if (getArguments() != null) {
            this.dialogTitle = getArguments().getString("title");
            this.buttonText = getArguments().getString("button_text");
            this.tankingDTO = (TankingDTO) getArguments().getSerializable("tanking");
        }
        simpleDateFormat = new SimpleDateFormat(getContext().getString(R.string.date_format));
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.tanking_detail_dialog, null);
        initViewComponents(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle(dialogTitle);
        builder.setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        setTankingData();


        return builder.create();


    }

    private void setTankingData() {
        tvGasStationName.setText(tankingDTO.getGasStationName());
        tvTankingDate.setText(simpleDateFormat.format
                (new Date(tankingDTO.getDate().getTime())));
        tvSpentAmount.setText((String.format("%.0f",
                tankingDTO.getSpentAmount())));
        tvMileage.setText((String.format("%.0f",
                tankingDTO.getMileage())));
    }

    private void initViewComponents(View view) {
        tvTankingDate = (TextView) view.findViewById(R.id.tv_tanking_date);
        tvGasStationName = (TextView) view.findViewById(R.id.tv_gas_station_name);
        tvMileage = (TextView) view.findViewById(R.id.tv_mileage);
        tvSpentAmount = (TextView) view.findViewById(R.id.tv_spent_amount);
    }
}
