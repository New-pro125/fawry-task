# E-Commerce Project

This repository contains a simple Java-based e-commerce application that demonstrates object-oriented programming concepts such as inheritance, interfaces, and the decorator pattern. It simulates product inventory, shopping cart functionality, customer balance checks, expiration handling, and shipping.

## Features

* **Product Types**

    * `Product`: Base class with `name`, `price`, and `quantity`.
    * `ExpirableProduct`: Extends `Product`, adds `expiryDate` and checks for expiration.
    * `ShippableProduct`: Extends `Product`, adds `weight` and implements a shipping interface.
    * `ShippableExpirableProduct`: Combines shipping and expiration behavior.
    * **Customers**: Track `name` and `balance`.
    * **Cart**: Associate a `Customer` with items to purchase; supports `addProduct`, `removeProduct`, and `checkout()` methods.
    * **ShippingService**: Singleton service that calculates shipping fees based on total package weight.
    * **Error Handling**:

        * Empty cart at checkout
        * Insufficient customer balance
        * Product out of stock
        * Attempt to buy expired products

## Project Structure

```
src/
├── org/fawrytask/ecommerceproject/
│   ├── Main.java                # Entry point with usage examples
│   ├── Customer.java            # Customer model
│   ├── Product.java             # Base abstract class
│   ├── ExpirableProduct.java    # Subclass for perishable items
│   ├── ShippableProduct.java    # Subclass for shippable items
│   ├── ShippableExpirableProduct.java # Combines shippable + expirable
│   ├── Cart.java                # Shopping cart implementation
│   └── ShippingService.java     # Shipping fee calculator (Singleton)
```

## Requirements

* Java 8 or higher
* Maven or your preferred build tool

## Getting Started

1. **Clone the repository**

   ```bash
   git clone https://github.com/New-pro125/ecommerce-project.git
   cd ecommerce-project
   ```

2. **Build**

   ```bash
   mvn clean compile
   ```

3. **Run**

   ```bash
   mvn exec:java -Dexec.mainClass="org.fawrytask.ecommerceproject.Main"
   ```

## Usage

The `Main` class demonstrates the following scenarios:

1. **Insufficient balance**: Attempt to purchase when the customer has too little money.
2. **Expired product**: Prevent buying expired items.
3. **Quantity checks**: Block adding more items than available stock.
4. **Mixed checkout**: Purchase a mix of regular, expirable, and shippable products.

Inspect the console output for detailed messages and remaining quantities.

## Example Output

```
Not enough balance
Product is expired
The bought product quantity exceed the available quantity
Mobile Quantity 5:
Milk Quantity 10:
Mobile Scratch Card Quantity 4:

** Checkout receipt **
1x    Mobile Scratch Card 20.00
5x    Cheese 750.00
--------------------------------------
Subtotal         770.00
Shipping         0.00
Amount           770.00

Cheese Quantity 5:
Mobile Scratch Card Quantity 3:

** Shipment notice **
1x    Mobile 1.40kg
1x    TV 5.40kg
8x    Butter 8.00kg
Total package weight 14.80kg
** Checkout receipt **
1x    Mobile 3000.00
1x    Mobile Scratch Card 20.00
5x    Cheese 750.00
1x    TV 25000.00
8x    Butter 16000.00
--------------------------------------
Subtotal         44770.00
Shipping         444.00
Amount           45214.00

TV Quantity 2:
Mobile  Quantity 4:
Cheese Quantity 0:
Butter Quantity 42:
Mobile Scratch Card Quantity 2:
```

## Extending the Project in the Future

* **Inventory Singleton**: You may add an `Inventory` class to track products globally.
* **Discounts & Promotions**: Implement a decorator or strategy for discount rules.
* **Persistence**: Integrate a database or file storage to save state between runs.

