package me.hsgamer.bettergui.converter;

import me.hsgamer.bettergui.BetterGUI;
import me.hsgamer.bettergui.converter.command.ConvertMenuCommand;
import me.hsgamer.hscore.bukkit.addon.PluginAddon;

public final class Converter extends PluginAddon {
    @Override
    public void onEnable() {
        BetterGUI.getInstance().getCommandManager().register(new ConvertMenuCommand(this));
    }
}
