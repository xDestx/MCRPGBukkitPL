package me.xDest.mcrpg.listener;



import java.util.List;

import java.util.Random;

import me.xDest.mcrpg.PotionManager;
import me.xDest.mcrpg.SLAPI;
import me.xDest.mcrpg.manager.Manager;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RxpListener implements Listener {
	
	@EventHandler
	public void playerJoinEvent(PlayerJoinEvent event) {
		try {
			World w = event.getPlayer().getWorld();
			Location l = event.getPlayer().getLocation();
			w.strikeLightningEffect(l);
		} catch (Exception e) {
			
		}
		SLAPI.savePlayerData();
		SLAPI.loadPlayerData();
		Manager.setStats(event.getPlayer(), Manager.getStats(event.getPlayer()));
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onEntityDeath(EntityDeathEvent event) {
		//Taken from some nice guy on forums
		Entity e = event.getEntity();
		
		if(e.getLastDamageCause() instanceof EntityDamageByEntityEvent) {

			EntityDamageByEntityEvent nEvent = (EntityDamageByEntityEvent) e.getLastDamageCause();
			
			if(nEvent.getDamager() instanceof Player) {
				Player p = (Player) nEvent.getDamager();
				Manager.hasKilled(p, event.getEntityType());
				Random rnd = new Random();
				if (rnd.nextInt(5000) == 22)
				{
					Manager.getStats(p).addRxp(5000);
				}
				if ((Entity) event.getEntity() instanceof Player) {
					Player en = (Player) event.getEntity();
				
					Manager.hasKilledPlayer(p, en);
				}
			}
			
		}
		
		if (e instanceof Player)
		{
			Player p = (Player)e;
			int lvl = Manager.getStats(p).getLevelOfPlayer();
			if (lvl >= 40)
			{
				Block c = p.getLocation().getBlock();
				c.setType(Material.CHEST);
				Chest chest = (Chest)c.getState();
				List<ItemStack> drops = event.getDrops();
				Inventory cinv = chest.getInventory();
				for (int i = 0; i < cinv.getSize() - 1; i++)
				{
					ItemStack x;
					try {
						x = drops.get(i);
					} catch (IndexOutOfBoundsException exc)
					{
						x = null;
					}
					if (x == null)
					{
						cinv.addItem(new ItemStack(Material.AIR, 0));
					} else {
						cinv.addItem(x);
						drops.get(i).setType(Material.AIR);
						drops.get(i).setAmount(0);
					}
				}
				Block s =  new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY() + 1, p.getLocation().getBlockZ()).getBlock();
				s.setType(Material.SIGN_POST);
				Sign sign = (Sign)s.getState();
				sign.setLine(1, p.getName() + " died here");
				sign.update();
				
				
			}
		}
	}
	
	@EventHandler
	public void onRespawnEvent(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		if (player != null) {
			player.sendMessage(ChatColor.GREEN + "Not buffed? Try /forcestats!");
			Manager.forceStats(event.getPlayer(), true);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block b = event.getBlock();
		Player p = event.getPlayer();
		Material bt = b.getType();
		if(bt.equals(Material.COAL_ORE) || bt.equals(Material.IRON_ORE) || bt.equals(Material.GOLD_ORE) || bt.equals(Material.DIAMOND_ORE) || bt.equals(Material.GOLD_ORE) || bt.equals(Material.REDSTONE_ORE) || bt.equals(Material.EMERALD_ORE) || bt.equals(Material.QUARTZ_ORE)) {
			Manager.playerBrokeOre(p, bt);
			Random rnd = new Random();
			int x = rnd.nextInt(99);
			if (x >= 10 && x <= 19) {
				//p.getInventory().addItem(new ItemStack(Material.IRON_ORE));
			}
			ItemStack item = p.getItemInHand();
			if (item.getType() == Material.STONE_PICKAXE && b.getType() == Material.GOLD_ORE) {
			//	b.getLocation().getWorld().dropItemNaturally(new Location(b.getWorld(), b.getX(), b.getY() + 0.5D, b.getZ()), new ItemStack(b.getType(), 1));
			}
		} else {
			Manager.playerBrokeNorm(p, bt);
			Random rnd = new Random();
			int x = rnd.nextInt(99);
			if (x >= 10 && x <= 14) {
			//	p.getInventory().addItem(new ItemStack(Material.IRON_ORE));
			}
		}
		//Random rnd = new Random();
		//if (rnd.nextInt(10000) == 22)
		//{
		//	for (int i = 0; i < 4; i++)
			//{
			////	p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.IRON_BLOCK, 64));
			//}
		//}
	}
	
	@EventHandler
	public void onFurnaceGrab(FurnaceExtractEvent event) {
		if (event.getItemType().equals(Material.IRON_INGOT)) {
			Manager.playerSmelted(event.getPlayer(), "IRON_INGOT", event.getItemAmount());
		}
		if (event.getItemType().equals(Material.GOLD_INGOT)) {
			Manager.playerSmelted(event.getPlayer(), "GOLD_INGOT", event.getItemAmount());
		}
		if (event.getItemType().equals(Material.BRICK)) {
			Manager.playerSmelted(event.getPlayer(), "BRICK", event.getItemAmount());
		}
		if (event.getItemType().equals(Material.COOKED_BEEF) || event.getItemType().equals(Material.COOKED_CHICKEN) || event.getItemType().equals(Material.COOKED_FISH) || event.getItemType().equals(Material.BAKED_POTATO)) {
			Manager.playerSmelted(event.getPlayer(), "FOOD_ITEM", event.getItemAmount());
		}
		
	}
	
	@EventHandler (priority = EventPriority.MONITOR)
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		if(!(event.getDamager() instanceof Player))
			return;
		if((event.getDamager() instanceof Player) && (event.getEntity() instanceof Player)) {
			Player dmgr = (Player) event.getDamager();
			Player dmgd = (Player) event.getEntity();
			if (dmgr.getGameMode() == GameMode.ADVENTURE || dmgd.getGameMode() == GameMode.ADVENTURE) {
				event.setCancelled(true);
				return;
			}
		}
		Player p = (Player) event.getDamager();
		LivingEntity en = null;
		Player ep = null;
		Random rnd = new Random();
		int x = rnd.nextInt(99);
			//Messenger.info("DGFGFG " + x);
		try {
			ep = (Player) event.getEntity();
			if (x > 40 && x < 50) {
				ep.addPotionEffect(PotionManager.getPotionEffect("SLOW", 80, 200));
				ep.addPotionEffect(PotionManager.getPotionEffect("JUMP", 80, 200));
				ep.addPotionEffect(PotionManager.getPotionEffect("WEAKNESS", 80, 200));
				//ep.addPotionEffect(PotionManager.getPotionEffect("POISON", 80, 200));
				ep.addPotionEffect(PotionManager.getPotionEffect("BLINDNESS", 80, 200));
				if (event.getEntity() instanceof Player) {
					ep.sendMessage(ChatColor.DARK_RED + "YOU WERE STUNNED BY " + p.getName());
					p.sendMessage(ChatColor.DARK_RED + "YOU STUNNED " + ep.getName());
				} else {
					p.sendMessage(ChatColor.DARK_RED + "YOU STUNNED " + event.getEntity().getType());
				}
			}
		} catch (Exception e) {
			if (x > 40 && x < 50) {
				en = (LivingEntity) event.getEntity();
				en.addPotionEffect(PotionManager.getPotionEffect("SLOW", 80, 200));
				en.addPotionEffect(PotionManager.getPotionEffect("JUMP", 80, 200));
				en.addPotionEffect(PotionManager.getPotionEffect("WEAKNESS", 80, 200));
			//	en.addPotionEffect(PotionManager.getPotionEffect("POISON", 80, 200));
				en.addPotionEffect(PotionManager.getPotionEffect("BLINDNESS", 80, 200));
				if (event.getEntity() instanceof Player) {
					ep.sendMessage(ChatColor.DARK_RED + "YOU WERE STUNNED BY " + p.getName());
					p.sendMessage(ChatColor.DARK_RED + "YOU STUNNED " + ep.getName());
				} else {
					p.sendMessage(ChatColor.DARK_RED + "YOU STUNNED " + event.getEntity().getType());
				}
			}
		}
		if (x > 34 && x < 40) {
			try {
				en.damage(event.getDamage());
			} catch (Exception e) {
				if (ep != null)
					ep.damage(event.getDamage());
			}
			p.sendMessage(ChatColor.DARK_RED + "You got a crit! +100% damage");
			try {
				ep.sendMessage(ChatColor.DARK_RED + "You were crit for +100% damage by " + p.getName());
			} catch (Exception e) {
				
			}
		}
	}
	
}
