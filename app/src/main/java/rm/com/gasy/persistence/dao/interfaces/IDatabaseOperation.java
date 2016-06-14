package rm.com.gasy.persistence.dao.interfaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.GenericArrayType;
import java.util.List;

/**
 * Created by oscargallon on 6/5/16.
 */
public interface IDatabaseOperation {

    float insert(SQLiteDatabase db, List<ContentValues> contentValuesList);

    float update(SQLiteDatabase db, List<ContentValues> contentValuesList, String whereClause,
                 String[] whereArg);

    float delete(SQLiteDatabase db, String whereClause, String[] whereArg);

    List<?> get(SQLiteDatabase db,String whereClause, String[] columnNames,
                         String[] whereArg);


    List<?> getObjectFromCursor(Cursor cursor);

    List<ContentValues> getContentValuesFromObject(List<?> objects);

    void setCurrentContext(Context context);
}
