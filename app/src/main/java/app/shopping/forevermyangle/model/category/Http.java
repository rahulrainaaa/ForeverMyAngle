
package app.shopping.forevermyangle.model.category;

import java.util.HashMap;
import java.util.Map;

public class Http {

    private Request request;
    private Response response;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
