package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema<Map<String, Object>> {

    public MapSchema required() {
        addCheck("required", requiredCheck());
        return this;
    }

    public MapSchema sizeof(int size) {
        addCheck("sizeof", sizeofCheck(size));
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema<?>> schemas) {
        addCheck("shape", shapeCheck(schemas));
        return this;
    }

    private Predicate<Map<String, Object>> requiredCheck() {
        return Objects::nonNull;
    }

    private Predicate<Map<String, Object>> sizeofCheck(int size) {
        return m -> m.size() == size;
    }

    @SuppressWarnings("unchecked")
    private Predicate<Map<String, Object>> shapeCheck(Map<String, BaseSchema<?>> schemas) {
        return m -> {
            for (var entry : schemas.entrySet()) {
                var key = entry.getKey();
                var schema = (BaseSchema<Object>) entry.getValue();
                var value = m.get(key);
                if (!schema.isValid(value)) {
                    return false;
                }
            }
            return true;
        };
    }
}

