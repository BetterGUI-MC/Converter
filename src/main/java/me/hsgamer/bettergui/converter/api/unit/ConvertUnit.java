package me.hsgamer.bettergui.converter.api.unit;

import me.hsgamer.bettergui.converter.api.object.ConvertObject;

import java.util.function.Function;

public class ConvertUnit<T> {
    private final int priority;
    private final Function<Object, T> converter;

    public ConvertUnit(int priority, Function<Object, T> converter) {
        this.priority = priority;
        this.converter = converter;
    }

    public int getPriority() {
        return priority;
    }

    public Function<Object, T> getConverter() {
        return converter;
    }

    public ConvertObject<T> create(Object from) {
        return new ConvertObject<>(from, this);
    }
}
