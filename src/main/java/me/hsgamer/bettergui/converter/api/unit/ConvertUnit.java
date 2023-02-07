package me.hsgamer.bettergui.converter.api.unit;

import me.hsgamer.bettergui.converter.api.object.ConvertObject;

import java.util.function.UnaryOperator;

public class ConvertUnit {
    private final int priority;
    private final String key;
    private final UnaryOperator<Object> converter;

    public ConvertUnit(int priority, String key, UnaryOperator<Object> converter) {
        this.priority = priority;
        this.key = key;
        this.converter = converter;
    }

    public ConvertUnit(String key, UnaryOperator<Object> converter) {
        this(Integer.MAX_VALUE, key, converter);
    }

    public ConvertUnit(int priority, String key) {
        this(priority, key, o -> o);
    }

    public ConvertUnit(String key) {
        this(Integer.MAX_VALUE, key);
    }

    public int getPriority() {
        return priority;
    }

    public UnaryOperator<Object> getConverter() {
        return converter;
    }

    public ConvertObject create(Object from) {
        return new ConvertObject(from, this);
    }

    public String getKey() {
        return key;
    }
}
