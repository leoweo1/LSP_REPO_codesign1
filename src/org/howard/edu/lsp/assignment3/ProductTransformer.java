package org.howard.edu.lsp.assignment3;

import java.util.List;

/**
 * handles transformation of product data according to business rules.
 * encapsulates transformation logic in a dedicated class.
 */
public class ProductTransformer {
    
    /**
     * applies all required transformations to a list of products.
     * transformation order: uppercase → discount → recategorization → price range.
     * 
     * @param products the list of products to transform
     */
    public void transform(List<Product> products) {
        for (Product product : products) {
            // 1. uppercase product names
            product.uppercaseName();
            
            // 2. apply discount to Electronics
            product.applyElectronicsDiscount();
            
            // 3. recategorize Premium Electronics
            product.upgradeToPremium();
            
            // 4. calculate price range
            product.calculatePriceRange();
        }
    }
    
    /**
     * gets the CSV header for the transformed data.
     * 
     * @return the CSV header string
     */
    public String getHeader() {
        return "ProductID,Name,Price,Category,PriceRange";
    }
}