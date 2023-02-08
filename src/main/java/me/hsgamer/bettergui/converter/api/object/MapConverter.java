package me.hsgamer.bettergui.converter.api.object;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapConverter extends CompoundConverter<Map<String, Object>, Map<String, Object>> {
    @Override
    public Map<String, Object> createEmpty() {
        return new LinkedHashMap<>();
    }

    @Override
    public Map<String, Object> join(Map<String, Object> all, Map<String, Object> current) {
        all.putAll(current);
        return all;
    }
}
