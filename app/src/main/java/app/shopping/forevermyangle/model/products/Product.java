package app.shopping.forevermyangle.model.products;

import org.json.JSONObject;

import app.shopping.forevermyangle.model.base.BaseModel;

/**
 * @class Product
 * @desc Model class to hold the product list.
 */
public class Product extends BaseModel {

    public int id = -1;
    public String name = null;
    public String permalink = null;
    public String price = null;
    public String total_sales = null;
    public Boolean in_stock = null;
    public int stock_qty = 0;
    public String image = null;
    public String average_rating = null;
    public String rating_count = null;

    public JSONObject json = null;

}
