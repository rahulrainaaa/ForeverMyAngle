
package app.shopping.forevermyangle.model.category;

import java.util.HashMap;
import java.util.Map;

public class Response {

    private String body;
    private Integer code;
    private Headers headers;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
