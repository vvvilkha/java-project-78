package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    @Override
    public StringSchema required() {
        super.required(); // запрещаем null
        // запрещаем только пустую строку (""), пробелы разрешены — без trim()
        addChecks(x -> x instanceof String && !((String) x).isEmpty());
        return this;
    }

    public StringSchema contains(String sub) {
        if (sub == null) {
            throw new IllegalArgumentException("Substring must not be null");
        }
        addChecks(x -> {
            if (x == null) {
                return true; // до required() null считается "нет значения"
            }
            if (!(x instanceof String)) {
                return false;
            }
            String s = (String) x;
            if (!isRequired() && s.isEmpty()) {
                return true; // до required() пустая строка валидна
            }
            return s.contains(sub); // без trim()
        });
        return this;
    }

    public StringSchema minLength(int len) {
        addChecks(x -> {
            if (x == null) {
                return true;
            }
            if (!(x instanceof String)) {
                return false;
            }
            String s = (String) x;
            if (!isRequired() && s.isEmpty()) {
                return true; // до required() пустая строка валидна
            }
            return s.length() >= len; // без trim()
        });
        return this;
    }
}

