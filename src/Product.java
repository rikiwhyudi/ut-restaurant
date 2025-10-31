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

    // format harga ke dalam rupiah
    private String currencyFormatted(int price) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatter.format(price).replace(",00", "");
    }

    // tampilkan produk dalam bentuk string
    @Override
    public String toString() {
        return "[" + category + "] "+ productName + " - " + currencyFormatted(price);
    }

}
