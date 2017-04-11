
package app.shopping.forevermyangle.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Headers {

    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Server")
    @Expose
    private String server;
    @SerializedName("X-Powered-By")
    @Expose
    private String xPoweredBy;
    @SerializedName("Set-Cookie")
    @Expose
    private String setCookie;
    @SerializedName("Expires")
    @Expose
    private String expires;
    @SerializedName("Cache-Control")
    @Expose
    private String cacheControl;
    @SerializedName("Pragma")
    @Expose
    private String pragma;
    @SerializedName("Vary")
    @Expose
    private String vary;
    @SerializedName("Content-Length")
    @Expose
    private String contentLength;
    @SerializedName("Content-Type")
    @Expose
    private String contentType;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getXPoweredBy() {
        return xPoweredBy;
    }

    public void setXPoweredBy(String xPoweredBy) {
        this.xPoweredBy = xPoweredBy;
    }

    public String getSetCookie() {
        return setCookie;
    }

    public void setSetCookie(String setCookie) {
        this.setCookie = setCookie;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getCacheControl() {
        return cacheControl;
    }

    public void setCacheControl(String cacheControl) {
        this.cacheControl = cacheControl;
    }

    public String getPragma() {
        return pragma;
    }

    public void setPragma(String pragma) {
        this.pragma = pragma;
    }

    public String getVary() {
        return vary;
    }

    public void setVary(String vary) {
        this.vary = vary;
    }

    public String getContentLength() {
        return contentLength;
    }

    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}
