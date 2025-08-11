package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapSchemaTest {

    @Test
    void testRequiredAndSizeof() {
        MapSchema schema = new MapSchema().required();
        assertFalse(schema.isValid(null));
        Map<String, Object> data = new HashMap<>();
        assertTrue(schema.isValid(data));

        schema.sizeof(2);
        data.put("a", 1);
        assertFalse(schema.isValid(data));
        data.put("b", 2);
        assertTrue(schema.isValid(data));
    }

    @Test
    void testShapeValidation() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema<?>> shape = new HashMap<>();
        shape.put("name", v.string().required());
        shape.put("age", v.number().positive());

        schema.shape(shape);

        Map<String, Object> human = new HashMap<>();
        human.put("name", "Alex");
        human.put("age", 25);
        assertTrue(schema.isValid(human));

        human.put("age", -5);
        assertFalse(schema.isValid(human));

        human.put("name", "");
        assertFalse(schema.isValid(human));
    }
}



