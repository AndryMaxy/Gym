package builder;

import entity.UserRole;
import entity.Visitor;

//TODO  МБ СТОИТ УДАЛТЬ
public class VisitorBuilder {

    private String login;
    private String hash;
    private String salt;
    private String name;
    private String surname;
    private UserRole userRole;

    private int discount;
    private boolean vip;
    private boolean regular;
    private int balance = 1000;

    public VisitorBuilder buildLogin(String login) {
        this.login = login;
        return this;
    }

    public VisitorBuilder buildHash(String hash) {
        this.hash = hash;
        return this;
    }

    public VisitorBuilder buildSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public VisitorBuilder buildName(String name) {
        this.name = name;
        return this;
    }

    public VisitorBuilder buildSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public VisitorBuilder buildUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public VisitorBuilder buildDiscount(int discount) {
        this.discount = discount;
        return this;
    }

    public VisitorBuilder buildVip(boolean vip) {
        this.vip = vip;
        return this;
    }

    public VisitorBuilder buildRegular(boolean regular) {
        this.regular = regular;
        return this;
    }

    public VisitorBuilder buildBalance(int balance) {
        this.balance = balance;
        return this;
    }

    public Visitor build(){
        Visitor visitor = new Visitor();
        visitor.setLogin(login);
        visitor.setHash(hash);
        visitor.setSalt(salt);
        visitor.setName(name);
        visitor.setSurname(surname);
        visitor.setUserRole(userRole);
        visitor.setDiscount(discount);
        visitor.setVip(vip);
        visitor.setRegular(regular);
        visitor.setBalance(balance);
        return visitor;
    }
}
