package rm.com.gasy.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import rm.com.gasy.persistence.contracts.DatabaseContract;

/**
 * Created by oscargallon on 6/5/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        runCreateStatements(db);
    }

    private void runCreateStatements(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.TankingTable.CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.TankingTable.DELETE_TABLE);
        runCreateStatements(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.TankingTable.DELETE_TABLE);
        runCreateStatements(db);

    }
}
