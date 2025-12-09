package org.howard.edu.lsp.finale.question1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PasswordGeneratorServiceTest {

    private PasswordGeneratorService service;

    @BeforeEach
    public void setup() {
        service = PasswordGeneratorService.getInstance();
    }

    @Test
    public void checkInstanceNotNull() {
        // TODO: verify that 'service' is not null
        assertNotNull(service, "Service instance should not be null");
    }

    @Test
    public void checkSingleInstanceBehavior() {
        PasswordGeneratorService second = PasswordGeneratorService.getInstance();
        // TODO: Verify that both 'service' (created in @BeforeEach) 
        // and 'second' refer to the EXACT same object in memory. This 
        // test must confirm true singleton behavior — not just that the 
        // two objects are equal, but they are the *same 
        // instance* returned by getInstance().
        assertSame(service, second, 
            "Both references should point to the same singleton instance");
    }

    @Test
    public void generateWithoutSettingAlgorithmThrowsException() {
        PasswordGeneratorService s = PasswordGeneratorService.getInstance();
        // TODO: verify correct exception behavior for each algorithm
        assertThrows(IllegalStateException.class, () -> {
            s.generatePassword(10);
        }, "Should throw IllegalStateException when no algorithm is set");
        
        // Reset service state for the next test
        service.setAlgorithm("basic");
    }

    @Test
    public void basicAlgorithmGeneratesCorrectLengthAndDigitsOnly() {
        service.setAlgorithm("basic");
        String p = service.generatePassword(10);
        // TODO: verify required behavior
        assertEquals(10, p.length(), 
            "Password should have exactly 10 characters");
        assertTrue(p.matches("^\\d{10}$"), 
            "Password should contain only digits (0-9)");
        
        // Test with different length
        p = service.generatePassword(5);
        assertEquals(5, p.length(), 
            "Password should have exactly 5 characters");
        assertTrue(p.matches("^\\d{5}$"), 
            "Password should contain only digits (0-9)");
    }

    @Test
    public void enhancedAlgorithmGeneratesCorrectCharactersAndLength() {
        service.setAlgorithm("enhanced");
        String p = service.generatePassword(12);
        // TODO: verify required behavior
        assertEquals(12, p.length(), 
            "Password should have exactly 12 characters");
        assertTrue(p.matches("^[A-Za-z0-9]{12}$"), 
            "Password should contain only alphanumeric characters");
        
        // Generate multiple passwords to test randomness and character distribution
        for (int i = 0; i < 10; i++) {
            p = service.generatePassword(15);
            assertEquals(15, p.length(), 
                "Password should have exactly 15 characters");
            assertTrue(p.matches("^[A-Za-z0-9]{15}$"), 
                "Password should contain only alphanumeric characters");
        }
    }

    @Test
    public void lettersAlgorithmGeneratesLettersOnly() {
        service.setAlgorithm("letters");
        String p = service.generatePassword(8);
        // TODO: verify required behavior
        assertEquals(8, p.length(), 
            "Password should have exactly 8 characters");
        assertTrue(p.matches("^[A-Za-z]{8}$"), 
            "Password should contain only letters");
        
        // Test with different length
        p = service.generatePassword(20);
        assertEquals(20, p.length(), 
            "Password should have exactly 20 characters");
        assertTrue(p.matches("^[A-Za-z]{20}$"), 
            "Password should contain only letters");
    }

    @Test
    public void switchingAlgorithmsChangesBehavior() {
        // Test basic algorithm
        service.setAlgorithm("basic");
        String p1 = service.generatePassword(10);
        assertEquals(10, p1.length(), "Basic password should be length 10");
        assertTrue(p1.matches("^\\d{10}$"), 
            "Basic algorithm should generate only digits");
        
        // Test letters algorithm
        service.setAlgorithm("letters");
        String p2 = service.generatePassword(10);
        assertEquals(10, p2.length(), "Letters password should be length 10");
        assertTrue(p2.matches("^[A-Za-z]{10}$"), 
            "Letters algorithm should generate only letters");
        
        // Verify p1 and p2 have different character sets
        assertFalse(p1.matches("^[A-Za-z]+$"), 
            "Basic algorithm output should not match letters pattern");
        assertFalse(p2.matches("^\\d+$"), 
            "Letters algorithm output should not match digits pattern");
        
        // Test enhanced algorithm
        service.setAlgorithm("enhanced");
        String p3 = service.generatePassword(10);
        assertEquals(10, p3.length(), "Enhanced password should be length 10");
        assertTrue(p3.matches("^[A-Za-z0-9]{10}$"), 
            "Enhanced algorithm should generate alphanumeric characters");
        
        // Verify all are different (very high probability they will be different)
        assertNotEquals(p1, p2, 
            "Basic and Letters algorithms should produce different passwords");
        assertNotEquals(p2, p3, 
            "Letters and Enhanced algorithms should produce different passwords");
        assertNotEquals(p1, p3, 
            "Basic and Enhanced algorithms should produce different passwords");
    }
}
