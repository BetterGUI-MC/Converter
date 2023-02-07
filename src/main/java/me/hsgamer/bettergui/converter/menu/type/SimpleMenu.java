package me.hsgamer.bettergui.converter.menu.type;

import me.hsgamer.bettergui.converter.item.ItemConverter;
import me.hsgamer.bettergui.converter.menu.MenuConverter;

import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleMenu {
    private final Map<String, ItemConverter> itemConverterMap = new LinkedHashMap<>();
    private MenuConverter menuConverter;

    public MenuConverter getMenu() {
        if (menuConverter == null) {
            menuConverter = new MenuConverter();
        }
        return menuConverter;
    }

    public void setMenu(MenuConverter menuConverter) {
        this.menuConverter = menuConverter;
    }

    public Map<String, ItemConverter> getItemMap() {
        return itemConverterMap;
    }

    public void addItem(String name, ItemConverter itemConverter) {
        itemConverterMap.put(name, itemConverter);
    }

    public void removeItem(String name) {
        itemConverterMap.remove(name);
    }

    public ItemConverter getItem(String name) {
        return itemConverterMap.computeIfAbsent(name, s -> new ItemConverter());
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        if (menuConverter != null) {
            map.put("menu-settings", menuConverter.convert());
        }
        itemConverterMap.forEach((s, itemConverter) -> map.put(s, itemConverter.convert()));
        return map;
    }
}
