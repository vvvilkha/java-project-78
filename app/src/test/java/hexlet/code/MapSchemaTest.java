package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapSchemaTest {
    private static Map<String, Number> emptyMap;
    private static Map<String, Number> smallMap;
    private static Map<String, Number> bigMap;

    private Validator validator;
    private MapSchema schema;

    @BeforeAll
    static void setFixtures() {
        emptyMap = Map.of();
        smallMap = Map.of("one", 1, "two", 2);
        bigMap = Map.of("one", 1, "two", 2, "three", 3, "four", 4);
    }

    @BeforeEach
    void setValidationTools() {
        validator = new Validator();
        schema = validator.map();
    }

    @Test
    void testDefault() {
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(emptyMap));
        assertTrue(schema.isValid(smallMap));
        assertTrue(schema.isValid(bigMap));
    }

    @Test
    void testRequired() {
        schema.required();
        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(emptyMap));
        assertTrue(schema.isValid(smallMap));
    }

    @Test
    void testSizeof() {
        assertThrows(IllegalArgumentException.class, () -> schema.sizeof(-1));

        schema.sizeof(0);
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(emptyMap));
        assertFalse(schema.isValid(smallMap));

        schema.sizeof(2);
        assertTrue(schema.isValid(null));
        assertFalse(schema.isValid(emptyMap));
        assertTrue(schema.isValid(smallMap));

        schema.sizeof(4);
        assertFalse(schema.isValid(smallMap));
        assertTrue(schema.isValid(bigMap));
    }

    @Test
    void testStringShape() {
        Map<String, BaseSchema<String>> schemas1 = new HashMap<>();
        schemas1.put("str1", validator.string().required().minLength(6).contains("ring"));
        schemas1.put("str2", validator.string().required().minLength(1));
        schemas1.put("noSuchStringSoCorrect", validator.string().required());
        var schema1 = validator.map().shape(schemas1);

        Map<String, String> correct = Map.of("str1", "string", "str2", "spring");
        Map<String, String> incorrect = Map.of("str1", "string", "str2", "");

        assertTrue(schema1.isValid(correct));
        assertFalse(schema1.isValid(incorrect));
    }

    @Test
    void testNumberShape() {
        Map<String, BaseSchema<Integer>> schemas1 = new HashMap<>();

        schemas1.put("num1", validator.number().positive());
        schemas1.put("num2", validator.number().required().positive().range(-1, 1));
        var schema1 = validator.map().shape(schemas1);

        Map<String, Integer> correct = new HashMap<>();
        correct.put("num1", null);
        correct.put("num2", 1);

        Map<String, Integer> incorrect = new HashMap<>();
        incorrect.put("num1", 1);
        incorrect.put("num2", 0);

        assertTrue(schema1.isValid(correct));
        assertFalse(schema1.isValid(incorrect));
    }

    @Test
    void testMapShape() {
        Map<String, BaseSchema<Map<?, ?>>> schemas1 = new HashMap<>();

        schemas1.put("map1", validator.map().required().sizeof(1));
        schemas1.put("map2", validator.map().sizeof(2));
        var schema1 = validator.map().shape(schemas1);

        Map<String, Map<String, Number>> correct = new HashMap<>();
        correct.put("map1", Map.of("str1", 1));
        correct.put("map2", Map.of("str1", 1, "str2", 2));

        Map<String, Map<String, Number>> incorrect = new HashMap<>();
        incorrect.put("map1", Map.of("str1", 1));
        incorrect.put("map2", Map.of("str1", 1));

        assertTrue(schema1.isValid(correct));
        assertFalse(schema1.isValid(incorrect));
    }

    @Test
    void testNestedShape() {
        Map<String, BaseSchema<Map<?, ?>>> schemas1 = new HashMap<>();
        Map<String, BaseSchema<Integer>> numberSchemas = Map.of("num", validator.number().required().positive());

        schemas1.put("map1", validator.map().shape(numberSchemas));
        var schema1 = validator.map().shape(schemas1);

        Map<String, Map<String, Number>> correct = new HashMap<>();
        correct.put("map1", Map.of("num", 1));
        correct.put("map2", Map.of());

        Map<String, Map<String, Number>> incorrect = new HashMap<>();
        incorrect.put("map1", Map.of("num", -1));
        incorrect.put("map2", Map.of());

        assertTrue(schema1.isValid(correct));
        assertFalse(schema1.isValid(incorrect));
    }
}
