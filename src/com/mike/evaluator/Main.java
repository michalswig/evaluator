package com.mike.evaluator;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Evaluator evaluator = new AdviseSimpleEvaluator();
        Map<String, VariableValue> variables = new HashMap<>();
        variables.put("customer.name", new VariableValue("Adam", DataType.STRING));
        Context context = new Context(variables);
        evaluator.evaluate(context, new Expression("customer.name==Adam"));


    }
}