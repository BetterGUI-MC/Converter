package me.hsgamer.bettergui.converter.item;

import me.hsgamer.bettergui.converter.api.object.ConvertObject;
import me.hsgamer.bettergui.converter.api.unit.ConvertUnit;
import me.hsgamer.bettergui.converter.api.unit.SimpleConvertUnit;
import me.hsgamer.bettergui.converter.util.StringUtil;
import me.hsgamer.hscore.bukkit.item.ItemModifier;
import me.hsgamer.hscore.bukkit.item.modifier.*;
import me.hsgamer.hscore.common.CollectionUtils;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class ItemConvertUnit extends SimpleConvertUnit {
    private final Function<ItemStack, Object> itemGetter;

    public ItemConvertUnit(int priority, String key, UnaryOperator<Object> converter, Function<ItemStack, Object> itemGetter) {
        super(priority, key, converter);
        this.itemGetter = itemGetter;
    }

    public ItemConvertUnit(String key, UnaryOperator<Object> converter, Function<ItemStack, Object> itemGetter) {
        super(key, converter);
        this.itemGetter = itemGetter;
    }

    public ItemConvertUnit(int priority, String key, Function<ItemStack, Object> itemGetter) {
        super(priority, key);
        this.itemGetter = itemGetter;
    }

    public ItemConvertUnit(String key, Function<ItemStack, Object> itemGetter) {
        super(key);
        this.itemGetter = itemGetter;
    }

    public static Function<ItemStack, Object> fromItemMeta(Function<ItemMeta, Object> itemMetaGetter) {
        return itemStack -> {
            ItemMeta itemMeta = itemStack.getItemMeta();
            return itemMeta != null ? itemMetaGetter.apply(itemMeta) : null;
        };
    }

    public static <T extends ItemMeta> Function<ItemStack, Object> fromItemMeta(Class<T> itemMetaClass, Function<T, Object> itemMetaGetter) {
        return fromItemMeta(itemMeta -> itemMetaClass.isInstance(itemMeta) ? itemMetaGetter.apply(itemMetaClass.cast(itemMeta)) : null);
    }

    public static Function<ItemStack, Object> fromModifier(Supplier<ItemModifier> itemModifierSupplier) {
        return itemStack -> {
            ItemModifier itemModifier = itemModifierSupplier.get();
            if (!itemModifier.canLoadFromItemStack(itemStack)) return null;
            itemModifier.loadFromItemStack(itemStack);
            return itemModifier.toObject();
        };
    }

    public static Set<ConvertObject> getFromItem(ItemStack itemStack, ItemConvertUnit... extraUnits) {
        Set<ItemConvertUnit> units = Arrays.stream(Standard.values()).map(Standard::getUnit).collect(Collectors.toSet());
        units.addAll(Arrays.asList(extraUnits));
        return units.stream().map(unit -> unit.createFromItem(itemStack)).collect(Collectors.toSet());
    }

    public ConvertObject createFromItem(ItemStack itemStack) {
        return create(itemGetter.apply(itemStack));
    }

    public enum Standard {
        ID(new ItemConvertUnit(0, "id", fromModifier(MaterialModifier::new))),
        AMOUNT(new ItemConvertUnit(1, "amount", fromModifier(AmountModifier::new))),
        DURABILITY(new ItemConvertUnit(2, "durability", fromModifier(DurabilityModifier::new))),
        DISPLAY_NAME(new ItemConvertUnit(3, "name", fromModifier(NameModifier::new).andThen(o -> StringUtil.reverseColor(String.valueOf(o))))),
        LORE(new ItemConvertUnit(4, "lore", fromModifier(LoreModifier::new).andThen(o -> CollectionUtils.createStringListFromObject(o).stream().map(StringUtil::reverseColor).collect(Collectors.toList())))),
        ITEM_FLAGS(new ItemConvertUnit(5, "flags", fromModifier(ItemFlagModifier::new).andThen(o -> {
            List<String> list = CollectionUtils.createStringListFromObject(o);
            boolean all = true;
            for (ItemFlag flag : ItemFlag.values()) {
                if (!list.contains(flag.name())) {
                    all = false;
                    break;
                }
            }
            return all ? "ALL" : o;
        }))),
        SKULL_OWNER(new ItemConvertUnit(6, "skull-owner", fromModifier(SkullModifier::new))),
        ENCHANT(new ItemConvertUnit(7, "enchant", fromModifier(EnchantmentModifier::new))),
        POTION_EFFECT(new ItemConvertUnit(8, "potion", fromModifier(PotionEffectModifier::new))),
        ;

        private final ItemConvertUnit unit;

        Standard(ItemConvertUnit unit) {
            this.unit = unit;
        }

        public ItemConvertUnit getUnit() {
            return unit;
        }
    }

    public enum Extra {
        SLOT(new SimpleConvertUnit("slot"));

        private final ConvertUnit unit;

        Extra(ConvertUnit unit) {
            this.unit = unit;
        }

        public ConvertUnit getUnit() {
            return unit;
        }
    }
}
