public class Menu {
    private Product[] products = new Product[0];

    private boolean isDuplicate(Product product) {
        for (Product p : products) {
            if (p.getProductName().equalsIgnoreCase(product.getProductName())) {
                return true;
            }
        }
        return false;
    }

    private int getInsertIndex(Product product) {
        int index = products.length;

        // jika makanan, cari dimana kategori makanan berakhir
        if ("Makanan".equalsIgnoreCase(product.getCategory())) {
            index = 0;
            // lakukan traversal selama masih melihat katogori makanan
            while (index < products.length && "Makanan".equalsIgnoreCase(products[index].getCategory())) {
                index++;
            }
        }
        return index;
    }

    private Product[] insertIntoArray(Product product, int insertIndex) {

        Product[] newArr = new Product[products.length + 1];

        // salin semua elemen dari index 0 hingga sebelum insertIndex
        for (int i = 0; i < insertIndex; i++) {
            newArr[i] = products[i];
        }

        // sisipkan item baru di posisi yang sudah ditentukan.
        newArr[insertIndex] = product;

        // salin elemen dari insertIndex array lama ke posisi (i + 1) array baru.
        for (int i = insertIndex; i < products.length; i++) {
            newArr[i + 1] = products[i];
        }
        return newArr;
    }

    // cek apakah indexnya invalid
    private boolean isInvalidIndex(int index) {
        return index < 0 || index >= products.length;
    }

    public void addProduct(Product product) {
        // cek apakah produk sudah ada di daftar
        if (isDuplicate(product)) {
            System.out.println("Produk sudah ada di daftar.");
            return;
        }

        // tentukan posisi penyisipan product
        int insertIndex = getInsertIndex(product);

        // sisipkan dan ganti referensi ke array baru
        products = insertIntoArray(product, insertIndex);
    }

    public void removeProduct(int index) {
        if (isInvalidIndex(index)) {
            return;
        }

        Product[] newArr = new Product[products.length - 1];
        int j = 0;

        for (int i = 0; i < products.length; i++) {
            if (i != index) {
                newArr[j] = products[i];
                j++;
            }
        }
        products = newArr;
    }

    public void updateProductName(int index, String name) {
        if (isInvalidIndex(index)) {
            return;
        }
        products[index].setProductName(name);
    }

    public void updateProductPrice(int index, int price) {
        if (isInvalidIndex(index)) {
            return;
        }
        products[index].setPrice(price);
    }

    public void updateProductCategory(int index, String category) {
        if (isInvalidIndex(index)) {
            return;
        }
        products[index].setCategory(category);
    }

    public Product getProduct(int index) {
        if (isInvalidIndex(index)) {
            return null;
        }
        return products[index];
    }

    public Product[] getProducts() {
        return products;
    }

    public void showAllMenu() {
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i]);
        }
    }

    public void showDrinks() {
        for (int i = 0; i < products.length; i++) {
            if ("Minuman".equalsIgnoreCase(products[i].getCategory())) {
                System.out.println((i + 1) + ". " + products[i]);
            }
        }
    }
}