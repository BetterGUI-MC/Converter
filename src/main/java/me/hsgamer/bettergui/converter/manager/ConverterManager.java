package me.hsgamer.bettergui.converter.manager;

import me.hsgamer.bettergui.converter.api.converter.ConverterType;
import me.hsgamer.bettergui.converter.type.ChestCommands;
import me.hsgamer.bettergui.converter.type.DeluxeMenus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class ConverterManager {
    private static final Map<String, ConverterType> CONVERTER_TYPE_MAP = new HashMap<>();

    static {
        registerConverterType(ChestCommands::new, ChestCommands::isAvailable, "chestcommands", "cc");
        registerConverterType(DeluxeMenus::new, DeluxeMenus::isAvailable, "deluxemenus", "dm");
    }

    private ConverterManager() {
        // EMPTY
    }

    public static void registerConverterType(Supplier<ConverterType> converterTypeSupplier, String... name) {
        ConverterType converterType = converterTypeSupplier.get();
        for (String s : name) {
            CONVERTER_TYPE_MAP.put(s, converterType);
        }
    }

    public static void registerConverterType(Supplier<ConverterType> converterTypeSupplier, BooleanSupplier condition, String... name) {
        if (condition.getAsBoolean()) {
            registerConverterType(converterTypeSupplier, name);
        }
    }

    public static ConverterType getConverterType(String name) {
        return CONVERTER_TYPE_MAP.get(name);
    }
}
