package me.hsgamer.bettergui.converter.command;

import me.hsgamer.bettergui.BetterGUI;
import me.hsgamer.bettergui.converter.Converter;
import me.hsgamer.bettergui.converter.api.converter.ConverterType;
import me.hsgamer.bettergui.converter.manager.ConverterManager;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.config.PathString;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ConvertMenuCommand extends Command {
    private static final Permission PERMISSION = new Permission("bettergui.convertmenu", "Convert a menu from another plugin", PermissionDefault.OP);
    private final Converter addon;

    public ConvertMenuCommand(Converter addon) {
        super("convertmenu", "Convert a menu from another plugin", "/convertmenu <plugin> [menu]", Collections.singletonList("cm"));
        this.addon = addon;
        setPermission(PERMISSION.getName());
    }

    private static CompletableFuture<Void> convert(ConverterType converter, File convertedFolder, String name, Player player) {
        return CompletableFuture.runAsync(() -> {
            File file = new File(convertedFolder, name.toLowerCase().endsWith(".yml") ? name : name + ".yml");
            if (file.exists()) {
                file.delete();
            }
            Optional<Map<String, Object>> optionalConverted = converter.convert(name, player);
            if (optionalConverted.isPresent()) {
                BukkitConfig config = new BukkitConfig(file);
                config.setup();
                optionalConverted.get().forEach((k, v) -> config.set(new PathString(k), v));
                config.save();
                MessageUtils.sendMessage(player, "&aConverted: " + file.getName());
            } else {
                MessageUtils.sendMessage(player, "&cFailed to convert: " + name);
            }
        });
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        if (!testPermission(sender)) {
            return false;
        }
        if (!(sender instanceof Player)) {
            MessageUtils.sendMessage(sender, BetterGUI.getInstance().getMessageConfig().getPlayerOnly());
            return false;
        }
        if (args.length < 1) {
            MessageUtils.sendMessage(sender, "&cUsage: " + getUsage());
            return false;
        }
        ConverterType converter = ConverterManager.getConverterType(args[0]);
        if (converter == null) {
            MessageUtils.sendMessage(sender, "&cUnknown converter: " + args[0]);
            return false;
        }
        MessageUtils.sendMessage(sender, "&aConverting...");
        File convertedFolder = new File(addon.getDataFolder(), converter.getName());
        if (!convertedFolder.exists()) {
            convertedFolder.mkdirs();
            MessageUtils.sendMessage(sender, "&aCreated folder: " + convertedFolder.getName());
        }

        CompletableFuture<Void> future;
        if (args.length > 1) {
            future = convert(converter, convertedFolder, args[1], (Player) sender);
        } else {
            future = CompletableFuture.allOf(converter.getNames().stream().map(name -> convert(converter, convertedFolder, name, (Player) sender)).toArray(CompletableFuture[]::new));
        }
        future.whenComplete((aVoid, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
            }
            MessageUtils.sendMessage(sender, "&aDone");
        });
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        if (args.length == 1) {
            return ConverterManager.getConverterTypeNames();
        } else if (args.length == 2) {
            ConverterType converter = ConverterManager.getConverterType(args[0]);
            if (converter != null) {
                return converter.getNames();
            }
        }
        return Collections.emptyList();
    }
}
