package com.mike.evaluator;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Expression expressionText = new Expression("customer.name == Adam");
        Expression expressionInteger = new Expression("customer.height <= 178");
        Expression expressionDateBirth = new Expression("customer.dateOfBirth > 1960-01-15");
        Expression expressionDateBirthTime = new Expression("customer.dateOfBirthTime < 1982-01-15 01:40");
        Evaluator evaluator = new AdviseSimpleEvaluator();
        Map<String, VariableValue> variables = new HashMap<>();
        variables.put("customer.name", new VariableValue("Wojtek", DataType.STRING));
        variables.put("customer.height", new VariableValue("170", DataType.INTEGER));
        variables.put("customer.dateOfBirth", new VariableValue("1980-01-01", DataType.DATE));
        variables.put("customer.dateOfBirthTime", new VariableValue("1982-01-15 01:40", DataType.DATE_TIME));

        Context context = new Context(variables);
        boolean evaluateText = evaluator.evaluate(context, expressionText);
        System.out.println(expressionText  + " and value: " + "'" + variables.get("customer.name").getValue() + "' is " + evaluateText);
        boolean evaluateInteger = evaluator.evaluate(context, expressionInteger);
        System.out.println(expressionInteger  + " and value: " + "'" + variables.get("customer.height").getValue() + "' is " + evaluateInteger);
        boolean evaluateDateBirth = evaluator.evaluate(context, expressionDateBirth);
        System.out.println(expressionDateBirth  + " and date: " + "'" + variables.get("customer.dateOfBirth").getValue() + "' is " + evaluateDateBirth);
        boolean evaluateDateBirthTime = evaluator.evaluate(context, expressionDateBirthTime);
        System.out.println(expressionDateBirthTime  + " and date: " + "'" + variables.get("customer.dateOfBirthTime").getValue() + "' is " + evaluateDateBirthTime);


    }



}