package hexlet.code.schemas;

import java.util.Objects;
import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema required() {
        addCheck("required", requiredCheck());

        return this;
    }

    public NumberSchema positive() {
        addCheck("positive", positiveCheck());
        return this;
    }

    public NumberSchema range(int min, int max) {
        addCheck("range", rangeCheck(min, max));
        return this;
    }

    private Predicate<Integer> requiredCheck() {
        return Objects::nonNull;
    }

    private Predicate<Integer> positiveCheck() {
        return n -> n == null || n > 0;
    }

    private Predicate<Integer> rangeCheck(int min, int max) {
        return n -> n == null || (n >= min && n <= max);
    }
}

