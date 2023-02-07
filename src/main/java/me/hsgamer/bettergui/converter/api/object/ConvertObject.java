package me.hsgamer.bettergui.converter.api.object;

import me.hsgamer.bettergui.converter.api.unit.ConvertUnit;
import me.hsgamer.hscore.common.Pair;

public class ConvertObject {
    private final Object object;
    private final ConvertUnit unit;

    public ConvertObject(Object object, ConvertUnit unit) {
        this.object = object;
        this.unit = unit;
    }

    public Object getObject() {
        return object;
    }

    public ConvertUnit getUnit() {
        return unit;
    }

    public Pair<String, Object> convert() {
        return Pair.of(unit.getKey(), unit.getConverter().apply(object));
    }
}
