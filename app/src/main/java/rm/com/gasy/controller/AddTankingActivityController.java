package rm.com.gasy.controller;

import android.app.Activity;

import com.rm.androidesentials.controllers.abstracts.AbstractController;
import com.rm.androidesentials.utils.Validation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import rm.com.core.model.dto.TankingDTO;
import rm.com.core.model.dto.builders.TankingDTOBuilder;
import rm.com.gasy.R;
import rm.com.gasy.presentation.activities.AddTankingActivity;

/**
 * Created by oscargallon on 6/6/16.
 * {@link AddTankingActivity} controller
 */
public class AddTankingActivityController extends AbstractController {


    /**
     * Contructor de la clase
     *
     * @param activity actividad a la cual pertenece el controlador
     */
    public AddTankingActivityController(Activity activity) {
        super(activity);

    }

    /**
     * This method validate if the mileage enter has the right lengt
     *
     * @param mileage mileage enter by user
     * @return the correct mileage format
     */
    private String fillMileage(String mileage) {
        String zeros = "";
        int diference = 3 - mileage.length();
        for (int i = 0; i < diference; i++) {

            zeros += "0";

        }
        mileage = zeros + mileage;
        return mileage;
    }

    /**
     * This method validate the form data
     *
     * @param tankingDate    date enter by user
     * @param gasStationName the gas station
     * @param mileage        mileage enter by user
     * @param spentAmount    spent amount enter by user
     */
    public void validateTankingData(final long tankingDate, final String gasStationName, String mileage,
                                    final String spentAmount) {

        /**
         * Validate if the gasStationName has been entered
         */
        if (!Validation.validateStringThanCanNotBeEmpty(gasStationName)) {
            ((AddTankingActivity) getActivity()).getAtGasStationName()
                    .setError(getActivity()
                            .getString(R.string.should_add_gas_station_name_label));
            ((AddTankingActivity) getActivity()).getAtGasStationName().requestFocus();
            return;
        }

        if (mileage.length() < 3) {
            mileage = fillMileage(mileage);
        }

        /**
         * We validate if the user has entered a mileage, and if that mileage is not equals
         * to 0
         */
        if (!Validation.validateStringThanCanNotBeEmpty(mileage) && !mileage.equals("0000")) {
            ((AddTankingActivity) getActivity()).getEtMileage()
                    .setError(getActivity().getString(R.string.should_enter_mileage_label));
            ((AddTankingActivity) getActivity()).getEtMileage().requestFocus();
            return;
        }

        /**
         * We validate if the user has enter a spent amount for the tanking
         */
        if (!Validation.validateStringThanCanNotBeEmpty(spentAmount)) {
            ((AddTankingActivity) getActivity()).getEtSpentAmount()
                    .setError(getActivity().getString(R.string.should_enter_spent_amount));
            ((AddTankingActivity) getActivity()).getEtSpentAmount()
                    .requestFocus();
            return;
        }
        /**
         * If form data if valid, we create a {@link TankingDTO}
         * list to be returned to the parent Activity
         */
        List<TankingDTO> tankingDTOList = new ArrayList<>();
        tankingDTOList.add(new TankingDTOBuilder()
                .withADate(new Timestamp(tankingDate))
                .withAGasStationName(gasStationName)
                .withASpentAmount(Double.parseDouble(spentAmount))
                .withAMileage(Double.parseDouble(mileage))
                .createTakingDTO());

        ((AddTankingActivity) getActivity()).finishAndReturnResult(tankingDTOList);
    }
}
