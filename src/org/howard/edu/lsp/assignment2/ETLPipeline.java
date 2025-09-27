package org.howard.edu.lsp.assignment2;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class ETLPipeline {
    
    public static void main(String[] args) {
        ETLPipeline etl = new ETLPipeline();
        etl.run();
    }
    
    public void run() {
        String inputFile = "data/products.csv";
        String outputFile = "data/transformed_products.csv";
        
        System.out.println("Starting ETL Pipeline...");
        
        try {
            // extract
            List<String[]> data = extract(inputFile);
            if (data == null) {
                return; // error already handled in extract method
            }
            
            // transform
            List<String[]> transformedData = transform(data);
            
            // load
            load(transformedData, outputFile);
            
            // print summary
            printSummary(data.size(), outputFile);
            
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private List<String[]> extract(String filePath) {
        List<String[]> data = new ArrayList<>();
        File file = new File(filePath);
        
        // handle missing file
        if (!file.exists()) {
            System.err.println("Error: Input file '" + filePath + "' not found.");
            System.err.println("Please ensure the file exists in the data/ directory.");
            return null;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // skip empty lines
                
                String[] columns = line.split(",");
                
                // validate column count for data rows (header has 4, data rows should have 4)
                if (isFirstLine) {
                    if (columns.length != 4) {
                        System.out.println("Warning: Header has " + columns.length + " columns, expected 4");
                    }
                    isFirstLine = false;
                } else {
                    if (columns.length != 4) {
                        System.out.println("Warning: Skipping invalid row: " + line);
                        continue;
                    }
                }
                
                data.add(columns);
            }
            
            System.out.println("Extracted " + (data.size() - 1) + " data rows from " + filePath);
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
        
        return data;
    }
    
    private List<String[]> transform(List<String[]> data) {
        if (data.isEmpty()) {
            return new ArrayList<>(); // return empty list if no data
        }
        
        List<String[]> transformedData = new ArrayList<>();
        
        // add header row with new PriceRange column
        String[] header = Arrays.copyOf(data.get(0), 5); // expand to 5 columns
        header[4] = "PriceRange";
        transformedData.add(header);
        
        // skip header row for data transformation
        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);
            
            try {
                // parse the original values
                int productId = Integer.parseInt(row[0].trim());
                String name = row[1].trim();
                double price = Double.parseDouble(row[2].trim());
                String category = row[3].trim();
                
                // TRANSFORMATION ORDER:
                // 1. Uppercase product names
                name = name.toUpperCase();
                
                // 2. apply 10% discount to Electronics category
                double originalPrice = price; // for reference original value before discount
                if ("Electronics".equalsIgnoreCase(category)) {
                    price = price * 0.9; // 10% discount
                    price = roundPrice(price);
                }
                
                // 3. recategorize Premium Electronics
                if ("Electronics".equalsIgnoreCase(category) && price > 500.00) {
                    category = "Premium Electronics";
                }
                
                // 4. computing PriceRange from final price
                String priceRange = getPriceRange(price);
                
                // create transformed row
                String[] transformedRow = new String[5];
                transformedRow[0] = String.valueOf(productId);
                transformedRow[1] = name;
                transformedRow[2] = String.format("%.2f", price);
                transformedRow[3] = category;
                transformedRow[4] = priceRange;
                
                transformedData.add(transformedRow);
                
            } catch (NumberFormatException e) {
                System.out.println("Warning: Skipping invalid data row: " + String.join(",", row));
            }
        }
        
        return transformedData;
    }
    
    private double roundPrice(double price) {
        BigDecimal bd = new BigDecimal(price);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    private String getPriceRange(double price) {
        if (price <= 10.00) {
            return "Low";
        } else if (price <= 100.00) {
            return "Medium";
        } else if (price <= 500.00) {
            return "High";
        } else {
            return "Premium";
        }
    }
    
    private void load(List<String[]> transformedData, String outputPath) {
        // create data directory if it doesn't exist
        File outputFile = new File(outputPath);
        outputFile.getParentFile().mkdirs(); // create the data directory if needed
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            for (String[] row : transformedData) {
                writer.println(String.join(",", row));
            }
            System.out.println("Transformed data written to: " + outputPath);
            
        } catch (IOException e) {
            System.err.println("Error writing output file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
    private void printSummary(int totalRows, String outputPath) {
        int dataRows = totalRows - 1; // Subtract header row
        System.out.println("\n=== ETL Pipeline Summary ===");
        System.out.println("Rows read: " + dataRows);
        System.out.println("Rows transformed: " + dataRows);
        System.out.println("Rows skipped: 0");
        System.out.println("Output path: " + outputPath);
        System.out.println("ETL process completed successfully!");
    }
}