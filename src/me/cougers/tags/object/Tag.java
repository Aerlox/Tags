package me.cougers.tags.object;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.cougers.tags.Tags;
import me.cougers.tags.utility.Utils;

public class Tag {

	private static FileConfiguration config = Tags.getInstance().getConfig();
	public static FileConfiguration data = Tags.getData();
	private String name, prefix;

	public Tag(String name, String prefix) {
		this.name = name;
		this.prefix = prefix;
	}
	public static Tag findTag(String name) {
		Tag result = null;
		for (String tag : config.getConfigurationSection("tags.").getKeys(false)) {
			if (tag.equalsIgnoreCase(name)) {
				result = new Tag(name, config.getString("tags." + name + ".prefix"));
				break;
			}
		}
		return result;
	}
	public static String create(String name, String prefix) {
		if (getTags().size() >= 45) {
			return "&cYou have reached the max tag capacity, please remove some tags.";
		}
		if (name.contains("&") || name.contains("§")) {
			return "&cYou aren't allowed to use colors in the tag names.";
		}
		if (!exists(name)) {
			config.set("tags." + name, name);
			config.set("tags." + name + ".prefix", prefix);
			config.set("tags." + name + ".description", "&6Description: &eNone :(");
			return "&f > &6Created new tag '" + name + "' with prefix: " + Utils.c(prefix);
		}
		return "&cThe tag '" + name + "' already exists.";
	}
	private static boolean exists(String name) {
		return config.getString("tags." + name) == null ? false : true;
	}
	public String getDescription() {
		return config.getString("tags." + name + ".description");
	}
	public String getName() {
		return name;
	}
	public String getPrefix() {
		return prefix;
	}
	public static boolean hasTag(UUID id, String name) {
		if (data.getString("owned." + id.toString()) != null) {
			for (String tag : getOwnedTags(id)) {
				if (tag.equalsIgnoreCase(name)) {
					return true;
				}
			}
		} 
		return false;
	}
	public static List<String> getOwnedTags(UUID id){
		return (data.getString("owned." + id.toString()) == null ? new ArrayList<>() : data.getStringList("owned." + id.toString()));
	}
	public static String take(UUID id, Tag tag, boolean online, Player targetPlayer, String targetName) {
		if (!hasTag(id, tag.getName())) {
			return "That player doesn't have the tag: '" + tag.getName() + "'";
		}
		List<String> list = getOwnedTags(id);
		list.remove(tag.getName());
		data.set("owned." + id.toString(), list);
		data.set("selected." + id.toString(), null);
		if (online) {
			targetPlayer.sendMessage("");
			targetPlayer.sendMessage(Utils.c("&cYou no longer own the tag:&6&l " + tag.getName()));
			targetPlayer.sendMessage("");
			Utils.getTagSound(targetPlayer);
		}
		return "&f > &aGiven " + targetName + " the tag '" + tag.getName() + "'";
	}
	public static String give(UUID id, Tag tag, boolean online, Player targetPlayer, String targetName) {
		if (hasTag(id, tag.getName())) {
			return "That player already the tag: '" + tag.getName() + "'";
		}
		List<String> list = getOwnedTags(id);
		list.add(tag.getName());
		data.set("owned." + id.toString(), list);
		if (online) {
			targetPlayer.sendMessage("");
			targetPlayer.sendMessage(Utils.c("  &a&nCongratulations!&6 You now own the tag:&6&l " + tag.getName() + "&6." + "\n" + " " + "\n" + "   &f > &6Select your tag by doing &a/tags"));
			targetPlayer.sendMessage("");
			Utils.getTagSound(targetPlayer);
		}
		return "&f > &aGiven " + targetName + " the tag '" + tag.getName() + "'";
	}
	public static String deleteTag(String name) {
		if (config.getString("tags") == null || config.getConfigurationSection("tags") == null) {
			return "&cTag '" + name + "' couldn't be deleted because it doesn't exist.";
		}
		boolean exists = false;
		String c = "";
		for (String s : config.getConfigurationSection("tags").getKeys(false)) {
			if (s.equalsIgnoreCase(name)) {
				exists = true;
				c = s;
				break;
			}
		}
		if (!exists) {
			return "&cTag '" + name + "' couldn't be deleted because it doesn't exist.";
		}
		int index = 0;
		List<String> tags = null;

		if (data.getConfigurationSection("selected") != null) {
			for (String user : data.getConfigurationSection("selected").getKeys(false)) {
				if (data.getString("selected." + user).equalsIgnoreCase(name)) {
					data.set("selected." + user, null);
				}
			}
		}
		if (data.getConfigurationSection("owned") != null) {
			for (String user : data.getConfigurationSection("owned").getKeys(false)) {
				if (data.getString("owned." + user) != null) {
					tags = data.getStringList("owned." + user);
					while (index < tags.size()) {
						if (tags.get(index).equalsIgnoreCase(name) && findTag(tags.get(index)) != null) {
							tags.remove(index);
							data.set("owned." + user, tags);
							break;
						}
						index++;
					}
				}
			}
			config.set("tags." + c, null);
			Tags.getInstance().saveConfig();
		}
		return "&cTag '" + c + "' has been deleted.";
	}
	public static void unequip(UUID id) {
		if (data.getString("selected." + id.toString()) != null) {
			data.set("selected." + id.toString(), null);
		}
	}
	public static String select(UUID id, Tag tag) {
		data.set("selected." + id.toString(), tag.getName());
		return "&f > &6You have selected the tag: " + tag.getName();
	}
	public static List<Tag> getTags() {
		List<Tag> result = new ArrayList<>();
		if (config.getString("tags") == null || config.getConfigurationSection("tags") == null) {
			return result;
		}
		for (String tag : config.getConfigurationSection("tags").getKeys(false)) {
			result.add(new Tag(tag, config.getString("tags." + tag + ".prefix")));
		}
		return result;
	}
	public String edit(Tag tag, String newTag) {
		String change = Utils.c("&f > &aChanged " + tag.getPrefix() + "&a to: " + newTag);
		config.set("tags." + tag.getName() + ".prefix", newTag);
		this.prefix = config.getString("tags." + tag.getName() + ".prefix");
		return change;
	}
	public static String setDescription(Tag tag, String desc) {
		config.set("tags." + tag.getName() + ".description", desc);
		return "&aSet description of tag '" + tag.getName() + "' to:&f " + desc + "&a.";
	}
	public static Tag getTag(UUID id) {
		Tag result = null;
		if (data.getString("selected." + id.toString()) != null) {
			result = findTag(data.getString("selected." + id.toString()));
		}
		return result;
	}
}
