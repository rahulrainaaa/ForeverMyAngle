
package app.shopping.forevermyangle.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Params {

    @SerializedName("oauth_consumer_key")
    @Expose
    private String oauthConsumerKey;
    @SerializedName("oauth_timestamp")
    @Expose
    private Integer oauthTimestamp;
    @SerializedName("oauth_nonce")
    @Expose
    private String oauthNonce;
    @SerializedName("oauth_signature_method")
    @Expose
    private String oauthSignatureMethod;
    @SerializedName("oauth_signature")
    @Expose
    private String oauthSignature;

    public String getOauthConsumerKey() {
        return oauthConsumerKey;
    }

    public void setOauthConsumerKey(String oauthConsumerKey) {
        this.oauthConsumerKey = oauthConsumerKey;
    }

    public Integer getOauthTimestamp() {
        return oauthTimestamp;
    }

    public void setOauthTimestamp(Integer oauthTimestamp) {
        this.oauthTimestamp = oauthTimestamp;
    }

    public String getOauthNonce() {
        return oauthNonce;
    }

    public void setOauthNonce(String oauthNonce) {
        this.oauthNonce = oauthNonce;
    }

    public String getOauthSignatureMethod() {
        return oauthSignatureMethod;
    }

    public void setOauthSignatureMethod(String oauthSignatureMethod) {
        this.oauthSignatureMethod = oauthSignatureMethod;
    }

    public String getOauthSignature() {
        return oauthSignature;
    }

    public void setOauthSignature(String oauthSignature) {
        this.oauthSignature = oauthSignature;
    }

}
