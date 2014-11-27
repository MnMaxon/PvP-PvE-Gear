package me.MnMaxon.PlayerVersusGear;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {
	public static String dataFolder;
	public static Main plugin;
	public static boolean running = false;

	@Override
	public void onEnable() {
		plugin = this;
		getServer().getPluginManager().registerEvents(this, this);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player p = (Player) sender;
		if (args.length == 2 && args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("PvP"))
			if (p.getItemInHand() == null || p.getItemInHand().getType().name().equals("AIR"))
				p.sendMessage(ChatColor.DARK_RED + "You are not holding anything");
			else {
				if (p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasLore())
					p.sendMessage(ChatColor.DARK_RED + "This item already has lore.");
				else {
					ItemMeta im = p.getPlayer().getItemInHand().getItemMeta();
					List<String> lore = new ArrayList<String>();
					lore.add("[PvP]");
					im.setLore(lore);
					p.getItemInHand().setItemMeta(im);
				}
			}
		else if (args.length == 2 && args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("PvE"))
			if (p.getItemInHand() == null || p.getItemInHand().getType().name().equals("AIR"))
				p.sendMessage(ChatColor.DARK_RED + "You are not holding anything");
			else {
				if (p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasLore())
					p.sendMessage(ChatColor.DARK_RED + "This item already has lore.");
				else {
					ItemMeta im = p.getPlayer().getItemInHand().getItemMeta();
					List<String> lore = new ArrayList<String>();
					lore.add("[PvE]");
					im.setLore(lore);
					p.getItemInHand().setItemMeta(im);
				}
			}
		else
			displayHelp(p);
		return false;
	}

	private void displayHelp(Player p) {
		p.sendMessage(ChatColor.GOLD + "===== " + ChatColor.DARK_PURPLE + "PvG Help" + ChatColor.GOLD + " =====");
		p.sendMessage(ChatColor.DARK_AQUA + "/PvG" + ChatColor.DARK_PURPLE + " - Displays Help");
		p.sendMessage(ChatColor.DARK_AQUA + "/PvG Set PvP" + ChatColor.DARK_PURPLE + " - Sets the item lore to [PvP]");
		p.sendMessage(ChatColor.DARK_AQUA + "/PvG Set PvE" + ChatColor.DARK_PURPLE + " - Sets the item lore to [PvE]");
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		boolean pvp = false;
		Entity damager = e.getDamager();
		Entity damaged = e.getEntity();
		if (damager instanceof Player && damaged instanceof Player)
			pvp = true;
		else if (damager instanceof Arrow) {
			if (((Arrow) damager).getShooter() instanceof Player) {
				pvp = true;
				damager = (Entity) ((Arrow) damager).getShooter();
			}
		}
		if (pvp) {
			if (((Player) damager).getItemInHand() != null && ((Player) damager).getItemInHand().hasItemMeta()
					&& ((Player) damager).getItemInHand().getItemMeta().hasLore())
				for (String lore : ((Player) damager).getItemInHand().getItemMeta().getLore())
					if (lore.toLowerCase().contains("[pve]")) {
						e.setCancelled(true);
						return;
					}
			Player p = (Player) damaged;
			int removeArmor = 0;
			if (p.getInventory().getArmorContents() != null)
				for (ItemStack is : p.getInventory().getArmorContents())
					if (is.getType() != Material.AIR && is.hasItemMeta() && is.getItemMeta().hasLore())
						for (String lore : is.getItemMeta().getLore())
							if (lore.toLowerCase().contains("[pve]")) {
								removeArmor = removeArmor + Armor.getRating(is);
							}
			e.setDamage(e.getDamage() / (1 - removeArmor * .04));
		} else {
			if (damager instanceof Player && ((Player) damager).getItemInHand() != null
					&& ((Player) damager).getItemInHand().hasItemMeta()
					&& ((Player) damager).getItemInHand().getItemMeta().hasLore())
				for (String lore : ((Player) damager).getItemInHand().getItemMeta().getLore())
					if (lore.toLowerCase().contains("[pvp]")) {
						e.setCancelled(true);
						return;
					}
			if (damaged instanceof Player) {
				Player p = (Player) damaged;
				int removeArmor = 0;
				if (p.getInventory().getArmorContents() != null) {
					for (ItemStack is : p.getInventory().getArmorContents()) {
						if (is.getType() != Material.AIR && is.hasItemMeta() && is.getItemMeta().hasLore()) {
							for (String lore : is.getItemMeta().getLore())
								if (lore.toLowerCase().contains("[pvp]")) {
									removeArmor = removeArmor + Armor.getRating(is);
								}
						}
					}
					e.setDamage(e.getDamage() / (1 - removeArmor * .04));
				}
			}
		}
	}
}