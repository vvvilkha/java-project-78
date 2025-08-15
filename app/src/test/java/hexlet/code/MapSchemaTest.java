package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapSchemaTest {

    @ParameterizedTest(name = "[sizeof={0}] mapSize={1} ⇒ valid={2}")
    @CsvSource({
        "2, 2, true",
        "2, 1, false",
        "2, 3, false"
    })
    void sizeofParam(int requiredSize, int mapSize, boolean expected) {
        var schema = new Validator().map().sizeof(requiredSize);

        Map<String, String> data = new HashMap<>();
        for (int i = 0; i < mapSize; i++) {
            data.put("k" + i, Integer.toString(i));
        }
        assertEquals(expected, schema.isValid(data));
    }

    @Test
    void requiredRejectsNullAllowsEmptyMap() {
        var schema = new Validator().map().required(); // MapSchema<String>
        assertFalse(schema.isValid(null));             // required => null невалиден
        assertTrue(schema.isValid(Map.of()));          // пустая карта валидна (нет sizeof/shape)
    }

    @Test
    void sizeofZeroAllowsEmptyMap() {
        var schema = new Validator().map().sizeof(0);
        assertTrue(schema.isValid(Map.of()));
        assertFalse(schema.isValid(Map.of("k", "v")));
    }

    @Test
    void shapeRequiredFieldMissingKeyIsInvalid() {
        var v = new Validator();
        var schema = v.map();

        var shape = Map.<String, BaseSchema<String>>of(
                "name", v.string().required()
        );
        schema.shape(shape);

        assertFalse(schema.isValid(Map.of()));               // "name" отсутствует => value=null => required ломает
        assertTrue(schema.isValid(Map.of("name", "John")));  // ок
    }

    @Test
    void shapeMultipleFieldsMustAllPass() {
        var v = new Validator();
        var schema = v.map();

        var shape = Map.<String, BaseSchema<String>>of(
                "name", v.string().required().minLength(2),
                "city", v.string().required().minLength(3)
        );
        schema.shape(shape);

        assertFalse(schema.isValid(Map.of("name", "Al", "city", "NY")));   // слишком короткий city
        assertTrue(schema.isValid(Map.of("name", "Al", "city", "Rome")));  // оба проходят
    }
}

