package entity;

public class User implements Entity{
    //TODO EQUALS HASHCODE TO STRING
    private int id;
    private String login;
    private String hash;
    private String salt;
    private String name;
    private String surname;
    private UserRole userRole;
    private int discount;
    private int balance = 1000;
    private boolean isAppointmentReady;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isAppointmentReady() {
        return isAppointmentReady;
    }

    public void setAppointmentReady(boolean appointmentReady) {
        isAppointmentReady = appointmentReady;
    }
}
