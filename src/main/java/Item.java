public class Item {
    private String name;
    private int price;

    //Write getter to get Price of the items
    public int getPrice() {
        return this.price;
    }

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    @Override
    public String toString(){
        return  name + ":"
                + price
                + "\n"
                ;
    }
}
