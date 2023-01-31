package pl.com.mike.evaluator;

@FunctionalInterface
public interface Evaluator {
    boolean evaluate(Context context, Expression expression);
}
