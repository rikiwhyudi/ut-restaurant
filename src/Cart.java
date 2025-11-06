public class Cart {
    private CartItem[] carts = new CartItem[0];
    private Product freeDrink;

    public void addToCart(Product product, int qty) {
        for (CartItem item : carts) {
            if (item.getProduct().getProductName().equalsIgnoreCase(product.getProductName())) {
                item.setQty(item.getQty() + qty);
                return;
            }
        }

        CartItem[] newArr = new CartItem[carts.length + 1];
        for (int i = 0; i < carts.length; i++) {
            newArr[i] = carts[i];
        }
        newArr[carts.length] = new CartItem(product, qty);
        carts = newArr;
    }

    public int getSubtotal(String category) {
        int subtotal = 0;
        for (CartItem item : carts) {
            if (category == null || item.getProduct().getCategory().equalsIgnoreCase(category)) {
                subtotal += item.getTotal();
            }
        }
        return subtotal;
    }

    public boolean isEligibleFreeDrink() {
        if (getSubtotal("Minuman") > 50000) {
            return true;
        } else {
            return false;
        }
    }

    public void setFreeDrink(Product drink) {
        this.freeDrink = drink;
    }

    public Product getFreeDrink() {
        return freeDrink;
    }

    public void clearCart() {
        carts = new CartItem[0];
        freeDrink = null;
    }

    public CartItem[] getItems() {
        return carts;
    }
}
