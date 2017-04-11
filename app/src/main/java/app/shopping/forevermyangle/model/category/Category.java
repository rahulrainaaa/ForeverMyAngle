
package app.shopping.forevermyangle.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import app.shopping.forevermyangle.model.base.BaseModel;

public class Category extends BaseModel {

    @SerializedName("product_categories")
    @Expose
    private List<ProductCategory> productCategories = null;
    @SerializedName("http")
    @Expose
    private Http http;

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

}
