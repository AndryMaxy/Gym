package entity;

import java.util.Objects;

/**
 * This class represents order of gym.
 *
 * @author Andrey Akuclich
 */
public class Booking {

    private int id;
    private User user;
    private Membership membership;
    private int visitCountLeft;
    private boolean needAppointment;
    private String feedback;

    /**
     * Returns object identifier.
     *
     * @return object identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Sets object identifier.
     *
     * @param id object identifier.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns {@link User} instance.
     *
     * @return {@link User} instance
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets {@link User} instance.
     *
     * @param user {@link User} instance
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns {@link Membership} instance.
     *
     * @return {@link Membership} instance
     */
    public Membership getMembership() {
        return membership;
    }

    /**
     * Sets {@link Membership} instance.
     *
     * @param membership {@link Membership} instance
     */
    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    /**
     * Returns a number of remaining visits by the gym user of this order.
     *
     * @return a number of remaining visits
     */
    public int getVisitCountLeft() {
        return visitCountLeft;
    }

    /**
     * Sets a number of remaining visits by the gym user of this order.
     *
     * @param visitCountLeft number of remaining visits
     */
    public void setVisitCountLeft(int visitCountLeft) {
        this.visitCountLeft = visitCountLeft;
    }

    /**
     * Returns {@code true} if visitor need appointment otherwise {@code false}.
     *
     * @return {@code true} if visitor need appointment otherwise {@code false}
     */
    public boolean isNeedAppointment() {
        return needAppointment;
    }

    /**
     * Sets a param in needAppointment field.
     *
     * @param needAppointment says whether the visitor needs an appointment
     */
    public void setNeedAppointment(boolean needAppointment) {
        this.needAppointment = needAppointment;
    }

    /**
     * Returns visitor feedback of this order.
     *
     * @return visitor feedback of this order.
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets visitor feedback of this order.
     *
     * @param feedback visitor feedback of this order
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
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
        if (!(o instanceof Booking)) {
            return false;
        }
        Booking booking = (Booking) o;
        return id == booking.id &&
                visitCountLeft == booking.visitCountLeft &&
                needAppointment == booking.needAppointment &&
                Objects.equals(user, booking.user) &&
                membership == booking.membership &&
                Objects.equals(feedback, booking.feedback);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, user, membership, visitCountLeft, needAppointment, feedback);
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", user=" + user +
                ", membership=" + membership +
                ", visitCountLeft=" + visitCountLeft +
                ", needAppointment=" + needAppointment +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
