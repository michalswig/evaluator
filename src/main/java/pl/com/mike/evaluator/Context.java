package pl.com.mike.evaluator;

import java.util.Map;

public class Context {

    public static final String VARIABLES_IS_NULL = "Variables is null";
    private Map<String, VariableValue> variables;


    public Map<String, VariableValue> getVariables() {
        return variables;
    }

}
