package by.epam.akulich.gym.entity;

import java.util.Objects;

/**
 * The class represents order of gym.
 *
 * @author Andrey Akuclich
 */
public class Booking {

    /**
     * A booking identifier in database
     */
    private int id;

    /**
     * User instance
     */
    private User user;

    /**
     * Membership instance
     */
    private Membership membership;

    /**
     * A number of remaining visits
     */
    private int visitCountLeft;

    /**
     * {@code true} if visitor need appointment
     */
    private boolean needAppointment;

    /**
     * Visitor feedback of this order
     */
    private String feedback;

    /**
     * Constructs this class
     */
    public Booking() {
    }

    /**
     * Returns a booking identifier in database.
     *
     * @return a booking identifier in database
     */
    public int getId() {
        return id;
    }

    /**
     * Sets a booking identifier in database.
     *
     * @param id a booking identifier in database
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
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
     * @return the number of remaining visits
     */
    public int getVisitCountLeft() {
        return visitCountLeft;
    }

    /**
     * Sets a number of remaining visits by the gym user of this order.
     *
     * @param visitCountLeft the number of remaining visits
     */
    public void setVisitCountLeft(int visitCountLeft) {
        this.visitCountLeft = visitCountLeft;
    }

    /**
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
     * @return a hash code value for the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, user, membership, visitCountLeft, needAppointment, feedback);
    }

    /**
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
