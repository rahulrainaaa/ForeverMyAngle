package app.shopping.forevermyangle.sqlite;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

class DBHandler {

    private SQLiteDatabase mDatabase = null;
    private boolean mMutex = false;
    private String DB_NAME = "FMADBASE";
    private Activity mActivity = null;

    private static final DBHandler ourInstance = new DBHandler();

    static DBHandler getInstance() {
        return ourInstance;
    }

    public void openDatabase() {
        closeDatabase();
        this.DB_NAME = DB_NAME.trim();
        mDatabase = mActivity.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
    }

    public void closeDatabase() {

        try {
            if (mDatabase != null) {
                if (mDatabase.isOpen()) {
                    mDatabase.close();
                }
                mDatabase = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            mDatabase = null;
            this.DB_NAME = null;
        }

    }

    public SQLiteDatabase getDatabase() {

        return this.mDatabase;
    }

    public void createApplicationDatabaseSchema() {

    }

    public void cleanDatabase(int i) {

        if (i == (i | 1)) {     // Clear table 1.

        }
        if (i == (i | 2)) {     // Clear table 2.

        }

    }

}
