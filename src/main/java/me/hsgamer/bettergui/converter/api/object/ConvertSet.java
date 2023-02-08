package me.hsgamer.bettergui.converter.api.object;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class ConvertSet {
    private final PriorityQueue<ConvertObject> convertObjectSet = new PriorityQueue<>(Comparator.comparingInt(o -> o.getUnit().getPriority()));

    public void add(ConvertObject convertObject) {
        convertObjectSet.add(convertObject);
    }

    public Map<String, Object> convert() {
        Map<String, Object> map = new LinkedHashMap<>();
        for (ConvertObject convertObject : convertObjectSet) {
            map.putAll(convertObject.convert());
        }
        return map;
    }
}
