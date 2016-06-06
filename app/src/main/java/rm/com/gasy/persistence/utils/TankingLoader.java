package rm.com.gasy.persistence.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import rm.com.core.model.dto.TankingDTO;
import rm.com.gasy.persistence.contracts.DatabaseContract;
import rm.com.gasy.persistence.dao.interfaces.IDatabaseOperation;

/**
 * Created by oscargallon on 6/5/16.
 */

public class TankingLoader extends AsyncTaskLoader<List<?>> {


    private IDatabaseOperation iDatabaseOperation;

    private SQLiteDatabase sqLiteDatabase;

    private String whereClause;

    private String[] whereArg;

    public TankingLoader(Context context) {
        super(context);
    }

    public void setParameters(IDatabaseOperation iDatabaseOperation,
            SQLiteDatabase sqLiteDatabase, String whereClasuse, String[] whereArg) {
        this.iDatabaseOperation = iDatabaseOperation;
        this.sqLiteDatabase = sqLiteDatabase;
        this.whereClause = whereClasuse;
        this.whereArg = whereArg;
    }

    @Override
    public List<TankingDTO> loadInBackground() {
        try {
            return (List<TankingDTO>) iDatabaseOperation.get(sqLiteDatabase, whereClause, DatabaseContract.TankingTable
                    .COLUMN_NAMES, whereArg);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }

    }
}
