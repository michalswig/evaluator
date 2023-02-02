package pl.com.mike.evaluator.convert;


import java.util.*;

import static pl.com.mike.evaluator.convert.Associativity.LEFT;
import static pl.com.mike.evaluator.convert.Associativity.RIGHT;

public class RPNConvert {

    final static Map<String, Operators> OPERATORS = new HashMap<>();
    public static final String LEFT_PARENTHESIS = "(";
    public static final String RIGHT_PARENTHESIS = ")";

    static {
        for (Operators operator : Operators.values()) {
            OPERATORS.put(operator.symbol, operator);
        }
    }

    public static String[] convertInfixToRPN(List<String> tokens) {
        List<String> output = new LinkedList<>();
        Stack<String> stack = new Stack<>();
        for (String token : tokens) {
            if (OPERATORS.containsKey(token)) {
                while (!stack.isEmpty() && OPERATORS.containsKey(stack.peek())) {
                    Operators currentOperator = OPERATORS.get(token);
                    Operators topOperatorFromTheStack = OPERATORS.get(stack.peek());
                    if ((currentOperator.associativity == LEFT && currentOperator.comparePrecedence(topOperatorFromTheStack) <= 0) ||
                            (currentOperator.associativity == RIGHT && currentOperator.comparePrecedence(topOperatorFromTheStack) < 0)) {
                        output.add(stack.pop());
                        continue;
                    }
                    break;
                }
                stack.push(token);
            } else if (LEFT_PARENTHESIS.equals(token)) {
                stack.push(token);
            } else if (RIGHT_PARENTHESIS.equals(token)) {
                while (!stack.isEmpty() && !stack.peek().equals(LEFT_PARENTHESIS)) {
                    output.add(stack.pop());
                }
                stack.pop();
            } else {
                output.add(token);
            }
        }
        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }
        return output.toArray(new String[0]);
    }
}
