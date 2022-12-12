package com.mike.evaluator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Set;

import static com.mike.evaluator.DataType.INTEGER;

public class AdviseSimpleEvaluator implements Evaluator {

    @Override
    public boolean evaluate(Context context, Expression expression) {

        DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Map<String, VariableValue> variables = context.getVariables();

        validate(variables, expression);

        String value = expression.getValue();

        String[] expressions = value.trim().split(" ");
        String keyExpression;
        String operator;
        String valueExpression;

        if (expressions.length == 3) {
            keyExpression = expressions[0].trim();
            operator = expressions[1].trim();
            valueExpression = expressions[2].trim();
        } else {
            keyExpression = expressions[0].trim();
            operator = expressions[1].trim();
            valueExpression = expressions[2].trim() + " " + expressions[3].trim();
        }

        Set<String> keys = variables.keySet();
        for (String key : keys) {
            if (key.equals(keyExpression)) {
                int keyVariable = 0;
                int valueExpressionData = 0;
                LocalDate keyLocalDate;
                LocalDate expressionDate;
                LocalDateTime keyLocalDateTime;
                LocalDateTime expressionDateTime;
                DataType[] values = DataType.values();
                DataType dataTypeByKey = variables.get(key).getDataType();
                for (DataType dataType : values) {
                    switch (dataTypeByKey) {
                        case INTEGER -> {
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
                        case DATE -> {
                            keyLocalDate = LocalDate.parse(variables.get(key).getValue());
                            expressionDate = LocalDate.parse(valueExpression, localDateFormatter);
                            if (operator.equals(Operator.EQUAL.getOperator()) && keyLocalDate.isEqual(expressionDate))
                                return true;
                            else if (operator.equals(Operator.NOT_EQUAL.getOperator()) && !keyLocalDate.isEqual(expressionDate))
                                return true;
                            else if (operator.equals(Operator.MORE.getOperator()) && !keyLocalDate.isAfter(expressionDate))
                                return true;
                            else if (operator.equals(Operator.LESS.getOperator()) && !keyLocalDate.isBefore(expressionDate))
                                return true;
                            else {
                                return false;
                            }
                        }
                        case DATE_TIME -> {
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
                        case STRING -> {
                            if (operator.equals(Operator.EQUAL.getOperator()) && valueExpression.equals(variables.get(key).getValue()))
                                return true;
                            else {
                                return false;
                            }
                        }
                    }
                }
            }
        }
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
