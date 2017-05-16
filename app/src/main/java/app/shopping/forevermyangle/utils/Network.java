package app.shopping.forevermyangle.utils;

/**
 * @class Network
 * @desc Network class to hold url.
 */
public class Network {

    /**
     * URL to get all categories.
     */
    //public static final String URL_GET_ALL_CATEGORIES = "http://forevermyangel.com/fma-api.php?action=getallcategories";
    public static final String URL_GET_ALL_CATEGORIES = "https://forevermyangel.com/wp-json/wc/v2/products/categories?per_page=100";

    /**
     * URL to get product.
     */
    public static final String URL_GET_ALL_PRODUCTS = "http://......";

    /**
     * URL for  FMA user login.
     */
    public static final String URL_FMA_USER_LOGIN = "http://forevermyangel.com/wp-json/jwt-auth/v1/token";
}
