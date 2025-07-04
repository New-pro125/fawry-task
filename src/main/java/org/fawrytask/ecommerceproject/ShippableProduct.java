package org.fawrytask.ecommerceproject;

class ShippableProduct extends Product implements Shippable {
    private final double weight;
    public ShippableProduct(String name,double price,int quantity, double weight) {
        super(name, price, quantity);
        this.weight = weight;
    }
    @Override
    public double getWeight() {
        return weight;
    }
}
