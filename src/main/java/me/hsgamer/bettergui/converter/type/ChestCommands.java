package me.hsgamer.bettergui.converter.type;

import me.filoghost.chestcommands.api.ConfigurableIcon;
import me.filoghost.chestcommands.api.Icon;
import me.filoghost.chestcommands.inventory.Grid;
import me.filoghost.chestcommands.menu.InternalMenu;
import me.filoghost.chestcommands.menu.MenuManager;
import me.hsgamer.bettergui.converter.api.converter.ConverterType;
import me.hsgamer.bettergui.converter.item.ItemConvertUnit;
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

    private static ItemConverter convert(Icon icon, Player player) {
        ItemConverter converter = new ItemConverter();
        if (icon instanceof ConfigurableIcon) {
            ConfigurableIcon configurableIcon = (ConfigurableIcon) icon;
            converter.add(ItemConvertUnit.Standard.ID.getUnit().create(configurableIcon.getMaterial().name()));
            converter.add(ItemConvertUnit.Standard.AMOUNT.getUnit().create(configurableIcon.getAmount()));
            converter.add(ItemConvertUnit.Standard.DURABILITY.getUnit().create(configurableIcon.getDurability()));
            Optional.ofNullable(configurableIcon.getNBTData()).ifPresent(s -> converter.add(ItemConvertUnit.Extra.NBT.getUnit().create(s)));
            Optional.ofNullable(configurableIcon.getName()).ifPresent(s -> converter.add(ItemConvertUnit.Standard.NAME.getUnit().create(s)));
            Optional.ofNullable(configurableIcon.getLore()).ifPresent(s -> converter.add(ItemConvertUnit.Standard.LORE.getUnit().create(s)));
            Optional.ofNullable(configurableIcon.getEnchantments()).ifPresent(map -> {
                List<String> enchantments = new ArrayList<>();
                map.forEach((enchantment, integer) -> enchantments.add(enchantment.getName() + ", " + integer));
                converter.add(ItemConvertUnit.Standard.ENCHANT.getUnit().create(enchantments));
            });
            Optional.ofNullable(configurableIcon.getSkullOwner()).ifPresent(list -> converter.add(ItemConvertUnit.Standard.SKULL_OWNER.getUnit().create(list)));
        } else {
            converter.add(icon.render(player));
        }
        return converter;
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
        MenuConverter menuConverter = simpleMenu.getMenu();
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
                    .map(icon -> convert(icon, player))
                    .map(itemConverter -> {
                        itemConverter.addSlot(finalI);
                        return itemConverter;
                    })
                    .ifPresent(itemConverter -> simpleMenu.addItem(Integer.toString(finalI), itemConverter));
        }

        return Optional.of(simpleMenu.toMap());
    }
}
