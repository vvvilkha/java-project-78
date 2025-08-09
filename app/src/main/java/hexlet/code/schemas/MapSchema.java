package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map<String, Object>> {

    @Override
    public MapSchema required() {
        addChecks(x -> x instanceof Map);
        return this;
    }

    public MapSchema sizeof(int n) {
        addChecks(x -> x == null || ((Map<?, ?>) x).size() == n);
        return this;
    }

    public MapSchema shape(Map<String, ? extends BaseSchema<?>> schemas) {
        addChecks(x -> {
            if (x == null) {
                return true;
            }
            Map<?, ?> map = (Map<?, ?>) x;
            for (var e : schemas.entrySet()) {
                String key = e.getKey();
                BaseSchema<?> schema = e.getValue();
                Object value = map.get(key);
                if (!schema.isValid(value)) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }
}

