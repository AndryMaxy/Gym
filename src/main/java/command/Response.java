package command;

public class Response {

    private String url;
    private boolean isGoodMessage;
    private boolean isRedirect;

    public Response(String url, boolean isRedirect) {
        this.url = url;
        this.isRedirect = isRedirect;
    }

    public Response(String url, boolean isRedirect, boolean isGoodMessage) {
        this.url = url;
        this.isRedirect = isRedirect;
        this.isGoodMessage = isGoodMessage;
    }

    public String getUrl() {
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

    public boolean isGoodMessage() {
        return isGoodMessage;
    }

    public void setGoodMessage(boolean goodMessage) {
        isGoodMessage = goodMessage;
    }
}
