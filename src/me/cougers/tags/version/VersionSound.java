package me.cougers.tags.version;

import org.bukkit.Sound;

public enum VersionSound {

	ENTITY_PLAYER_LEVELUP("LEVEL_UP"),
	ENTITY_EXPERIENCE_ORB_PICKUP("ORB_PICKUP"),
	ENTITY_ENDERMEN_HURT("ENDERMAN_HIT"),
	ENTITY_ENDERMEN_DEATH("ENDERMAN_DEATH"),
	ENTITY_VILLAGER_NO("VILLAGER_NO"),
	ENTITY_PLAYER_ATTACK_CRIT("ITEM_BREAK"),
	ENTITY_GENERIC_EAT("EAT"),
	ENTITY_ZOMBIE_ATTACK_DOOR_WOOD("ZOMBIE_WOOD"),
	ENTITY_ZOMBIE_ATTACK_IRON_DOOR("ZOMBIE_WOODBREAK"),
	ENTITY_FIREWORK_BLAST("FIREWORK_BLAST"),
	ENTITY_WITHER_SHOOT("WITHER_SHOOT"),
	ENTITY_ENDERDRAGON_FLAP("ENDERDRAGON_WINGS"),
	ENTITY_ITEM_PICKUP("ITEM_PICKUP"),
	ENTITY_ARROW_SHOOT("SHOOT_ARROW"),
	ENTITY_COW_HURT("COW_HURT"),
	ENTITY_BLAZE_HURT("BLAZE_HIT"),
	ENTITY_SHEEP_DEATH("SHEEP_IDLE"),
	ENTITY_ZOMBIE_PIG_ANGRY("ZOMBIE_PIG_ANGRY"),
	ENTITY_SKELETON_HURT("SKELETON_HURT"),
	ENTITY_GENERIC_DRINK("DRINK"),
	ENTITY_ENDERMEN_TELEPORT("ENDERMAN_TELEPORT"),
	ENTITY_GENERIC_EXPLODE("EXPLODE"),
	ENTITY_SNOWBALL_THROW("SHOOT_ARROW"),
	ENTITY_FIREWORK_TWINKLE("FIREWORK_TWINKLE"),
	ENTITY_FIREWORK_LARGE_BLAST("FIREWORK_LARGE_BLAST"),
	ENTITY_CHICKEN_EGG("CHICKEN_EGG_POP"),
	ENTITY_BLAZE_AMBIENT("BLAZE_BREATH"),
	BLOCK_GLASS_BREAK("GLASS"),
	BLOCK_GRAVEL_BREAK("DIG_GRAVEL"),
	BLOCK_ANVIL_LAND("ANVIL_LAND"),
	BLOCK_FIRE_AMBIENT("FIRE"),
	BLOCK_NOTE_HAT("NOTE_STICKS"),
	BLOCK_NOTE_PLING("NOTE_PLING"),
	BLOCK_TRIPWIRE_ATTACH("CLICK"),
	BLOCK_FIRE_EXTINGUISH("FIZZ"),
	BLOCK_ANVIL_USE("ANVIL_USE"),
	BLOCK_BREWING_STAND_BREW("CREEPER_HISS"),
	UI_BUTTON_CLICK("CLICK");

	private Sound sound;
	private VersionSound(String current) {
		try {
			sound = Sound.valueOf(current);
		} catch (IllegalArgumentException iae) {
			sound = Sound.valueOf(name());
		}
	}
	public Sound get() {
		return sound;
	}
}