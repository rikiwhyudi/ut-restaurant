import java.text.NumberFormat;
import java.util.Locale;

public class Product {
    private String productName;
    private int price;
    private String category;

    public Product(String productName, int price, String category) {
        this.productName = productName;
        this.price = price;
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }
    public int getPrice() {
        return price;
    }
    public String getCategory() {
        return category;
    }

    public void setProductName(String name) {
        this.productName = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static String currencyFormatted(int value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatter.format(value).replace(",00", "");
    }

    @Override
    public String toString() {
        return "[" + category + "] " + productName + " - " + currencyFormatted(price);
    }
}
