package builder;

import entity.User;
import entity.UserRole;

/**
 * This class designed by Builder pattern and it uses for build {@link User}
 *
 * @author Andrey Akulich
 */
public class UserBuilder {

    private int id;
    private String login;
    private String hash;
    private String salt;
    private String name;
    private String surname;
    private UserRole userRole;
    private int discount;
    private int balance = 800;

    /**
     * Sets a param in the id field
     *
     * @param id will be set in the id field
     * @return this object
     */
    public UserBuilder buildId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Sets a param in the login field
     *
     * @param login will be set in the login field
     * @return this object
     */
    public UserBuilder buildLogin(String login) {
        this.login = login;
        return this;
    }

    /**
     * Sets a param in the hash field
     *
     * @param hash will be set in the hash field
     * @return this object
     */
    public UserBuilder buildHash(String hash) {
        this.hash = hash;
        return this;
    }

    /**
     * Sets a param in the salt field
     *
     * @param salt will be set in the salt field
     * @return this object
     */
    public UserBuilder buildSalt(String salt) {
        this.salt = salt;
        return this;
    }

    /**
     * Sets a param in the name field
     *
     * @param name will be set in the name field
     * @return this object
     */
    public UserBuilder buildName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets a param in the surname field
     *
     * @param surname will be set in the surname field
     * @return this object
     */
    public UserBuilder buildSurname(String surname) {
        this.surname = surname;
        return this;
    }

    /**
     * Sets a param in the userRole field
     *
     * @param userRole will be set in the userRole field
     * @return this object
     */
    public UserBuilder buildUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    /**
     * Sets a param in the discount field
     *
     * @param discount will be set in the discount field
     * @return this object
     */
    public UserBuilder buildDiscount(int discount) {
        this.discount = discount;
        return this;
    }

    /**
     * Sets a param in the balance field
     *
     * @param balance will be set in the balance field
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
