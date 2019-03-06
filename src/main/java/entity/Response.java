package entity;

import java.util.Objects;

public class Response {

    private String url;
    private int status;

    public Response(int status) {
        this.status = status;
    }

    public Response(String url, int status) {
        this.url = url;
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Response)) {
            return false;
        }
        Response response = (Response) o;
        return status == response.status &&
                Objects.equals(url, response.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, status);
    }

    @Override
    public String toString() {
        return "Response{" +
                "url='" + url + '\'' +
                ", status=" + status +
                '}';
    }
}
