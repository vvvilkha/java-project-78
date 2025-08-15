package hexlet.code;

import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;

public final class Validator {

    public StringSchema string() {
        return new StringSchema();
    }

    public NumberSchema number() {
        return new NumberSchema();
    }

    public MapSchema<String> map() {
        return new MapSchema<>();
    }

    public <V> MapSchema<V> mapOf(Class<V> valueType) {
        return new MapSchema<>();
    }
}

