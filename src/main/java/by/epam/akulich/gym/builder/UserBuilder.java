package by.epam.akulich.gym.builder;

import by.epam.akulich.gym.entity.User;
import by.epam.akulich.gym.entity.UserRole;

/**
 * The class designed by Builder pattern and it uses for build {@link User}.
 *
 * @author Andrey Akulich
 */
public class UserBuilder {

    /**
     * User identifier in database.
     */
    private int id;

    /**
     * User login.
     */
    private String login;

    /**
     * Hash of password.
     */
    private String hash;

    /**
     * Salt for hash.
     */
    private String salt;

    /**
     * User first name.
     */
    private String name;

    /**
     * User last name.
     */
    private String surname;

    /**
     * User role.
     */
    private UserRole userRole;

    /**
     * User discount.
     */
    private int discount;

    /**
     * User balance.
     */
    private int balance = 800;

    /**
     * Sets a user's identifier in database.
     *
     * @param id a user's identifier in database
     * @return this object
     */
    public UserBuilder buildId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Sets a login.
     *
     * @param login a user's login
     * @return this object
     */
    public UserBuilder buildLogin(String login) {
        this.login = login;
        return this;
    }

    /**
     * Sets a hash of password.
     *
     * @param hash hash of password
     * @return this object
     */
    public UserBuilder buildHash(String hash) {
        this.hash = hash;
        return this;
    }

    /**
     * Sets a salt.
     *
     * @param salt a salt for hashed password
     * @return this object
     */
    public UserBuilder buildSalt(String salt) {
        this.salt = salt;
        return this;
    }

    /**
     * Sets a user name
     *
     * @param name a user name
     * @return this object
     */
    public UserBuilder buildName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets a user surname
     *
     * @param surname a user surname
     * @return this object
     */
    public UserBuilder buildSurname(String surname) {
        this.surname = surname;
        return this;
    }

    /**
     * Sets a user role.
     *
     * @param userRole a user role
     * @return this object
     */
    public UserBuilder buildUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    /**
     * Sets a discount for user.
     *
     * @param discount a user's discount
     * @return this object
     */
    public UserBuilder buildDiscount(int discount) {
        this.discount = discount;
        return this;
    }

    /**
     * Sets a balance for user.
     *
     * @param balance a user's balance
     * @return this object
     */
    public UserBuilder buildBalance(int balance) {
        this.balance = balance;
        return this;
    }

    /**
     * Sets all builder fields in {@link User} fields then returns {@link User} instance.
     *
     * @return {@link User} instance
     */
    public User build(){
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setName(name);
        user.setHash(hash);
        user.setSalt(salt);
        user.setSurname(surname);
        user.setRole(userRole);
        user.setDiscount(discount);
        user.setBalance(balance);
        return user;
    }
}
