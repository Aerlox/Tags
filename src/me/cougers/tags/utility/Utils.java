package me.cougers.tags.utility;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {

	public static String c(String txt) {
		return ChatColor.translateAlternateColorCodes('&', txt);
	}
	public static void log(String log) {
		Bukkit.getServer().getConsoleSender().sendMessage("[Tags] " + log);
	}
	public static void msg(String msg, CommandSender s) {
		if (s instanceof Player)
			((Player) s).sendMessage(c(msg));
		else {
			ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			console.sendMessage(c(msg));
		}
	}
	public static ItemStack mod(ItemStack stack, String name, String[] lore, boolean itemflags) {
		ItemStack item = stack;
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(c(name));
		ArrayList<String> list = new ArrayList<>();
		for (String s : lore) {
			list.add(c(s));
		}
		if (itemflags) {
			meta.addItemFlags(new ItemFlag[] {ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE});
		}
		meta.setLore(list);
		item.setItemMeta(meta);
		return item;
	}
	public static void getTagSound(Player p) {
		Sound s = null;
		try {
			s = Sound.valueOf("LEVEL_UP");
		} catch (IllegalArgumentException iae) {
			s = Sound.valueOf("ENTITY_PLAYER_LEVELUP");
		}
		p.playSound(p.getLocation(), s, 1.0f, 1.0f);
	}
	@SuppressWarnings("deprecation")
	public static String[] grabUser(String argument) {
		String data = "";
		Player t = Bukkit.getPlayer(argument);
		if (t == null) {
			OfflinePlayer offp = Bukkit.getOfflinePlayer(argument);
			if (offp == null) 
				return null;
			else
				data = offp.getName() + ";" + offp.getUniqueId().toString()+ ";" + "offline";
		} else {
			data = t.getName() + ";" + t.getUniqueId().toString() + ";" + "online";
		}
		return data.split(";");
	}
}
