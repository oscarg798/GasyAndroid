package rm.com.gasy.persistence.dao.implementation;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import rm.com.core.model.dto.TankingDTO;
import rm.com.core.model.dto.builders.TankingDTOBuilder;
import rm.com.gasy.persistence.contracts.DatabaseContract;
import rm.com.gasy.persistence.dao.interfaces.ITankingDAO;

/**
 * Created by oscargallon on 6/5/16.
 * This class is the Data Access Object to a {@link TankingDTO} persistence data.
 * This class provide CRUD methods to manipulate the {@link TankingDTO} data
 * Also this class have some utils methods  for get a {@link ContentValues} list from
 * a {@link TankingDTO} list or get a {@link TankingDTO} list from a {@link Cursor} object
 */
public class TankingDAO implements ITankingDAO {

    /**
     * This method insert a TankingDTO data to database
     *
     * @param db                database instance
     * @param contentValuesList data to insert
     * @return return the number of row affected by the insert
     */
    @Override
    public float insert(SQLiteDatabase db, List<ContentValues> contentValuesList) {
        float result = 0;
        for (ContentValues contentValues : contentValuesList) {
            result += db.insert(DatabaseContract.TankingTable.TABLE_NAME, null, contentValues);
        }

        return result;
    }

    /**
     * This method update a tankingDTO data
     *
     * @param db                database instance
     * @param contentValuesList data to update
     * @param whereClause       where some column equals to...
     * @param whereArg          where arguments
     * @return the number of rows affected
     */
    @Override
    public float update(SQLiteDatabase db, List<ContentValues> contentValuesList,
                        String whereClause, String[] whereArg) {
        return 0;
    }

    /**
     * This method return a TankingDTO data form database
     *
     * @param db          database instance
     * @param whereClause where some column equals to...
     * @param columnNames the from columns to get
     * @param whereArg    the where arguments
     * @return a list of {@link TankingDTO} or null if some error
     */
    @Override
    public List<TankingDTO> get(SQLiteDatabase db, String whereClause,
                                String[] columnNames, String[] whereArg) {

        Cursor cursor = db.query(DatabaseContract.TankingTable.TABLE_NAME, columnNames,
                whereClause, whereArg, null, null, null, null);
        return getObjectFromCursor(cursor);
    }

    /**
     * This method return a List of {@link TankingDTO} from a {@link Cursor}
     *
     * @param cursor the pointer to database table
     * @return {@link TankingDTO} list or null if error
     */
    @Override
    public List<TankingDTO> getObjectFromCursor(Cursor cursor) {
        List<TankingDTO> tankingDTOList = null;
        if (cursor == null) {
            return tankingDTOList;
        }

        if (cursor.moveToFirst()) {
            tankingDTOList = new ArrayList<>();
            do {
                tankingDTOList.add(new TankingDTOBuilder()
                        .withAnID(cursor.getInt(cursor.getColumnIndex(DatabaseContract.TankingTable
                                .COLUMN_ID)))
                        .withADate(new Timestamp(cursor.getLong(cursor.getColumnIndex(DatabaseContract
                                .TankingTable.COLUMN_DATE))))
                        .withAGasStationName(cursor.getString(cursor.getColumnIndex(DatabaseContract
                                .TankingTable.COLUMN_GAS_STATION_NAME)))
                        .withAMileage(cursor.getDouble(cursor.getColumnIndex(DatabaseContract
                                .TankingTable.COLUMN_MILEAGE)))
                        .withASpentAmount(cursor.getDouble(cursor.getColumnIndex(DatabaseContract
                                .TankingTable.COLUMN_SPENT_AMOUNT))).createTakingDTO());

            } while (cursor.moveToNext());


        }


        return tankingDTOList;
    }

    /**
     * This method return a List of {@link ContentValues} from a {@link TankingDTO} List
     *
     * @param objects the {@link TankingDTO} objects
     * @return {@link ContentValues}  list
     */
    @Override
    public List<ContentValues> getContentValuesFromObject(List<?> objects) {
        List<ContentValues> contentValuesList = new ArrayList<>();
        try {
            List<TankingDTO> tankingDTOList = (List<TankingDTO>) objects;

            for (TankingDTO tankingDTO : tankingDTOList) {

                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseContract.TankingTable.COLUMN_DATE,
                        tankingDTO.getDate().getTime());
                contentValues.put(DatabaseContract.TankingTable.COLUMN_GAS_STATION_NAME,
                        tankingDTO.getGasStationName());
                contentValues.put(DatabaseContract.TankingTable.COLUMN_MILEAGE,
                        tankingDTO.getMileage());
                contentValues.put(DatabaseContract.TankingTable.COLUMN_SPENT_AMOUNT,
                        tankingDTO.getSpentAmount());

                contentValuesList.add(contentValues);

            }
        } catch (ClassCastException e) {
            e.printStackTrace();
            contentValuesList = null;
            return contentValuesList;
        }

        return contentValuesList;
    }


}
