package pl.com.mike.evaluator.convert;

import pl.com.mike.evaluator.EvaluationException;

public enum Operators implements Comparable<Operators> {

    ADDITION("+", Associativity.RIGHT, 0),
    SUBTRACTION("-", Associativity.RIGHT, 0),
    DIVISION("/", Associativity.LEFT, 5),
    MULTIPLICATION("*", Associativity.LEFT, 5),
    MODULUS("%", Associativity.LEFT, 5),
    POWER("^", Associativity.LEFT, 10),
    LOGICAL_OR("||", Associativity.LEFT, 0),
    LOGICAL_AND("&&", Associativity.LEFT, 5),
    EQUALITY("==", Associativity.LEFT, 10),
    NO_EQUALITY("!=", Associativity.LEFT, 10),
    MORE(">", Associativity.LEFT, 15),
    MORE_EQUAL(">=", Associativity.LEFT, 15),
    LESS("<", Associativity.LEFT, 15),
    LESS_EQUAL("<=", Associativity.LEFT, 15);


    final Associativity associativity;
    final int precedence;
    final String symbol;

    Operators(String symbol, Associativity associativity, int precedence) {
        this.symbol = symbol;
        this.associativity = associativity;
        this.precedence = precedence;
    }

    public int comparePrecedence(Operators operator) {
        return this.precedence - operator.precedence;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Operators prepareOperator(String token) {
        for (Operators value : Operators.values()) {
            if (value.getSymbol().equals(token)) {
                return value;
            }
        }
        throw new EvaluationException("No supported operator: " + token);
    }

}


