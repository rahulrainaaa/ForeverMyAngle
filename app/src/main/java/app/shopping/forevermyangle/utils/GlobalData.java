package app.shopping.forevermyangle.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.shopping.forevermyangle.model.category.ProductCategory;
import app.shopping.forevermyangle.model.login.Login;
import app.shopping.forevermyangle.receiver.callback.ConnectionReceiverCallback;

/**
 * @class GlobalData
 * @desc Class to hold the dynamic data globally for application lifetime.
 */
public class GlobalData {

    /**
     * Category {@link HashMap<ProductCategory, List<ProductCategory>} for Expandable list view.
     */
    public static HashMap<Integer, List<ProductCategory>> category = new HashMap<Integer, List<ProductCategory>>();
    public static ArrayList<ProductCategory> parentCategories = new ArrayList<ProductCategory>();

    /**
     * Login Session related data.
     */
    public static Login login = null;

    /**
     * Network Found Receiver callback interface.
     */
    public static ConnectionReceiverCallback connectionCallback = null;

}
