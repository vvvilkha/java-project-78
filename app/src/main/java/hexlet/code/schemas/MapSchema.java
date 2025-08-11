package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map<String, Object>> {

    @Override
    public MapSchema required() {
        super.required(); // запрещаем null
        addChecks(x -> x instanceof Map); // и требуем Map
        return this;
    }

    // По условиям курса обычно нужен ТОЧНЫЙ размер
    public MapSchema sizeof(int n) {
        addChecks(x -> x == null || ((Map<?, ?>) x).size() == n);
        return this;
    }

    public MapSchema shape(Map<String, ? extends BaseSchema<?>> schemas) {
        addChecks(x -> {
            if (x == null) {
                return true; // до required() null валиден
            }
            Map<?, ?> map = (Map<?, ?>) x;
            for (var e : schemas.entrySet()) {
                Object value = map.get(e.getKey());
                if (!e.getValue().isValid(value)) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }
}

