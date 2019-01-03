package me.cougers.tags.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.cougers.tags.Tags;
import me.cougers.tags.object.Tag;
import me.cougers.tags.utility.Utils;

public class Admin implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if (!s.hasPermission("tags.admin")) {
			Utils.msg("&cInsufficient permission", s);
			return true;
		}
		if (args.length < 3 && args.length != 2 && args.length != 1) {
			Utils.msg("", s);
			Utils.msg("&c/" + label + " <reload/create/remove> <name> [tag..]", s);
			Utils.msg("&c/" + label + " <taketag/givetag> <tagname> <playername>", s);
			Utils.msg("&c/" + label + " checktags <playername>", s);
			Utils.msg("&c/" + label + " setdescription <tag> [description..]", s);
			Utils.msg("&c/" + label + " edit <tag> [new tag..]", s);
			Utils.msg("", s);
			return true;
		}

		if (args.length >= 3 && args[0].equalsIgnoreCase("edit")) {
			String tag = "";
			for (int i = 2; i < args.length; i++) {
				tag = (tag.matches("") ? tag + args[i] : tag + " " + args[i]);
			}
			Tag req = Tag.findTag(args[1]);
			if (req == null) {
				Utils.msg("&cYour requested tag '" + args[1] + "' couldn't be found.", s);
				return true;
			} else {
				Utils.msg(req.edit(req, tag), s);
			}
		}
		if (args.length >= 3 && args[0].equalsIgnoreCase("setdescription")) {
			String desc = "";
			for (int i = 2; i < args.length; i++) {
				desc = (desc.matches("") ? desc + args[i] : desc + " " + args[i]);
			}
			Tag req = Tag.findTag(args[1]);
			if (req == null) {
				Utils.msg("&cYour requested tag '" + args[1] + "' couldn't be found.", s);
				return true;
			} else {
				Utils.msg(Tag.setDescription(req, desc), s);
			}
		}
		if (args[0].equalsIgnoreCase("checktags") && args.length == 2) {
			String list = "";
			for (String tag : Tag.getOwnedTags(UUID.fromString(Utils.grabUser(args[1])[1]))) {
				list = (list.matches("") ? list + tag : list + ", " + tag);
			}
			String name = Utils.grabUser(args[1])[0];
			Utils.msg("&6" + name + (name.endsWith("s") ? "'" : "'s") + " tags:" + "\n" + "&e" + (list.matches("") ? "&cThis user has no tags." : "&e" + list + "&6.") , s);
		}

		if (args[0].equalsIgnoreCase("taketag") && args.length == 3) {
			boolean online = Utils.grabUser(args[2])[2].equals("online") ? true : false;
			UUID id = UUID.fromString(Utils.grabUser(args[2])[1]);
			@SuppressWarnings("deprecation")
			Player target = Bukkit.getPlayer(args[2]);
			String name = Utils.grabUser(args[2])[0];
			Tag requested = Tag.findTag(args[1]);
			if (requested == null) {
				Utils.msg("&cYour requested tag '" + args[1] + "' couldn't be found.", s);
				return true;
			}
			if (!Tag.hasTag(id, requested.getName())) {
				Utils.msg("&cUser " + name + " doesn't have the tag '" + requested.getName() + "'", s);
				return true;
			}
			Tag.take(id, requested, online, target, name);
			Utils.msg("&aTaken tag: '" + requested.getName() + "' from user " + name, s);
		}

		if (args[0].equalsIgnoreCase("givetag") && args.length == 3) {
			boolean online = Utils.grabUser(args[2])[2].equals("online") ? true : false;
			UUID id = UUID.fromString(Utils.grabUser(args[2])[1]);
			@SuppressWarnings("deprecation")
			Player target = Bukkit.getPlayer(args[2]);
			String name = Utils.grabUser(args[2])[0];
			Tag requested = Tag.findTag(args[1]);
			if (requested == null) {
				Utils.msg("&cYour requested tag '" + args[1] + "' couldn't be found.", s);
				return true;
			}
			if (Tag.hasTag(id, requested.getName())) {
				Utils.msg("&cUser " + name + " already has the tag '" + requested.getName() + "'", s);
				return true;
			}
			Tag.give(id, requested, online, target, name);
			Utils.msg("&aGiven tag: '" + requested.getName() + "' to user " + name, s);
		}

		if (args[0].equalsIgnoreCase("reload") && args.length == 1) {
			Tags.getInstance().saveConfig();
			Utils.msg("&f > &aThe configuration has been reloaded.", s);
		}

		if (args[0].equalsIgnoreCase("create") && args.length >= 3) {

			String prefix = "";
			for (int i = 2; i < args.length; i++) {
				prefix = (prefix.matches("") ? prefix + args[i] : prefix + " " + args[i]);
			}
			Utils.msg(Tag.create(args[1], prefix.replace("'", "`")), s);
		}

		if (args[0].equalsIgnoreCase("remove") && args.length == 2) {
			Utils.msg(Tag.deleteTag(args[1]), s);
		}

		return true;
	}

}
