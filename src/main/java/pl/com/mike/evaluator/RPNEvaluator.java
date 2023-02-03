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

        String[] tokens = RPNConvert.convertInfixToRPN(getTokensFromExpression(expression));

        return false;

    }

    private static List<String> getTokensFromExpression(Expression expression) {
        String[] tokens = expression.getValue().split(SPLIT_REGEX);
        return prepareTrimmedStrings(tokens);
    }

    private static List<String> prepareTrimmedStrings(String[] tokens) {
        return Arrays.stream(tokens).map(String::trim).filter(x -> !(x.isEmpty())).toList();
    }


}
