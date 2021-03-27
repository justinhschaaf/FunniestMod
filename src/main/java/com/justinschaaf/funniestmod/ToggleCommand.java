package com.justinschaaf.funniestmod;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ToggleCommand implements CommandExecutor {

    private FunniestMod plugin;

    public ToggleCommand(FunniestMod plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (commandSender.hasPermission("funniest.toggle")) {

            plugin.enabled = !plugin.enabled;

            commandSender.sendMessage("§7The FUNNIEST mod is now §x§c§c§b§b§6§6" + (plugin.enabled ? "enabled" : "disabled") + "§7!");

            return true;

        }

        return false;

    }

}
