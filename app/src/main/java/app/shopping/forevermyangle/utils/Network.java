package app.shopping.forevermyangle.utils;

/**
 * @class Network
 * @desc Network class to hold url.
 */
public class Network {

    /**
     * URL to get all categories.
     */
    //public static final String URL_GET_ALL_CATEGORIES = "https://forevermyangel.com/fma-api.php?action=getallcategories";
    public static final String URL_GET_ALL_CATEGORIES = "https://forevermyangel.com/wp-json/wc/v2/products/categories?per_page=100";

    /**
     * URL to get product.
     */
    public static final String URL_GET_ALL_PRODUCTS = "https://forevermyangel.com/wp-json/wc/v2/products";

    /**
     * URL for  FMA user login.
     */
    public static final String URL_FMA_USER_LOGIN = "https://forevermyangel.com/wp-json/jwt-auth/v1/token";

    /**
     * URL for  FMA user get detail from email.
     */
    public static final String URL_FMA_USER_DETAIL = "https://forevermyangel.com/wp-json/wc/v2/customers";

    /**
     * Custom URL for adding to cart.
     */
    public static final String URL_ADD_TO_CART = "https://forevermyangel.com/fma-api.php?action=addtocart";

    /**
     * Custom URL for get all products detials from cart.
     */
    public static final String URL_GET_CART = "https://forevermyangel.com/fma-api.php?action=addtocart";
}
