package rm.com.gasy.persistence.contracts;

/**
 * Created by oscargallon on 6/5/16.
 */

public class DatabaseContract {


    public static final String DATABASE_NAME = "gasy_db";

    public static final int DATABASE_VERSION = 2;

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String REAL_TYPE = " REAL";

    private static final String COMMA_SEP = ",";

    private static final String PRIMARY_KEY = " PRIMARY KEY";

    private static final String AUTOINCREMENT_KEY = " AUTOINCREMENT";

    private DatabaseContract() {
    }

    public static abstract class TankingTable {
        public static final String TABLE_NAME = "TANKING";

        public static final String COLUMN_ID = "ID";

        public static final String COLUMN_DATE = "DATE";

        public static final String COLUMN_GAS_STATION_NAME = "GAS_STATION_NAME";

        public static final String COLUMN_SPENT_AMOUNT = "SPENT_AMOUNT";

        public static final String COLUMN_MILEAGE = "MILEAGE";


        public static final String[] COLUMN_NAMES =
                new String[]{COLUMN_ID,COLUMN_DATE, COLUMN_GAS_STATION_NAME, COLUMN_SPENT_AMOUNT, COLUMN_MILEAGE};

        public static final String CREATE_TABLE = "CREATE TABLE  IF NOT EXISTS " +
                TABLE_NAME + " (" +
                COLUMN_ID + INTEGER_TYPE + PRIMARY_KEY + AUTOINCREMENT_KEY + COMMA_SEP +
                COLUMN_DATE + REAL_TYPE  + COMMA_SEP +
                COLUMN_GAS_STATION_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_SPENT_AMOUNT + REAL_TYPE + COMMA_SEP +
                COLUMN_MILEAGE + REAL_TYPE +
                ")";

        public static final String COUNT_WHERE =
                "select count(*) from " + TABLE_NAME + " where " + COLUMN_DATE + " = ?";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }


}
