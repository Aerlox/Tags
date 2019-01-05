package me.cougers.tags.version;

import org.bukkit.Bukkit;

public class BukkitVersion {

	private String ver;
	public BukkitVersion(String ver) {
		this.ver = ver;
	}

	public static BukkitVersion get() {
		
		String version = Bukkit.getServer().getVersion();
		int l = 0;
		char[] charArray;
		
		for (int length = (charArray = version.toCharArray()).length, k = 0; k < length; ++k) {
			final Character c = charArray[k];
			++l;
			if (c.equals('(')) {
				break;
			}
		}
		
		return new BukkitVersion(version.substring(l).replace("MC: ", "").replace(")", "").toString());
	}

	public String ver() {
		return ver;
	}
	
	public boolean compatible() {
		
		boolean compatible = false;
		final String[] supported = {"1.12", "1.11", "1.10", "1.9", "1.8", "1.8.8", "1.12.2", "1.13", "1.13.1", "1.13.2"};
		
		for (int j = 0; j < supported.length; ++j) {
			if (ver.toLowerCase().contains(supported[j].toLowerCase())) {
				compatible = true;
				break;
			}
		}
		
		return compatible;
	}
}
