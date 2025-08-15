package hexlet.code;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MapSchemaTest {

    @ParameterizedTest(name = "[{index}] sizeof={0}, mapSize={1} ⇒ valid={2}")
    @CsvSource({
            "2, 2, true",
            "2, 1, false",
            "2, 3, false"
    })
    void sizeof_param(int requiredSize, int mapSize, boolean expected) {
        var v = new Validator();
        var schema = v.map().sizeof(requiredSize);

        Map<String, Object> data = new HashMap<>();
        for (int i = 0; i < mapSize; i++) {
            data.put("k" + i, i);
        }

        assertEquals(expected, schema.isValid(data));
    }

    @ParameterizedTest
    @NullSource
    void required_null_is_invalid(Map<String, Object> data) {
        var v = new Validator();
        var schema = v.map().required();
        assertFalse(schema.isValid(data));
    }
}

