package app.shopping.forevermyangle.utils;

/**
 * @class Constants
 * @desc Class to hold configurable values.
 */
public class Constants {

    /**
     * Resolution Data.
     */
    public static int RES_WIDTH = 0;
    public static int RES_HEIGHT = 0;

    /**
     * SQLite Create Table Queries.
     */
    public static String SQL_C_PRODUCT_CATEGORY = "CREATE TABLE IF NOT EXISTS product_category (id INTEGER PRIMARY KEY, name VARCHAR, slug VARCHAR, parent INTEGER, description VARCHAR, display VARCHAR, image VARCHAR, count INTEGER);";

    /**
     * SQlite Insert Table Queries.
     */
    public static String SQL_I_PRODUCT_CATEGORY = "INSERT INTO product_category (id, name, slug, parent, description, display, image, count) VALUES (?,?,?,?,?,?,?,?);";

    /**
     * SQLite Delete from Table Query.
     */
    public static String SQL_D_PRODUCT_CATEGORY = "DELETE FROM product_category";

}
