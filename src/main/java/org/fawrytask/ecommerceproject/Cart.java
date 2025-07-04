package org.fawrytask.ecommerceproject;

import java.util.HashMap;

class Cart {
    private final HashMap<Product, Integer> cartProducts;
    private final Customer customer;
    private double productsPrice;
    public Cart(Customer customer) {
        this.cartProducts = new HashMap<Product, Integer>();
        this.customer = customer;
        this.productsPrice = 0;
    }

    public void addProduct(Product product, int quantity){
        int productQuantity = product.getQuantity();
        if(quantity > productQuantity){
            throw new IllegalArgumentException("The bought product quantity exceed the available quantity");
        }
        else {
            if(product instanceof ExpirableProduct expirableProduct){
                if(expirableProduct.isExpired()){
                    throw new IllegalArgumentException("Product is expired");
                }
            }
            if(this.cartProducts.containsKey(product)){
                this.cartProducts.put(product,this.cartProducts.get(product)+quantity);
            }
            else {
                this.cartProducts.put(product,quantity);
            }
            if(product instanceof ShippableProduct shippableProduct){
                ShippingService service = ShippingService.getInstance();
                service.addShippedProducts(customer,shippableProduct,quantity);
            }
            if(product instanceof ShippableExpirableProduct product1){
                ShippingService service = ShippingService.getInstance();
                service.addShippedProducts(customer,product1,quantity);
            }
            product.setQuantity(productQuantity - quantity);
            this.productsPrice += product.getPrice() *  quantity;
        }
    }
    public void removeProduct(Product product, int quantity){
        if(this.cartProducts.containsKey(product)){
            int productQuantityInCart = this.cartProducts.get(product);
            int availableProductQuantity = product.getQuantity();
            if(productQuantityInCart<=quantity){
                if(productQuantityInCart < quantity)
                    this.cartProducts.put(product,productQuantityInCart-quantity);
                else
                    this.cartProducts.remove(product);
                this.productsPrice -= product.getPrice() *  quantity;
                ShippingService service = ShippingService.getInstance();
                if(product instanceof ShippableProduct product1)
                    service.removeShippedProducts(customer,product1,quantity);
                if(product instanceof ShippableExpirableProduct product1){
                    service.removeShippedProducts(customer,product1,quantity);
                }
                product.setQuantity(availableProductQuantity + quantity);
            }
            else {
                throw new IllegalArgumentException("Product quantity to be removed exceed the quantity in the cart ");
            }
        }
        else {

            throw new IllegalArgumentException("Cannot remove the product that doesn't exist");
        }
    }
    public void checkout() throws Exception {
        if(this.cartProducts.isEmpty()){
            throw new Exception("Empty Cart");
        }
        else {
            ShippingService service = ShippingService.getInstance();
            double shippingPrice = service.getTotalShipmentPrice(customer);
            double customerBalance = this.customer.getBalance();
            if(customerBalance>= this.productsPrice + shippingPrice){
                this.customer.setBalance(customerBalance - (this.productsPrice + shippingPrice));

                service.shipProducts(customer);
                printReceipt();
                System.out.println("--------------------------------------");
                System.out.printf("Subtotal         %.2f%n",this.productsPrice);
                System.out.printf("Shipping         %.2f%n",shippingPrice);
                System.out.printf("Amount           %.2f%n",shippingPrice + this.productsPrice);

            }
            else {

                throw new Exception("Not enough balance");

            }
        }

    }



    private void printReceipt() {

        System.out.println("** Checkout receipt **");
        for (Product product : this.cartProducts.keySet()) {
            int productQuantity = this.cartProducts.get(product);
            double productTotalPrice = product.getPrice() * productQuantity;
            System.out.printf("%dx    %s %.2f%n", productQuantity,product.getName(),productTotalPrice);
        }

    }

}
