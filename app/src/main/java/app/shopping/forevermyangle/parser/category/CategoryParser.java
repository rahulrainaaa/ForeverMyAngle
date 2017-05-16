package app.shopping.forevermyangle.parser.category;

import com.google.gson.Gson;

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
     * @param raw {@link List<Category>}
     * @method parseRawCategoryList
     * @desc Method to parse Raw category list (from web service) into expandable List as {@link java.util.HashMap<Category, List<Category>>}.
     */
    public void parseRawCategoryList(List<Category> raw) {

        // Clean the map and iterate raw data for parent = 0 (main category).
        GlobalData.category.clear();
        GlobalData.parentCategories.clear();
        Iterator<Category> categoryIterator = raw.iterator();
        Gson gson = new Gson();
        while (categoryIterator.hasNext()) {

            Category tempData = categoryIterator.next();

            if (tempData.getParent() == 0) {

                GlobalData.parentCategories.add(tempData);
                // Iterate the raw data to find sub category of main category.
                ArrayList<Category> subCategoryList = new ArrayList<>();
                Iterator<Category> subCategoryIterator = raw.iterator();

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
