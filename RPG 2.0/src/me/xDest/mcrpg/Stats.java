package me.xDest.mcrpg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.xDest.mcrpg.Messenger;
import me.xDest.mcrpg.manager.Manager;
import me.xDest.mcrpg.manager.PotionManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Stats {
	
	
	private final int MAX_SPEED_AMP = 26;
	private final int MAX_STR_AMP = 120;
	private final int MAX_DAMAGE_RESISTANCE_AMP = 2;
	private final int MAX_WATER_BREATHE_AMP = 1;
	private final int MAX_FIRE_IMMUNE_AMP = 1;
	private final int MAX_POISON_IMMUNE_AMP = 1;
	private final int MAX_NIGHT_VISION_AMP = 1;
	private final int MAX_HASTE_AMP = 110;
	private final int MAX_JUMP_AMP = 21;
	private final int MAX_REGEN_AMP = 15;
	private final int MAX_HP_AMP = 110;
	private final int forever = 999999;
	//2 Level points per level
	
	private int levelpts = 0;
	
	private int hp = 0;
	private int str = 0;
	private int jump = 0;
	private int haste = 0;
	private int speed = 0;
	private int night_vision = 0;
	private int fire_immune = 0;
	private int water_breathe = 0;
	private int damage_resistance = 0;
	private int regen = 0;
	private int poison_immune = 0;
	private double rxp = 0;
	private boolean hasLeveledUp = false;
	private int lastknownlevel = 0;
	private int level = 0;
	//0 false, 1 true
	private int hasRush = 0;
	final private String[] tier1 = {"rush"};
	
	private final int LEVEL_INC;
	private final int MAX_LEVEL;
	private HashMap<String, ItemStack> icons = new HashMap<String, ItemStack>();
	private HashMap<String, Integer> all = new HashMap<String, Integer>();
	
	private final String player;
	//Player in UUID format
	
	
	
	public Stats(int maxlvl, int lvlinc, String player) {
		this.LEVEL_INC = lvlinc;
		this.MAX_LEVEL = maxlvl;
		this.player = player;
		setIcons();
		addToAll();
	}

	
	public Stats(int hp, int str, int jump, int haste, int speed, int night, int fire, int water, int res, int regen, int poison, double rxp, int maxlvl, int lvlinc, String player, int hasRush) {
		this.hp = hp;
		this.str = str;
		this.jump = jump;
		this.haste = haste;
		this.speed = speed;
		this.night_vision = night;
		this.fire_immune = fire;
		this.water_breathe = water;
		this.damage_resistance = res;
		this.regen = regen;
		this.poison_immune = poison;
		this.MAX_LEVEL = maxlvl;
		this.LEVEL_INC = lvlinc;
		int lvl = getLevelOfPlayer(LEVEL_INC, MAX_LEVEL);
		lastknownlevel = lvl;
		this.levelpts = calcLvlPts(lvl);
		this.player = player;
		this.hasRush = hasRush;
		setIcons();
		addToAll();
	}
	
	private void addToAll() {
		all.put("hp", hp);
		all.put("str", str);
		all.put("jump", jump);
		all.put("haste", haste);
		all.put("speed", speed);
		all.put("night_vision", night_vision);
		all.put("fire_immune", fire_immune);
		all.put("water_breathe", water_breathe);
		all.put("damage_resistance", damage_resistance);
		all.put("regen", regen);
		all.put("poison_immune", poison_immune);
		all.put("rxp", (int)rxp);
		all.put("rush", hasRush);
	}
	
	public int getMaxHP() {
		return this.MAX_HP_AMP;
	}
	
	public int getMaxHaste() {
		return this.MAX_HASTE_AMP;
	}
	
	public int getMaxStr() {
		return this.MAX_STR_AMP;
	}
	
	public int getMaxJump() {
		return this.MAX_JUMP_AMP;
	}
	
	public int getMaxSpeed() {
		return this.MAX_SPEED_AMP;
	}
	
	public int getMaxNv() {
		return this.MAX_NIGHT_VISION_AMP;
	}
	
	public int getMaxFire() {
		return this.MAX_FIRE_IMMUNE_AMP;
	}
	
	public int getMaxWater() {
		return this.MAX_WATER_BREATHE_AMP;
	}
	
	public int getMaxRes() {
		return this.MAX_DAMAGE_RESISTANCE_AMP;
	}
	
	public int getMaxRegen() {
		return this.MAX_REGEN_AMP;
	}
	
	public int getMaxPoison() {
		return this.MAX_POISON_IMMUNE_AMP;
	}
	
	private void setIcons() {
		ItemStack hp = new ItemStack(Material.APPLE);
		ItemMeta hpmeta = hp.getItemMeta();
		hpmeta.setDisplayName(ChatColor.DARK_RED + "Health " + ChatColor.GRAY + this.hp + ChatColor.RED + "/" + this.MAX_HP_AMP);
		hp.setItemMeta(hpmeta);
		
		ItemStack str = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta strmeta = str.getItemMeta();
		strmeta.setDisplayName(ChatColor.DARK_RED + "Strength " + ChatColor.GRAY + this.str + ChatColor.RED + "/" + this.MAX_STR_AMP);
		str.setItemMeta(strmeta);
		
		ItemStack jump = new ItemStack(Material.LEATHER_BOOTS);
		ItemMeta jumpmeta = jump.getItemMeta();
		jumpmeta.setDisplayName(ChatColor.GREEN + "Jump " + ChatColor.GRAY + this.jump + ChatColor.RED + "/" + this.MAX_JUMP_AMP);
		jump.setItemMeta(jumpmeta);
		
		ItemStack haste = new ItemStack(Material.BLAZE_POWDER);
		ItemMeta hastemeta = haste.getItemMeta();
		hastemeta.setDisplayName(ChatColor.GOLD + "Haste " + ChatColor.GRAY + this.haste + ChatColor.RED + "/" + this.MAX_HP_AMP);
		haste.setItemMeta(hastemeta);
		
		ItemStack speed = new ItemStack(Material.FEATHER);
		ItemMeta speedmeta = speed.getItemMeta();
		speedmeta.setDisplayName("Speed " + ChatColor.GRAY + this.speed + ChatColor.RED + "/" + this.MAX_SPEED_AMP);
		speed.setItemMeta(speedmeta);
		
		ItemStack nv = new ItemStack(Material.EYE_OF_ENDER);
		ItemMeta nvmeta = nv.getItemMeta();
		nvmeta.setDisplayName(ChatColor.DARK_PURPLE + "Night Vision " + ChatColor.GRAY + this.night_vision + ChatColor.RED + "/" + this.MAX_NIGHT_VISION_AMP);
		nv.setItemMeta(nvmeta);
		
		ItemStack fi = new ItemStack(Material.FURNACE);
		ItemMeta fimeta = fi.getItemMeta();
		fimeta.setDisplayName(ChatColor.RED + "Fire Resistance " + ChatColor.GRAY + this.fire_immune + ChatColor.RED + "/" + this.MAX_FIRE_IMMUNE_AMP);
		fi.setItemMeta(fimeta);
		
		ItemStack water = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta watermeta = water.getItemMeta();
		watermeta.setDisplayName(ChatColor.AQUA + "Water Breathing " + ChatColor.GRAY + this.water_breathe + ChatColor.RED + "/" + this.MAX_WATER_BREATHE_AMP);
		water.setItemMeta(watermeta);
		
		ItemStack res = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta resmeta = res.getItemMeta();
		resmeta.setDisplayName(ChatColor.DARK_RED + "Resistance " + ChatColor.GRAY + this.damage_resistance + ChatColor.RED + "/" + this.MAX_DAMAGE_RESISTANCE_AMP);
		res.setItemMeta(resmeta);
		
		ItemStack regen = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta regenmeta = regen.getItemMeta();
		regenmeta.setDisplayName(ChatColor.RED + "Regeneration " + ChatColor.GRAY + this.regen + ChatColor.RED + "/" + this.MAX_REGEN_AMP);
		regen.setItemMeta(regenmeta);
		
		ItemStack poison = new ItemStack(Material.FERMENTED_SPIDER_EYE);
		ItemMeta poisonmeta = poison.getItemMeta();
		poisonmeta.setDisplayName(ChatColor.DARK_GREEN + "Poison Resistance " + ChatColor.GRAY + this.poison_immune + ChatColor.RED + "/" + this.MAX_POISON_IMMUNE_AMP);
		poison.setItemMeta(poisonmeta);
		
		icons.put("hp", hp);
		icons.put("str", str);
		icons.put("jump", jump);
		icons.put("haste", haste);
		icons.put("speed", speed);
		icons.put("night_vision", nv);
		icons.put("fire_resistance", fi);
		icons.put("water_breathe", water);
		icons.put("resistance", res);
		icons.put("regen", regen);
		icons.put("poison", poison);//HASTE, HEALTH, STR, SPEED, RES, POISON RES, REGEN, NV
									//MISSING JUMP, FIRE RES, WATER BREATHING
	}
	
	public String getStat(String statname) {
		addToAll();
		return all.get(statname).toString();
	}
	
	public void setStat(String statname, int stat) {
		if (statname.equals("hp")) {
			hp = stat;
		} else if (statname.equals("str")) {
			str = stat;
		} else if (statname.equals("jump")) {
			jump = stat;
		} else if (statname.equals("haste")) {
			haste = stat;
		} else if (statname.equals("speed")) {
			speed = stat;
		} else if (statname.equals("night_vision")) {
			night_vision = stat;
		} else if (statname.equals("fire_immune")) {
			fire_immune = stat;
		} else if (statname.equals("water_breathe")) {
			water_breathe = stat;
		} else if (statname.equals("damage_resistance")) {
			damage_resistance = stat;
		} else if (statname.equals("regen")) {
			regen = stat;
		} else if (statname.equals("poison_immune")) {
			poison_immune = stat;
		} else if (statname.equals("rxp")) {
			rxp = stat;
		} else if (statname.equals("rush")) { 
			hasRush = stat;
		}else {
			throw new IllegalArgumentException("Incorrect stat type");
		}
		setIcons();
	}
	
	public ItemStack getIcon(String stat) {
		setIcons();
		return icons.get(stat);
	}
	
	
	public HashMap<String, ItemStack> getIcons() {
		setIcons();
		
		//Messenger.info("Given hashmap");
		//Debug line
		return icons;
	}
	
	public List<String> getSaves() {
		List<String> savetypes = new ArrayList<String>();
		savetypes.add("hp");
		savetypes.add("str");
		savetypes.add("jump");
		savetypes.add("haste");
		savetypes.add("speed");
		savetypes.add("night_vision");
		savetypes.add("fire_immune");
		savetypes.add("damage_resistance");
		savetypes.add("water_breathe");
		savetypes.add("damage_resistance");
		savetypes.add("regen");
		savetypes.add("poison_immune");
		savetypes.add("rxp");
		savetypes.add("rush");
		return savetypes;
	}
	
	public int getLevelOfPlayer(int LEVEL_INC, int MAX_LEVEL) {
		if (rxp <= 0) {
			return 0;
		}
		int ls = 0;
		int le = LEVEL_INC;
		int level = 1;
		for (int i =0; i <= MAX_LEVEL; i++) {
			if (rxp >= ls && rxp <= le) {
				level = i;
				break;
			} else {
				if (i == MAX_LEVEL) {
					level = i;
				}
				ls = le-1;
				le = le + (i*LEVEL_INC);
			}
		}
		if (lastknownlevel != level) {
			lastknownlevel = level;
			this.level = level;
			Manager.hasLeveledUp(Bukkit.getPlayer(UUID.fromString(player)));
		}
		this.level = level;
		return level;
	}
	
	public int getLevelOfPlayer() {
		if (rxp <= 0) {
			return 0;
		}
		int ls = 0;
		int le = LEVEL_INC;
		int level = 1;
		for (int i =0; i <= MAX_LEVEL; i++) {
			if (rxp >= ls && rxp <= le) {
				level = i;
				break;
			} else {
				if (i == MAX_LEVEL) {
					level = i;
				}
				ls = le-1;
				le = le + (i*LEVEL_INC);
			}
		}
		if (lastknownlevel != level) {
			lastknownlevel = level;
			this.level = level;
			Manager.hasLeveledUp(Bukkit.getPlayer(UUID.fromString(player)));
		}
		lastknownlevel = level;
		this.level = level;
		return level;
	}
	
	public int getLastKnownLevel() {
		return lastknownlevel;
	}
	
	
	public double setRxp(double rxp) {
		this.rxp = rxp;
		getLevelOfPlayer();
		Player p = Bukkit.getPlayer(UUID.fromString(player));
		p.addPotionEffects(getBonuses());
		p.setLevel((int) this.rxp);
		return this.rxp;
	}
	
	public double addRxp(double rxp) {
		this.rxp += rxp;
		getLevelOfPlayer();
		Player p = Bukkit.getPlayer(UUID.fromString(player));
		p.setLevel((int) this.rxp);
		return this.rxp;
	}
	
	public double getRxp() {
		return rxp;
	}
	
	public int calcLvlPts(int lvl) {
		int total = lvl * 2;
		addToAll();
		for (String s : all.keySet()) {
			if (!s.equals("rxp")) {
				total = total - all.get(s);
			//	Messenger.info((total + all.get(s)) + " - " + all.get(s) + " = " + total + "  From: " + s);
			}
		}
		if (total < 0) {
			total = 0;
		}
		this.levelpts = total;
		return total;
	}
	
	public void setHPStat(int hp) {
		this.hp = hp;
	}
	
	public int getHPStat() {
		return hp;
	}
	
	public boolean addHPStat(int amt) {
		if ((hp + amt) > MAX_HP_AMP) {
			return false;
		} else {
			hp += amt;
			return true;
		}
	}
	///////////
	public void setJumpStat(int jump) {
		this.jump = jump;
	}
	
	public int getJumpStat() {
		return jump;
	}
	
	public boolean addJumpStat(int amt) {
		if ((jump + amt) > MAX_JUMP_AMP) {
			return false;
		} else {
			jump += amt;
			return true;
		}
	}
	////////////
	public void setStrStat(int str) {
		this.str = str;
	}
	
	public int getStrStat() {
		return str;
	}
	
	public boolean addStrStat(int amt) {
		if ((str + amt) > MAX_STR_AMP) {
			return false;
		} else {
			str += amt;
			return true;
		}
	}
	////////////
	public void setHasteStat(int haste) {
		this.haste = haste;
	}
	
	public int getHasteStat() {
		return haste;
	}
	
	public boolean addHasteStat(int amt) {
		if ((haste + amt) > MAX_HASTE_AMP) {
			return false;
		} else {
			haste += amt;
			return true;
		}
	}
	///////////
	public void setSpeedStat(int speed) {
		this.speed = speed;
	}
	
	public int getSpeedStat() {
		return speed;
	}
	
	public boolean addSpeedStat(int amt) {
		if ((speed + amt) > MAX_SPEED_AMP) {
			return false;
		} else {
			speed += amt;
			return true;
		}
	}
	//////////
	public void setNightStat(int amt) {
		this.night_vision = amt;
	}
	
	public int getNightStat() {
		return night_vision;
	}
	
	public boolean addNightStat(int amt) {
		if ((night_vision + amt) > MAX_NIGHT_VISION_AMP) {
			return false;
		} else {
			night_vision += amt;
			return true;
		}
	}
	//////////
	public boolean setFireStat(int amt) {
		if (poison_immune != 0) {
			return false;
		} else {
			fire_immune = amt;
			return true;
		}
	}
	
	public int getFireStat() {
		return fire_immune;
	}
	
	public boolean addFireStat(int amt) {
		if (poison_immune != 0) {
			return false;
		}
		if ((fire_immune + amt) > MAX_FIRE_IMMUNE_AMP) {
			return false;
		} else {
			fire_immune += amt;
			return true;
		}
	}
	//////////
	public void setWaterStat(int amt) {
		water_breathe = amt;
	}
	
	public int getWaterStat() {
		return water_breathe;
	}
	
	public boolean addWaterStat(int amt) {
		if ((water_breathe + amt) > MAX_WATER_BREATHE_AMP) {
			return false;
		} else {
			water_breathe += amt;
			return true;
		}
	}
	///////////
	public void setResStat(int amt) {
		damage_resistance = amt;
	}
	
	public int getResStat() {
		return damage_resistance;
	}
	
	public boolean addResStat(int amt) {
		if ((damage_resistance + amt) > MAX_DAMAGE_RESISTANCE_AMP) {
			return false;
		} else {
			damage_resistance += amt;
			return true;
		}
	}
	//////////
	public void setRegenStat(int amt) {
		regen = amt;
	}
	
	public int getRegenStat() {
		return regen;
	}
	
	public boolean addRegenStat(int amt) {
		if ((regen + amt) > MAX_REGEN_AMP) {
			return false;
		} else {
			regen += amt;
			return true;
		}
	}
	////////////
	public boolean setPoisonStat(int amt) {
		if (fire_immune != 0) {
			return false;
		} else {
			poison_immune = amt;
			return true;
		}
	}
	
	public int getPoisonStat() {
		return poison_immune;
	}
	
	public boolean addPoisonStat(int amt) {
		if (fire_immune != 0) {
			return false;
		}
		if ((poison_immune + amt) > MAX_POISON_IMMUNE_AMP) {
			return false;
		} else {
			poison_immune += amt;
			return true;
		}
	}
	//////////
	public void setLvlPts(int amt) {
		levelpts = amt;
	}
	
	public int getLvlPts() {
		if (this.level == 0) {
			this.getLevelOfPlayer();
		}
		calcLvlPts(this.level);
		//Messenger.info(""+this.level);
		return levelpts;
	}
	
	public void addLvlPts(int amt) {
		levelpts += amt;
	}
	
	public void removeLvlPts(int amt) {
		levelpts -= amt;
	}
	
	public void resetLvlPts(int level) {
		setAllToZero();
		levelpts = level * 2;
	}
	
	public HashMap<String, Integer> toList() {
		/*
		 * hp: x
		 * str: x
		 * jump: x
		 * haste: x
		 * speed: x
		 * nv: x
		 * fi: x
		 * wb: x
		 * dr: x
		 * regen: x
		 * pi: x
		 */
		HashMap<String, Integer> stats = new HashMap<String, Integer>();
		stats.put("hp", hp);
		stats.put("str", str);
		stats.put("jump", jump);
		stats.put("haste", haste);
		stats.put("speed", speed);
		stats.put("nv", night_vision);
		stats.put("fi", fire_immune);
		stats.put("wb", water_breathe);
		stats.put("dr", damage_resistance);
		stats.put("regen", regen);
		stats.put("pi", poison_immune);
		return stats;
	}
	
	private void setAllToZero() {
		this.hp = 0;
		this.str = 0;
		this.jump = 0;
		this.haste = 0;
		this.speed = 0;
		this.night_vision = 0;
		this.fire_immune = 0;
		this.water_breathe = 0;
		this.damage_resistance = 0;
		this.regen = 0;
		this.poison_immune = 0;
		this.levelpts = 0;
	}


	public Collection<PotionEffect> getBonuses() {
		PotionEffect nullp = PotionManager.getPotionEffect("SPEED", forever);
		Collection<PotionEffect> nullpotions = new ArrayList<PotionEffect>();
		nullpotions.add(nullp);
		Collection<PotionEffect> realset = new ArrayList<PotionEffect>();
		boolean addedatleast1 = false;
		if(hp > 0) {
			realset.add(PotionManager.getPotionEffect("HEALTH_BOOST", forever, hp-1));
			//Messenger.info("Added hp with " + hp);
			addedatleast1 = true;
		}
		if(str > 0) {
			realset.add(PotionManager.getPotionEffect("INCREASE_DAMAGE", forever, str-1));
			addedatleast1 = true;
		//	Messenger.info("Added increase_damage with " + str);
		}
		if(jump > 0) {
			realset.add(PotionManager.getPotionEffect("JUMP", forever, jump-1));
			addedatleast1 = true;
		//	Messenger.info("Added jump with " + jump);
		}
		if(haste > 0) {
			realset.add(PotionManager.getPotionEffect("FAST_DIGGING", forever, haste-1));
			addedatleast1 = true;
		//	Messenger.info("Added haste with " + haste);
		}
		if(speed > 0) {
			realset.add(PotionManager.getPotionEffect("SPEED", forever, speed-1));
			addedatleast1 = true;
		//	Messenger.info("Added speed with " + speed);
		}
		if(night_vision > 0) {
			realset.add(PotionManager.getPotionEffect("NIGHT_VISION", forever, night_vision));
			addedatleast1 = true;
		//	Messenger.info("Added night vision with " + night_vision);
		}
		if(fire_immune > 0) {
			realset.add(PotionManager.getPotionEffect("FIRE_RESISTANCE", forever, fire_immune));
			addedatleast1 = true;
			//Messenger.info("Added fire_immune with " + fire_immune);
		}
		if(water_breathe > 0) {
			realset.add(PotionManager.getPotionEffect("WATER_BREATHING", forever, water_breathe));
			addedatleast1 = true;
			//Messenger.info("Added water_breathing with " + water_breathe);
		}
		if(damage_resistance > 0) {
			realset.add(PotionManager.getPotionEffect("DAMAGE_RESISTANCE", forever, damage_resistance - 1));
			addedatleast1 = true;
			//Messenger.info("Added damage_resistance with " + damage_resistance);
		}
		if(regen  > 0) {
			realset.add(PotionManager.getPotionEffect("REGENERATION", forever, regen));
			addedatleast1 = true;
		//	Messenger.info("Added regen with " + regen);
		}
		if(addedatleast1)
			return realset;
		
		return nullpotions;
	}
	
	
}
