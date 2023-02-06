package pl.com.mike.evaluator;

import pl.com.mike.evaluator.convert.RPNConvert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RPNEvaluator implements Evaluator {

    public static final String SPLIT_REGEX = "(?<![(])(?=[)])|(?<![&|])(?=[&|])|(?<=[&|])(?![&|])|(?<![=+<>^*!()])(?=[=+<>^*!()])|(?<=[=+<>^*!()])(?![=+<>^*!()])";

    @Override
    public boolean evaluate(Context context, Expression expression) {

        String[] tokens = RPNConvert.convertInfixToRPN(getTokensFromExpression(expression));

        Map<DataType, String> dataTypeWithValue = prepareExpression(tokens, context.getVariables());

        return false;

    }

    private static Map<DataType, String> prepareExpression(String[] tokens, Map<String, VariableValue> variables) {
        Map<DataType, String> valueDataTypes = new HashMap<>();
        for (String key : variables.keySet()) {
            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].equals(key)) {
                    tokens[i] = variables.get(key).getValue();
                    valueDataTypes.put(variables.get(key).getDataType(), tokens[i]);
                }
            }
        }
        return valueDataTypes;
    }

    private static List<String> getTokensFromExpression(Expression expression) {
        String[] tokens = expression.getValue().split(SPLIT_REGEX);
        return prepareTrimmedStrings(tokens);
    }

    private static List<String> prepareTrimmedStrings(String[] tokens) {
        return Arrays.stream(tokens).map(String::trim).filter(x -> !(x.isEmpty())).toList();
    }


}
