package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import org.junit.jupiter.params.provider.Arguments;

class NumberSchemaTest {

    private NumberSchema schema;

    @BeforeEach
    void setUp() {
        var validator = new Validator();
        schema = validator.number();
    }

    @ParameterizedTest(name = "[default] value={0} ⇒ valid={1}")
    @MethodSource("defaultCases")
    void defaultValidation(Integer value, boolean expected) {
        assertEquals(expected, schema.isValid(value));
    }

    static Stream<Arguments> defaultCases() {
        return Stream.of(
                arguments(null, true),
                arguments(0, true),
                arguments(-1, true),
                arguments(1, true)
        );
    }

    @ParameterizedTest(name = "[required] value={0} ⇒ valid={1}")
    @MethodSource("requiredCases")
    void requiredValidation(Integer value, boolean expected) {
        schema.required();
        assertEquals(expected, schema.isValid(value));
    }

    static Stream<Arguments> requiredCases() {
        return Stream.of(
                arguments(null, false),
                arguments(0, true),
                arguments(-1, true),
                arguments(1, true)
        );
    }

    @ParameterizedTest(name = "[positive] value={0} ⇒ valid={1}")
    @MethodSource("positiveCases")
    void positiveValidation(Integer value, boolean expected) {
        schema.positive();
        assertEquals(expected, schema.isValid(value));
    }

    static Stream<Arguments> positiveCases() {
        return Stream.of(
                arguments(null, true),
                arguments(0, false),
                arguments(-1, false),
                arguments(1, true)
        );
    }

    @ParameterizedTest(name = "[range -1..3] value={0} ⇒ valid={1}")
    @MethodSource("rangeCases")
    void rangeValidation(Integer value, boolean expected) {
        schema.range(-1, 3);
        assertEquals(expected, schema.isValid(value));
    }

    static Stream<Arguments> rangeCases() {
        return Stream.of(
                arguments(null, true),
                arguments(-4, false),
                arguments(-1, true),
                arguments(0, true),
                arguments(3, true),
                arguments(4, false)
        );
    }

    @ParameterizedTest(name = "[mixed required+positive+range] value={0} ⇒ valid={1}")
    @MethodSource("mixedCases")
    void mixedValidation(Integer value, boolean expected) {
        schema.required().positive().range(-1, 3);
        assertEquals(expected, schema.isValid(value));
    }

    static Stream<Arguments> mixedCases() {
        return Stream.of(
                arguments(null, false),
                arguments(0, false),
                arguments(-1, false),
                arguments(1, true),
                arguments(4, false)
        );
    }
}

