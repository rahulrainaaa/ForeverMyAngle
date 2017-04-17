package app.shopping.forevermyangle.utils;

import java.util.ArrayList;

import app.shopping.forevermyangle.model.category.ProductCategory;

/**
 * @class Constants
 * @desc Class to hold globally hold and share data throughout the application.
 */
public class Constants {

    /**
     * Resolution Data.
     */
    public static int RES_WIDTH = 0;
    public static int RES_HEIGHT = 0;

    /**
     * Application Global shared {@link ProductCategory} list.
     */
    public static ArrayList<ProductCategory> CATEGORY_LIST = new ArrayList<>();
    public static ArrayList<ProductCategory> ALL_CATEGORY_LIST = new ArrayList<>();

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
