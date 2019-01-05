package me.cougers.tags.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.cougers.tags.Tags;
import me.cougers.tags.object.Tag;
import me.cougers.tags.utility.Utils;
import me.cougers.tags.utility.gui.GUI;

public class Click implements Listener {

	@EventHandler
	public void click(InventoryClickEvent e) {

		Player p = (Player) e.getWhoClicked();
		ItemStack i = e.getCurrentItem();
		if (!e.getInventory().getTitle().equalsIgnoreCase(Utils.c("&6&lTags")) || i == null || i.getType() == Material.AIR || !i.hasItemMeta() || i.getItemMeta() == null || i.getItemMeta().getDisplayName() == null) {
			return;
		}

		e.setCancelled(true);

		if (i.getItemMeta().getDisplayName().contains(Utils.c("&6Disable Current Tag"))) {
			if (Tags.getData().getString("selected." + p.getUniqueId().toString()) != null) {
				Tag.unequip(p.getUniqueId());
				p.sendMessage(Utils.c("&f > &6You have unequipped your tag."));
				p.closeInventory();
				GUI.openGUI(p);
			} else {
				p.sendMessage(Utils.c("&cYou currently don't have a selected tag!"));
			}
		}

		if (i.getItemMeta().getLore() != null && i.getItemMeta().hasLore()) {
			boolean tag = false;
			for (String line : i.getItemMeta().getLore()) {
				if (line.contains("Tag:")) {
					tag = true;
					break;
				}
			}
			if (!tag) {
				return;
			}
			
			String name = ChatColor.stripColor(i.getItemMeta().getDisplayName().replace("*", "").replace(" ", "").toString());
			if (Tag.hasTag(p.getUniqueId(), name) && Tag.findTag(name) != null) {
				p.sendMessage(Utils.c(Tag.select(p.getUniqueId(), Tag.findTag(name))));
				p.closeInventory();
			}

		}

	}
}
