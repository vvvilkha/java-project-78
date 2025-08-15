package hexlet.code;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringSchemaTest {

    @ParameterizedTest(name = "[default] value=\"{0}\" ⇒ valid={1}")
    @MethodSource("defaultCases")
    void defaultValidation(String value, boolean expected) {
        var schema = new Validator().string();
        assertEquals(expected, schema.isValid(value));
    }

    static Stream<Arguments> defaultCases() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of("", true),
                Arguments.of("text", true)
        );
    }

    @ParameterizedTest(name = "[required] value=\"{0}\" ⇒ valid={1}")
    @MethodSource("requiredCases")
    void requiredValidation(String value, boolean expected) {
        var schema = new Validator().string().required();
        assertEquals(expected, schema.isValid(value));
    }

    static Stream<Arguments> requiredCases() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of("", false),
                Arguments.of("text", true)
        );
    }

    @ParameterizedTest(name = "[minLength={0}] value=\"{1}\" ⇒ valid={2}")
    @MethodSource("minLengthCases")
    void minLengthValidation(int min, String value, boolean expected) {
        var schema = new Validator().string().minLength(min);
        assertEquals(expected, schema.isValid(value));
    }

    static Stream<Arguments> minLengthCases() {
        return Stream.of(
                Arguments.of(0, null, true),
                Arguments.of(0, "", true),
                Arguments.of(0, "text", true),
                Arguments.of(0, "another text 123", true),

                Arguments.of(5, null, true),
                Arguments.of(5, "", false),
                Arguments.of(5, "text", false),
                Arguments.of(5, "another text 123", true)
        );
    }

    @ParameterizedTest(name = "[contains=\"{0}\"] value=\"{1}\" ⇒ valid={2}")
    @MethodSource("containsCases")
    void containsValidation(String needle, String value, boolean expected) {
        var schema = new Validator().string().contains(needle);
        assertEquals(expected, schema.isValid(value));
    }

    static Stream<Arguments> containsCases() {
        return Stream.of(
                Arguments.of("", null, true),
                Arguments.of("", "", true),
                Arguments.of("", "text", true),
                Arguments.of("", "another text 123", true),

                Arguments.of("text", null, true),
                Arguments.of("text", "", false),
                Arguments.of("text", "text", true),
                Arguments.of("text", "another text 123", true),

                Arguments.of(" ", null, true),
                Arguments.of(" ", "", false),
                Arguments.of(" ", "text", false),
                Arguments.of(" ", "another text 123", true)
        );
    }

    @ParameterizedTest(name = "[contains=\"text\" & minLength=4] value=\"{0}\" ⇒ valid={1}")
    @MethodSource("containsMin4Cases")
    void containsWithMinLengthInitial(String value, boolean expected) {
        var schema = new Validator().string().contains("text").minLength(4);
        assertEquals(expected, schema.isValid(value));
    }

    static Stream<Arguments> containsMin4Cases() {
        return Stream.of(
                Arguments.of("text", true),
                Arguments.of("another text 123", true)
        );
    }

    @ParameterizedTest(name = "[contains=\"text\" & minLength=4 → then minLength=5] value=\"{0}\" ⇒ valid={1}")
    @MethodSource("containsMinThenIncreaseCases")
    void containsWithMinLengthIncrease(String value, boolean expected) {
        var schema = new Validator().string().contains("text").minLength(4);
        schema.minLength(5);
        assertEquals(expected, schema.isValid(value));
    }

    static Stream<Arguments> containsMinThenIncreaseCases() {
        return Stream.of(
                Arguments.of("text", false),
                Arguments.of("another text 123", true)
        );
    }

    @ParameterizedTest
    @NullSource
    void containsNullArgumentThrows(String content) {
        var schema = new Validator().string();
        assertThrows(IllegalArgumentException.class, () -> schema.contains(content));
    }
}

