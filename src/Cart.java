import java.text.NumberFormat;
import java.util.Locale;

public class Cart {
    private Product[] cart = new Product[4];
    private int[] qty = new int[4];
    private int count = 0;
    private Product freeDrink = null;

    private String currencyFormatted(int price) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatter.format(price).replace(",00", "");
    }

    //tambahkan pesanan
    public void addToCart(Product product, int qty) {
        addToCart(product, qty, 0);
    }

    private void addToCart(Product product, int qty, int index) {
        if (index >= count) {
            cart[count] = product;
            this.qty[count] = qty;
            count++;
            return;
        }

        if (cart[index] != null && cart[index].getProductName().equals(product.getProductName())) {
            this.qty[index] += qty;
            return;
        }
        addToCart(product, qty, index + 1);
    }

    public void setFreeDrink(Product freeDrink) {
        this.freeDrink = freeDrink;
    }

    public int getCount() {
        return count;
    }

    //hitung subtotal seluruh pesanan
    public int getSubtotal(int index) {
        if (index >= count) {
            return 0;
        }
        return cart[index].getPrice() * qty[index] + getSubtotal(index + 1);
    }

    public void showCart(int index) {
        if (index >= count) {
            return;
        }
        int total = cart[index].getPrice() * qty[index];
        System.out.println(cart[index].getProductName() + " x" + qty[index] + " = " + currencyFormatted(total));
        showCart(index + 1);
    }

    //tampilkan struk pesanan
    public void showReceipt() {
        System.out.println("\n========= STRUK PEMBAYARAN =========");
        showCart(0);

        int subtotal = getSubtotal(0);
        System.out.println("-".repeat(36));
        System.out.println("Subtotal : " + currencyFormatted(subtotal));

        double discount = 0;
        double ppn = 0.10 * subtotal;
        int service = 20000;
        int total = (int) (subtotal - discount + service + ppn);

        System.out.println("Biaya Pelayanan : " + currencyFormatted(service));

        System.out.println("PPN 10% : " + currencyFormatted((int) ppn));

        if (subtotal > 100000) {
            discount = 0.10 * subtotal;
            System.out.println("Diskon 10%: -" + currencyFormatted((int) discount));
        }

        if (freeDrink != null) {
            System.out.println("Bonus Minuman: " + freeDrink.getProductName());
        }

        System.out.println("-".repeat(36));

        System.out.println("GRAND TOTAL: " + currencyFormatted(total));

        System.out.println("=".repeat(36));
    }

    //cek apakah gratis minuman
    public boolean isEligibleFreeDrink() {
        return getSubtotal(0) > 50000;
    }

    //hapus semua item dari keranjang pesanan
    public void clearCart(int index) {
        if (index >= cart.length) {
            return;
        }
        cart[index] = null;
        qty[index] = 0;
        clearCart(index + 1);
        if (index == 0) {
            count = 0;
            freeDrink = null;
        }
    }
}
