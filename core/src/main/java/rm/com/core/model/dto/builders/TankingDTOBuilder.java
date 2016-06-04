package rm.com.core.model.dto.builders;

import java.sql.Timestamp;

import rm.com.core.model.dto.TankingDTO;


/**
 * Created by oscargallon on 6/4/16.
 * Builder pattern for TakingDTO class
 */
public class TankingDTOBuilder {

    /**
     * TankingDTO Date
     */
    private Timestamp aDate;

    /**
     * Name of the gas station in which the user fill the fuel of his car
     */
    private String aGasStationName;

    /**
     * Spent amount
     */
    private String aSpentAmount;


    /**
     * Mileage before fill the fuel
     */
    private String aMileage;


    public TankingDTOBuilder withADate(Timestamp aDate) {
        this.aDate = aDate;
        return this;
    }

    public TankingDTOBuilder withAGasStationName(String aGasStationName) {
        this.aGasStationName = aGasStationName;
        return this;
    }

    public TankingDTOBuilder withASpentAmount(String aSpentAmount) {
        this.aSpentAmount = aSpentAmount;
        return this;

    }

    public TankingDTOBuilder withAMileage(String aMileage) {
        this.aMileage = aMileage;
        return this;
    }

    public TankingDTO createTakingDTO() {
        return new TankingDTO(aDate, aGasStationName, aSpentAmount, aMileage);
    }


}
