package me.xDest.mcrpg.manager;

import java.util.HashMap;

import java.util.Random;

import me.xDest.mcrpg.Messenger;
import me.xDest.mcrpg.SLAPI;
import me.xDest.mcrpg.Stats;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public class Manager {
	
	public static final EntityType[] moblist = {EntityType.BLAZE, EntityType.CHICKEN, EntityType.COW, EntityType.BAT, EntityType.CAVE_SPIDER, EntityType.CREEPER, EntityType.ENDERMAN, EntityType.OCELOT, EntityType.PIG, EntityType.PIG_ZOMBIE, EntityType.RABBIT, EntityType.SHEEP, EntityType.SKELETON, EntityType.SPIDER, EntityType.SQUID, EntityType.WITCH, EntityType.ZOMBIE};
	public static final Material[] blocklist = {Material.LOG, Material.IRON_ORE, Material.COAL_ORE, Material.REDSTONE_ORE, Material.GOLD_ORE, Material.SAND, Material.COBBLESTONE, Material.LEAVES, Material.SANDSTONE, Material.ICE, Material.SOUL_SAND, Material.QUARTZ_ORE, Material.NETHERRACK};
	
	
	private static JavaPlugin plugin;
	private static HashMap<String,Stats> users = new HashMap<String, Stats>();
	private static final int MAX_LEVEL = 120;
	private static final int LEVEL_INC = 50;
	private static final int XPFORORE = 15;
	private static final int XPFORNORM = 4;
	private static final int XPFORCOOK = 6;
	private static final int XPFORKILL = 50;
	private static final double NIGHT_MULTI = 3;
	private static final double DAY_MULTI = 1;
	private static final double MOTD_MULTI = 3;
	private static final double BOTD_MULTI = 2;
	private static EntityType moboftheday = EntityType.ZOMBIE;
	private static Material blockoftheday = Material.LOG;
	private static double MULTI = 1;

	public static void enable(JavaPlugin pl) {
		plugin = pl;
	}
	
	public static int getLevelInc() {
		return LEVEL_INC;
	}
	
	public static int getMaxLvl() {
		return MAX_LEVEL;
	}
	
	public static void showStats(Player player, Player viewer) {
		if (!users.containsKey(player.getUniqueId().toString()))
			users.put(player.getUniqueId().toString(), new Stats(MAX_LEVEL, LEVEL_INC, player.getUniqueId().toString()));
		Stats s = users.get(player.getUniqueId().toString());
		Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, "Stats");
		HashMap<String, ItemStack> icons = s.getIcons();
		final int[] positions = {2,3,4,5,6,10,12,14,16,20,24};
		int next = 0;
		//Messenger.info(""+icons.size());
		for (String string : icons.keySet()) {
			inv.setItem(positions[next], icons.get(string));
			next++;
		}
		
		viewer.openInventory(inv);
	}
	
	public static void hasLeveledUp(Player player) {
		if (!users.containsKey(player.getUniqueId().toString()))
			users.put(player.getUniqueId().toString(), new Stats(MAX_LEVEL, LEVEL_INC, player.getUniqueId().toString()));
		
		Stats s = users.get(player.getUniqueId().toString());
		Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, "Use Points " + s.getLvlPts());
		HashMap<String, ItemStack> icons = s.getIcons();
		final int[] positions = {2,3,4,5,6,10,12,14,16,20,24};
		int next = 0;
		//Messenger.info(""+icons.size());
		for (String string : icons.keySet()) {
			inv.setItem(positions[next], icons.get(string));
			next++;
		}
		
		player.openInventory(inv);
	}
	
	public static Stats getStats(Player p) {
		if (!users.containsKey(p.getUniqueId().toString()))
			users.put(p.getUniqueId().toString(), new Stats(MAX_LEVEL, LEVEL_INC, p.getUniqueId().toString()));
		return users.get(p.getUniqueId().toString());
	}
	
	public static void setStats(Player p, Stats s) {
		users.put(p.getUniqueId().toString(), s);
	}

	public static HashMap<String, Stats> getUserMap() {
		return users;
	}
	
	public static void hasKilled(Player p, EntityType killed) {
		if (!users.containsKey(p.getUniqueId().toString()))
			users.put(p.getUniqueId().toString(), new Stats(MAX_LEVEL, LEVEL_INC, p.getUniqueId().toString()));
		Stats s = users.get(p.getUniqueId().toString());
		if (killed != moboftheday) {
			s.addRxp(XPFORKILL * MULTI);
		} else {
			s.addRxp(XPFORKILL * MULTI * MOTD_MULTI);
		}
	}

	public static void hasKilledPlayer(Player p, Player en) {
		String killeruuid = p.getUniqueId().toString();
		Stats killerstats = users.get(killeruuid);
		String deaduuid = en.getUniqueId().toString();
		Stats deadstats = users.get(deaduuid);
		killerstats.addRxp(deadstats.getRxp() / 10);
	}

	public static void forceStats(Player player, boolean fromrespawn) {
		
		Stats s = users.get(player.getUniqueId().toString());
		for (PotionEffectType pot : PotionEffectType.values()) {
			try {
				player.removePotionEffect(pot);
			} catch (Exception e) {
				continue;
			}
		}
		player.addPotionEffects(s.getBonuses());
		
	}

	public static void playerBrokeOre(Player p, Material m) {
		String uuid = p.getUniqueId().toString();
		Stats s = users.get(uuid);
		if (m != blockoftheday) {
			s.addRxp(XPFORORE * getOppositeMulti());
		} else {
			s.addRxp(XPFORORE * getOppositeMulti() * BOTD_MULTI);
		}
		
	}

	public static void playerBrokeNorm(Player p, Material m) {
		String uuid = p.getUniqueId().toString();
		Stats s = users.get(uuid);
		if (m != blockoftheday) {
			s.addRxp(XPFORNORM * getOppositeMulti());
		} else {
			s.addRxp(XPFORNORM * getOppositeMulti() * BOTD_MULTI);
		}
	}

	public static void playerSmelted(Player p, String item, int itemcount) {
		String uuid = p.getUniqueId().toString();
		Stats s = users.get(uuid);
		if (itemcount == 0)
			itemcount = 1;
		
		if(item.equals("IRON_INGOT")) {
			s.addRxp((XPFORCOOK * 2.5) * itemcount);
		}
		if(item.equals("GOLD_INGOT")) {
			s.addRxp((XPFORCOOK * 3) * itemcount);
		}
		if(item.equals("BRICK")) {
			s.addRxp((XPFORCOOK * 2.25) * itemcount);
		}
		if(item.equals("FOOD_ITEM")) {
			s.addRxp(XPFORCOOK * itemcount);
		}
	}
	
	public static void enable() {
		//poison remove
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
		    public void run() {
		        for (Player p : Bukkit.getOnlinePlayers()) {
		        	String uuid = p.getUniqueId().toString();
		        	for (String s : users.keySet()) {
		        		if (users.get(s).getPoisonStat() > 0) {
		        			p.removePotionEffect(PotionEffectType.POISON);
		        		}
		        	}
		        }
		    }
		}, 10, 10);
		//day check
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
		    public void run() {
		    	for (Player p : Bukkit.getOnlinePlayers()) {
		    		if (p.getWorld().getTime() <= 30) {
		    			setMulti("DAY");
		    			break;
		    		}
		    		if (p.getWorld().getTime() >= 13000 && p.getWorld().getTime() <= 13030) {
		    			setMulti("NIGHT");
		    			break;
		    		}
		    	}
		    }
		}, 20, 20);
	}
	
	public static void setMulti(String day) {
		if (day.equals("DAY")) {
			MULTI = DAY_MULTI;
			Messenger.broadcast(ChatColor.GOLD + "Day Multiplier on! " + ChatColor.AQUA + DAY_MULTI + ChatColor.GOLD + " extra RXP for killing and dying, " + ChatColor.RED + NIGHT_MULTI + ChatColor.GOLD + " for mining and breaking!");
			newBlockOfTheDay();
			newMobOfTheDay();
		} else if (day.equals("NIGHT")) {
			MULTI = NIGHT_MULTI;
			Messenger.broadcast(ChatColor.RED + "Night Multiplier on! " + ChatColor.GOLD + NIGHT_MULTI + ChatColor.RED + " extra RXP for killing and dying, " + ChatColor.AQUA + DAY_MULTI + ChatColor.RED + " for mining and breaking!");
		}
	}
	
	public static JavaPlugin  getPlugin() {
		return plugin;
	}
	
	private static double getOppositeMulti() {
		if (MULTI == DAY_MULTI) {
			return NIGHT_MULTI;
		} else {
			return DAY_MULTI;
		}
	}
	
	public static boolean tryOpenLvlGUI(Player player) {
		
		
		Stats s = users.get(player.getUniqueId().toString());
		if (s.getLvlPts() > 0) {
			Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, "Use Points " + s.getLvlPts());
			HashMap<String, ItemStack> icons = s.getIcons();
			final int[] positions = {2,3,4,5,6,10,12,14,16,20,24};
			int next = 0;
		//	Messenger.info(""+icons.size());
			for (String string : icons.keySet()) {
				inv.setItem(positions[next], icons.get(string));
				next++;
			}
			player.openInventory(inv);
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean resetPoints(Player player) {
		if (!users.containsKey(player.getUniqueId().toString())) {
			users.put(player.getUniqueId().toString(), new Stats(MAX_LEVEL, LEVEL_INC, player.getUniqueId().toString()));
			return false;
		}
		Stats s = users.get(player.getUniqueId().toString());
		int lvl = s.getLevelOfPlayer();
		if (lvl > 55) {
			s.resetLvlPts(lvl);
			s.addRxp(-10000);
			for (PotionEffectType pot : PotionEffectType.values()) {
				try {
					player.removePotionEffect(pot);
				} catch (Exception e) {
					continue;
				}
			}
			return true;
		} else {
			return false;
		}
		
	}
	
	public static void newBlockOfTheDay()
	{
		Random r = new Random();
		blockoftheday = blocklist[r.nextInt(blocklist.length)];
		Messenger.broadcast(ChatColor.GOLD + "New block of the day: " + ChatColor.RED +  blockoftheday.toString() + ChatColor.GOLD + "!!! Break it for " + BOTD_MULTI + "x normal xp!");
	}
	
	public static void newMobOfTheDay()
	{
		Random r = new Random();
		moboftheday = moblist[r.nextInt(moblist.length)];
		Messenger.broadcast(ChatColor.GOLD + "New mob of the day: " + ChatColor.RED +  moboftheday.toString() + ChatColor.GOLD + "!!! Kill it for " + MOTD_MULTI + "x normal xp!");
	}
	
	

	
}
