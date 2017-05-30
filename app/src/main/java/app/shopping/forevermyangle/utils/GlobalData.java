package app.shopping.forevermyangle.utils;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.shopping.forevermyangle.model.category.Category;
import app.shopping.forevermyangle.model.login.Login;
import app.shopping.forevermyangle.model.products.Product;
import app.shopping.forevermyangle.receiver.callback.ConnectionReceiverCallback;

/**
 * @class GlobalData
 * @desc Class to hold the dynamic data globally for application lifetime.
 */
public class GlobalData {

    /**
     * Category {@link HashMap<app.shopping.forevermyangle.model.category.Category, List<app.shopping.forevermyangle.model.category.Category>} for Expandable list view.
     */
    public static HashMap<Integer, List<Category>> category = new HashMap<Integer, List<Category>>();
    public static ArrayList<Category> parentCategories = new ArrayList<Category>();

    /**
     * Login Session: offline global data object.
     */
    public static Login login = null;

    /**
     * Network Found Receiver callback interface.
     */
    public static ConnectionReceiverCallback connectionCallback = null;

    /**
     * User detail.
     */
    public static JSONObject jsonUserDetail = null;

    /**
     * Dashboard Banner 12 products for 3 fragment types.
     */
    public static JSONArray NewArrivedProducts = new JSONArray();
    public static JSONArray TopRatedProducts = new JSONArray();
    public static JSONArray TopSellProducts = new JSONArray();

    public static ArrayList<Product> TotalProducts = new ArrayList<>();        // Holds total products displayed on page.

    public static JSONObject SelectedProduct = null;         // Selected product from description.
    /**
     * Search filtering.
     */
    public static String srch_category_id = "";

}
