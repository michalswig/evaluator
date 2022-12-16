package com.mike.evaluator;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Expression expressionText = new Expression("customer.name == Adam");
        Expression expressionTextTwo = new Expression("customer.name == Wojtek");

        Expression expressionInteger = new Expression("customer.height <= 169");
        Expression expressionIntegerTwo = new Expression("customer.height <= 170");
        Expression expressionIntegerThree = new Expression("customer.height <= 171");

        Expression expressionDateBirth = new Expression("customer.dateOfBirth > 1960-01-15");
        Expression expressionDateBirthTime = new Expression("customer.dateOfBirthTime < 1982-01-15 01:40");

        Evaluator evaluator = new AdviseSimpleEvaluator();
        Map<String, VariableValue> variables = new HashMap<>();
        variables.put("customer.name", new VariableValue("Wojtek", DataType.STRING));
        variables.put("customer.height", new VariableValue("170", DataType.INTEGER));
        variables.put("customer.dateOfBirth", new VariableValue("1980-01-01", DataType.DATE));
        variables.put("customer.dateOfBirthTime", new VariableValue("2023-01-15 01:40", DataType.DATE_TIME));

        Context context = new Context(variables);

        boolean evaluateText = evaluator.evaluate(context, expressionText);
        System.out.println(expressionText + ",  value: " + "'" + variables.get("customer.name").getValue() + "' is " + evaluateText);
        boolean evaluateTextTwo = evaluator.evaluate(context, expressionTextTwo);
        System.out.println(expressionTextTwo + ",  value: " + "'" + variables.get("customer.name").getValue() + "' is " + evaluateTextTwo);

        boolean evaluateInteger = evaluator.evaluate(context, expressionInteger);
        System.out.println(expressionInteger + ", value: " + "'" + variables.get("customer.height").getValue() + "' is " + evaluateInteger);
        boolean evaluateIntegerTwo = evaluator.evaluate(context, expressionIntegerTwo);
        System.out.println(expressionIntegerTwo + ", value: " + "'" + variables.get("customer.height").getValue() + "' is " + evaluateIntegerTwo);
        boolean evaluateIntegerThree = evaluator.evaluate(context, expressionIntegerThree);
        System.out.println(expressionIntegerThree + ", value: " + "'" + variables.get("customer.height").getValue() + "' is " + evaluateIntegerThree);

        boolean evaluateDateBirth = evaluator.evaluate(context, expressionDateBirth);
        System.out.println(expressionDateBirth + ", date: " + "'" + variables.get("customer.dateOfBirth").getValue() + "' is " + evaluateDateBirth);
        boolean evaluateDateBirthTime = evaluator.evaluate(context, expressionDateBirthTime);
        System.out.println(expressionDateBirthTime + ", date: " + "'" + variables.get("customer.dateOfBirthTime").getValue() + "' is " + evaluateDateBirthTime);

        Expression expressionNew = new Expression("customer.dateOfBirth < 1982-01-01 && customer.height == 170");
        AdviseEvaluator adviseEvaluator = new AdviseEvaluator();
        boolean evaluate = adviseEvaluator.evaluate(context, expressionNew);
        System.out.println("expression : " + expressionNew + " " + evaluate);

    }


}