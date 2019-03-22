package by.epam.akulich.gym.builder;

import by.epam.akulich.gym.entity.Booking;
import by.epam.akulich.gym.entity.Membership;
import by.epam.akulich.gym.entity.User;

/**
 * The class designed by Builder pattern and it uses for build {@link Booking}.
 *
 * @author Andrey Akulich
 */
public class BookingBuilder {

    /**
     * A booking identifier in database.
     */
    private int id;

    /**
     * User instance.
     */
    private User user;

    /**
     * Membership instance.
     */
    private Membership membership;

    /**
     * A number of remaining visits.
     */
    private int visitCountLeft;

    /**
     * {@code true} if visitor need appointment.
     */
    private boolean needAppointment;

    /**
     * Visitor feedback of this order.
     */
    private String feedback;

    /**
     * Sets a booking's identifier in database.
     *
     * @param id a user's identifier in database
     * @return this object
     */
    public BookingBuilder buildId(int id){
        this.id = id;
        return this;
    }

    /**
     * Sets a {@link User} instance.
     *
     * @param user {@link User} instance
     * @return this object
     */
    public BookingBuilder buildUser(User user){
        this.user = user;
        return this;
    }

    /**
     * Sets a {@link Membership} instance.
     *
     * @param membership {@link Membership} instance
     * @return this object
     */
    public BookingBuilder buildMembership(Membership membership){
        this.membership = membership;
        return this;
    }

    /**
     * Sets a number of remaining visits by the gym user of this order.
     *
     * @param visitCountLeft the number of remaining visits
     * @return this object
     */
    public BookingBuilder buildVisitCountLeft(int visitCountLeft){
        this.visitCountLeft = visitCountLeft;
        return this;
    }

    /**
     * Sets a param which says whether the visitor needs an appointment.
     *
     * @param needAppointment says whether the visitor needs an appointment
     * @return this object
     */
    public BookingBuilder buildNeedAppointment(boolean needAppointment){
        this.needAppointment = needAppointment;
        return this;
    }

    /**
     * Sets visitor feedback of this order.
     *
     * @param feedback visitor feedback of this order
     * @return this object
     */
    public BookingBuilder buildFeedback(String feedback){
        this.feedback = feedback;
        return this;
    }

    /**
     * Sets all builder fields in {@link Booking} fields then returns {@link Booking} instance.
     *
     * @return {@link Booking} instance
     */
    public Booking build(){
        Booking booking = new Booking();
        booking.setId(id);
        booking.setUser(user);
        booking.setMembership(membership);
        booking.setVisitCountLeft(visitCountLeft);
        booking.setNeedAppointment(needAppointment);
        booking.setFeedback(feedback);
        return booking;
    }
}
