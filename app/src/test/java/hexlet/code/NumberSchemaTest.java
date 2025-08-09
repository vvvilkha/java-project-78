package hexlet.code;

import hexlet.code.Validator;
import hexlet.code.schemas.NumberSchema;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumberSchemaTest {
    static final int TEN = 10;
    static final int FIVE = 5;

    @Test
    public void testNoParam() {
        Validator v = new Validator();
        NumberSchema schema = v.number();
        assertTrue(schema.isValid(null));
    }

    @Test
    public void testRequired() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        schema.required();

        assertTrue(schema.isValid(TEN));
        assertFalse(schema.isValid("5"));
        assertFalse(schema.isValid(null));

    }

    @Test
    public void testPositive() {
        Validator v = new Validator();
        NumberSchema schema = v.number();
        assertTrue(schema.positive().isValid(TEN)); // true
        assertFalse(schema.isValid(-TEN)); // false
        assertTrue(schema.positive().isValid(null)); // true
    }

    @Test
    public void testRange() {
        Validator v = new Validator();
        NumberSchema schema = v.number();
        schema.required();
        schema.range(FIVE, TEN);

        assertTrue(schema.isValid(FIVE)); // true
        assertTrue(schema.isValid(TEN)); // true
        assertFalse(schema.isValid(FIVE - 1)); // false
        assertFalse(schema.isValid(TEN + 1)); // false
    }

}

