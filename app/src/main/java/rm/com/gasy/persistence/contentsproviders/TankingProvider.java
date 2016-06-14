package rm.com.gasy.persistence.contentsproviders;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import rm.com.gasy.persistence.DatabaseHelper;
import rm.com.gasy.persistence.contracts.DatabaseContract;

/**
 * Created by ogallonr on 10/06/2016.
 * This is a {@link ContentProvider} for {@link rm.com.core.model.dto.TankingDTO}
 */
public class TankingProvider extends ContentProvider {

    /**
     * This is the access uri  to the provider
     * is defined as string an later convert to a {@link Uri} object
     */
    private static final String URI = "content://com.rm.gasy.contentproviders/tanking";

    /**
     * We convert the uri String to a {@link Uri} object
     */
    public static final Uri CONTENT_URI = Uri.parse(URI);

    /**
     * This is the identifier for the uris that will try to access to all {@link rm.com.core.model.dto.TankingDTO}
     * data
     */
    private static final int TANKING = 1;

    /**
     * This is the identifier for the uris that will try to access to
     * a specific {@link rm.com.core.model.dto.TankingDTO}
     */
    private static final int TANKING_ID = 2;

    private static final UriMatcher uriMatcher;

    /**
     * A instance of the database helper to get the {@link android.database.sqlite.SQLiteDatabase}
     */
    private DatabaseHelper databaseHelper;

    /**
     * The uri access to all {@link rm.com.core.model.dto.TankingDTO} data or just a specific row
     */
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.rm.gasy.contentproviders", "tanking", TANKING);
        uriMatcher.addURI("com.rm.gasy.contentproviderss", "tanking/#", TANKING_ID);
    }

    /**
     * We create a new {@link DatabaseHelper} instance and return true
     * @return true
     */
    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        String where = uriMatcher.match(uri) == TANKING_ID ?
                DatabaseContract.TankingTable.COLUMN_ID + "=" + uri.getLastPathSegment() : selection;


        return databaseHelper.getReadableDatabase()
                .query(DatabaseContract.TankingTable.TABLE_NAME,
                        projection, where, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match) {
            case TANKING:
                return "vnd.android.cursor.dir/vnd.com.rm.gasy.tanking";
            case TANKING_ID:
                return "vnd.android.cursor.item/vnd.com.rm.gasy.tanking";
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long regID;

        regID = databaseHelper.getWritableDatabase().insert(DatabaseContract.TankingTable.TABLE_NAME,
                null, values);


        return ContentUris.withAppendedId(CONTENT_URI, regID);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int count;
        String where = uriMatcher.match(uri) == TANKING_ID ?
                DatabaseContract.TankingTable.COLUMN_ID + "=" + uri.getLastPathSegment() : selection;
        count = databaseHelper.getWritableDatabase()
                .delete(DatabaseContract.TankingTable.TABLE_NAME, where, selectionArgs);

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int count;
        String where = uriMatcher.match(uri) == TANKING_ID ?
                DatabaseContract.TankingTable.COLUMN_ID + "=" + uri.getLastPathSegment() : selection;
        count = databaseHelper.getWritableDatabase()
                .update(DatabaseContract.TankingTable.TABLE_NAME, values, where, selectionArgs);

        return count;
    }
}
