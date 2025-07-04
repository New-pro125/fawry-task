package org.fawrytask.ecommerceproject;

import java.time.LocalDate;

class ExpirableProduct extends Product implements Expirable {
    private final LocalDate expiryDate;
    public ExpirableProduct(String name,double price,int quantity, LocalDate expiryDate) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
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
