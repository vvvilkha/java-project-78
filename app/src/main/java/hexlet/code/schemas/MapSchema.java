package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public final class MapSchema<V> extends BaseSchema<Map<String, V>> {

    public MapSchema<V> required() {
        addCheck("required", requiredCheck());
        return this;
    }

    public MapSchema<V> sizeof(int size) {
        addCheck("sizeof", sizeofCheck(size));
        return this;
    }

    public MapSchema<V> shape(Map<String, BaseSchema<V>> schemas) {
        addCheck("shape", shapeCheck(schemas));
        return this;
    }

    // --- Predicates ---
    private Predicate<Map<String, V>> requiredCheck() {
        return Objects::nonNull;
    }

    private Predicate<Map<String, V>> sizeofCheck(int size) {
        return m -> m.size() == size;
    }

    private Predicate<Map<String, V>> shapeCheck(Map<String, BaseSchema<V>> schemas) {
        return m -> {
            for (var e : schemas.entrySet()) {
                var key = e.getKey();
                var schema = e.getValue();
                var value = m.get(key);
                if (!schema.isValid(value)) {
                    return false;
                }
            }
            return true;
        };
    }
}

