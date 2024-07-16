package me.hsgamer.bettergui.converter;

import io.github.projectunified.minelib.plugin.command.CommandComponent;
import me.hsgamer.bettergui.BetterGUI;
import me.hsgamer.bettergui.converter.command.ConvertMenuCommand;
import me.hsgamer.hscore.expansion.common.Expansion;
import me.hsgamer.hscore.expansion.extra.expansion.DataFolder;

public final class Converter implements Expansion, DataFolder {
    @Override
    public void onEnable() {
        BetterGUI.getInstance().get(CommandComponent.class).register(new ConvertMenuCommand(this));
    }
}
