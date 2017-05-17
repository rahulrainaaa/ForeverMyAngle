package app.shopping.forevermyangle.parser.category;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import app.shopping.forevermyangle.model.category.Category;
import app.shopping.forevermyangle.parser.BaseParser.BaseParser;
import app.shopping.forevermyangle.utils.GlobalData;

/**
 * @class CategoryParser
 * @desc Parser class to parse the category class.
 */
public class CategoryParser extends BaseParser {

    /**
     * @param rawJsonArray
     * @method parseRawCategoryList
     * @desc Method to parse Raw category list (from web service) into expandable List as {@link java.util.HashMap<Category, List<Category>>}.
     */
    public void parseRawCategoryList(JSONArray rawJsonArray) {

        // Gson: response to model mapping: Parsing Raw data from http.
        Gson gson = new Gson();

        ArrayList rawList = gson.fromJson(String.valueOf(rawJsonArray), ArrayList.class);
        ArrayList<Category> list = new ArrayList<>();       // List to store all categories.

        for (int i = 0; i < rawList.size(); i++) {

            try {
                JsonObject json = gson.toJsonTree(rawList.get(i)).getAsJsonObject();
                Category model = gson.fromJson(json, Category.class);


                list.add(model);

            } catch (JsonSyntaxException jse) {
                jse.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Clean the map and iterate raw data for parent = 0 (main category).
        GlobalData.category.clear();
        GlobalData.parentCategories.clear();

        // Start Iterator.
        Iterator<Category> categoryIterator = list.iterator();
        while (categoryIterator.hasNext()) {

            Category tempData = categoryIterator.next();

            if (tempData.getParent() == 0) {

                GlobalData.parentCategories.add(tempData);
                // Iterate the raw data to find sub category of main category.
                ArrayList<Category> subCategoryList = new ArrayList<>();
                Iterator<Category> subCategoryIterator = list.iterator();

                while (subCategoryIterator.hasNext()) {
                    Category tempSubData = subCategoryIterator.next();

                    if (tempSubData.getParent() == tempData.getId()) {
                        subCategoryList.add(tempSubData);
                    }
                }
                GlobalData.category.put(new Integer(GlobalData.parentCategories.size() - 1), subCategoryList);

            }

        }

    }

}
