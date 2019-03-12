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

    /**
     * User role identifier in database.
     */
    private int id;

    /**
     * Constructs instance of this class.
     *
     * @param id a identifier in database
     */
    UserRole(int id) {
        this.id = id;
    }

    /**
     * @return a identifier in database
     */
    public int getId() {
        return id;
    }

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                '}';
    }
}
