# LogicParser
Parser and Expression Evaluator for Logic Statements:

The steps behind the whole process of evaluating a given logic statement encompass:
- Parse the text file including the statement into a stream of tokens defined via an enum, by applying a lexing algorithm;
- Extract the operands from the token stream into a list, sort them, remove duplicates and assign each operand token a unique id via a map;
- Generate every possible boolean cartesian product of length equal to the number of operands and map every generated sequence with the operandsMap into a wrapper class(LogicConfiguration);
- Use an expression evaluation algorithm to get the result for every single logic configuration using an operator stack, an operand stack, the boolean sequences and the operand mapping;
- Extract the yielded results into a list and decide the nature of the logic statement.
