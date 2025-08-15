package hexlet.code.schemas;

import java.util.function.Predicate;

public final class StringSchema extends BaseSchema<String> {

    public StringSchema required() {
        addCheck("required", requiredCheck());
        return this;
    }

    public StringSchema minLength(int number) {
        addCheck("minLength", minLengthCheck(number));
        return this;
    }

    public StringSchema contains(String content) throws IllegalArgumentException {
        if (content == null) {
            throw new IllegalArgumentException("required content cannot be null");
        }
        addCheck("contains", containsCheck(content));
        return this;
    }

    private Predicate<String> requiredCheck() {
        return str -> str != null && !str.isEmpty();
    }

    private Predicate<String> minLengthCheck(int number) {
        return str -> str.length() >= number;
    }

    private Predicate<String> containsCheck(String content) {
        return str -> str.contains(content);
    }
}

