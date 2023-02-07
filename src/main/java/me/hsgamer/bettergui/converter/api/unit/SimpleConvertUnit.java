package me.hsgamer.bettergui.converter.api.unit;

import java.util.Collections;
import java.util.function.UnaryOperator;

public class SimpleConvertUnit extends ConvertUnit {
    public SimpleConvertUnit(int priority, String key, UnaryOperator<Object> converter) {
        super(priority, o -> Collections.singletonMap(key, converter.apply(o)));
    }

    public SimpleConvertUnit(String key, UnaryOperator<Object> converter) {
        this(Integer.MAX_VALUE, key, converter);
    }

    public SimpleConvertUnit(int priority, String key) {
        this(priority, key, o -> o);
    }

    public SimpleConvertUnit(String key) {
        this(Integer.MAX_VALUE, key);
    }
}
