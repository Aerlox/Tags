package me.cougers.tags.utility.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.cougers.tags.Tags;
import me.cougers.tags.object.Tag;
import me.cougers.tags.utility.Utils;

public class GUI {

	public static void openGUI(Player p) {
		Inventory gui = Bukkit.createInventory(p, 54, Utils.c("&6&lTags"));
		gui.setItem(49, Utils.mod(new ItemStack(Material.ANVIL), "&6&l * &6Current Tag", new String[] {"", "  &7> " + (Tag.getTag(p.getUniqueId()) == null ? "&cNone." : "&e" + Tag.getTag(p.getUniqueId()).getName()), "  &eTag: " + (Tag.getTag(p.getUniqueId()) == null ? "&cNot selected." : ChatColor.WHITE + "" + Utils.c(Tag.getTag(p.getUniqueId()).getPrefix()))}, true));
		gui.setItem(46, Utils.mod(new ItemStack(Material.MINECART), "&6&l * &6Disable Current Tag", new String[] {"", "&7&o(Click: Disables your current tag)", "", "&6NOTE:&e This only un-equips the tag"}, true));
		gui.setItem(52, Utils.mod(new ItemStack(Material.MINECART), "&6&l * &6Owned Tags", new String[] {"", " &eYou currently own&6&l " + Tag.getOwnedTags(p.getUniqueId()).size() + " &etag" + (Tag.getOwnedTags(p.getUniqueId()).size() > 1 ? "s." : "."), ""}, true));
		int[] arr = {45, 47, 51, 48, 50, 53};
		ItemStack border = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		for (int i = 0; i < arr.length; i++) {
			gui.setItem(arr[i], Utils.mod(border, " ", new String[] {" "}, true));
		}
		for (Tag t : Tag.getTags()) {
			gui.addItem(Utils.mod(new ItemStack(Material.NAME_TAG), "&8&l * &6&l" + t.getName(), new String[] {"", (Tag.hasTag(p.getUniqueId(), t.getName()) ? "&aClick to select this tag." : "&cYou don't have this tag."), "&eTag: " + ChatColor.RESET + "" + t.getPrefix(), "", " " + Tags.getInstance().getConfig().getString("tags." + t.getName() + ".description"),""}, true));
		}
		p.openInventory(gui);
	}

}
