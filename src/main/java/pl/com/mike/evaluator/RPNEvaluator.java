package pl.com.mike.evaluator;

import pl.com.mike.evaluator.convert.Operators;
import pl.com.mike.evaluator.convert.RPNConvert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class RPNEvaluator implements Evaluator {

    public static final String SPLIT_REGEX = "(?<![(])(?=[)])|(?<![&|])(?=[&|])|(?<=[&|])(?![&|])|(?<![=+<>^*!()])(?=[=+<>^*!()])|(?<=[=+<>^*!()])(?![=+<>^*!()])";

    @Override
    public boolean evaluate(Context context, Expression expression) {

        try {

            String[] tokens = RPNConvert.convertInfixToRPN(getTokensFromExpression(expression));

            Map<DataType, String> dataTypeWithValue = prepareExpression(tokens, context.getVariables());

            Stack<String> stack = new Stack<>();

            for (String token : tokens) {
                if (isNotOperator(token)) {
                    stack.push(token);
                    continue;
                }
                switch (Operators.prepareOperator(token)) {
                    case LESS -> prepareResultWhenLessOperator(dataTypeWithValue, stack);
                    case LESS_EQUAL -> prepareResultWhenLessEqualOperator(dataTypeWithValue, stack);
                    case MORE -> prepareResultWhenMoreOperator(dataTypeWithValue, stack);
                    case MORE_EQUAL -> prepareResultWhenMoreEqualOperator(dataTypeWithValue, stack);

                }
            }
            return Boolean.parseBoolean(stack.pop());
        } catch (DateTimeParseException | NumberFormatException | EmptyStackException e) {
            throw new EvaluationException(e.getMessage());
        }

    }

    private static boolean isNotOperator(String token) {
        for (Operators value : Operators.values()) {
            if (value.getSymbol().equals(token)) {
                return false;
            }
        }
        return true;
    }

    private static void prepareResultWhenLessEqualOperator(Map<DataType, String> valueDataTypeSet, Stack<String> stack) {
        String valueFirst = stack.pop();
        String valueSecond = stack.pop();
        for (DataType dataType : valueDataTypeSet.keySet()) {
            if (isValidDataType(valueDataTypeSet, valueFirst, valueSecond, dataType)) {
                stack.push(getResultWhenLessEqualOperator(valueFirst, valueSecond, dataType));
            }
        }
    }

    private static void prepareResultWhenLessOperator(Map<DataType, String> valueDataTypeSet, Stack<String> stack) {
        String valueFirst = stack.pop();
        String valueSecond = stack.pop();
        for (DataType dataType : valueDataTypeSet.keySet()) {
            if (isValidDataType(valueDataTypeSet, valueFirst, valueSecond, dataType)) {
                stack.push(getResultWhenLessOperator(valueFirst, valueSecond, dataType));
            }
        }
    }

    private static void prepareResultWhenMoreOperator(Map<DataType, String> valueDataTypeSet, Stack<String> stack) {
        String valueFirst = stack.pop();
        String valueSecond = stack.pop();
        for (DataType dataType : valueDataTypeSet.keySet()) {
            if (isValidDataType(valueDataTypeSet, valueFirst, valueSecond, dataType)) {
                stack.push(getResultWhenMoreOperator(valueFirst, valueSecond, dataType));
            }
        }
    }

    private static void prepareResultWhenMoreEqualOperator(Map<DataType, String> valueDataTypeSet, Stack<String> stack) {
        String valueFirst = stack.pop();
        String valueSecond = stack.pop();
        for (DataType dataType : valueDataTypeSet.keySet()) {
            if (isValidDataType(valueDataTypeSet, valueFirst, valueSecond, dataType)) {
                stack.push(getResultWhenMoreEqualOperator(valueFirst, valueSecond, dataType));
            }
        }
    }

    private static String getResultWhenMoreEqualOperator(String valueFirst, String valueSecond, DataType dataType) {
        switch (dataType) {
            case DATE -> {
                return String.valueOf(LocalDate.parse(valueSecond).isAfter(LocalDate.parse(valueFirst)) || LocalDate.parse(valueSecond).isEqual(LocalDate.parse(valueFirst)));
            }
            case DATE_TIME -> {
                return String.valueOf(LocalDateTime.parse(valueSecond).isAfter(LocalDateTime.parse(valueFirst)) || LocalDateTime.parse(valueSecond).isEqual(LocalDateTime.parse(valueFirst)));
            }
            case INTEGER -> {
                return String.valueOf(Integer.parseInt(valueSecond) >= Integer.parseInt(valueFirst));
            }
        }
        throw new EvaluationException("uknown data type: " + dataType);
    }

    private static String getResultWhenMoreOperator(String valueFirst, String valueSecond, DataType dataType) {
        switch (dataType) {
            case DATE -> {
                return String.valueOf(LocalDate.parse(valueSecond).isAfter(LocalDate.parse(valueFirst)));
            }
            case DATE_TIME -> {
                return String.valueOf(LocalDateTime.parse(valueSecond).isAfter(LocalDateTime.parse(valueFirst)));
            }
            case INTEGER -> {
                return String.valueOf(Integer.parseInt(valueSecond) > Integer.parseInt(valueFirst));
            }
        }
        throw new EvaluationException("uknown data type: " + dataType);
    }

    private static boolean isValidDataType(Map<DataType, String> valueDataTypeSet, String valueFirst, String valueSecond, DataType dataType) {
        return valueDataTypeSet.get(dataType).equals(valueFirst) || valueDataTypeSet.get(dataType).equals(valueSecond);
    }

    private static String getResultWhenLessOperator(String valueFirst, String valueSecond, DataType dataType) {
        switch (dataType) {
            case DATE -> {
                return String.valueOf(LocalDate.parse(valueSecond).isBefore(LocalDate.parse(valueFirst)));
            }
            case DATE_TIME -> {
                return String.valueOf(LocalDateTime.parse(valueSecond).isBefore(LocalDateTime.parse(valueFirst)));
            }
            case INTEGER -> {
                return String.valueOf(Integer.parseInt(valueSecond) < Integer.parseInt(valueFirst));
            }
        }
        throw new EvaluationException("uknown data type: " + dataType);
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

    private static String getResultWhenLessEqualOperator(String valueFirst, String valueSecond, DataType dataType) {
        switch (dataType) {
            case DATE -> {
                return String.valueOf((LocalDate.parse(valueSecond).isBefore(LocalDate.parse(valueFirst)) || LocalDate.parse(valueSecond).isEqual(LocalDate.parse(valueFirst))));
            }
            case DATE_TIME -> {
                return String.valueOf(LocalDateTime.parse(valueSecond).isBefore(LocalDateTime.parse(valueFirst)));
            }
            case INTEGER -> {
                return String.valueOf(Integer.parseInt(valueSecond) <= Integer.parseInt(valueFirst));
            }
        }
        throw new EvaluationException("uknown data type: " + dataType);
    }


}
