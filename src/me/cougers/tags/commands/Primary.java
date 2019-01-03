package me.cougers.tags.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cougers.tags.utility.Utils;
import me.cougers.tags.utility.gui.GUI;

public class Primary implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if (!(s instanceof Player)) {
			Utils.msg("&cYou must be a player!", s);
		}
		if (!s.hasPermission("tags.user")) {
			Utils.msg("&cInsufficient permission", s);
			return true;
		}
		GUI.openGUI(((Player)s));
		return true;
	}

}
