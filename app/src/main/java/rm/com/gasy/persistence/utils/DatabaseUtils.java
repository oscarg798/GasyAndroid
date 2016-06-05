package rm.com.gasy.persistence.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by oscargallon on 6/5/16.
 */

public class DatabaseUtils {


    private DatabaseUtils() {
    }

    public static int getCount(SQLiteDatabase db, String query, String key) {
        Cursor c = null;
        String[] whereArg = null;
        if (key != null) {
            whereArg = new String[]{key};
        }
        c = db.rawQuery(query, whereArg);
        if (c.moveToFirst()) {
            return c.getInt(0);
        }
        return 0;
    }
}
