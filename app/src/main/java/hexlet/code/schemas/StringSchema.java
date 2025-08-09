package hexlet.code.schemas;

public final class StringSchema extends BaseSchema{

    @Override
    public StringSchema required() {
        addChecks(x -> x != null);
        addChecks(x -> x instanceof String);
        addChecks(x -> !(((String) x).trim().isEmpty()));
        return this;
    }

    public StringSchema contains(String strIn) {
        addChecks(x -> x == null || x instanceof String && ((String) x).contains(strIn));
        return this;
    }

    public StringSchema minLength(int intIn) {
        addChecks(x -> x == null || x instanceof String && ((String) x).trim().length() >= intIn);
        return this;
    }
}
