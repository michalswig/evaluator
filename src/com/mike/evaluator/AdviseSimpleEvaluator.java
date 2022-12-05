package com.mike.evaluator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

public class AdviseSimpleEvaluator implements Evaluator {

    @Override
    public boolean evaluate(Context context, Expression expression) {

        DateTimeFormatter localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Map<String, VariableValue> variables = context.getVariables();
        validate(variables, expression);

        String value = expression.getValue();

        String[] valuesExpression = value.trim().split(" ");
        String keyExpression;
        String operator;

        String valueExpression;
        if (valuesExpression.length > 3) {
            keyExpression = valuesExpression[0].trim();
            operator = valuesExpression[1].trim();
            valueExpression = valuesExpression[2].trim() + " " + valuesExpression[3].trim();
        } else {
            keyExpression = valuesExpression[0].trim();
            operator = valuesExpression[1].trim();
            valueExpression = valuesExpression[2].trim();
        }


        Set<String> keys = variables.keySet();
        for (String key : keys) {

            if (key.equals(keyExpression)) {
                //STRINGS
                if (operator.equals(Operator.EQUAL.getOperator()) && variables.get(key).getDataType().equals(DataType.STRING) && valueExpression.equals(variables.get(key).getValue())) {
                    return true;
                }
                //INTEGERS
                if (operator.equals(Operator.EQUAL.getOperator()) && variables.get(key).getDataType().equals(DataType.INTEGER) && (Integer.parseInt(variables.get(key).getValue()) == Integer.parseInt(valueExpression))) {
                    return true;
                }
                if (operator.equals(Operator.MORE.getOperator()) && variables.get(key).getDataType().equals(DataType.INTEGER) && (Integer.parseInt(variables.get(key).getValue()) > Integer.parseInt(valueExpression))) {
                    return true;
                }
                if (operator.equals(Operator.LESS.getOperator()) && variables.get(key).getDataType().equals(DataType.INTEGER) && (Integer.parseInt(variables.get(key).getValue()) < Integer.parseInt(valueExpression))) {
                    return true;
                }
                if (operator.equals(Operator.MORE_EQUAL.getOperator()) && variables.get(key).getDataType().equals(DataType.INTEGER) && (Integer.parseInt(variables.get(key).getValue()) >= Integer.parseInt(valueExpression))) {
                    return true;
                }
                if (operator.equals(Operator.LESS_EQUAL.getOperator()) && variables.get(key).getDataType().equals(DataType.INTEGER) && (Integer.parseInt(variables.get(key).getValue()) >= Integer.parseInt(valueExpression))) {
                    return true;
                }
                if (operator.equals(Operator.NOT_EQUAL.getOperator()) && variables.get(key).getDataType().equals(DataType.INTEGER) && (Integer.parseInt(variables.get(key).getValue()) != Integer.parseInt(valueExpression))) {
                    return true;
                }
                //LOCALDATE
                if (operator.equals(Operator.EQUAL.getOperator()) && variables.get(key).getDataType().equals(DataType.DATE) && (LocalDate.parse(variables.get(key).getValue(), localDateFormatter)).isEqual(LocalDate.parse(valueExpression, localDateFormatter))) {
                    return true;
                }
                if (operator.equals(Operator.NOT_EQUAL.getOperator()) && variables.get(key).getDataType().equals(DataType.DATE) && !((LocalDate.parse(variables.get(key).getValue(), localDateFormatter)).isEqual(LocalDate.parse(valueExpression, localDateFormatter)))) {
                    return true;
                }
                if (operator.equals(Operator.MORE.getOperator()) && variables.get(key).getDataType().equals(DataType.DATE) && (LocalDate.parse(variables.get(key).getValue(), localDateFormatter)).isAfter(LocalDate.parse(valueExpression, localDateFormatter))) {
                    return true;
                }
                if (operator.equals(Operator.LESS.getOperator()) && variables.get(key).getDataType().equals(DataType.DATE) && (LocalDate.parse(variables.get(key).getValue(), localDateFormatter)).isBefore(LocalDate.parse(valueExpression, localDateFormatter))) {
                    return true;
                }
                //LOCALDATETIME
                if (operator.equals(Operator.EQUAL.getOperator()) && variables.get(key).getDataType().equals(DataType.DATE_TIME) && ((LocalDateTime.parse(variables.get(key).getValue(), localDateTimeFormatter)).isEqual(LocalDateTime.parse(valueExpression, localDateTimeFormatter)))) {
                    return true;
                }
                if (operator.equals(Operator.NOT_EQUAL.getOperator()) && variables.get(key).getDataType().equals(DataType.DATE_TIME) && !((LocalDateTime.parse(variables.get(key).getValue(), localDateTimeFormatter)).isEqual(LocalDateTime.parse(valueExpression, localDateTimeFormatter)))) {
                    return true;
                }
                if (operator.equals(Operator.MORE.getOperator()) && variables.get(key).getDataType().equals(DataType.DATE_TIME) && ((LocalDateTime.parse(variables.get(key).getValue(), localDateTimeFormatter)).isAfter(LocalDateTime.parse(valueExpression, localDateTimeFormatter)))) {
                    return true;
                }
                if (operator.equals(Operator.LESS.getOperator()) && variables.get(key).getDataType().equals(DataType.DATE_TIME) && ((LocalDateTime.parse(variables.get(key).getValue(), localDateTimeFormatter)).isBefore(LocalDateTime.parse(valueExpression, localDateTimeFormatter)))) {
                    return true;
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
