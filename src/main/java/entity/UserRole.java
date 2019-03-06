package entity;

/**
 * Represents all user roles of gym.
 *
 * @author Andrey Akulich
 */
public enum UserRole {

    ADMIN(1),
    TRAINER(2),
    VISITOR(3),
    GUEST(4);

    private int id;

    /**
     * Constructs instance of this class.
     *
     * @param id object identifier
     */
    UserRole(int id) {
        this.id = id;
    }

    /**
     * Returns object identifier.
     *
     * @return object identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                '}';
    }
}
