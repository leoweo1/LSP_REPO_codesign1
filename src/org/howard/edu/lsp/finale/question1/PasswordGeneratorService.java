package org.howard.edu.lsp.finale.question1;

import java.util.Random;
import java.security.SecureRandom;

/**
 * Service for generating passwords using various algorithms.
 * This service follows the Strategy pattern for algorithm selection
 * and the Singleton pattern for single instance access.
 */
public class PasswordGeneratorService {
    private static PasswordGeneratorService instance;
    private PasswordGenerationStrategy strategy;
    
    /**
     * Strategy interface for password generation algorithms.
     */
    private interface PasswordGenerationStrategy {
        String generate(int length);
    }
    
    /**
     * Basic algorithm generating digits only.
     */
    private static class BasicStrategy implements PasswordGenerationStrategy {
        private final Random random = new Random();
        private static final String DIGITS = "0123456789";
        
        @Override
        public String generate(int length) {
            StringBuilder password = new StringBuilder();
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(DIGITS.length());
                password.append(DIGITS.charAt(index));
            }
            return password.toString();
        }
    }
    
    /**
     * Enhanced algorithm using SecureRandom with alphanumeric characters.
     */
    private static class EnhancedStrategy implements PasswordGenerationStrategy {
        private final SecureRandom secureRandom = new SecureRandom();
        private static final String ALLOWED_CHARS = 
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789";
        
        @Override
        public String generate(int length) {
            StringBuilder password = new StringBuilder();
            for (int i = 0; i < length; i++) {
                int index = secureRandom.nextInt(ALLOWED_CHARS.length());
                password.append(ALLOWED_CHARS.charAt(index));
            }
            return password.toString();
        }
    }
    
    /**
     * Letters-only algorithm generating only alphabetic characters.
     */
    private static class LettersStrategy implements PasswordGenerationStrategy {
        private final Random random = new Random();
        private static final String LETTERS = 
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz";
        
        @Override
        public String generate(int length) {
            StringBuilder password = new StringBuilder();
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(LETTERS.length());
                password.append(LETTERS.charAt(index));
            }
            return password.toString();
        }
    }
    
    /**
     * Private constructor for Singleton pattern.
     */
    private PasswordGeneratorService() {
        // Initialize with no strategy
        this.strategy = null;
    }
    
    /**
     * Returns the single instance of PasswordGeneratorService.
     * @return the singleton instance
     */
    public static PasswordGeneratorService getInstance() {
        if (instance == null) {
            instance = new PasswordGeneratorService();
        }
        return instance;
    }
    
    /**
     * Sets the password generation algorithm to use.
     * @param name the name of the algorithm ("basic", "enhanced", or "letters")
     */
    public void setAlgorithm(String name) {
        switch (name.toLowerCase()) {
            case "basic":
                this.strategy = new BasicStrategy();
                break;
            case "enhanced":
                this.strategy = new EnhancedStrategy();
                break;
            case "letters":
                this.strategy = new LettersStrategy();
                break;
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + name);
        }
    }
    
    /**
     * Generates a password of the specified length using the selected algorithm.
     * @param length the desired password length
     * @return the generated password
     * @throws IllegalStateException if no algorithm has been set
     */
    public String generatePassword(int length) {
        if (strategy == null) {
            throw new IllegalStateException("No algorithm selected. Call setAlgorithm() first.");
        }
        if (length <= 0) {
            throw new IllegalArgumentException("Password length must be positive");
        }
        return strategy.generate(length);
    }
    
    /*
     * PART C — Design Pattern Documentation
     * 
     * 1. Design Patterns Used:
     *    - Strategy Pattern
     *    - Singleton Pattern
     * 
     * 2. Explanation of Pattern Choice:
     * 
     * Strategy Pattern:
     * The Strategy pattern is appropriate here because it allows different password
     * generation algorithms to be encapsulated separately and made interchangeable
     * at runtime. Each algorithm (basic, enhanced, letters) is implemented as a
     * concrete strategy class that adheres to a common PasswordGenerationStrategy
     * interface. This pattern satisfies requirements #1-4:
     * 1. Multiple approaches are supported through different strategy implementations.
     * 2. The caller can select approaches at runtime via setAlgorithm().
     * 3. Future expansion is easy - just add new strategy classes without modifying
     *    existing code or client code.
     * 4. The behavior is swappable by changing the current strategy instance.
     * 
     * Singleton Pattern:
     * The Singleton pattern ensures only one instance of PasswordGeneratorService
     * exists, providing a single shared access point as required in requirement #5.
     * The getInstance() method guarantees that all clients access the same instance,
     * which is important for maintaining consistent state and resource management.
     * The private constructor prevents external instantiation, enforcing the
     * single instance constraint throughout the application.
     */
}
