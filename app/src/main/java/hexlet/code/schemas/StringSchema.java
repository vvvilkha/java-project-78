package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class StringSchema {

        private final List<Predicate<String>> checks = new ArrayList<>();
        private boolean isRequired = false;

        public StringSchema required() {
            isRequired = true;
            checks.add(s -> s != null && !s.isEmpty());
            return this;
        }

        public StringSchema minLength(int length) {
            checks.add(s -> s != null && s.length() >= length);
            return this;
        }

        public StringSchema contains(String substring) {
            checks.add(s -> s != null && s.contains(substring));
            return this;
        }

        public boolean isValid(Object input) {
            if (!(input instanceof String) && input != null) {
                return false;
            }

            String str = (String) input;

            if (!isRequired && (str == null || str.isEmpty())) {
                return true;
            }

            for (Predicate<String> check : checks) {
                if (!check.test(str)) {
                    return false;
                }
            }

            return true;
        }
    }



