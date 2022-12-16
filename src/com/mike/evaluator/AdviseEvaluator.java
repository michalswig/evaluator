package com.mike.evaluator;

public class AdviseEvaluator implements Evaluator {

    private final AdviseSimpleEvaluator adviseSimpleEvaluator = new AdviseSimpleEvaluator();

    @Override
    public boolean evaluate(Context context, Expression expression) {
        String[] values = getStrings(expression);
        return evaluateTwoBooleans(
                adviseSimpleEvaluator.evaluate(context, new Expression(values[0] + " " + values[1] + " " + values[2])),
                adviseSimpleEvaluator.evaluate(context, new Expression(values[4] + " " + values[5] + " " + values[6])),
                values[3]
        );
    }

    private static String[] getStrings(Expression expression) {
        return expression.getValue().trim().split(" ");
    }

    private static Boolean evaluateTwoBooleans(Boolean first, Boolean second, String operator) {
        if (operator.equals("&&") && (first && second)) {
            return true;
        } else if (operator.equals("||") && (first || second)) {
            return true;
        }
        return false;
    }
}
