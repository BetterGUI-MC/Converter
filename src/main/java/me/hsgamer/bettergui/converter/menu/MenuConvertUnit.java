package me.hsgamer.bettergui.converter.menu;

import me.hsgamer.bettergui.converter.api.unit.ConvertUnit;
import me.hsgamer.bettergui.converter.api.unit.SimpleConvertUnit;
import me.hsgamer.bettergui.converter.util.StringUtil;

public class MenuConvertUnit {
    public static final ConvertUnit TITLE = new SimpleConvertUnit("title", o -> StringUtil.reverseColor(String.valueOf(o)));
    public static final ConvertUnit ROWS = new SimpleConvertUnit("rows");
    public static final ConvertUnit SLOTS = new SimpleConvertUnit("slots");
    public static final ConvertUnit PERMISSION = new SimpleConvertUnit("permission");
    public static final ConvertUnit COMMAND = new SimpleConvertUnit("command");
    public static final ConvertUnit TICKS = new SimpleConvertUnit("ticks");
    public static final ConvertUnit TYPE = new SimpleConvertUnit("inventory-type");

    private MenuConvertUnit() {
        // EMPTY
    }
}
