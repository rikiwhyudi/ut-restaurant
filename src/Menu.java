public class Menu {
    private Product[] products = {
        new Product("Burger", 10000, "Makanan"),
        new Product("Sandwich", 15000, "Makanan"),
        new Product("Pizza", 20000, "Makanan"),
        new Product("Fried Chicken", 15000, "Makanan"),
        new Product("Ice Coffee", 5000, "Minuman"),
        new Product("Ice Tea", 5000, "Minuman"),
        new Product("Coca Cola", 5000, "Minuman"),
        new Product("Lemon Tea", 10000, "Minuman")
    };

    public Product[] getProducts() {
        return products;
    }

    // tampilkan semua produk
    public void showAllMenu(int index) {
        if (index >= products.length) {
            return;
        }
        System.out.println((index + 1) + ". " +products[index].toString());
        showAllMenu(index + 1);
    }

    // tampilkan semua minuman untuk promo bogo
    public void showDrinks(int index) {
        if (index >= products.length) {
            return;
        }
        if (products[index].getCategory().equals("Minuman")) {
            System.out.println((index + 1) + ". " +products[index].toString());
        }
        showDrinks(index + 1);
    }

}
