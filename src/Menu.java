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
        for (int i = 0; i < products.length; i++) {
            newArr[i] = products[i];
        }
        newArr[products.length] = product;
        products = newArr;
    }

    public void removeProduct(int index) {
        if (!isValidIndex(index)) {
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
        if (isValidIndex(index)) {
            products[index].setProductName(name);
        }
    }

    public void updateProductPrice(int index, int price) {
        if (isValidIndex(index)) {
            products[index].setPrice(price);
        }
    }

    public void updateProductCategory(int index, String category) {
        if (isValidIndex(index)) {
            products[index].setCategory(category);
        }
    }

    public Product[] getProducts() {
        return products;
    }

    public Product getProduct(int index) {
        if (!isValidIndex(index)) {
            return null;
        }
        return products[index];
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < products.length;
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
