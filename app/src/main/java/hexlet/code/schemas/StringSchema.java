package hexlet.code.schemas;

public final class StringSchema extends BaseSchema<String> {

    @Override
    public StringSchema required() {
        super.required();
        addChecks(x -> x instanceof String && !((String) x).isEmpty());
        return this;
    }

    public StringSchema contains(String sub) {
        if (sub == null) {
            throw new IllegalArgumentException("Substring must not be null");
        }
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
            return s.contains(sub);
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
            return s.length() >= len;
        });
        return this;
    }
}





