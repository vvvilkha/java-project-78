package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringSchemaTest {

    @Test
    void testNullAndEmptyBeforeRequired() {
        StringSchema schema = new StringSchema();
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid(""));
    }

    @Test
    void testRequiredBlocksNullAndEmpty() {
        StringSchema schema = new StringSchema().required();
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertTrue(schema.isValid("a"));
    }

    @Test
    void testContainsBeforeAndAfterRequired() {
        StringSchema schema = new StringSchema().contains("lo");
        assertTrue(schema.isValid("hello"));
        assertTrue(schema.isValid(""));
        assertFalse(schema.isValid(123));

        schema.required();
        assertFalse(schema.isValid(""));
        assertTrue(schema.isValid("yellow"));
        assertFalse(schema.isValid("red"));
    }

    @Test
    void testMinLength() {
        StringSchema schema = new StringSchema().minLength(3);
        assertTrue(schema.isValid("abc"));
        assertFalse(schema.isValid("ab"));
        assertTrue(schema.isValid(""));

        schema.required();
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid("ab"));
        assertTrue(schema.isValid("abcd"));
    }
}



