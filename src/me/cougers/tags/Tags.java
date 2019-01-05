package me.cougers.tags;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.chat.EssentialsChat;

import me.cougers.tags.commands.Admin;
import me.cougers.tags.commands.Primary;
import me.cougers.tags.listeners.Chat;
import me.cougers.tags.listeners.Click;
import me.cougers.tags.object.CustomFile;
import me.cougers.tags.version.BukkitVersion;

public class Tags extends JavaPlugin {

	private static Tags instance;
	private static CustomFile data;
	private static EssentialsChat essentials;

	@Override
	public void onEnable() {
		
		saveDefaultConfig();
		instance = this;

		if (!BukkitVersion.get().compatible()) {
			getServer().getConsoleSender().sendMessage(getClass().getSimpleName() + ":"
					+ " You might experience issues because you are running a version of CraftBukkit"
					+ " (" + BukkitVersion.get().ver() + ") that Tags does not support.");
		}


		Bukkit.getPluginManager().registerEvents(new Chat(), this);
		Bukkit.getPluginManager().registerEvents(new Click(), this);

		getCommand("admintags").setExecutor(new Admin());
		getCommand("tags").setExecutor(new Primary());
		data = new CustomFile("plugins/Tags/data.yml");
		
	}
	@Override
	public void onDisable() {
		saveConfig();
		data.save();

		instance = null; essentials = null; data = null;
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
