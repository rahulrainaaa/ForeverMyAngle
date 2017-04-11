
package app.shopping.forevermyangle.model.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category {

    private List<ProductCategory> productCategories = null;
    private Http http;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }

    public Http getHttp() {
        return http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
