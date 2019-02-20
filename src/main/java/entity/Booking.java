package entity;

public class Booking {

    private int id;
    private int userId;
    private Membership membership;
    private int visitCountLeft;
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

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public int getVisitCountLeft() {
        return visitCountLeft;
    }

    public void setVisitCountLeft(int visitCountLeft) {
        this.visitCountLeft = visitCountLeft;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
