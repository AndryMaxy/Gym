package entity;

import java.util.Objects;

/**
 * This class represents order of gym.
 * @author Andrey Akuclich
 */
public class Booking {

    private int id;
    private User user;
    private Membership membership;
    private int visitCountLeft;
    private String feedback;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Booking)) {
            return false;
        }
        Booking booking = (Booking) o;
        return id == booking.id &&
                visitCountLeft == booking.visitCountLeft &&
                Objects.equals(user, booking.user) &&
                membership == booking.membership &&
                Objects.equals(feedback, booking.feedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, membership, visitCountLeft, feedback);
    }

    /**
     * Return string of this object with all fields.
     * @return string of this object with all fields.
     */
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", user=" + user +
                ", membership=" + membership +
                ", visitCountLeft=" + visitCountLeft +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
