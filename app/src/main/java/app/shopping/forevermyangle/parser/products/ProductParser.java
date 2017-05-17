package app.shopping.forevermyangle.parser.products;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;

import java.util.ArrayList;

import app.shopping.forevermyangle.model.products.Product;
import app.shopping.forevermyangle.parser.BaseParser.BaseParser;

/**
 * @class ProductParser
 * @desc Parser class for product parse handling.
 */
public class ProductParser extends BaseParser {


    /**
     * @param raw
     * @method parseProducts
     * @desc Method to parse the http raw Json array response into model.
     */
    public void parseProducts(JSONArray raw) {


    }

    /**
     * @param raw {@link JSONArray} of {@link app.shopping.forevermyangle.model.products.Product} Object.
     * @method parseNewArrivedProducts
     * @desc Method to parse 4 newly arrived products.
     */
    public void parseNewArrivedProducts(JSONArray raw) {

        Gson gson = new Gson();

        ArrayList rawList = gson.fromJson(String.valueOf(raw), ArrayList.class);
        ArrayList<Product> list = new ArrayList<>();       // List to store all products.

        for (int i = 0; i < rawList.size(); i++) {

            try {
                JsonObject json = gson.toJsonTree(rawList.get(i)).getAsJsonObject();

                Product model = gson.fromJson(json, Product.class);
                list.add(model);

            } catch (JsonSyntaxException jse) {

                jse.printStackTrace();
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
}
