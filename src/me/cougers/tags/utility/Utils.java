package me.cougers.tags.utility;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cougers.tags.version.VersionSound;

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
	public static void sound(Player p, VersionSound sound) {
		p.playSound(p.getLocation(), sound.get(), 1.0f, 1.0f);
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
