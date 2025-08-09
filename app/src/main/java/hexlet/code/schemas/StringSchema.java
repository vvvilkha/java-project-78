package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    @Override
    public StringSchema required() {
        addChecks(x -> x instanceof String && !((String) x).trim().isEmpty());
        return this;
    }

    public StringSchema contains(String sub) {
        if (sub == null) {
            throw new IllegalArgumentException("Substring must not be null");
        }
        addChecks(x -> x == null || (x instanceof String && ((String) x).contains(sub)));
        return this;
    }

    public StringSchema minLength(int len) {
        addChecks(x -> x == null || (x instanceof String && ((String) x).trim().length() >= len));
        return this;
    }
}


