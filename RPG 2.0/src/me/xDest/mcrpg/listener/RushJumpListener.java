package me.xDest.mcrpg.listener;

import java.util.ArrayList;
import java.util.List;

import me.xDest.mcrpg.manager.Manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

public class RushJumpListener implements Listener {
	
	private List<String> cooldown = new ArrayList<String>();

	@EventHandler
	public void onPlayerSneak(PlayerToggleSneakEvent event) {
		final Player p = event.getPlayer();
		if (!(Integer.parseInt(Manager.getStats(p).getStat("rush")) > 0)) {
			return;
		}
		if (!p.isSneaking())
			return;
		if (cooldown.contains(p.getUniqueId().toString())) {
			p.sendMessage(ChatColor.RED + "Cool down still in effect for rush!");
			return;
		}
			if (!p.isOnGround()) {
				//Check if they have rush later or now lol
				p.setVelocity(p.getLocation().getDirection().multiply(2));;
				cooldown.add(p.getUniqueId().toString());
				Bukkit.getScheduler().scheduleSyncDelayedTask(Manager.getPlugin(), new Runnable() {
					@Override
					public void run() {
						if (cooldown.contains(p.getUniqueId().toString()));
							cooldown.remove(p.getUniqueId().toString());
					}
					
				}, 300l);
			}
	}
	
	
}
