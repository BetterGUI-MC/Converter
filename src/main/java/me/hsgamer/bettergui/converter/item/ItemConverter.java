package me.hsgamer.bettergui.converter.item;

import me.hsgamer.bettergui.converter.api.object.ConvertSet;
import org.bukkit.inventory.ItemStack;

public class ItemConverter extends ConvertSet {
    public void add(ItemStack itemStack, ItemConvertUnit... extraUnits) {
        ItemConvertUnit.getFromItem(itemStack, extraUnits).forEach(this::add);
    }
}
