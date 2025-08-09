package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema<Integer> {

    @Override
    public NumberSchema required() {
        addChecks(x -> x instanceof Integer);
        return this;
    }

    public NumberSchema positive() {
        addChecks(x -> x == null || (x instanceof Integer && (Integer) x > 0));
        return this;
    }

    public NumberSchema range(int from, int to) {
        addChecks(x -> x == null || (x instanceof Integer && (Integer) x >= from && (Integer) x <= to));
        return this;
    }
}

