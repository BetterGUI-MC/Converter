package me.hsgamer.bettergui.converter.menu;

import me.hsgamer.bettergui.converter.api.unit.ConvertMapUnit;
import me.hsgamer.bettergui.converter.api.unit.SimpleConvertMapUnit;
import me.hsgamer.bettergui.converter.util.StringUtil;

public class MenuConvertUnit {
    public static final ConvertMapUnit TITLE = new SimpleConvertMapUnit("title", o -> StringUtil.reverseColor(String.valueOf(o)));
    public static final ConvertMapUnit ROWS = new SimpleConvertMapUnit("rows");
    public static final ConvertMapUnit SLOTS = new SimpleConvertMapUnit("slots");
    public static final ConvertMapUnit PERMISSION = new SimpleConvertMapUnit("permission");
    public static final ConvertMapUnit COMMAND = new SimpleConvertMapUnit("command");
    public static final ConvertMapUnit TICKS = new SimpleConvertMapUnit("ticks");
    public static final ConvertMapUnit TYPE = new SimpleConvertMapUnit("inventory-type");

    private MenuConvertUnit() {
        // EMPTY
    }
}
