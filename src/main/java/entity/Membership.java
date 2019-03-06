package entity;

public enum Membership {

    ULTRA(1,30, 1000),
    SUPER(2,20, 800),
    STANDARD(3,15, 600),
    EASY(4,10, 500),
    ONE(5,1, 100);

    private int id;
    private int count;
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
    //todo ARTICLE A!!
    /**
     * Returns an object identifier.
     *
     * @return an object identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a number of visits.
     *
     * @return a number of visits
     */
    public int getCount() {
        return count;
    }

    /**
     * Returns a cost of this membership.
     *
     * @return a cost of this membership
     */
    public int getPrice() {
        return price;
    }

    /**
     * Returns the name of this enum constant, exactly as declared in its
     * enum declaration.
     *
     * @return the name of this enum constant
     */
    @Override
    public String toString() {
        return name();
    }
}
