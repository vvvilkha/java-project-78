package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringSchemaTest {
    static final int FIVE = 5;
    @Test
    public void testNoParam() {
        Validator v = new Validator();
        StringSchema schema = v.string();
        assertTrue(schema.isValid(null));
    }

    @Test
    public void testRequired() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        schema.required();

        assertTrue(schema.isValid("what does the fox say"));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(null));

    }

    @Test
    public void testContains() {
        Validator v = new Validator();
        StringSchema schema = v.string();
        schema.required();
        assertTrue(schema.contains("what").isValid("what does the fox say"));
        assertFalse(schema.contains("whatthe").isValid("what does the fox say"));
    }

    @Test
    public void testMinLength() {
        Validator v = new Validator();
        StringSchema schema = v.string();
        schema.required();
        assertTrue(schema.minLength(2).isValid("fo"));
        assertFalse(schema.minLength(FIVE).isValid("what"));
    }
}



