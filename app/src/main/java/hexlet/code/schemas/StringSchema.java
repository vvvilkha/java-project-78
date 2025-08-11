package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    @Override
    public StringSchema required() {
        super.required(); // запрещаем null
        // запрещаем только пустую строку (""), но пробелы разрешаем
        addChecks(x -> x instanceof String && !((String) x).isEmpty());
        return this;
    }

    public StringSchema contains(String sub) {
        if (sub == null) {
            throw new IllegalArgumentException("Substring must not be null");
        }
        addChecks(x -> {
            if (x == null) {
                return true; // null всегда ок, если не required
            }
            if (!(x instanceof String)) {
                return false; // не строка → false
            }
            String s = (String) x;
            if (!isRequired() && s.isEmpty()) {
                return true; // пустая строка ок до required
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
                return true;
            }
            return s.length() >= len; // без trim()
        });
        return this;
    }
}






