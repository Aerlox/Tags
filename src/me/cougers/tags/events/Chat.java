package me.cougers.tags.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.cougers.tags.object.Tag;
import me.cougers.tags.utility.Utils;

public class Chat implements Listener {

	@EventHandler
	public void chat(AsyncPlayerChatEvent e) {
		if (Tag.getTag(e.getPlayer().getUniqueId()) != null)
		e.setFormat(e.getFormat().replace("{tag}", Utils.c(Tag.getTag(e.getPlayer().getUniqueId()).getPrefix()) + "" + ChatColor.RESET + "") + ChatColor.RESET);
		else if (Tag.getTag(e.getPlayer().getUniqueId()) == null) {
			if (e.getFormat().contains(" {tag}")) {
				e.setFormat(e.getFormat().replace(" {tag}", ""));
				return;
			}
			e.setFormat(e.getFormat().replace("{tag}", ""));
		}
	}
	
}
