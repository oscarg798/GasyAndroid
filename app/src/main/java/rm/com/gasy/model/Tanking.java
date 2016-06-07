package rm.com.gasy.model;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import rm.com.core.model.dto.TankingDTO;
import rm.com.core.model.dto.utils.AsyncTaskExecutor;
import rm.com.core.model.dto.utils.IAsyncTaskExecutor;
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
     * @param db database instance
     * @param tankingDTOList list to be inserted
     * @param tankingDAO Data Access object that contains  to the insert method
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
}
