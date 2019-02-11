package entity;

public enum Membership {
    ULTRA(30, 1000),
    SUPER(20, 800),
    STANDARD(15, 600),
    EASY(10, 500),
    ONE(1, 100);

    private int count;
    private int price;

    Membership(int count, int price) {
        this.count = count;
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public int getPrice() {
        return price;
    }

    //TODO TO STRING!
    @Override
    public String toString() {
        return super.toString();
    }
}
