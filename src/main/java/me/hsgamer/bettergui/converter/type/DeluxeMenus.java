package me.hsgamer.bettergui.converter.type;

import com.extendedclip.deluxemenus.menu.Menu;
import com.extendedclip.deluxemenus.menu.MenuHolder;
import me.hsgamer.bettergui.converter.api.converter.ConverterType;
import me.hsgamer.bettergui.converter.item.ItemConverter;
import me.hsgamer.bettergui.converter.menu.MenuConvertUnit;
import me.hsgamer.bettergui.converter.menu.MenuConverter;
import me.hsgamer.bettergui.converter.menu.type.SimpleMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        menuConverter.add(MenuConvertUnit.TITLE.create(menu.getMenuTitle()));
        menuConverter.add(MenuConvertUnit.SLOTS.create(menu.getSize()));
        menuConverter.add(MenuConvertUnit.PERMISSION.create(menu.getPermission()));
        Optional.ofNullable(menu.getInventoryType()).ifPresent(type -> menuConverter.add(MenuConvertUnit.TYPE.create(type.name())));
        if (menu.registersCommand()) {
            menuConverter.add(MenuConvertUnit.COMMAND.create(menu.getMenuCommands()));
        }

        MenuHolder menuHolder = new MenuHolder(player);
        menu.getMenuItems().forEach((slot, priorityMap) -> {
            priorityMap.forEach((priority, menuItem) -> {
                ItemStack itemStack = menuItem.getItemStack(menuHolder);
                if (itemStack == null) {
                    return;
                }
                ItemConverter itemConverter = new ItemConverter();
                itemConverter.add(itemStack);
                itemConverter.addSlot(slot);
                simpleMenu.addItem(slot + "-" + priority, itemConverter);
            });
        });

        return Optional.of(simpleMenu.toMap());
    }
}
