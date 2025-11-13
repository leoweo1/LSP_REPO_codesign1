package org.howard.edu.lsp.assignment6;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test class for IntegerSet implementation.
 * Tests all public methods with various scenarios including edge cases.
 */
public class IntegerSetTest {
    private IntegerSet set1;
    private IntegerSet set2;
    private IntegerSet emptySet;

    /**
     * Set up test fixtures before each test method.
     */
    @Before
    public void setUp() {
        set1 = new IntegerSet();
        set2 = new IntegerSet();
        emptySet = new IntegerSet();
        
        // Initialize set1 with values 1, 2, 3
        set1.add(1);
        set1.add(2);
        set1.add(3);
        
        // Initialize set2 with values 2, 3, 4
        set2.add(2);
        set2.add(3);
        set2.add(4);
    }

    @Test
    public void testClear() {
        assertFalse(set1.isEmpty());
        set1.clear();
        assertTrue(set1.isEmpty());
        assertEquals(0, set1.length());
    }

    @Test
    public void testLength() {
        assertEquals(3, set1.length());
        assertEquals(0, emptySet.length());
        
        set1.add(4);
        assertEquals(4, set1.length());
        
        set1.remove(1);
        assertEquals(3, set1.length());
    }

    @Test
    public void testEquals() {
        IntegerSet set3 = new IntegerSet();
        set3.add(1);
        set3.add(2);
        set3.add(3);
        
        // Same elements, different order
        IntegerSet set4 = new IntegerSet();
        set4.add(3);
        set4.add(1);
        set4.add(2);
        
        assertTrue(set1.equals(set3));
        assertTrue(set1.equals(set4)); // Order shouldn't matter
        assertFalse(set1.equals(set2));
        assertFalse(set1.equals(emptySet));
        
        // Test with non-IntegerSet object
        assertFalse(set1.equals("not a set"));
        assertFalse(set1.equals(null));
    }

    @Test
    public void testContains() {
        assertTrue(set1.contains(1));
        assertTrue(set1.contains(2));
        assertTrue(set1.contains(3));
        assertFalse(set1.contains(4));
        assertFalse(emptySet.contains(1));
    }

    @Test
    public void testLargest() {
        assertEquals(3, set1.largest());
        
        set1.add(5);
        assertEquals(5, set1.largest());
    }

    @Test(expected = IllegalStateException.class)
    public void testLargestEmptySet() {
        emptySet.largest();
    }

    @Test
    public void testSmallest() {
        assertEquals(1, set1.smallest());
        
        set1.add(0);
        assertEquals(0, set1.smallest());
    }

    @Test(expected = IllegalStateException.class)
    public void testSmallestEmptySet() {
        emptySet.smallest();
    }

    @Test
    public void testAdd() {
        assertEquals(3, set1.length());
        set1.add(4); // New element
        assertEquals(4, set1.length());
        assertTrue(set1.contains(4));
        
        set1.add(2); // Duplicate element
        assertEquals(4, set1.length()); // Size shouldn't change
    }

    @Test
    public void testRemove() {
        assertEquals(3, set1.length());
        set1.remove(2); // Existing element
        assertEquals(2, set1.length());
        assertFalse(set1.contains(2));
        
        set1.remove(5); // Non-existing element
        assertEquals(2, set1.length()); // Size shouldn't change
    }

    @Test
    public void testUnion() {
        set1.union(set2);
        assertEquals(4, set1.length());
        assertTrue(set1.contains(1));
        assertTrue(set1.contains(2));
        assertTrue(set1.contains(3));
        assertTrue(set1.contains(4));
        
        // Union with empty set
        IntegerSet testSet = new IntegerSet();
        testSet.add(5);
        testSet.add(6);
        emptySet.union(testSet);
        assertEquals(2, emptySet.length());
        assertTrue(emptySet.contains(5));
        assertTrue(emptySet.contains(6));
    }

    @Test
    public void testIntersect() {
        set1.intersect(set2);
        assertEquals(2, set1.length());
        assertTrue(set1.contains(2));
        assertTrue(set1.contains(3));
        assertFalse(set1.contains(1));
        assertFalse(set1.contains(4));
        
        // Intersect with empty set
        set1.intersect(emptySet);
        assertTrue(set1.isEmpty());
    }

    @Test
    public void testDiff() {
        set1.diff(set2);
        assertEquals(1, set1.length());
        assertTrue(set1.contains(1));
        assertFalse(set1.contains(2));
        assertFalse(set1.contains(3));
        
        // Diff with empty set
        IntegerSet testSet = new IntegerSet();
        testSet.add(1);
        testSet.add(2);
        testSet.diff(emptySet);
        assertEquals(2, testSet.length());
    }

    @Test
    public void testComplement() {
        set1.complement(set2);
        assertEquals(1, set1.length());
        assertTrue(set1.contains(4));
        assertFalse(set1.contains(1));
        assertFalse(set1.contains(2));
        assertFalse(set1.contains(3));
        
        // Complement where this set is empty
        emptySet.complement(set2);
        assertEquals(3, emptySet.length());
        assertTrue(emptySet.contains(2));
        assertTrue(emptySet.contains(3));
        assertTrue(emptySet.contains(4));
    }

    @Test
    public void testIsEmpty() {
        assertFalse(set1.isEmpty());
        assertTrue(emptySet.isEmpty());
        
        set1.clear();
        assertTrue(set1.isEmpty());
        
        emptySet.add(1);
        assertFalse(emptySet.isEmpty());
    }

    @Test
    public void testToString() {
        String result = set1.toString();
        assertTrue(result.contains("1"));
        assertTrue(result.contains("2"));
        assertTrue(result.contains("3"));
        
        assertEquals("[]", emptySet.toString());
    }

    @Test
    public void testDuplicateHandling() {
        IntegerSet testSet = new IntegerSet();
        testSet.add(1);
        testSet.add(1);
        testSet.add(1);
        assertEquals(1, testSet.length());
        
        testSet.add(2);
        testSet.add(2);
        assertEquals(2, testSet.length());
    }
}