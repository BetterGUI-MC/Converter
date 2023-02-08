package me.hsgamer.bettergui.converter.api.object;

import me.hsgamer.bettergui.converter.api.unit.ConvertUnit;

public class ConvertObject<T> {
    private final Object object;
    private final ConvertUnit<T> unit;

    public ConvertObject(Object object, ConvertUnit<T> unit) {
        this.object = object;
        this.unit = unit;
    }

    public Object getObject() {
        return object;
    }

    public ConvertUnit<T> getUnit() {
        return unit;
    }

    public T convert() {
        return unit.getConverter().apply(object);
    }
}
