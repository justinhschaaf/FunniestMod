package com.justinschaaf.funniestmod;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class FunniestMod extends JavaPlugin {

    // Whether or not the madness is enabled
    public boolean enabled = false;

    @Override
    public void onEnable() {
        super.onEnable();

        getCommand("togglefunniest").setExecutor(new ToggleCommand(this));
        getServer().getPluginManager().registerEvents(new FunniestEvents(this), this);

        getServer().getScheduler().runTaskTimer(this, () -> {

            if (enabled) {

                for (Player p : getServer().getOnlinePlayers()) {

                    if (Math.random() <= .75) {

                        FallingBlock b = p.getWorld().spawnFallingBlock(p.getLocation().add(0, 2, 0), Bukkit.createBlockData(Material.ANVIL));

                        b.setDropItem(false);
                        b.setVelocity(new Vector(Math.random() * .1, 1, Math.random() * .1));

                    }

                }

            }

        }, 15, 15);

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
