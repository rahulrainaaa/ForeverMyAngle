package app.shopping.forevermyangle.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.shopping.forevermyangle.model.category.ProductCategory;

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

}
