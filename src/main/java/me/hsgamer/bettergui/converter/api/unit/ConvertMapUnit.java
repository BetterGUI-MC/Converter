package me.hsgamer.bettergui.converter.api.unit;

import java.util.Map;
import java.util.function.Function;

public class ConvertMapUnit extends ConvertUnit<Map<String, Object>> {
    public ConvertMapUnit(int priority, Function<Object, Map<String, Object>> converter) {
        super(priority, converter);
    }
}
