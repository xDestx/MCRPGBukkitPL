package me.xDest.mcrpg.listener;

import java.util.UUID;

import me.xDest.mcrpg.Messenger;
import me.xDest.mcrpg.Stats;
import me.xDest.mcrpg.manager.Manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class LevelPointGUIListener implements Listener {

	@EventHandler
	public void inventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getName().equalsIgnoreCase("Stats")) {
			event.setCancelled(true);
			return;
		}
		if (event.getInventory().getName().substring(0,10).equalsIgnoreCase("Use Points")) {
			event.setCancelled(true);
			if (event.getCurrentItem().getType() == null) {
				return;
			}
			Material clicked = event.getCurrentItem().getType();
			
			if (clicked.equals(Material.BLAZE_POWDER)) {
				Stats s = Manager.getStats((Player) event.getWhoClicked());
				if (s.addHasteStat(1)) {
					event.getInventory().getItem(event.getRawSlot()).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
					ItemMeta im = event.getInventory().getItem(event.getRawSlot()).getItemMeta();
					im.setDisplayName(ChatColor.GOLD + "Haste " + ChatColor.GREEN + s.getHasteStat() + ChatColor.RED + "/" + s.getMaxHaste());
					event.getInventory().getItem(event.getRawSlot()).setItemMeta(im);
					s.removeLvlPts(1);
				} else {
					((Player)event.getWhoClicked()).sendMessage(ChatColor.RED + "This stat is already maxed.");
				}
				
			} else if (clicked.equals(Material.APPLE)) {
				
				Stats s = Manager.getStats((Player) event.getWhoClicked());
				
				if (s.addHPStat(1)) {
					event.getInventory().getItem(event.getRawSlot()).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
					ItemMeta im = event.getInventory().getItem(event.getRawSlot()).getItemMeta();
					im.setDisplayName(ChatColor.DARK_RED + "Health " + ChatColor.GREEN + s.getHPStat() + ChatColor.RED + "/" + s.getMaxHP());
					event.getInventory().getItem(event.getRawSlot()).setItemMeta(im);
					s.removeLvlPts(1);
				} else {
					((Player)event.getWhoClicked()).sendMessage(ChatColor.RED + "This stat is already maxed.");
				}
			} else if (clicked.equals(Material.FURNACE)) {
				Stats s = Manager.getStats((Player) event.getWhoClicked());
				if (s.addFireStat(1)) {
					s.removeLvlPts(1);
					event.getInventory().getItem(event.getRawSlot()).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
					ItemMeta im = event.getInventory().getItem(event.getRawSlot()).getItemMeta();
					im.setDisplayName(ChatColor.DARK_RED + "Fire Resistance " + ChatColor.GREEN + s.getFireStat() + ChatColor.RED + "/" + s.getMaxFire());
					event.getInventory().getItem(event.getRawSlot()).setItemMeta(im);
				} else {
					((Player)event.getWhoClicked()).sendMessage(ChatColor.RED + "You cannot choose this because you have already enabled poison immunity, or already enabled fire immunity.");
				}
				
			} else if (clicked.equals(Material.FERMENTED_SPIDER_EYE)) {
				Stats s = Manager.getStats((Player) event.getWhoClicked());
				if (s.addPoisonStat(1)) {
					s.removeLvlPts(1);
					event.getInventory().getItem(event.getRawSlot()).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
					ItemMeta im = event.getInventory().getItem(event.getRawSlot()).getItemMeta();
					im.setDisplayName(ChatColor.DARK_GREEN + "Poison Resistance " + ChatColor.GREEN + s.getPoisonStat() + ChatColor.RED + "/" + s.getMaxPoison());
					event.getInventory().getItem(event.getRawSlot()).setItemMeta(im);
				} else {
					((Player)event.getWhoClicked()).sendMessage(ChatColor.RED + "You cannot choose this because you have already enabled fire immunity, or already enabled poison immunity.");
				}
				
			} else if (clicked.equals(Material.DIAMOND_CHESTPLATE)) {
				Stats s = Manager.getStats((Player) event.getWhoClicked());
				if (s.addResStat(1)) {
					s.removeLvlPts(1);
					event.getInventory().getItem(event.getRawSlot()).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
					ItemMeta im = event.getInventory().getItem(event.getRawSlot()).getItemMeta();
					im.setDisplayName(ChatColor.DARK_RED + "Resistance " + ChatColor.GREEN + s.getResStat() + ChatColor.RED + "/" + s.getMaxRes());
					event.getInventory().getItem(event.getRawSlot()).setItemMeta(im);
				} else {
					((Player)event.getWhoClicked()).sendMessage(ChatColor.RED + "This stat is already maxed.");
				}
				
			} else if (clicked.equals(Material.DIAMOND_HELMET)) {
				Stats s = Manager.getStats((Player) event.getWhoClicked());
				if (s.addWaterStat(1)) {
					s.removeLvlPts(1);
					event.getInventory().getItem(event.getRawSlot()).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
					ItemMeta im = event.getInventory().getItem(event.getRawSlot()).getItemMeta();
					im.setDisplayName(ChatColor.DARK_RED + "Resistance " + ChatColor.GREEN + s.getWaterStat() + ChatColor.RED + "/" + s.getMaxWater());
					event.getInventory().getItem(event.getRawSlot()).setItemMeta(im);
				} else {
					((Player)event.getWhoClicked()).sendMessage(ChatColor.RED + "This stat is already maxed.");
				}
				
			} else if (clicked.equals(Material.DIAMOND_SWORD)) {
				Stats s = Manager.getStats((Player) event.getWhoClicked());
				if (s.addStrStat(1)) {
					s.removeLvlPts(1);
					event.getInventory().getItem(event.getRawSlot()).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
					ItemMeta im = event.getInventory().getItem(event.getRawSlot()).getItemMeta();
					im.setDisplayName(ChatColor.DARK_RED + "Strength " + ChatColor.GREEN + s.getStrStat() + ChatColor.RED + "/" + s.getMaxStr());
					event.getInventory().getItem(event.getRawSlot()).setItemMeta(im);
				} else {
					((Player)event.getWhoClicked()).sendMessage(ChatColor.RED + "This stat is already maxed.");
				}
				
			} else if (clicked.equals(Material.FEATHER)) {
				Stats s = Manager.getStats((Player) event.getWhoClicked());
				if (s.addSpeedStat(1)) {
					s.removeLvlPts(1);
					event.getInventory().getItem(event.getRawSlot()).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
					ItemMeta im = event.getInventory().getItem(event.getRawSlot()).getItemMeta();
					im.setDisplayName("Speed " + ChatColor.GREEN + s.getSpeedStat() + ChatColor.RED + "/" + s.getMaxSpeed());
					event.getInventory().getItem(event.getRawSlot()).setItemMeta(im);
				} else {
					((Player)event.getWhoClicked()).sendMessage(ChatColor.RED + "This stat is already maxed.");
				}
				
			} else if (clicked.equals(Material.GOLDEN_APPLE)) {
				Stats s = Manager.getStats((Player) event.getWhoClicked());
				if (s.addRegenStat(1)) {
					s.removeLvlPts(1);
					event.getInventory().getItem(event.getRawSlot()).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
					ItemMeta im = event.getInventory().getItem(event.getRawSlot()).getItemMeta();
					im.setDisplayName(ChatColor.RED + "Regeneration " + ChatColor.GREEN + s.getRegenStat() + ChatColor.RED + "/" + s.getMaxRegen());
					event.getInventory().getItem(event.getRawSlot()).setItemMeta(im);
				} else {
					((Player)event.getWhoClicked()).sendMessage(ChatColor.RED + "This stat is already maxed.");
				}
				
			} else if (clicked.equals(Material.EYE_OF_ENDER)) {
				Stats s = Manager.getStats((Player) event.getWhoClicked());
				if (s.addNightStat(1)) {
					s.removeLvlPts(1);
					event.getInventory().getItem(event.getRawSlot()).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
					ItemMeta im = event.getInventory().getItem(event.getRawSlot()).getItemMeta();
					im.setDisplayName(ChatColor.DARK_PURPLE + "Night Vision " + ChatColor.GREEN + s.getNightStat() + ChatColor.RED + "/" + s.getMaxNv());
					event.getInventory().getItem(event.getRawSlot()).setItemMeta(im);
				} else {
					((Player)event.getWhoClicked()).sendMessage(ChatColor.RED + "This stat is already maxed.");
				}
				
			} else if (clicked.equals(Material.LEATHER_BOOTS)) {
				Stats s = Manager.getStats((Player) event.getWhoClicked());
				if (s.addJumpStat(1)) {
					s.removeLvlPts(1);
					event.getInventory().getItem(event.getRawSlot()).addUnsafeEnchantment(Enchantment.DURABILITY, 1);
					ItemMeta im = event.getInventory().getItem(event.getRawSlot()).getItemMeta();
					im.setDisplayName(ChatColor.GREEN + "Jump " + ChatColor.GREEN + s.getJumpStat() + ChatColor.RED + "/" + s.getMaxJump());
					event.getInventory().getItem(event.getRawSlot()).setItemMeta(im);
				} else {
					((Player)event.getWhoClicked()).sendMessage(ChatColor.RED + "This stat is already maxed.");
				}
				
			}
			if (Manager.getStats((Player) event.getWhoClicked()).getLvlPts() <= 0) {
				event.getInventory().clear();
				event.getWhoClicked().closeInventory();
				Manager.forceStats((Player) event.getWhoClicked(), false);
			}
		}
	}
	
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (event.getInventory().getName().length() < 10) {
			return;
		}
		if (event.getInventory().getName().substring(0, 10).equalsIgnoreCase("Use Points")) {
			event.getPlayer().addPotionEffects(Manager.getStats((Player) event.getPlayer()).getBonuses());
			Stats s = Manager.getStats((Player) event.getPlayer());
			int lvl = s.getLevelOfPlayer();
			if (lvl >= 20 && lvl < 40) {
				//if (s.hasNoTier(1)) {
				//	Manager.openTierGUI(1);
				//}
			}
		}
	}
	
	
}
