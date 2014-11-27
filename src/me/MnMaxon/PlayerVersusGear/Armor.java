package me.MnMaxon.PlayerVersusGear;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Armor {
	public static int getRating(ItemStack is) {
		Material type = is.getType();
		if (type.equals(Material.LEATHER_BOOTS))
			return 1;
		else if (type.equals(Material.LEATHER_CHESTPLATE))
			return 3;
		else if (type.equals(Material.LEATHER_HELMET))
			return 1;
		else if (type.equals(Material.LEATHER_LEGGINGS))
			return 2;
		else if (type.equals(Material.IRON_BOOTS))
			return 1;
		else if (type.equals(Material.IRON_CHESTPLATE))
			return 6;
		else if (type.equals(Material.IRON_HELMET))
			return 2;
		else if (type.equals(Material.IRON_LEGGINGS))
			return 5;
		else if (type.equals(Material.GOLD_BOOTS))
			return 1;
		else if (type.equals(Material.GOLD_CHESTPLATE))
			return 5;
		else if (type.equals(Material.GOLD_HELMET))
			return 2;
		else if (type.equals(Material.GOLD_LEGGINGS))
			return 3;
		else if (type.equals(Material.DIAMOND_BOOTS))
			return 3;
		else if (type.equals(Material.DIAMOND_CHESTPLATE))
			return 8;
		else if (type.equals(Material.DIAMOND_HELMET))
			return 3;
		else if (type.equals(Material.DIAMOND_LEGGINGS))
			return 6;
		else if (type.equals(Material.CHAINMAIL_BOOTS))
			return 1;
		else if (type.equals(Material.CHAINMAIL_CHESTPLATE))
			return 5;
		else if (type.equals(Material.CHAINMAIL_HELMET))
			return 2;
		else if (type.equals(Material.CHAINMAIL_LEGGINGS))
			return 4;
		return 0;
	}
}
