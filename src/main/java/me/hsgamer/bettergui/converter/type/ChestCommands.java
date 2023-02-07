package me.hsgamer.bettergui.converter.type;

import me.hsgamer.bettergui.converter.api.converter.ConverterType;
import org.bukkit.Bukkit;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ChestCommands implements ConverterType {
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().isPluginEnabled("ChestCommands");
    }

    @Override
    public List<String> getNames() {
        return Collections.emptyList();
    }

    @Override
    public Optional<Map<String, Object>> convert(String name) {
        return Optional.empty();
    }
}
