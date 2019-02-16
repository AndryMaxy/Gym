package builder;

import entity.User;
import entity.UserRole;

public class UserBuilder {

    private int id;
    private String login;
    private String hash;
    private String salt;
    private String name;
    private String surname;
    private UserRole userRole;
    private int discount;
    private int balance = 1000;

    public UserBuilder buildId(int id) {
        this.id = id;
        return this;
    }

    public UserBuilder buildLogin(String login) {
        this.login = login;
        return this;
    }

    public UserBuilder buildHash(String hash) {
        this.hash = hash;
        return this;
    }

    public UserBuilder buildSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public UserBuilder buildName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder buildSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public UserBuilder buildUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public UserBuilder buildDiscount(int discount) {
        this.discount = discount;
        return this;
    }

    public UserBuilder buildBalance(int balance) {
        this.balance = balance;
        return this;
    }

    public User build(){
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setName(name);
        user.setHash(hash);
        user.setSalt(salt);
        user.setSurname(surname);
        user.setUserRole(userRole);
        user.setDiscount(discount);
        user.setBalance(balance);
        return user;
    }
}
