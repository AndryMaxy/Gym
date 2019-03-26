package by.epam.akulich.gym.dto;

import by.epam.akulich.gym.entity.Constants;

import java.util.Objects;

/**
 * The class contains information about next url and its status.
 *
 * @author Andrey Akulich
 * @see Constants.ResponseStatus
 */
public class Response {

    /**
     * A next url.
     */
    private String url;

    /**
     * A response status of this application
     */
    private int status;

    /**
     * Constructs this class
     */
    public Response() {
    }

    /**
     * Constructs this class
     *
     * @param status current response status, see a constants {@link Constants.ResponseStatus}
     */
    public Response(int status) {
        this.status = status;
    }

    /**
     * Constructs this class
     *
     * @param url next url
     * @param status current response status, see a constants {@link Constants.ResponseStatus}
     */
    public Response(String url, int status) {
        this.url = url;
        this.status = status;
    }

    /**
     * @return a url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url next url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return a response status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets a status of this response. To set right see a constants {@link Constants.ResponseStatus}.
     *
     * @param status status of this response
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Returns {@code true} if the arguments are equal to each other
     * and {@code false} otherwise.
     *
     * @param o an object to be compared with this object
     * @return {@code true} if the arguments are equal to each other
     * and {@code false} otherwise
     */
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

    /**
     * @return a hash code value for the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(url, status);
    }

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Response{" +
                "url='" + url + '\'' +
                ", status=" + status +
                '}';
    }
}
