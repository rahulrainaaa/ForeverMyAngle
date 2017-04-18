package app.shopping.forevermyangle.parser.category;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import app.shopping.forevermyangle.model.category.ProductCategory;
import app.shopping.forevermyangle.parser.BaseParser.BaseParser;
import app.shopping.forevermyangle.utils.GlobalData;


public class CategoryParser extends BaseParser {

    /**
     * @param raw {@link List<ProductCategory>}
     * @method parseRawCategoryList
     * @desc Method to parse Raw category list (from web service) into expandable List as {@link java.util.HashMap<ProductCategory, List<ProductCategory>>}.
     */
    public void parseRawCategoryList(List<ProductCategory> raw) {

        // Clean the map and iterate raw data for parent = 0 (main category).
        GlobalData.category.clear();
        Iterator<ProductCategory> categoryIterator = raw.iterator();

        while (categoryIterator.hasNext()) {
            ProductCategory tempData = categoryIterator.next();

            if (tempData.getParent() == 0) {

                // Iterate the raw data to find sub category of main category.
                ArrayList<ProductCategory> subCategoryList = new ArrayList<>();
                Iterator<ProductCategory> subCategoryIterator = raw.iterator();

                while (subCategoryIterator.hasNext()) {
                    ProductCategory tempSubData = subCategoryIterator.next();

                    if (tempSubData.getParent() == tempData.getId()) {
                        subCategoryList.add(tempSubData);
                    }
                }
                GlobalData.category.put(tempData, subCategoryList);

            }

        }

    }

}
