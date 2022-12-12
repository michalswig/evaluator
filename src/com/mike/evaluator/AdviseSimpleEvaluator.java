package com.mike.evaluator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Set;

public class AdviseSimpleEvaluator implements Evaluator {

    @Override
    public boolean evaluate(Context context, Expression expression) {
        try {
            DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            Map<String, VariableValue> variables = context.getVariables();
            validate(variables, expression);
            String[] expressions = getStrings(expression);
            String keyExpression = expressions[0].trim();
            String operator = expressions[1].trim();
            String valueExpression = expressions.length == 3 ? expressions[2].trim() : expressions[2].trim() + " " + expressions[3].trim();
            Set<String> keys = variables.keySet();
            for (String key : keys) {
                if (key.equals(keyExpression)) {
                    switch (variables.get(key).getDataType()) {
                        case INTEGER -> {
                            return matchContextInteger(variables, operator, valueExpression, key);
                        }
                        case DATE -> {
                            return matchContextDate(localDateFormatter, variables, operator, valueExpression, key);
                        }
                        case DATE_TIME -> {
                            return matchContextDateTime(localDateTimeFormatter, variables, operator, valueExpression, key);
                        }
                        case STRING -> {
                            return matchContextString(variables, operator, valueExpression, key);
                        }
                    }
                }
            }
            return false;
        } catch (DateTimeParseException | NumberFormatException e) {
            throw new EvaluationException(e.getMessage());
        }
    }

    private static String[] getStrings(Expression expression) {
        String value = expression.getValue();
        String[] expressions = value.trim().split(" ");
        return expressions;
    }

    private static boolean matchContextString(Map<String, VariableValue> variables, String operator, String valueExpression, String key) {
        if (operator.equals(Operator.EQUAL.getOperator()) && valueExpression.equals(variables.get(key).getValue()))
            return true;
        else {
            return false;
        }
    }

    private static boolean matchContextDateTime(DateTimeFormatter localDateTimeFormatter, Map<String, VariableValue> variables, String operator, String valueExpression, String key) {
        LocalDateTime expressionDateTime;
        LocalDateTime keyLocalDateTime;
        keyLocalDateTime = LocalDateTime.parse(variables.get(key).getValue(), localDateTimeFormatter);
        expressionDateTime = LocalDateTime.parse(valueExpression, localDateTimeFormatter);
        if (operator.equals(Operator.EQUAL.getOperator()) && keyLocalDateTime.isEqual(expressionDateTime))
            return true;
        else if (operator.equals(Operator.NOT_EQUAL.getOperator()) && !keyLocalDateTime.isEqual(expressionDateTime))
            return true;
        else if (operator.equals(Operator.MORE.getOperator()) && keyLocalDateTime.isAfter(expressionDateTime))
            return true;
        else if (operator.equals(Operator.LESS.getOperator()) && keyLocalDateTime.isBefore(expressionDateTime))
            return true;
        else {
            return false;
        }
    }

    private static boolean matchContextDate(DateTimeFormatter localDateFormatter, Map<String, VariableValue> variables, String operator, String valueExpression, String key) {
        LocalDate keyLocalDate;
        LocalDate expressionDate;
        keyLocalDate = LocalDate.parse(variables.get(key).getValue());
        expressionDate = LocalDate.parse(valueExpression, localDateFormatter);
        if (operator.equals(Operator.EQUAL.getOperator()) && keyLocalDate.isEqual(expressionDate))
            return true;
        else if (operator.equals(Operator.NOT_EQUAL.getOperator()) && !keyLocalDate.isEqual(expressionDate))
            return true;
        else if (operator.equals(Operator.MORE.getOperator()) && keyLocalDate.isAfter(expressionDate))
            return true;
        else if (operator.equals(Operator.LESS.getOperator()) && keyLocalDate.isBefore(expressionDate))
            return true;
        else {
            return false;
        }
    }

    private static boolean matchContextInteger(Map<String, VariableValue> variables, String operator, String valueExpression, String key) {
        int keyVariable;
        int valueExpressionData;
        keyVariable = Integer.parseInt(variables.get(key).getValue());
        valueExpressionData = Integer.parseInt(valueExpression);
        if (operator.equals(Operator.EQUAL.getOperator()) && keyVariable == valueExpressionData)
            return true;
        else if (operator.equals(Operator.MORE.getOperator()) && keyVariable > valueExpressionData)
            return true;
        else if (operator.equals(Operator.LESS.getOperator()) && keyVariable < valueExpressionData)
            return true;
        else if (operator.equals(Operator.MORE_EQUAL.getOperator()) && keyVariable >= valueExpressionData)
            return true;
        else if (operator.equals(Operator.LESS_EQUAL.getOperator()) && keyVariable <= valueExpressionData)
            return true;
        else if (operator.equals(Operator.NOT_EQUAL.getOperator()) && keyVariable != valueExpressionData)
            return true;
        else {
            return false;
        }
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
