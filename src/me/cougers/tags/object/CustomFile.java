package me.cougers.tags.object;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.cougers.tags.utility.Utils;

public class CustomFile {

	private String path;
	private File f;
	private FileConfiguration yaml;

	public CustomFile(String path) {
		this.path = path;
		this.f = new File(path);

		if (!this.f.getParentFile().exists()) {
			this.f.mkdir();
		}

		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				Utils.log("An error occured while trying to create file: " + path);
			}
		}
		yaml = YamlConfiguration.loadConfiguration(f);
	}
	public String getPath() {
		return path;
	}
	public File getParent() {
		return new File(path).getParentFile();
	}
	public FileConfiguration getConfig() {
		return yaml;
	}
	public void save() {
		try {
			yaml.save(f);
		} catch (IOException e) {
			e.printStackTrace();
			Utils.log("An error occured while trying to save file: " + f.getPath());
		}
	}
}
