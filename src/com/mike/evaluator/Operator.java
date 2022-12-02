package com.mike.evaluator;

public enum Operator {
    MORE(">"),
    LESS("<"),
    MORE_EQUAL(">="),
    LESS_EQUAL("<="),
    EQUAL("=="),
    NOT_EQUAL("!="),
    END("&&"),
    OR("||");


    private String operator;

    Operator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
