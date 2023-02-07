package me.hsgamer.bettergui.converter.menu;

import me.hsgamer.bettergui.converter.api.unit.ConvertUnit;
import me.hsgamer.hscore.bukkit.gui.BukkitGUIUtils;

public class MenuConvertUnit {
    public static final ConvertUnit TITLE = new ConvertUnit("title");
    public static final ConvertUnit ROWS = new ConvertUnit("rows");
    public static final ConvertUnit SIZE = new ConvertUnit("rows", o -> {
        try {
            int size = Integer.parseInt(String.valueOf(o));
            return BukkitGUIUtils.normalizeToChestSize(size) / 9;
        } catch (NumberFormatException e) {
            return null;
        }
    });

    private MenuConvertUnit() {
        // EMPTY
    }
}
