package me.hsgamer.bettergui.converter.api.converter;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ConverterType {
    default String getName() {
        return getClass().getSimpleName();
    }

    List<String> getNames();

    Optional<Map<String, Object>> convert(String name, Player player);
}
