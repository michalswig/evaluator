package com.mike.evaluator;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Expression expressionText = new Expression("customer.name == Adam");
        Expression expressionInteger = new Expression("customer.height >= 190");
        Expression expressionDateBirth = new Expression("customer.dateOfBirth > 1960-01-15");
        Expression expressionDateBirthTime = new Expression("customer.dateOfBirthTime > 1982-01-15 01:40");
        Evaluator evaluator = new AdviseSimpleEvaluator();
        Map<String, VariableValue> variables = new HashMap<>();
        variables.put("customer.name", new VariableValue("Adam", DataType.STRING));
        variables.put("customer.height", new VariableValue("183", DataType.INTEGER));
        variables.put("customer.dateOfBirth", new VariableValue("1978-01-01", DataType.DATE));
        variables.put("customer.dateOfBirthTime", new VariableValue("1978-01-01 00:01", DataType.DATE_TIME));
        Context context = new Context(variables);
        boolean evaluateText = evaluator.evaluate(context, expressionText);
        System.out.println("customer.name evaluate: " + evaluateText);
        boolean evaluateInteger = evaluator.evaluate(context, expressionInteger);
        System.out.println("customer.height evaluate: " + evaluateInteger);
        boolean evaluateDateBirth = evaluator.evaluate(context, expressionDateBirth);
        System.out.println("customer.dateOfBirth evaluate: " + evaluateDateBirth);
        boolean evaluateDateBirthTime = evaluator.evaluate(context, expressionDateBirthTime);
        System.out.println("customer.dateOfBirthTime evaluate: " + evaluateDateBirthTime);


    }

}