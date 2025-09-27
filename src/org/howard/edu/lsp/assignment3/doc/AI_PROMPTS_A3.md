# AI Usage Transcript - Assignment 3

## Prompt 1:
"how can i change my monolithic ETL pipeline in assignment 2 into object-oriented classes following single responsibility principle?"

## AI Response Excerpt:
"Consider creating separate classes for each major responsibility: a Product class to encapsulate data, CSVExtractor for file reading, ProductTransformer for business logic, and CSVLoader for file writing. Use an orchestrator class to coordinate the process."

## How I Used This:
I created the exact class structure suggested but customized the Product class to include transformation methods and ensured the transformation order matches Assignment 2 requirements.

## Prompt 2:
"What OO principles should I demonstrate in an ETL pipeline redesign?"

## AI Response Excerpt:
"Focus on encapsulation (data hiding), single responsibility (each class has one purpose), composition (orchestrator uses components), and meaningful method names that reveal intent."

## How I Used This:
I applied encapsulation by making Product fields private with getters/setters, gave each class a single clear responsibility, and used composition in ETLOrchestrator.

## Prompt 3:
"how can I ensure my object-oriented version produces identical output to assignment 2s version"

## AI Response Excerpt:
"Maintain the exact same transformation sequence: 1) uppercase names, 2) apply discounts, 3) recategorize, 4) calculate price ranges. Test with the same input data and compare output files."

## How I Used This:
I preserved the exact transformation order and tested both versions to ensure identical CSV output.