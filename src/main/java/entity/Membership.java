package entity;

/**
 * The enum class represents memberships of gym.
 *
 * @author Andrey Akulich
 */
public enum Membership {

    ULTRA(1,30, 1000),
    SUPER(2,20, 800),
    STANDARD(3,15, 600),
    EASY(4,10, 500),
    ONE(5,1, 100);

    /**
     * A membership identifier in database.
     */
    private int id;

    /**
     * A number of visits according membership
     */
    private int count;

    /**
     * A cost according membership
     */
    private int price;

    /**
     * Constructs instance of this class.
     *
     * @param id object identifier
     * @param count a number of visits
     * @param price a cost of this membership
     */
    Membership(int id, int count, int price) {
        this.id = id;
        this.count = count;
        this.price = price;
    }

    /**
     * @return a identifier in database
     */
    public int getId() {
        return id;
    }

    /**
     * @return a number of visits
     */
    public int getCount() {
        return count;
    }

    /**
     * @return a cost of this membership
     */
    public int getPrice() {
        return price;
    }

    /**
     * Returns a name of this enum constant, exactly as declared in its
     * enum declaration.
     *
     * @return a name of this enum constant
     */
    @Override
    public String toString() {
        return name();
    }
}
