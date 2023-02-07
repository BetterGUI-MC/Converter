package me.hsgamer.bettergui.converter.api.converter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ConverterType {
    List<String> getNames();

    Optional<Map<String, Object>> convert(String name);
}
