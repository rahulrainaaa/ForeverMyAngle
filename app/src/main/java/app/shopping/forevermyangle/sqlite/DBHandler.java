package app.shopping.forevermyangle.sqlite;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @class DBHandler
 * @desc Singleton Class for SQLite Handling.
 */
class DBHandler {

    /**
     * Public static data members.
     */
    public static int PRODUCT_CATEGORIES = 1;

    /**
     * Private data members.
     */
    private SQLiteDatabase mDatabase = null;    // Database name.
    private boolean mMutex = false;             // true=openDB, false=closeDB.
    private String DB_NAME = "FMADBASE";        //DB Name.
    private Activity mActivity = null;

    /**
     * This class instance.
     */
    private static final DBHandler ourInstance = new DBHandler();

    /**
     * @return DBHandler this singletion class reference.
     * @method getInstance
     * @desc Method to get the reference of this singleton class object.
     */
    static DBHandler getInstance() {
        return ourInstance;
    }

    /**
     * @method openDatabase
     * @desc Method to open database. Close the older, if already open.
     */
    public void openDatabase() {
        closeDatabase();
        this.DB_NAME = DB_NAME.trim();
        mDatabase = mActivity.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
    }

    /**
     * @method closeDatabase
     * @desc Method to close the database if it is open.
     */
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

    /**
     * @return SQLiteDatabase
     * @method getDatabase
     * @desc Method to get the reference to open database. Otherwise return null.
     */
    public SQLiteDatabase getDatabase() {

        return this.mDatabase;
    }

    /**
     * @method createApplicationDatabaseSchema
     * @desc Method to create all the application tables.
     */
    public void createApplicationDatabaseSchema() {

    }

    /**
     * @param i Set of tables to clear.
     * @method cleanDatabase
     * @desc Method to clean all the rows from the specified tables
     */
    public void cleanDatabase(int i) {

        if (i == (i | 1)) {     // Clear table PRODUCT_CATEGORIES.

        }

    }

}
