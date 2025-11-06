import java.util.Scanner;

public class Main {
    private static final String SEPARATOR = "=".repeat(50);
    private static final String LINE = "-".repeat(50);

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Menu menu = new Menu();
        Cart cart = new Cart();
        initializeMenu(menu);
        runMainMenu(menu, cart, input);
        input.close();
    }

    private static void initializeMenu(Menu menu) {
        menu.addProduct(new Product("Burger", 10000, "Makanan"));
        menu.addProduct(new Product("Sandwich", 15000, "Makanan"));
        menu.addProduct(new Product("Pizza", 20000, "Makanan"));
        menu.addProduct(new Product("Fried Chicken", 15000, "Makanan"));
        menu.addProduct(new Product("Ice Coffee", 5000, "Minuman"));
        menu.addProduct(new Product("Ice Tea", 5000, "Minuman"));
        menu.addProduct(new Product("Coca Cola", 5000, "Minuman"));
        menu.addProduct(new Product("Lemon Tea", 10000, "Minuman"));
    }

    private static void runMainMenu(Menu menu, Cart cart, Scanner input) {
        boolean running = true;
        while (running) {
            displayHeader("MAIN MENU");
            System.out.println("1. Mulai Pemesanan");
            System.out.println("2. Kelola Menu");
            System.out.println(SEPARATOR);
            System.out.print("Pilih (0 = keluar): ");

            int choice = readInt(input);

            switch (choice) {
                case 1 -> startOrdering(menu, cart, input);
                case 2 -> manageMenu(menu, input);
                case 0 -> running = false;
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void startOrdering(Menu menu, Cart cart, Scanner input) {
        if (isMenuEmpty(menu)) {
            return;
        }

        boolean repeatOrder;

        do {
            cart.clearCart();
            repeatOrder = false;

            displayMenu(menu);
            addItemToCart(menu, cart, input);

            if (cart.getItems().length == 0) {
                System.out.println("Pemesanan dibatalkan.");
                break;
            }

            if (cart.isEligibleFreeDrink()) {
                displayHeader("DAFTAR MINUMAN GRATIS");
                menu.showDrinks();
                System.out.println(SEPARATOR);
                System.out.print("Pilih nomor produk (0 = tidak ambil): ");

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
                repeatOrder = true;
            }
        } while (repeatOrder);
    }

    private static void addItemToCart(Menu menu, Cart cart, Scanner input) {
        boolean adding = true;
        while (adding) {
            System.out.print("Pilih nomor produk (0 = batal): ");
            int choice = readInt(input);

            if (choice == 0) {
                adding = false;
                continue;
            }

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

        displayHeader("STRUK PEMBAYARAN");
        for (CartItem item : items) {
            System.out.println(item.getProduct().getProductName() + " x" + item.getQty() +
                    " = " + Product.currencyFormatted(item.getTotal()));
        }

        if (cart.getFreeDrink() != null) {
            System.out.println("Bonus: " + cart.getFreeDrink().getProductName());
        }

        int subtotal = cart.getSubtotal(null);

        double discount = 0;
        if (subtotal > 100000) {
            discount = subtotal * 0.10;
        }

        double subtotalAfterDiscount = subtotal - discount;
        double tax = subtotalAfterDiscount * 0.10;
        double serviceFee = 20000;

        int total = (int) (subtotalAfterDiscount + tax + serviceFee);

        System.out.println(LINE);
        System.out.println("Subtotal      : " + Product.currencyFormatted(subtotal));

        if (discount > 0) {
            System.out.println("Diskon (10%)  : -" + Product.currencyFormatted((int) discount));
        }

        System.out.println("PPN (10%)     : " + Product.currencyFormatted((int) tax));
        System.out.println("Pelayanan     : " + Product.currencyFormatted((int) serviceFee));
        System.out.println(SEPARATOR);
        System.out.println("GRAND TOTAL   : " + Product.currencyFormatted(total));
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
            System.out.println(SEPARATOR);
            System.out.print("Pilih (0 = kembali): ");

            int choice = readInt(input);

            switch (choice) {
                case 1 -> displayMenu(menu);
                case 2 -> addMenuItem(menu, input);
                case 3 -> updateMenuItem(menu, input);
                case 4 -> deleteMenuItem(menu, input);
                case 0 -> managing = false;
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void displayMenu(Menu menu) {
        if (isMenuEmpty(menu)) {
            return;
        }
        displayHeader("DAFTAR PRODUK");
        menu.showAllMenu();
        System.out.println(SEPARATOR);
    }

    private static void addMenuItem(Menu menu, Scanner input) {
        displayHeader("TAMBAHKAN PRODUK");

        System.out.print("Nama produk (0 = batal): ");
        input.nextLine();
        String name = input.nextLine().trim();

        if (name.equals("0")) {
            System.out.println("Operasi dibatalkan.");
            return;
        }

        if (!name.isEmpty()) {

            System.out.print("Harga: ");
            int price = readInt(input);

            System.out.print("Kategori (Makanan/Minuman): ");
            input.nextLine();
            String category = input.nextLine().trim();

            if (!category.isEmpty()) {
                menu.addProduct(new Product(name, price, category));
                System.out.println("Produk berhasil ditambahkan!");
            } else {
                System.out.println("Kategori tidak boleh kosong! \nOperasi dibatalkan.");
            }

        } else {
            System.out.println("Nama produk tidak boleh kosong! \nOperasi dibatalkan.");
        }
    }

    private static void updateMenuItem(Menu menu, Scanner input) {
        if (isMenuEmpty(menu)) {
            return;
        }

        displayHeader("UPDATE PRODUK");
        menu.showAllMenu();
        System.out.println(SEPARATOR);

        System.out.print("Pilih nomor produk untuk diupdate (0 = batal): ");
        int choice = readInt(input);

        if (choice == 0) {
            System.out.println("Operasi dibatalkan.");
            return;
        }

        int index = choice - 1;
        input.nextLine();

        if (menu.getProduct(index) == null) {
            System.out.println("Nomor produk tidak valid.");
            return;
        }

        Product currentProduct = menu.getProduct(index);

        System.out.println(LINE);
        System.out.println("-> Produk yang dipilih: " + currentProduct.getProductName());
        System.out.println(LINE);

        System.out.print("Ubah nama (kosongkan jika tidak): ");
        String newName = input.nextLine().trim();

        System.out.print("Ubah harga (0 jika tidak): ");
        int newPrice = readInt(input);

        input.nextLine();

        System.out.print("Ubah kategori (kosongkan jika tidak): ");
        String newCategory = input.nextLine().trim();

        System.out.print("\nTerapkan perubahan ini? (y/n): ");
        String confirmUpdate = input.next();

        if (confirmUpdate.equalsIgnoreCase("y")) {
            if (!newName.isEmpty()) {
                menu.updateProductName(index, newName);
            }
            if (newPrice > 0) {
                menu.updateProductPrice(index, newPrice);
            }
            if (!newCategory.isEmpty()) {
                menu.updateProductCategory(index, newCategory);
            }
            System.out.println("Produk berhasil diperbarui!");
        } else {
            System.out.println("Pembaruan dibatalkan.");
        }
    }


    private static void deleteMenuItem(Menu menu, Scanner input) {
        if (isMenuEmpty(menu)) {
            return;
        }

        displayHeader("HAPUS PRODUK");
        menu.showAllMenu();
        System.out.println(SEPARATOR);
        System.out.print("Pilih nomor produk untuk dihapus (0 = batal): ");

        int choice = readInt(input);

        if (choice == 0) {
            System.out.println("Operasi dibatalkan.");
            return;
        }

        int index = choice - 1;

        if (menu.getProduct(index) == null) {
            System.out.println("Nomor produk tidak valid.");
            return;
        }

        Product productToDelete = menu.getProduct(index);

        System.out.println(LINE);
        System.out.println("-> Produk yang dipilih: " + productToDelete.getProductName());
        System.out.println(LINE);

        System.out.print("\nYakin ingin menghapus produk? (y/n): ");
        String confirmDelete = input.next();

        if (confirmDelete.equalsIgnoreCase("y")) {
            menu.removeProduct(index);
            System.out.println("Produk berhasil dihapus!");
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
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
        return input.nextInt();
    }
}