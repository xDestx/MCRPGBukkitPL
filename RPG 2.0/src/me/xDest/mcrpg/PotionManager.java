package me.xDest.mcrpg;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionManager {
	
	private static PotionEffect[] potions = new PotionEffect[40];
	private static PotionEffectType[] potiontypes = new PotionEffectType[40];
	
	public static void enable() {
		int counter = 0;
		for (PotionEffectType pot : PotionEffectType.values()) {
			potiontypes[counter] = pot;
			counter++;
		}
		
	}
	
	public static PotionEffect getPotionEffect(String name, int duration, int multiplier) {
		PotionEffectType requested = PotionEffectType.getByName(name);
		return new PotionEffect(requested, duration, multiplier);
	}
	
	public static PotionEffect getPotionEffect(String name, int duration) {
		PotionEffectType requested = PotionEffectType.getByName(name);
		return new PotionEffect(requested, duration, 0);
	}

}
