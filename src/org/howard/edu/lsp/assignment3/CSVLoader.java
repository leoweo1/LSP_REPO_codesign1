package org.howard.edu.lsp.assignment3;

import java.io.*;
import java.util.List;

/**
 * handles loading of transformed product data to CSV files.
 * encapsulates file writing logic.
 */
public class CSVLoader {
    private String outputPath;
    
    /**
     * constructs a CSVLoader for the specified output path.
     * 
     * @param outputPath the path for the output CSV file
     */
    public CSVLoader(String outputPath) {
        this.outputPath = outputPath;
    }
    
    /**
     * writes products to a CSV file with header.
     * 
     * @param products the list of products to write
     * @param header the CSV header
     * @throws IOException if file cannot be written
     */
    public void load(List<Product> products, String header) throws IOException {
        File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdirs(); // create adirectory if needed
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            writer.println(header);
            
            for (Product product : products) {
                writer.println(product.toCSV());
            }
        }
    }
}