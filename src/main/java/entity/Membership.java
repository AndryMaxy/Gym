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

    Membership(int id, int count, int price) {
        this.id = id;
        this.count = count;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name();
    }
}
