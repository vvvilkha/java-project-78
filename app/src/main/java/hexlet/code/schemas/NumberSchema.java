package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema {

    @Override
    public NumberSchema required() {
        addChecks(x -> x != null);
        addChecks(x -> x instanceof Integer);
        return this;
    }

    public NumberSchema positive() {
        addChecks(x -> x == null || x instanceof Integer && (Integer) x > 0);
        return this;
    }

    public NumberSchema range(int oneIn, int twoIn) {
        addChecks(x -> x == null || x instanceof Integer && (((Integer) x) >= oneIn && ((Integer) x) <= twoIn));
        return this;
    }
}
