package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class BaseSchema<T> {
    private final List<Predicate<Object>> checks = new ArrayList<>();
    protected boolean required = false;

    protected void addChecks(Predicate<Object> check) {
        checks.add(check);
    }

    public BaseSchema<T> required() {
        this.required = true;
        addChecks(x -> x != null);
        return this;
    }

    protected boolean isRequired() {
        return required;
    }

    public boolean isValid(Object value) {
        for (Predicate<Object> check : checks) {
            if (!check.test(value)) {
                return false;
            }
        }
        return true;
    }
}

