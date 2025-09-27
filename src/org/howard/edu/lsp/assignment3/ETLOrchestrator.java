package org.howard.edu.lsp.assignment3;

import java.io.IOException;
import java.util.List;

/**
 * orchestrates the entire ETL process using dedicated components.
 * demonstrates composition and single responsibility principle.
 */
public class ETLOrchestrator {
    private CSVExtractor extractor;
    private ProductTransformer transformer;
    private CSVLoader loader;
    private String inputPath;
    private String outputPath;
    
    /**
     * constructs an ETLOrchestrator with specified file paths.
     * 
     * @param inputPath the input CSV file path
     * @param outputPath the output CSV file path
     */
    public ETLOrchestrator(String inputPath, String outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.extractor = new CSVExtractor(inputPath);
        this.transformer = new ProductTransformer();
        this.loader = new CSVLoader(outputPath);
    }
    
    /**
     * executes the complete ETL process.
     */
    public void execute() {
        System.out.println("Starting Object-Oriented ETL Pipeline...");
        
        try {
            // extract
            List<Product> products = extractor.extract();
            System.out.println("Extracted " + products.size() + " products from " + inputPath);
            
            // transform
            transformer.transform(products);
            System.out.println("Transformed " + products.size() + " products");
            
            // load
            loader.load(products, transformer.getHeader());
            System.out.println("Loaded transformed data to " + outputPath);
            
            // summary
            printSummary(products.size());
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Please ensure the input file exists in the data/ directory.");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
    
    /**
     * prints a summary of the ETL process.
     * 
     * @param rowsProcessed the number of rows processed
     */
    private void printSummary(int rowsProcessed) {
        System.out.println("\n=== ETL Pipeline Summary ===");
        System.out.println("Rows processed: " + rowsProcessed);
        System.out.println("Output path: " + outputPath);
        System.out.println("ETL process completed successfully!");
    }
    
    /**
     * main method to run the ETL pipeline.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        ETLOrchestrator orchestrator = new ETLOrchestrator(
            "data/products.csv", 
            "data/transformed_products.csv"
        );
        orchestrator.execute();
    }
}