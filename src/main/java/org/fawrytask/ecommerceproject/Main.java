package org.fawrytask.ecommerceproject;

import java.time.LocalDate;

/**
 *  Possible Classes
 //  *Inventory  Singleton Class List<Products> I can neglect it

 *  Product {interface in TS , Abstract Class in Java} {name , price , quantity} buy() gets,sets
 *  ExpirableProduct inherits The Super Class + Expire Date
 *  ShippableProduct inherits The Super Class + Weight
 *  Customers -> {balance, name}
 *  Cart -> {List<Products>} , addProduct(IProduct) , removeProduct(IProduct)
 *  ShippingService  implements Interface IShippableProduct -> string getName() , getWeight()

 */
public class Main {
    public static void main(String[] args) {
        //** Ecommerce Customers **
        Customer richCustomer = new Customer("Luffy",50000);
        Customer normalCustomer = new Customer("Zoro",10000);
        Customer poorCustomer = new Customer("Usopp",1000);
        // ** Inventory Products **
        Product mobileScratchCard = new Product("Mobile Scratch Card",20,4);
        ExpirableProduct cheese = new ExpirableProduct("Cheese",150,10, LocalDate.now().plusMonths(2));
        ExpirableProduct milk = new ExpirableProduct("Milk",40,10, LocalDate.now().minusMonths(2));
        ShippableProduct TV = new ShippableProduct("TV",25000,3,5.4);
        ShippableProduct Mobile = new ShippableProduct("Mobile",3000,7,1.4);
        ShippableExpirableProduct Butter = new ShippableExpirableProduct("Butter",2000,50,1,LocalDate.now().plusMonths(1));
        // ** Cart **
        Cart richCart = new Cart(richCustomer);
        Cart normalCart = new Cart(normalCustomer);
        Cart poorCart = new Cart(poorCustomer);
        // ** Shipping Service **
        ShippingService serv = ShippingService.getInstance();
        serv.setPricePerKg(30);

        // Not Enough Balance Case
        try {
            poorCart.addProduct(Mobile,2);
            poorCart.checkout();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        System.out.printf("Mobile Quantity %d:%n",Mobile.getQuantity());
        // Customer Buying Expired Product
        try {
            poorCart.addProduct(milk,5);
            poorCart.checkout();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        System.out.printf("Milk Quantity %d:%n",milk.getQuantity());
        // Customer Buying more than available Quantity
        try {
            normalCart.addProduct(mobileScratchCard,8);
            normalCart.checkout();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        System.out.printf("Mobile Scratch Card Quantity %d:%n",mobileScratchCard.getQuantity());
        // Customer Buying Without Shippable products
        try {
            normalCart.addProduct(cheese,5);
            normalCart.addProduct(mobileScratchCard,1);
            normalCart.checkout();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        System.out.printf("Cheese Quantity %d:%n",cheese.getQuantity());
        System.out.printf("Mobile Scratch Card Quantity %d:%n",mobileScratchCard.getQuantity());
        // Customer Buying Mix of Products
        try {
            richCart.addProduct(TV,1);
            richCart.addProduct(Mobile,1);
            richCart.addProduct(cheese,5);
            richCart.addProduct(Butter,8);
            richCart.addProduct(mobileScratchCard,1);
            richCart.checkout();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        System.out.printf("TV Quantity %d:%n",TV.getQuantity());
        System.out.printf("Mobile  Quantity %d:%n",Mobile.getQuantity());
        System.out.printf("Cheese Quantity %d:%n",cheese.getQuantity());
        System.out.printf("Butter Quantity %d:%n",Butter.getQuantity());
        System.out.printf("Mobile Scratch Card Quantity %d:%n",mobileScratchCard.getQuantity());
    }
}