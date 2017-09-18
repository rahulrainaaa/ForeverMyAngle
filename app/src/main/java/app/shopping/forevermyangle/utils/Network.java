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
     * Custom URL for get all products details from cart.
     */
    public static final String URL_GET_CART = "https://forevermyangel.com/fma-api.php?action=viewcart";

    /**
     * Custom URL to remove a product from cart.
     */
    public static final String URL_REM_FROM_CART = "https://forevermyangel.com/fma-api.php?action=removecart";

    /**
     * URL to place a new order (POST).
     */
    public static final String URL_PLACE_ORDER = "https://forevermyangel.com/wp-json/wc/v2/orders";

    /**
     * URL to get couponID from the entered coupon code.
     */
    public static final String URL_GET_COUPON_ID = "https://forevermyangel.com/wp-json/wc/v2/coupons";

    /**
     * Custom URL to clear the cart of particular userID.
     */
    public static final String URL_CLEAR_CART = "https://forevermyangel.com/fma-api.php?action=clearcart";

    /**
     * URL to get orders.
     */
    public static final String URL_GET_ORDERS = "https://forevermyangel.com/wp-json/wc/v2/orders";

    /**
     * URL to get product reviews.
     */
    public static final String URL_GET_PROD_REVIEWS = "https://forevermyangel.com/wp-json/wc/v2/products"; // "/<PROD_ID>/reviews"

    /**
     * URL to POST data and register new customer/subscriber.
     */
    public static final String URL_REGISTER_NEW = "https://forevermyangel.com/wp-json/wc/v2/customers";

}
