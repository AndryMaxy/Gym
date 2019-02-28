package entity;
//TODO PACKAGE?
public class Response {

    private static final String BAD = "?bad=t";
    private String url;
    private boolean isBadMessage;
    private boolean isRedirect;

    public Response(String url, boolean isRedirect) {
        this.url = url;
        this.isRedirect = isRedirect;
    }

    public Response(String url, boolean isRedirect, boolean isBadMessage) {
        this.url = url;
        this.isRedirect = isRedirect;
        this.isBadMessage = isBadMessage;
    }

    public String getUrl() {
        if (isBadMessage) {
            url = url + BAD;
        }
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }

    public boolean isBadMessage() {
        return isBadMessage;
    }

    public void setBadMessage(boolean badMessage) {
        isBadMessage = badMessage;
    }
}
