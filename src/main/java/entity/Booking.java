package entity;

public class Booking {

    private int id;
    private int userId;
    private int visitsLeft;
    private String feedback;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVisitsLeft() {
        return visitsLeft;
    }

    public void setVisitsLeft(int visitsLeft) {
        this.visitsLeft = visitsLeft;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
