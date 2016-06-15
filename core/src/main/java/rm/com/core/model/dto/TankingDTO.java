package rm.com.core.model.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by oscargallon on 6/4/16.
 */
public class TankingDTO implements Serializable {

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
    private final double spentAmount;


    /**
     * Mileage before fill the fuel
     */
    private final double mileage;

    private final int ID;

    public TankingDTO(int ID, Timestamp date, String gasStationName, double spentAmount, double mileage) {
        this.date = date;
        this.ID = ID;
        this.GasStationName = gasStationName;
        this.spentAmount = spentAmount;
        this.mileage = mileage;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getGasStationName() {
        return GasStationName;
    }

    public double getSpentAmount() {
        return spentAmount;
    }


    public double getMileage() {
        return mileage;

    }

    public int getID() {
        return ID;
    }
}
