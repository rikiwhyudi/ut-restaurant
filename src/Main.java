import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Menu menu = new Menu();
        Cart cart = new Cart();
        startOrdering(input, menu, cart);
        System.out.println("\nTerima kasih telah berbelanja..");
        input.close();
    }

    //mulai proses pesanan
    public static void startOrdering(Scanner input, Menu menu, Cart cart){
        System.out.println("\n========= MENU RESTAURANT =========");
        menu.showAllMenu(0);
        System.out.println("=".repeat(36));

        addItemToCart(input, menu, cart);

        if (cart.isEligibleFreeDrink()) {
            System.out.println("\nSelamat! Anda dapat minuman gratis ");
            System.out.println("========== MINUMAN GRATIS ==========");
            menu.showDrinks(0);
            System.out.println("=".repeat(36));
            System.out.print("Masukan nomor produk minuman: ");
            int choice = input.nextInt();
            cart.setFreeDrink(menu.getProducts()[choice - 1]);
        }

        cart.showReceipt();

        System.out.print("Buat pesanan baru? (y/n): ");
        String answer = input.next();
        if (answer.equalsIgnoreCase("y")) {
            cart.clearCart(0);
            startOrdering(input, menu, cart);
        }

    }

    //tambahkan item pesanan ke keranjang
    public static void addItemToCart(Scanner input, Menu menu, Cart cart){

        System.out.print("Masukkan nomor produk: ");
        int choice = input.nextInt();

        if (choice < 1 || choice > menu.getProducts().length) {
            System.out.println("Produk tidak ditemukan");
            addItemToCart(input, menu, cart);
            return;
        }

        Product selectedProduct = menu.getProducts()[choice - 1];
        System.out.print("Masukkan jumlah: ");
        int qty = input.nextInt();

        cart.addToCart(selectedProduct, qty);

        if (cart.getCount() >= 4) {
            System.out.println("\nKeranjang pesanan penuh (max 4 item).");
        } else {
            System.out.print("Tambahkan item lagi? (y/n): ");
            String answer = input.next();
            if (answer.equalsIgnoreCase("y")) {
                addItemToCart(input, menu, cart);
            }
        }
    }
}
