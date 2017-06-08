package app.shopping.forevermyangle.model.products;

import app.shopping.forevermyangle.model.base.BaseModel;


public class CartProduct extends BaseModel {

    public int id = -1;
    public String name = null;
    public String qty = null;
    public String image = null;
    public String key = null;

    public int total = 0;
    public int sub_total = 0;

    public int total_tax = 0;
    public int sub_total_tax = 0;
}
