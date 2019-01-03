package me.cougers.tags;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.chat.EssentialsChat;

import me.cougers.tags.commands.Admin;
import me.cougers.tags.commands.Primary;
import me.cougers.tags.events.Chat;
import me.cougers.tags.events.Click;
import me.cougers.tags.object.CustomFile;

public class Tags extends JavaPlugin {

	private static Tags instance;
	private static CustomFile data;
	private static EssentialsChat essentials;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		instance = this;
		data = new CustomFile("plugins/Tags/data/data.yml");
		
		Bukkit.getPluginManager().registerEvents(new Chat(), this);
		Bukkit.getPluginManager().registerEvents(new Click(), this);
		getCommand("admintags").setExecutor(new Admin());
		getCommand("tags").setExecutor(new Primary());
	}
	@Override
	public void onDisable() {
		saveConfig();
		data.save();
		instance = null;
		essentials = null;
		data = null;
	}
	public static EssentialsChat getAPI() {
		return essentials;
	}
	public static FileConfiguration getData() {
		return data.getConfig();
	}
	public static Tags getInstance() {
		return instance;
	}
}
