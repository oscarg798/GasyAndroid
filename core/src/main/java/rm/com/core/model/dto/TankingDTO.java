package rm.com.core.model.dto;

import java.sql.Timestamp;

/**
 * Created by oscargallon on 6/4/16.
 */
public class TankingDTO {

    /**
     * TankingDTO Date
     */
    private final Timestamp date;

    /**
     * Name of the gas station in which the user fill the fuel of his car
     */
    private final String GasStationName;

    /**
     * Spent amount
     */
    private final String spentAmount;


    /**
     * Mileage before fill the fuel
     */
    private final String mileage;

    public TankingDTO(Timestamp date, String gasStationName, String spentAmount, String mileage) {
        this.date = date;
        GasStationName = gasStationName;
        this.spentAmount = spentAmount;

        this.mileage = mileage;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getGasStationName() {
        return GasStationName;
    }

    public String getSpentAmount() {
        return spentAmount;
    }


    public String getMileage() {
        return mileage;

    }
}
