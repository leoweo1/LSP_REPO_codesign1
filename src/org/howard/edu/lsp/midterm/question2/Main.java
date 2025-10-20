package org.howard.edu.lsp.midterm.question2;

public class Main {
    public static void main(String[] args) {
        // invoke all methods statically to produce the required output
        System.out.println("Circle radius 3.0 → area = " + AreaCalculator.area(3.0));
        System.out.println("Rectangle 5.0 x 2.0 → area = " + AreaCalculator.area(5.0, 2.0));
        System.out.println("Triangle base 10, height 6 → area = " + AreaCalculator.area(10, 6));
        System.out.println("Square side 4 → area = " + AreaCalculator.area(4));

        // invoke a method with an illegal argument to demonstrate exception handling
        System.out.println("\n== Exception test ==");
        try {
            double invalidArea = AreaCalculator.area(-1.0);
            System.out.println("This line should not be printed.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /*
     * explanation: overloading is the better design choice in this context.
     * all methods perform the same fundamental operation: calculating an area.
     * using the same method name, `area`, improves code readability and
     * provides a consistent, intuitive API. The compiler differentiates
     * them based on the parameter list. Using different names like
     * `rectangleArea` and `circleArea` would be redundant and less elegant.
     */
}