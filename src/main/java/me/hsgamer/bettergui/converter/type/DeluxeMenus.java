package me.hsgamer.bettergui.converter.type;

import com.extendedclip.deluxemenus.menu.Menu;
import me.hsgamer.bettergui.converter.api.converter.ConverterType;
import me.hsgamer.bettergui.converter.menu.MenuConverter;
import me.hsgamer.bettergui.converter.menu.type.SimpleMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.*;

public class DeluxeMenus implements ConverterType {
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().isPluginEnabled("DeluxeMenus");
    }

    @Override
    public List<String> getNames() {
        try {
            Field field = Menu.class.getDeclaredField("menus");
            field.setAccessible(true);
            // noinspection unchecked
            Map<String, Menu> menus = (Map<String, Menu>) field.get(null);
            return new ArrayList<>(menus.keySet());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Map<String, Object>> convert(String name, Player player) {
        Menu menu = Menu.getMenu(name);
        if (menu == null) {
            return Optional.empty();
        }

        SimpleMenu simpleMenu = new SimpleMenu();
        MenuConverter menuConverter = simpleMenu.getMenu();
        // TODO: menu settings

        // TODO: menu items

        return Optional.of(simpleMenu.toMap());
    }
}
