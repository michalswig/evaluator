package com.mike.evaluator;

import java.util.Map;

public class AdviseSimpleEvaluator implements Evaluator {

    @Override
    public boolean evaluate(Context context, Expression expression) {
        validate(context.getVariables(), expression);
        return false;

    }

    private static void validate(Map<String, VariableValue> variables, Expression expression) {
        if (variables.isEmpty()) {
            throw new EvaluationException("No variables found");
        }
        if (expression == null) {
            throw new EvaluationException("No expression found");
        } else {
            if (expression.getValue() == null) {
                throw new EvaluationException("No expression value found");
            }
        }
    }

}
