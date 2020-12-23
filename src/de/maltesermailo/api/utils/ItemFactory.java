package de.maltesermailo.api.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Wool;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.bukkit.potion.Potion.Tier;

import net.md_5.bungee.api.ChatColor;

public class ItemFactory {

	private Material mat;
	private String name;
	private short damage;
	private byte data;
	
	private DyeColor dyeColor;
	
	private List<String> lore = new ArrayList<String>();
	private List<ItemFlag> itemFlags = new ArrayList<ItemFlag>();
	private List<ItemEnchantment> enchantments = new ArrayList<ItemEnchantment>();
	private List<PotionEffect> potionEffects = new ArrayList<PotionEffect>();
	private boolean isUnbreakable;
	private boolean isWool;
	private boolean isLeather;
	private String skullOwner = "";
	private PotionType potionType;
	
	private ItemFactory(Material mat, String name, short damage, byte data) {
		this.mat = mat;
		this.name = name;
		this.damage = damage;
		this.data = data;
	}
	
	public static ItemFactory newFactory(Material mat) {
		return new ItemFactory(mat, "", (short) 0, (byte)0);
	}
	
	public static ItemFactory newFactory(Material mat, String name) {
		return new ItemFactory(mat, name, (short) 0, (byte)0);
	}
	
	public static ItemFactory newFactory(Material mat, String name, short damage) {
		return new ItemFactory(mat, name, damage, (byte)0);
	}
	
	public static ItemFactory newFactory(Material mat, String name, byte data) {
		return new ItemFactory(mat, name, (short) 0, data);
	}
	
	public static ItemFactory newFactory(Material mat, String name, short damage, byte data) {
		return new ItemFactory(mat, name, damage, data);
	}
	
	public ItemFactory enchant(Enchantment ench, int level) {
		ItemEnchantment iench = new ItemEnchantment();
		iench.setEnchantment(ench);
		iench.setLevel(level);
		iench.setIgnoreLevelRestriction(true);
		this.enchantments.add(iench);
		return this;
	}
	
	public ItemFactory itemFlag(ItemFlag flag) {
		this.itemFlags.add(flag);
		return this;
	}
	
	public ItemFactory unbreakable() {
		this.isUnbreakable = true;
		return this;
	}
	
	public ItemFactory unbreakable(boolean active) {
		this.isUnbreakable = active;
		return this;
	}
	
	public ItemFactory lore(String ...args) {
		this.lore.addAll(Arrays.asList(args));
		return this;
	}
	
	public ItemFactory woolColor(DyeColor woolColor) {
		this.isWool = true;
		this.dyeColor = woolColor;
		return this;
	}
	
	public ItemFactory leatherColor(DyeColor color) {
		this.isLeather = true;
		this.dyeColor = color;
		return this;
	}
	
	public ItemFactory data(byte data) {
		this.data = data;
		return this;
	}
	
	public ItemFactory skullOwner(String name) {
		this.skullOwner = name;
		return this;
	}
	
	public ItemFactory potionEffect(PotionEffect effect) {
		this.potionEffects.add(effect);
		return this;
	}
	
	public ItemFactory potionType(PotionType type) {
		this.potionType = type;
		return this;
	}
	
	public ItemStack build() {
		return this.build(1);
	}
	
	
	@SuppressWarnings("deprecation")
	public ItemStack build(int amount) {
		ItemStack item = new ItemStack(this.mat, amount, this.damage);
		ItemMeta meta = item.getItemMeta();
		
		MaterialData data = item.getData();
		data.setData(this.data);
		
		if(this.isWool) {
			item = new Wool(this.dyeColor).toItemStack(amount);
		}
		
		if(!this.name.isEmpty()) {
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.name));
		}
		meta.setLore(this.lore);
		
		for(ItemFlag flag : this.itemFlags) {
			meta.addItemFlags(flag);
		}
		
		for(ItemEnchantment iench : this.enchantments) {
			meta.addEnchant(iench.getEnchantment(), iench.getLevel(), iench.isIgnoreLevelRestriction());
		}
		
		meta.spigot().setUnbreakable(this.isUnbreakable);
		
		item.setItemMeta(meta);
		
		if(!this.skullOwner.isEmpty()) {
			SkullMeta sMeta = (SkullMeta) item.getItemMeta();
			sMeta.setOwner(this.skullOwner);
			item.setItemMeta(sMeta);
		}
		
		if(this.potionType != null) {
			Potion potion = new Potion(1);
			potion.setType(this.potionType);
			potion.setLevel(1);
			potion.apply(item);
		}
		
		if(this.potionEffects.size() > 0) {
			PotionMeta pMeta = (PotionMeta) item.getItemMeta();
			for(PotionEffect effect : this.potionEffects) {
				pMeta.addCustomEffect(effect, true);
			}
			item.setItemMeta(pMeta);
		}
		
		if(this.isLeather) {
			LeatherArmorMeta lMeta = (LeatherArmorMeta) item.getItemMeta();
			lMeta.setColor(this.dyeColor.getColor());
			item.setItemMeta(lMeta);
		}
		
		item.setData(data);
		
		return item;
	}
	
	private class ItemEnchantment {
		
		private Enchantment ench;
		private int level;
		private boolean ignoreLevelRestriction;
		
		public Enchantment getEnchantment() {
			return ench;
		}
		
		public int getLevel() {
			return level;
		}
		
		public boolean isIgnoreLevelRestriction() {
			return ignoreLevelRestriction;
		}
		
		public void setEnchantment(Enchantment ench) {
			this.ench = ench;
		}
		
		public void setLevel(int level) {
			this.level = level;
		}
		
		public void setIgnoreLevelRestriction(boolean ignoreLevelRestriction) {
			this.ignoreLevelRestriction = ignoreLevelRestriction;
		}
	}
	
}
