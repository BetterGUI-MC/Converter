package me.hsgamer.bettergui.converter.item;

import me.hsgamer.bettergui.converter.api.object.MapConverter;
import org.bukkit.inventory.ItemStack;

public class ItemConverter extends MapConverter {
    public void add(ItemStack itemStack, ItemConvertUnit... extraUnits) {
        ItemConvertUnit.getFromItem(itemStack, extraUnits).forEach(this::add);
    }

    public void addSlot(int slot) {
        add(ItemConvertUnit.Extra.SLOT.getUnit().create(slot));
    }
}
