package org.howard.edu.lsp.assignment3;

import java.io.*;
import java.util.*;

/**
 * handles extraction of product data from CSV files.
 * encapsulates file reading and parsing logic.
 */
public class CSVExtractor {
    private String filePath;
    
    /**
     * constructs a CSVExtractor for the specified file path.
     * 
     * @param filePath the path to the CSV file
     */
    public CSVExtractor(String filePath) {
        this.filePath = filePath;
    }
    
    /**
     * extracts products from the CSV file.
     * 
     * @return list of Product objects
     * @throws IOException if file cannot be read
     */
    public List<Product> extract() throws IOException {
        List<Product> products = new ArrayList<>();
        File file = new File(filePath);
        
        if (!file.exists()) {
            throw new IOException("Input file '" + filePath + "' not found.");
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isHeader = true;
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header row
                }
                
                Product product = parseLine(line);
                if (product != null) {
                    products.add(product);
                }
            }
        }
        
        return products;
    }
    
    /**
     * parses a CSV line into a Product object.
     * 
     * @param line the CSV line to parse
     * @return product object or null if parsing fails
     */
    private Product parseLine(String line) {
        try {
            String[] columns = line.split(",");
            if (columns.length != 4) {
                System.out.println("Warning: Skipping invalid row: " + line);
                return null;
            }
            
            int productId = Integer.parseInt(columns[0].trim());
            String name = columns[1].trim();
            double price = Double.parseDouble(columns[2].trim());
            String category = columns[3].trim();
            
            return new Product(productId, name, price, category);
            
        } catch (NumberFormatException e) {
            System.out.println("Warning: Skipping row with invalid data: " + line);
            return null;
        }
    }
}