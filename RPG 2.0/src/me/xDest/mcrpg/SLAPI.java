package me.xDest.mcrpg;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import me.xDest.mcrpg.manager.Manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class SLAPI {

	private static JavaPlugin plugin;

	
	public static void enable(JavaPlugin pl) {
		plugin = pl;
	}
	
	public static void savePlayerData() {
		//File config = new File();
		for (String uuid : Manager.getUserMap().keySet()) {
			Stats s = Manager.getUserMap().get(uuid);
			ArrayList<String> saves = (ArrayList<String>) s.getSaves();
			for (String string : saves) {
				plugin.getConfig();
				plugin.getConfig().set("info."+uuid+"."+string, Manager.getUserMap().get(uuid).getStat(string));
		//		Messenger.info("Thing: " + string + " " +  Manager.getUserMap().get(uuid).getStat(string) + "for " + uuid);
			}
		}
		plugin.saveConfig();
	}
	
	public static void loadPlayerData() {
		if (!plugin.getConfig().contains("info")) {
			return;
		}
		List<String> onlineplayers = new ArrayList<String>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			onlineplayers.add(p.getUniqueId().toString());
		}
		for (String uuid : plugin.getConfig().getConfigurationSection("info").getKeys(true)) {
			if (onlineplayers.contains(uuid)) {
			
				Stats s = new Stats(Manager.getMaxLvl(), Manager.getLevelInc(), uuid);
				for (String st : s.getSaves()) {
					s.setStat(st, Integer.parseInt(plugin.getConfig().getString("info."+uuid+"."+st)));
				//	Messenger.info("Loading: " + st + Integer.parseInt(plugin.getConfig().getString("info."+uuid+"."+st)));
				}
				Manager.setStats(Bukkit.getPlayer(UUID.fromString(uuid)), s);
			}
		}
	}

	
}
