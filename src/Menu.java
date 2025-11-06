public class Menu {
    private Product[] products = new Product[0];

    public void addProduct(Product product) {
        for (Product p : products) {
            if (p.getProductName().equalsIgnoreCase(product.getProductName())) {
                System.out.println("Produk sudah ada di daftar.");
                return;
            }
        }

        Product[] newArr = new Product[products.length + 1];
        int insertIndex = 0;

        if ("Makanan".equalsIgnoreCase(product.getCategory())) {
            while (insertIndex < products.length && "Makanan".equalsIgnoreCase(products[insertIndex].getCategory())) {
                insertIndex++;
            }
        } else {
            insertIndex = products.length;
        }

        for (int i = 0; i < insertIndex; i++) {
            newArr[i] = products[i];
        }

        newArr[insertIndex] = product;

        for (int i = insertIndex; i < products.length; i++) {
            newArr[i + 1] = products[i];
        }
        products = newArr;
    }

    // cek apakah index invalid
    private boolean isInvalidIndex(int index) {
        return index < 0 || index >= products.length;
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