package org.howard.edu.lsp.assignment3;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * represents a product with its attributes and transformation capabilities.
 * encapsulates product data and transformation logic.
 */
public class Product {
    private int productId;
    private String name;
    private double price;
    private String category;
    private String priceRange;
    
    /**
     * constructs a Product with the specified attributes.
     * 
     * @param productId the product identifier
     * @param name the product name
     * @param price the product price
     * @param category the product category
     */
    public Product(int productId, String name, double price, String category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.priceRange = "";
    }
    
    // getters and setters
    public int getProductId() { return productId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public String getPriceRange() { return priceRange; }
    
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
    public void setPriceRange(String priceRange) { this.priceRange = priceRange; }
    
    /**
     * converts the product name to uppercase.
     */
    public void uppercaseName() {
        this.name = this.name.toUpperCase();
    }
    
    /**
     * applies a 10% discount if the product is in Electronics category.
     * 
     * @return true if discount was applied, false otherwise
     */
    public boolean applyElectronicsDiscount() {
        if ("Electronics".equalsIgnoreCase(this.category)) {
            this.price = roundPrice(this.price * 0.9);
            return true;
        }
        return false;
    }
    
    /**
     * upgrades category to Premium Electronics if conditions are met.
     */
    public void upgradeToPremium() {
        if ("Electronics".equalsIgnoreCase(this.category) && this.price > 500.00) {
            this.category = "Premium Electronics";
        }
    }
    
    /**
     * determines and sets the price range based on the current price
     */
    public void calculatePriceRange() {
        if (this.price <= 10.00) {
            this.priceRange = "Low";
        } else if (this.price <= 100.00) {
            this.priceRange = "Medium";
        } else if (this.price <= 500.00) {
            this.priceRange = "High";
        } else {
            this.priceRange = "Premium";
        }
    }
    
    /**
     * rounds the price to two decimal places using half-up rounding
     * 
     * @param price the price to round
     * @return the rounded price
     */
    private double roundPrice(double price) {
        BigDecimal bd = new BigDecimal(price);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    /**
     * returns CSV representation of the product.
     * 
     * @return CSV string of product attributes
     */
    public String toCSV() {
        return String.format("%d,%s,%.2f,%s,%s", 
            productId, name, price, category, priceRange);
    }
}