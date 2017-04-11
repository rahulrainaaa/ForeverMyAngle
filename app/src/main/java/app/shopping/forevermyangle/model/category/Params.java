
package app.shopping.forevermyangle.model.category;

import java.util.HashMap;
import java.util.Map;

public class Params {

    private String oauthConsumerKey;
    private Integer oauthTimestamp;
    private String oauthNonce;
    private String oauthSignatureMethod;
    private String oauthSignature;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
