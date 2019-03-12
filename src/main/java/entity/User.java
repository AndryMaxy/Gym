package entity;

import java.util.Objects;

/**
 * This class represents users of gym.
 *
 * @author Andrey Akulich
 */
public class User {

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
    private UserRole role;

    /**
     * User discount.
     */
    private int discount;

    /**
     * User balance.
     */
    private int balance = 800;

    /**
     * Constructs this class
     */
    public User() {
    }

    /**
     * @return a user identifier in database
     */
    public int getId() {
        return id;
    }

    /**
     * @param id a user identifier in database
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return a user login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets a user login.
     *
     * @param login a user login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return a hash of password
     */
    public String getHash() {
        return hash;
    }

    /**
     * Sets a hash of password.
     *
     * @param hash a hash of password
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @return a salt for hash
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Sets a salt for hash.
     *
     * @param salt a salt for hash.
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return a user name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a user name.
     *
     * @param name a user name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return a user surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets a user surname.
     *
     * @param surname a user surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return a user role
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Sets a user role.
     *
     * @param role a user role
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * @return a user discount
     */
    public int getDiscount() {
        return discount;
    }

    /**
     * Sets a discount for user.
     *
     * @param discount a user's discount
     */
    public void setDiscount(int discount) {
        this.discount = discount;
    }

    /**
     * @return a user's balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Sets a balance for user.
     *
     * @param balance a user's balance
     */
    public void setBalance(int balance) {
        this.balance = balance;
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
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return id == user.id &&
                discount == user.discount &&
                balance == user.balance &&
                Objects.equals(login, user.login) &&
                Objects.equals(hash, user.hash) &&
                Objects.equals(salt, user.salt) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                role == user.role;
    }

    /**
     * @return a hash code value for the object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, login, hash, salt, name, surname, role, discount, balance);
    }

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", hash='" + hash + '\'' +
                ", salt='" + salt + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role=" + role +
                ", discount=" + discount +
                ", balance=" + balance +
                '}';
    }
}
