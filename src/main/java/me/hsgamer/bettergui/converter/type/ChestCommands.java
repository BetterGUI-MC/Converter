package me.hsgamer.bettergui.converter.type;

import me.filoghost.chestcommands.api.Icon;
import me.filoghost.chestcommands.inventory.Grid;
import me.filoghost.chestcommands.menu.InternalMenu;
import me.filoghost.chestcommands.menu.MenuManager;
import me.hsgamer.bettergui.converter.api.converter.ConverterType;
import me.hsgamer.bettergui.converter.item.ItemConverter;
import me.hsgamer.bettergui.converter.menu.MenuConvertUnit;
import me.hsgamer.bettergui.converter.menu.MenuConverter;
import me.hsgamer.bettergui.converter.menu.type.SimpleMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ChestCommands implements ConverterType {
    public static boolean isAvailable() {
        return Bukkit.getPluginManager().isPluginEnabled("ChestCommands");
    }

    private Optional<String> getOpenCommand(InternalMenu menu) {
        try {
            Field field = MenuManager.class.getDeclaredField("menusByOpenCommand");
            field.setAccessible(true);
            // noinspection unchecked
            Map<String, InternalMenu> map = (Map<String, InternalMenu>) field.get(null);
            for (Map.Entry<String, InternalMenu> entry : map.entrySet()) {
                if (entry.getValue().equals(menu)) {
                    return Optional.of(entry.getKey());
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<String> getNames() {
        return new ArrayList<>(MenuManager.getMenuFileNames());
    }

    @Override
    public Optional<Map<String, Object>> convert(String name, Player player) {
        InternalMenu menu = MenuManager.getMenuByFileName(name);
        if (menu == null) {
            return Optional.empty();
        }
        SimpleMenu simpleMenu = new SimpleMenu();

        MenuConverter menuConverter = new MenuConverter();
        simpleMenu.setMenu(menuConverter);
        menuConverter.add(MenuConvertUnit.TITLE.create(menu.getTitle()));
        menuConverter.add(MenuConvertUnit.ROWS.create(menu.getRows()));
        menuConverter.add(MenuConvertUnit.PERMISSION.create(menu.getOpenPermission()));
        menuConverter.add(MenuConvertUnit.TICKS.create(menu.getRefreshTicks()));
        getOpenCommand(menu).ifPresent(s -> menuConverter.add(MenuConvertUnit.COMMAND.create(s)));

        Grid<Icon> grid = menu.getIcons();
        int size = grid.getSize();
        for (int i = 0; i < size; i++) {
            int finalI = i;
            Optional.ofNullable(grid.getByIndex(i))
                    .map(icon -> icon.render(player))
                    .map(item -> {
                        ItemConverter itemConverter = new ItemConverter();
                        itemConverter.add(item);
                        return itemConverter;
                    })
                    .ifPresent(itemConverter -> simpleMenu.addItem(Integer.toString(finalI), itemConverter));
        }

        return Optional.of(simpleMenu.toMap());
    }
}
