# Reflection: Assignment 2 vs Assignment 3

## Design Differences

**Assignment 2 (Procedural Approach):**
- Single monolithic class (`ETLPipeline`) with all logic
- Methods handled specific tasks but shared state through parameters
- Data represented as String arrays
- Linear execution flow in main method

**Assignment 3 (Object-Oriented Approach):**
- Multiple specialized classes with clear responsibilities
- `Product` class encapsulates data and behavior
- Separate classes for extraction, transformation, and loading
- `ETLOrchestrator` coordinates the process through composition

## Object-Oriented Principles Applied

**Encapsulation:**
- `Product` class encapsulates all product data and transformation logic
- Private fields with public getters/setters
- Internal implementation details hidden from other classes

**Single Responsibility Principle:**
- `CSVExtractor`: Only handles file reading and parsing
- `ProductTransformer`: Only handles business logic transformations  
- `CSVLoader`: Only handles file writing
- `ETLOrchestrator`: Only coordinates the process

**Composition:**
- `ETLOrchestrator` composes smaller components rather than inheriting
- Each component can be tested and modified independently

**Abstraction:**
- Complex file I/O and transformation logic abstracted behind simple interfaces
- Main method only needs to create orchestrator and call `execute()`

## Testing and Verification

To ensure Assignment 3 produces identical output to Assignment 2:

1. **Same Input Data**: Used identical `data/products.csv`
2. **Transformation Order**: Maintained exact sequence: uppercase → discount → recategorize → price range
3. **Output Comparison**: Both versions produce byte-for-byte identical `data/transformed_products.csv`
4. **Error Handling**: Both handle missing files and empty input identically
5. **Edge Cases**: Tested with boundary prices ($10, $100, $500) to verify rounding and categorization

## Benefits of Object-Oriented Design

- **Maintainability**: Easier to modify individual components
- **Testability**: Each class can be unit tested independently
- **Reusability**: `Product` and `CSVExtractor` could be reused in other contexts
- **Readability**: Clear separation of concerns makes code easier to understand
- **Extensibility**: New transformations can be added without modifying existing classes

The object-oriented design provides a more suitable and flexible foundation while maintaining identical external behavior.

## Conclusion
The redesign from Assignment 2 to Assignment 3 represents a basic shift from procedural scripting to true object-oriented engineering. While both implementations produce identical results, the object-oriented version provides a more robust foundation for maintenance, testing, and future extension. The decomposition into specialized classes with clearly specified responsibilities makes the code more self documenting and reduces the cognitive load required to understand or modify the system.

This exercise demonstrates that object-oriented design isn't just about organizing code but about creating systems that are more understandable, maintainable, and reliable while solving the same business problem.