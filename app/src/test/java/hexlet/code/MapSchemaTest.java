package hexlet.code;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}

