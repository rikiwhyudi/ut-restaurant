import java.util.Scanner;

public class Main {
    private static final String SEPARATOR = "=".repeat(40);
    private static final String LINE = "-".repeat(40);

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Menu menu = new Menu();
        Cart cart = new Cart();
        runMainMenu(menu, cart, input);
        System.out.println("\nTerima kasih, telah mencoba!");
        input.close();
    }

    private static void runMainMenu(Menu menu, Cart cart, Scanner input) {
        boolean running = true;
        while (running) {
            displayHeader("MENU UTAMA");
            System.out.println("1. Mulai Pemesanan");
            System.out.println("2. Kelola Menu");
            System.out.println("3. Keluar");
            System.out.println(SEPARATOR);
            System.out.print("Pilih: ");

            int choice = readInt(input);

            switch (choice) {
                case 1 -> startOrdering(menu, cart, input);
                case 2 -> manageMenu(menu, input);
                case 3 -> running = false;
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void startOrdering(Menu menu, Cart cart, Scanner input) {
        if (isMenuEmpty(menu)) {
            return;
        }

        displayMenu(menu);
        addItemToCart(menu, cart, input);

        if (cart.isEligibleFreeDrink()) {
            displayHeader("DAFTAR MINUMAN GRATIS");
            menu.showDrinks();
            System.out.println(SEPARATOR);
            System.out.print("Pilih nomor produk (0 untuk tidak ambil): ");

            int drinkChoice = readInt(input);

            if (drinkChoice == 0) {
                System.out.println("Tidak mengambil minuman gratis.");
            } else {
                Product selectedDrink = menu.getProduct(drinkChoice - 1);
                if (selectedDrink != null && selectedDrink.getCategory().equalsIgnoreCase("Minuman")) {
                    cart.setFreeDrink(selectedDrink);
                    System.out.println("Bonus " + selectedDrink.getProductName() + " berhasil ditambahkan.");
                } else {
                    System.out.println("Pilihan bukan kategori minuman! Bonus diabaikan.");
                }
            }
        }

        showReceipt(cart);

        System.out.print("\nBuat pesanan baru? (y/n): ");
        String again = input.next();
        if (again.equalsIgnoreCase("y")) {
            cart.clearCart();
            startOrdering(menu, cart, input);
        } else {
            cart.clearCart();
        }
    }

    private static void addItemToCart(Menu menu, Cart cart, Scanner input) {
        boolean adding = true;
        while (adding) {
            System.out.print("Masukkan nomor produk: ");
            int choice = readInt(input);
            Product selected = menu.getProduct(choice - 1);

            if (selected == null) {
                System.out.println("Produk tidak ditemukan! Silakan coba lagi.");
                continue;
            }

            System.out.print("Masukkan jumlah: ");
            int qty = readInt(input);
            if (qty <= 0) {
                System.out.println("Jumlah harus lebih dari 0.");
                continue;
            }

            cart.addToCart(selected, qty);

            System.out.print("Tambahkan item lagi? (y/n): ");
            String more = input.next();
            if (more.equalsIgnoreCase("n")) {
                adding = false;
            }
        }
    }

    private static void showReceipt(Cart cart) {
        CartItem[] items = cart.getItems();
        if (items.length == 0) {
            System.out.println("Keranjang kosong.");
            return;
        }

        displayHeader("STRUK PEMBELIAN");
        for (CartItem item : items) {
            System.out.println(item.getProduct().getProductName() + " x" + item.getQty() +
                    " = " + Product.currencyFormatted(item.getTotal()));
        }

        if (cart.getFreeDrink() != null) {
            System.out.println("Bonus: " + cart.getFreeDrink().getProductName());
        }

        int subtotal = cart.getSubtotal(null);
        int discount = 0;
        if (subtotal > 100000) {
            discount = subtotal * 10 / 100;
        }
        int subtotalAfterDiscount = subtotal - discount;
        int tax = subtotalAfterDiscount * 10 / 100;
        int serviceFee = 20000;
        int total = subtotalAfterDiscount + tax + serviceFee;

        System.out.println(LINE);
        System.out.println("Subtotal      : " + Product.currencyFormatted(subtotal));
        if (discount > 0) {
            System.out.println("Diskon (10%)  : -" + Product.currencyFormatted(discount));
        }
        System.out.println(LINE);
        System.out.println("PPN (10%)     : " + Product.currencyFormatted(tax));
        System.out.println("Pelayanan     : " + Product.currencyFormatted(serviceFee));
        System.out.println("Total         : " + Product.currencyFormatted(total));
        System.out.println(SEPARATOR);
    }

    private static void manageMenu(Menu menu, Scanner input) {
        boolean managing = true;
        while (managing) {
            displayHeader("KELOLA MENU");
            System.out.println("1. Lihat Semua Menu");
            System.out.println("2. Tambah Menu");
            System.out.println("3. Update Menu");
            System.out.println("4. Hapus Menu");
            System.out.println("5. Kembali");
            System.out.println(SEPARATOR);
            System.out.print("Pilih: ");

            int choice = readInt(input);

            switch (choice) {
                case 1 -> displayMenu(menu);
                case 2 -> addMenuItem(menu, input);
                case 3 -> updateMenuItem(menu, input);
                case 4 -> deleteMenuItem(menu, input);
                case 5 -> managing = false;
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void displayMenu(Menu menu) {
        if (isMenuEmpty(menu)) {
            return;
        }
        displayHeader("DAFTAR MENU");
        menu.showAllMenu();
        System.out.println(SEPARATOR);
    }

    private static void addMenuItem(Menu menu, Scanner input) {
        displayHeader("TAMBAH MENU");

        System.out.print("Nama produk: ");
        input.nextLine();
        String name = input.nextLine().trim();

        System.out.print("Harga: ");
        int price = readInt(input);

        System.out.print("Kategori (Makanan/Minuman): ");
        input.nextLine();
        String category = input.nextLine().trim();

        menu.addProduct(new Product(name, price, category));
        System.out.println("Produk berhasil ditambahkan!");
    }

    private static void updateMenuItem(Menu menu, Scanner input) {
        if (isMenuEmpty(menu)) {
            return;
        }

        displayHeader("UPDATE MENU");
        menu.showAllMenu();
        System.out.println(LINE);

        System.out.print("Pilih nomor produk untuk diupdate: ");
        int index = readInt(input) - 1;
        input.nextLine();

        if (index < 0 || index >= menu.getProducts().length) {
            System.out.println("Nomor produk tidak valid.");
            return;
        }

        System.out.print("Ubah nama (kosongkan jika tidak): ");
        String newName = input.nextLine().trim();
        if (!newName.isEmpty()) {
            menu.updateProductName(index, newName);
        }

        System.out.print("Ubah harga (0 jika tidak): ");
        int newPrice = readInt(input);
        if (newPrice > 0) {
            menu.updateProductPrice(index, newPrice);
        }
        input.nextLine();

        System.out.print("Ubah kategori (kosongkan jika tidak): ");
        String newCategory = input.nextLine().trim();
        if (!newCategory.isEmpty()) {
            menu.updateProductCategory(index, newCategory);
        }

        System.out.println("Produk berhasil diperbarui!");
    }


    private static void deleteMenuItem(Menu menu, Scanner input) {
        if (isMenuEmpty(menu)) {
            return;
        }

        displayHeader("HAPUS MENU");
        menu.showAllMenu();
        System.out.println(LINE);
        System.out.print("Masukkan nomor produk yang ingin dihapus: ");
        int index = readInt(input) - 1;

        if (index < 0 || index >= menu.getProducts().length) {
            System.out.println("Nomor produk tidak valid.");
            return;
        }

        menu.removeProduct(index);
        System.out.println("Produk berhasil dihapus!");
    }

    private static boolean isMenuEmpty(Menu menu) {
        if (menu.getProducts().length == 0) {
            System.out.println("\nBelum ada menu yang tersedia.");
            return true;
        }
        return false;
    }

    private static void displayHeader(String title) {
        System.out.println("\n" + title);
        System.out.println(SEPARATOR);
    }

    private static int readInt(Scanner input) {
        while (!input.hasNextInt()) {
            System.out.print("Masukkan angka yang valid: ");
            input.next();
        }
        int result = input.nextInt();
        return result;
    }
}
