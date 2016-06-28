package me.xDest.mcrpg;

import me.xDest.mcrpg.listener.RushJumpListener;
import me.xDest.mcrpg.listener.LevelPointGUIListener;
import me.xDest.mcrpg.listener.RushJumpListener;
import me.xDest.mcrpg.listener.RxpListener;
import me.xDest.mcrpg.manager.Manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class MCRPG extends JavaPlugin {

	/*
	 * Players will have a max of 5 abilities
	 * @lvl 20, 40, 60, 80, 100 +1 ability
	 */
	
	@Override
	public void onEnable() {
		Messenger.info("Hello! This is McLeveler starting up! ;)");
		SLAPI.enable(this);
		Manager.enable(this);
		Manager.enable(); 
		SLAPI.loadPlayerData();
		Bukkit.getPluginManager().registerEvents(new LevelPointGUIListener(), this);
		Bukkit.getPluginManager().registerEvents(new RxpListener(), this);
		Bukkit.getPluginManager().registerEvents(new RushJumpListener(), this);
		getCommand("gm").setExecutor(new SetGamemodeCommand());
		getCommand("gamemode").setExecutor(new SetGamemodeCommand());
	}
	
	
	@Override
	public void onDisable() {
		SLAPI.savePlayerData();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (!(sender instanceof Player)) {
			return false;
		}
		if(label.equals("stats")) {
			Player p = (Player) sender;
			if (args.length >= 1) {
				Player target = Bukkit.getPlayer(args[0]);
				Manager.showStats(target, p);
			} else {
				Manager.showStats(p, p);
			}
			return true;
		}
		if(label.equals("setstats")) {
			Player p = (Player) sender;
			if (!Manager.tryOpenLvlGUI(p)) {
				p.sendMessage(ChatColor.DARK_RED + "You don't have any level points. If you wish to view your stats, try /stats");
				return true;
			} else {
				return true;
			}
		}
		if (label.equals("addrxp")) {
			Player p = (Player) sender;
			if (!p.isOp()) {
				return true;
			} else {
				if(args.length == 0) {
					return false;
				}
				if (args.length == 2) {
					//xp player
					Player target = Bukkit.getPlayer(args[1]);
					Stats s = Manager.getStats(target);
					s.addRxp(Double.parseDouble(args[0]));
				}
				Stats s = Manager.getStats(p);
				s.addRxp(Double.parseDouble(args[0]));	
				return true;
			}
		}
		if(label.equals("forcestats")) {
			Player p = (Player) sender;
			Manager.forceStats(p, false);
			return true;
		}
		if(label.equals("resetstats")) {
			Player p = (Player) sender;
			if(Manager.resetPoints(p)) {
			
			} else {
				p.sendMessage(ChatColor.RED + "Not a high enough level.");
			}
			return true;
		}
		if(label.equals("getlevel")) {
			Player p = (Player) sender;
			Stats s = Manager.getStats(p);
			p.sendMessage(ChatColor.GOLD + "You are level " + s.getLevelOfPlayer());
			return true;
		}
		
		
		
		
		return false;
	}
	
}
