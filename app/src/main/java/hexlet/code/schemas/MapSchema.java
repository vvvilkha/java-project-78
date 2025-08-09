package hexlet.code.schemas;

import java.util.Map;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema {

    @Override
    public MapSchema required() {
        addChecks(x -> x != null);
        addChecks(x -> x instanceof Map);
        return this;
    }

    public MapSchema sizeof(int intIn) {
        addChecks(x -> x == null || x instanceof Map && ((Map<Object, Object>) x).size() >= intIn);
        return this;
    }

    public MapSchema shape(Map<String, hexlet.code.schemas.BaseSchema> shapeSchemIn) {
        Predicate<Object> predicate = x ->
                !(((Map<Object, Object>) x).entrySet()
                        .stream()
                        .filter(z -> !shapeSchemIn.get(z.getKey()).isValid(z.getValue())).count() > 0);

        addChecks(predicate);

        return this;
    }
}
