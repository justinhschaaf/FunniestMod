package com.justinschaaf.funniestmod;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

public class FunniestEvents implements Listener {

    private FunniestMod plugin;

    public FunniestEvents(FunniestMod plugin) {
        this.plugin = plugin;
    }

    // Buff mob equipment
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {

        if (!plugin.enabled) return;

        if (event.getEntityType() == EntityType.ZOMBIE) {

            Zombie z = (Zombie) event.getEntity();
            z.setBaby();

            // Generate Equipment
            ItemStack helm = new ItemStack(Material.NETHERITE_HELMET);
            helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            helm.getItemMeta().setUnbreakable(true);
            z.getEquipment().setHelmet(helm);

            ItemStack chest = new ItemStack(Material.NETHERITE_CHESTPLATE);
            chest.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            helm.getItemMeta().setUnbreakable(true);
            z.getEquipment().setChestplate(chest);

            ItemStack legs = new ItemStack(Material.NETHERITE_LEGGINGS);
            legs.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            helm.getItemMeta().setUnbreakable(true);
            z.getEquipment().setLeggings(legs);

            ItemStack boots = new ItemStack(Material.NETHERITE_BOOTS);
            boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            helm.getItemMeta().setUnbreakable(true);
            z.getEquipment().setBoots(boots);

        } else if (event.getEntityType() == EntityType.SKELETON) {

            Skeleton s = (Skeleton) event.getEntity();

            ItemStack helm = new ItemStack(Material.IRON_HELMET);
            helm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            helm.getItemMeta().setUnbreakable(true);
            s.getEquipment().setHelmet(helm);

            ItemStack bow = new ItemStack(Material.BOW);
            bow.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 10);
            bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 3);
            bow.getItemMeta().setUnbreakable(true);
            s.getEquipment().setItemInMainHand(bow);

        }

    }

    // Create an explosion on mob death
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        if (!plugin.enabled) return;

        if (event.getEntityType() != EntityType.PLAYER) {

            event.getEntity().getWorld().createExplosion(
                    event.getEntity().getLocation(),
                    (int) Math.ceil(event.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * 0.25d),
                    false,
                    true
            );

        }

    }

    // On Block Fall
    @EventHandler
    public void onBlockLand(EntityChangeBlockEvent event) {

        if (!plugin.enabled) return;

        if (event.getEntityType() == EntityType.FALLING_BLOCK && event.getEntity().isOnGround()) {

            FallingBlock b = (FallingBlock) event.getEntity();

            if (b.getBlockData().getMaterial() == Material.ANVIL) {

                // Override with our own logic
                //event.setCancelled(true);

                // Remove the block under
                Block target = b.getWorld().getBlockAt(b.getLocation().toBlockLocation().add(0, -1, 0));
                if (
                        target.getType() != Material.OBSIDIAN &&
                        target.getType() != Material.CRYING_OBSIDIAN &&
                        target.getType() != Material.BEDROCK &&
                        target.getType() != Material.AIR &&
                        target.getType() != Material.END_PORTAL_FRAME &&
                        target.getType() != Material.NETHER_PORTAL &&
                        target.getType() != Material.END_PORTAL
                ) {
                    target.setType(Material.AIR);
                }

                // Kill the falling block entity
                //b.remove();

            }

        }

    }

    // On Block Break
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        if (!plugin.enabled) return;

        event.getBlock().getWorld().createExplosion(
                event.getBlock().getLocation(),
                (int) Math.ceil(5 * Math.pow(Math.random(), 3)),
                false,
                true
        );

    }

}
