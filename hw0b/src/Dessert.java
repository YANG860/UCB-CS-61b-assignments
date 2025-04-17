
public class Dessert {

    static int numDesserts = 0;
    int price;
    int flavor;

    public Dessert(int flavor, int price) {
        this.flavor = flavor;
        this.price = price;
        Dessert.numDesserts++;
    }

    public void printDessert() {
        System.out.printf("%d %d %d", this.flavor, this.price, Dessert.numDesserts);
    }

    public static void main(String[] args) {
        System.out.println("I love dessert!");
    }

}
