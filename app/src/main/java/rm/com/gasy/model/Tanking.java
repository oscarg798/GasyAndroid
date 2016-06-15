package rm.com.gasy.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import rm.com.core.model.dto.TankingDTO;
import rm.com.core.model.dto.utils.AsyncTaskExecutor;
import rm.com.core.model.dto.utils.Callbacks;
import rm.com.core.model.dto.utils.IAsyncTaskExecutor;
import rm.com.gasy.persistence.contracts.DatabaseContract;
import rm.com.gasy.persistence.dao.implementation.TankingDAO;
import rm.com.gasy.persistence.dao.interfaces.IDatabaseOperation;

/**
 * Created by oscargallon on 6/6/16.
 * This class represents the Tanking business object
 * This class was not created into the core module because, it has methods
 * that are only for smartphones and tablets device
 */
public class Tanking {

    /**
     * Is a singleton so we need a static instance
     */
    private static Tanking instance = new Tanking();


    private Tanking() {

    }

    public static Tanking getInstance() {
        return instance;
    }

    /**
     * This method insert a @{@link TankingDTO} list into the database
     *
     * @param db             database instance
     * @param tankingDTOList list to be inserted
     * @param tankingDAO     Data Access object that contains  to the insert method
     */
    public void insertTankingIntoDatabase(final SQLiteDatabase db, final List<TankingDTO> tankingDTOList,
                                          final IDatabaseOperation tankingDAO) {

        /**
         * We create a background task to insert the data into the database.
         */
        AsyncTaskExecutor asyncTaskExecutor = new AsyncTaskExecutor(new IAsyncTaskExecutor() {
            @Override
            public Object execute() {
                tankingDAO.insert(db, tankingDAO.getContentValuesFromObject(tankingDTOList));
                return true;
            }

            @Override
            public void onExecuteComplete(Object object) {
                Log.i("INSERTE", "LOGRE INSERTAR");
            }

            @Override
            public void onExecuteFaliure(Exception e) {
                e.printStackTrace();
                Log.i("INSERTE", "NO LOGRE INSERTAR");
            }
        });

        asyncTaskExecutor.execute();
    }

    public void deleteTankigDTO(final List<TankingDTO> tankingDTOList, final SQLiteDatabase db,
                                final Callbacks.IDatabaseOperationCallback iDatabaseOperationCallback,
                                final IDatabaseOperation tankingDAO) {
        AsyncTaskExecutor asyncTaskExecutor = new AsyncTaskExecutor(new IAsyncTaskExecutor() {
            @Override
            public Object execute() {
                float affectedRows = tankingDAO.delete(db, DatabaseContract.TankingTable.WHERE_PRIMARY_KEY,
                        new String[]{Integer.toString(tankingDTOList.get(0).getID())});
                return affectedRows;
            }

            @Override
            public void onExecuteComplete(Object object) {
                float affectedRows = (float) object;
                iDatabaseOperationCallback.onOperationExecuted(affectedRows, null);
                Log.i("BORRAR", "LOGRE BORRAR");
            }

            @Override
            public void onExecuteFaliure(Exception e) {
                e.printStackTrace();
                Log.i("BORRAR", "NO LOGRE BORRAR");
            }
        }

        );

        asyncTaskExecutor.execute();
    }


    public void editTankingDTO(final List<TankingDTO> tankingDTOList, final SQLiteDatabase db,
                               final Callbacks.IDatabaseOperationCallback iDatabaseOperationCallback,
                               final IDatabaseOperation tankingDAO) {


        final List<ContentValues> contentValuesList = tankingDAO.getContentValuesFromObject(tankingDTOList);
        contentValuesList.get(0).remove(DatabaseContract.TankingTable.COLUMN_ID);

        AsyncTaskExecutor asyncTaskExecutor = new AsyncTaskExecutor(new IAsyncTaskExecutor() {
            @Override
            public Object execute() {
                float affectedRows = tankingDAO.update(db, contentValuesList,
                        DatabaseContract.TankingTable.WHERE_PRIMARY_KEY,
                        new String[]{Integer.toString(tankingDTOList.get(0).getID())});
                return affectedRows;
            }

            @Override
            public void onExecuteComplete(Object object) {
                float affectedRows = (float) object;
                iDatabaseOperationCallback.onOperationExecuted(affectedRows, null);
                Log.i("ACTUALIZAR", "LOGRE ACTUALIZAR");
            }

            @Override
            public void onExecuteFaliure(Exception e) {
                e.printStackTrace();
                Log.i("ACTUALIZAR", "NO LOGRE ACTUALIZAR");
            }
        }

        );

        asyncTaskExecutor.execute();
    }
}
