package org.fawrytask.ecommerceproject;

import java.time.LocalDate;

class ShippableExpirableProduct extends Product implements Shippable,Expirable {
    private final double weight;
    private final LocalDate expiryDate;
    public ShippableExpirableProduct(String name, double price, int quantity,double weight, LocalDate expiryDate) {
        super(name, price, quantity);
        this.weight = weight;
        this.expiryDate = expiryDate;
    }
    @Override
    public double getWeight() {
        return weight;
    }
    @Override
    public boolean isExpired(){
        return expiryDate.isBefore(LocalDate.now());
    }
    @Override
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
}
