package org.fawrytask.ecommerceproject;

import java.util.HashMap;

class ShippingService {
    private double pricePerKg = 0;
    private final HashMap<Customer, HashMap<Shippable, Integer>> shippedPackage  = new HashMap<Customer, HashMap<Shippable, Integer>>();
    private static ShippingService instance;
    private final HashMap<Customer,Double>totalShipmentPrice = new HashMap<Customer, Double>();
    private ShippingService() {}
    public static ShippingService getInstance() {
        if(instance==null){
            instance = new ShippingService();
        }
        return instance;
    }
    public double getPricePerKg() {
        return pricePerKg;
    }
    public double getTotalShipmentPrice(Customer customer) {
        if(totalShipmentPrice.containsKey(customer)){
            return totalShipmentPrice.get(customer);
        }
        return 0.0;
    }
    public void setPricePerKg(double pricePerKg) {
        this.pricePerKg = pricePerKg;
    }
    public void addShippedProducts(Customer customer, Shippable product, int quantity){
        if(this.shippedPackage.containsKey(customer)) {
            if (this.shippedPackage.get(customer).containsKey(product)) {
                int shippedQuantity = this.shippedPackage.get(customer).get(product);
                this.shippedPackage.get(customer).put(product, (shippedQuantity + quantity));
            } else {
                this.shippedPackage.get(customer).put(product, quantity);
            }
        }
        else {
            HashMap<Shippable, Integer> shippedProducts = new HashMap<>();
            shippedProducts.put(product, quantity);
            this.shippedPackage.put(customer, shippedProducts);
        }
        double oldTotalShipmentPrice = getTotalShipmentPrice(customer);
        totalShipmentPrice.put(customer,(oldTotalShipmentPrice + product.getWeight() * quantity *  pricePerKg));
    }
    public void removeShippedProducts(Customer customer,Shippable product,int quantity){
        if(this.shippedPackage.containsKey(customer)) {
            if (this.shippedPackage.get(customer).containsKey(product)) {
                int shippedQuantity = this.shippedPackage.get(customer).get(product);
                if(shippedQuantity > quantity){
                    this.shippedPackage.get(customer).put(product, (shippedQuantity - quantity));
                } else {
                    this.shippedPackage.get(customer).remove(product);
                }
            }
            double oldTotalShipmentPrice = getTotalShipmentPrice(customer);
            totalShipmentPrice.put(customer,oldTotalShipmentPrice - product.getWeight() * quantity *  pricePerKg);
        }
        else {
            throw new IllegalArgumentException("Can't remove from Shipping package Customer that doesn't exist");
        }

    }
    public void shipProducts(Customer customer){
        if(!shippedPackage.containsKey(customer)){
            return;
        }
        System.out.println("** Shipment notice **");
        double totalWeight = 0;
        for (Shippable shippable : this.shippedPackage.get(customer).keySet()) {
            int shippableQuantity = this.shippedPackage.get(customer).get(shippable);
            double shippableTotalWeight = shippable.getWeight() *  shippableQuantity;
            totalWeight += shippableTotalWeight;
            System.out.printf("%dx    %s %.2fkg%n",shippableQuantity,shippable.getName(),shippableTotalWeight);

        }
        System.out.printf("Total package weight %.2fkg%n",totalWeight);
        cleanUp(customer);
    }
    public void cleanUp(Customer customer){
        shippedPackage.remove(customer);
        totalShipmentPrice.remove(customer);
    }
}
