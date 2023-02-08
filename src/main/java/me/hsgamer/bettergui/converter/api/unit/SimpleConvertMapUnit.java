package me.hsgamer.bettergui.converter.api.unit;

import java.util.Collections;
import java.util.function.UnaryOperator;

public class SimpleConvertMapUnit extends ConvertMapUnit {
    public SimpleConvertMapUnit(int priority, String key, UnaryOperator<Object> converter) {
        super(priority, o -> {
            Object value = converter.apply(o);
            if (value == null) {
                return Collections.emptyMap();
            } else {
                return Collections.singletonMap(key, value);
            }
        });
    }

    public SimpleConvertMapUnit(String key, UnaryOperator<Object> converter) {
        this(Integer.MAX_VALUE, key, converter);
    }

    public SimpleConvertMapUnit(int priority, String key) {
        this(priority, key, o -> o);
    }

    public SimpleConvertMapUnit(String key) {
        this(Integer.MAX_VALUE, key);
    }
}
