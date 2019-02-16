package entity;

public enum UserRole {
    ADMIN(1),
    TRAINER(2),
    VISITOR(3),
    GUEST(4);

    private int id;

    UserRole(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
