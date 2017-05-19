package app.shopping.forevermyangle.parser.products;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.shopping.forevermyangle.model.products.Product;
import app.shopping.forevermyangle.parser.BaseParser.BaseParser;
import app.shopping.forevermyangle.utils.GlobalData;

/**
 * @class ProductParser
 * @desc Parser class for product parse handling.
 */
public class ProductParser extends BaseParser {

    /**
     * @param raw
     * @return int Total number of new products inserted.
     * @method parseProducts
     * @desc Method to parse the http raw Json array response into model.
     */
    public int parseProducts(JSONArray raw) {

        try {
            parse(raw);
        } catch (JSONException jsonE) {
            jsonE.printStackTrace();
        }
        return raw.length();
    }

    private void parse(JSONArray raw) throws JSONException {

        Product product = null;
        JSONObject json = null;
        int length = raw.length();

        for (int i = 0; i < length; i++) {

            product = new Product();

            product.json = raw.getJSONObject(i);
            json = product.json;

            product.id = json.getInt("id");
            product.name = json.getString("name");
            product.permalink = json.getString("permalink");
            product.price = json.getString("price");
            product.total_sales = json.getString("total_sales");
            product.in_stock = json.getBoolean("in_stock");
            product.average_rating = json.getString("average_rating");
            product.rating_count = json.getString("rating_count");
            product.image = json.getJSONArray("images").getJSONObject(0).getString("src");

            GlobalData.TotalProducts.add(product);
        }


    }


}
