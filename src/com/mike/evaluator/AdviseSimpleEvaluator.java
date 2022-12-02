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
        String keyExpression = valuesExpression[0].trim();
        String operator = valuesExpression[1].trim();
        String valueExpression = valuesExpression[2].trim();

        Set<String> keys = variables.keySet();
        for (String key : keys) {

            if(key.equals(keyExpression)){
                //STRINGS
                if(operator.equals(Operator.EQUAL.getOperator()) && variables.get(key).getDataType().equals(DataType.STRING) && valueExpression.equals(variables.get(key).getValue())){
                    return true;
                }
                //INTEGERS
                if(operator.equals(Operator.EQUAL.getOperator()) && variables.get(key).getDataType().equals(DataType.INTEGER) && (Integer.parseInt(valueExpression) == Integer.parseInt(variables.get(key).getValue()))){
                    return true;
                }
                if(operator.equals(Operator.MORE.getOperator()) && variables.get(key).getDataType().equals(DataType.INTEGER) && (Integer.parseInt(valueExpression) > Integer.parseInt(variables.get(key).getValue()))){
                    return true;
                }
                if(operator.equals(Operator.LESS.getOperator()) && variables.get(key).getDataType().equals(DataType.INTEGER) && (Integer.parseInt(valueExpression) < Integer.parseInt(variables.get(key).getValue()))){
                    return true;
                }
                if(operator.equals(Operator.MORE_EQUAL.getOperator()) && variables.get(key).getDataType().equals(DataType.INTEGER) && (Integer.parseInt(valueExpression) >= Integer.parseInt(variables.get(key).getValue()))){
                    return true;
                }
                if(operator.equals(Operator.LESS_EQUAL.getOperator()) && variables.get(key).getDataType().equals(DataType.INTEGER) && (Integer.parseInt(valueExpression) <= Integer.parseInt(variables.get(key).getValue()))){
                    return true;
                }
                if(operator.equals(Operator.NOT_EQUAL.getOperator()) && variables.get(key).getDataType().equals(DataType.INTEGER) && (Integer.parseInt(valueExpression) != Integer.parseInt(variables.get(key).getValue()))){
                    return true;
                }
                //LOCALDATE
                if(operator.equals(Operator.EQUAL.getOperator()) && variables.get(key).getDataType().equals(DataType.DATE) && (LocalDate.parse(valueExpression, localDateFormatter).isEqual(LocalDate.parse(variables.get(key).getValue())))){
                    return true;
                }
                if(operator.equals(Operator.NOT_EQUAL.getOperator()) && variables.get(key).getDataType().equals(DataType.DATE) && !LocalDate.parse(valueExpression, localDateFormatter).isEqual(LocalDate.parse(variables.get(key).getValue()))){
                    return true;
                }
                if(operator.equals(Operator.MORE.getOperator()) && variables.get(key).getDataType().equals(DataType.DATE) && (LocalDate.parse(valueExpression, localDateFormatter).isAfter(LocalDate.parse(variables.get(key).getValue())))){
                    return true;
                }
                if(operator.equals(Operator.LESS.getOperator()) && variables.get(key).getDataType().equals(DataType.DATE) && (LocalDate.parse(valueExpression, localDateFormatter).isBefore(LocalDate.parse(variables.get(key).getValue())))){
                    return true;
                }
                //LOCALDATETIME
                if(operator.equals(Operator.EQUAL.getOperator()) && variables.get(key).getDataType().equals(DataType.DATE) && (LocalDateTime.parse(valueExpression, localDateTimeFormatter).isEqual(LocalDateTime.parse(variables.get(key).getValue())))){
                    return true;
                }
                if(operator.equals(Operator.NOT_EQUAL.getOperator()) && variables.get(key).getDataType().equals(DataType.DATE) && !LocalDateTime.parse(valueExpression, localDateTimeFormatter).isEqual(LocalDateTime.parse(variables.get(key).getValue()))){
                    return true;
                }
                if(operator.equals(Operator.MORE.getOperator()) && variables.get(key).getDataType().equals(DataType.DATE) && (LocalDateTime.parse(valueExpression, localDateTimeFormatter).isAfter(LocalDateTime.parse(variables.get(key).getValue())))){
                    return true;
                }
                if(operator.equals(Operator.LESS.getOperator()) && variables.get(key).getDataType().equals(DataType.DATE) && (LocalDateTime.parse(valueExpression, localDateTimeFormatter).isBefore(LocalDateTime.parse(variables.get(key).getValue())))){
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
