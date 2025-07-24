package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StringSchemaTest {

    @Test
    void testDefaultBehavior() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertThat(schema.isValid("")).isTrue();
        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid("text")).isTrue();
    }

    @Test
    void testRequired() {
        Validator v = new Validator();
        StringSchema schema = v.string().required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("")).isFalse();
        assertThat(schema.isValid("abc")).isTrue();
    }

    @Test
    void testMinLength() {
        Validator v = new Validator();
        StringSchema schema = v.string().required().minLength(5);

        assertThat(schema.isValid("abcde")).isTrue();
        assertThat(schema.isValid("abcd")).isFalse();
    }

    @Test
    void testContains() {
        Validator v = new Validator();
        StringSchema schema = v.string().required().contains("fox");

        assertThat(schema.isValid("the quick brown fox")).isTrue();
        assertThat(schema.isValid("the quick brown dog")).isFalse();
    }

    @Test
    void testCombinedChecks() {
        Validator v = new Validator();
        StringSchema schema = v.string().required().minLength(5).contains("hex");

        assertThat(schema.isValid("hexlet")).isTrue();
        assertThat(schema.isValid("hello")).isFalse();
        assertThat(schema.isValid("he")).isFalse();
    }

    @Test
    void testMinLengthOverride() {
        Validator v = new Validator();
        StringSchema schema = v.string();
        schema.minLength(10).minLength(4);

        assertThat(schema.isValid("Hexlet")).isFalse();
        assertThat(schema.isValid("superlongstring")).isTrue();
    }
}




