package builder;

import entity.User;
import entity.UserRole;

public class UserBuilder {

    private String login;
    private String hash;
    private String salt;
    private String name;
    private String surname;
    private UserRole userRole;

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

    public User build(){
        User user = new User();
        user.setLogin(login);
        user.setHash(hash);
        user.setSalt(salt);
        user.setName(name);
        user.setSurname(surname);
        user.setUserRole(userRole);
        return user;
    }
}
