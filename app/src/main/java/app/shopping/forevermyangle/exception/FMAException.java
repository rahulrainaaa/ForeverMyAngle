package app.shopping.forevermyangle.exception;

/**
 * @class FMAException
 * @desc Exception handling class for this application.
 */
public class FMAException extends Exception {

    /**
     * Exception custom code holder variable.
     */
    private int mCode = 0;

    /**
     * @param code
     * @param message
     * @constructor: FMAException
     */
    public FMAException(int code, String message) {
        super(message);
        mCode = code;
    }

    /**
     * @return int Exception Code
     * @method getCode
     * @desc Method to get the Exception code.
     */
    public int getCode() {
        return mCode;
    }
}
