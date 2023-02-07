package me.hsgamer.bettergui.converter.api.unit;

import me.hsgamer.bettergui.converter.api.object.ConvertObject;

import java.util.Map;
import java.util.function.Function;

public class ConvertUnit {
    private final int priority;
    private final Function<Object, Map<String, Object>> converter;

    public ConvertUnit(int priority, Function<Object, Map<String, Object>> converter) {
        this.priority = priority;
        this.converter = converter;
    }

    public int getPriority() {
        return priority;
    }

    public Function<Object, Map<String, Object>> getConverter() {
        return converter;
    }

    public ConvertObject create(Object from) {
        return new ConvertObject(from, this);
    }
}
