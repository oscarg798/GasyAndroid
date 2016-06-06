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
import rm.com.gasy.persistence.dao.interfaces.IDatabaseOperation;
import rm.com.gasy.persistence.dao.interfaces.ITankingDAO;

/**
 * Created by oscargallon on 6/5/16.
 */

public class TankingDAO implements ITankingDAO {


    @Override
    public float insert(SQLiteDatabase db, List<ContentValues> contentValuesList) {
        float result = 0;
        for (ContentValues contentValues : contentValuesList) {
            result += db.insert(DatabaseContract.TankingTable.TABLE_NAME, null, contentValues);
        }

        return result;
    }

    @Override
    public float update(SQLiteDatabase db, List<ContentValues> contentValuesList,
                        String whereClause, String[] whereArg) {
        return 0;
    }

    @Override
    public List<TankingDTO> get(SQLiteDatabase db, String whereClause,
                                String[] columnNames, String[] whereArg) {

        Cursor cursor = db.query(DatabaseContract.TankingTable.TABLE_NAME, columnNames,
                whereClause, whereArg, null, null, null, null);
        return getObjectFromCursor(cursor);
    }

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
