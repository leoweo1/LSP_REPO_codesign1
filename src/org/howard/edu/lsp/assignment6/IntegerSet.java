package org.howard.edu.lsp.assignment6;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a mathematical set of integers with standard set operations.
 * This class uses an ArrayList to store elements and ensures no duplicates.
 */
public class IntegerSet {
    private List<Integer> set = new ArrayList<Integer>();

    /**
     * Clears the internal representation of the set.
     */
    public void clear() {
        set.clear();
    }

    /**
     * Returns the number of elements in the set.
     * 
     * @return the number of elements in the set
     */
    public int length() {
        return set.size();
    }

    /**
     * Returns true if the 2 sets are equal, false otherwise.
     * Two sets are equal if they contain all of the same values in ANY order.
     * This overrides the equals method from the Object class.
     * 
     * @param o the object to compare with
     * @return true if the sets are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        IntegerSet otherSet = (IntegerSet) o;
        
        // If sizes are different, sets can't be equal
        if (this.length() != otherSet.length()) {
            return false;
        }
        
        // Check if all elements in this set are contained in the other set
        for (Integer element : set) {
            if (!otherSet.contains(element)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if the set contains the value, otherwise false.
     * 
     * @param value the value to check for membership
     * @return true if the set contains the value, false otherwise
     */
    public boolean contains(int value) {
        return set.contains(value);
    }

    /**
     * Returns the largest item in the set.
     * 
     * @return the largest item in the set
     * @throws IllegalStateException if the set is empty
     */
    public int largest() {
        if (isEmpty()) {
            throw new IllegalStateException("Set is empty");
        }
        
        int max = set.get(0);
        for (int i = 1; i < set.size(); i++) {
            if (set.get(i) > max) {
                max = set.get(i);
            }
        }
        return max;
    }

    /**
     * Returns the smallest item in the set.
     * 
     * @return the smallest item in the set
     * @throws IllegalStateException if the set is empty
     */
    public int smallest() {
        if (isEmpty()) {
            throw new IllegalStateException("Set is empty");
        }
        
        int min = set.get(0);
        for (int i = 1; i < set.size(); i++) {
            if (set.get(i) < min) {
                min = set.get(i);
            }
        }
        return min;
    }

    /**
     * Adds an item to the set or does nothing if already present.
     * 
     * @param item the item to add to the set
     */
    public void add(int item) {
        if (!set.contains(item)) {
            set.add(item);
        }
    }

    /**
     * Removes an item from the set or does nothing if not there.
     * 
     * @param item the item to remove from the set
     */
    public void remove(int item) {
        // Using Integer.valueOf to avoid removing by index
        set.remove(Integer.valueOf(item));
    }

    /**
     * Set union: modifies this to contain all unique elements in this or other.
     * 
     * @param other the other IntegerSet to union with
     */
    public void union(IntegerSet other) {
        for (int i = 0; i < other.length(); i++) {
            int element = other.set.get(i);
            if (!this.contains(element)) {
                this.add(element);
            }
        }
    }

    /**
     * Set intersection: modifies this to contain only elements in both sets.
     * 
     * @param other the other IntegerSet to intersect with
     */
    public void intersect(IntegerSet other) {
        List<Integer> intersection = new ArrayList<>();
        for (Integer element : set) {
            if (other.contains(element)) {
                intersection.add(element);
            }
        }
        set = intersection;
    }

    /**
     * Set difference (this \ other): modifies this to remove elements found in other.
     * 
     * @param other the other IntegerSet to subtract
     */
    public void diff(IntegerSet other) {
        for (int i = 0; i < other.length(); i++) {
            int element = other.set.get(i);
            this.remove(element);
        }
    }

    /**
     * Set complement: modifies this to become (other \ this).
     * 
     * @param other the other IntegerSet to use for complement
     */
    public void complement(IntegerSet other) {
        List<Integer> complement = new ArrayList<>();
        for (int i = 0; i < other.length(); i++) {
            int element = other.set.get(i);
            if (!this.contains(element)) {
                complement.add(element);
            }
        }
        set = complement;
    }

    /**
     * Returns true if the set is empty, false otherwise.
     * 
     * @return true if the set is empty, false otherwise
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * Returns a String representation of the set.
     * Overrides Object.toString().
     * 
     * @return string representation of the set in format [1, 2, 3]
     */
    @Override
    public String toString() {
        return set.toString();
    }
}