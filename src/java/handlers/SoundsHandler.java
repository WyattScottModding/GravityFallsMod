package handlers;

import main.Reference;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


public class SoundsHandler
{
	public static SoundEvent ENTITY_GNOME_AMBIENT;
	public static SoundEvent ENTITY_GNOME_HURT;
	
	public static SoundEvent ENTITY_BILL_DEATH;
	public static SoundEvent ENTITY_BILL_SUMMONED;
	
	public static SoundEvent ENTITY_DROID_AMBIENT;
	public static SoundEvent ENTITY_DROID_DEATH;
	
	public static SoundEvent ENTITY_UNICORN_AMBIENT;

	public static SoundEvent ENTITY_TIMEBABY_CRYING;

	public static final SoundEvent RECORD_THEMESONG;
	public static final SoundEvent RECORD_THEMESONG2;
	public static final SoundEvent RECORD_WEIRDMAGEDDON;
	public static final SoundEvent RECORD_STRAIGHTBLANCHIN;
	public static final SoundEvent RECORD_DISCOGIRL;
	public static final SoundEvent RECORD_MEETAGAIN;

	public static SoundEvent BLOCK_PASSWORD;
	
	public static SoundEvent PORTAL_WORKING;
	public static SoundEvent PORTAL_FINISHED;
	
	public static SoundEvent ITEM_MAGNETGUN;
	public static SoundEvent ITEM_RAISEDEAD;
	public static SoundEvent ITEM_LASER_ARM_CANNON;
	public static SoundEvent SMILE_DIP;
	public static SoundEvent EMP;

	public static SoundEvent JOURNAL_3;
	public static SoundEvent ALIEN_SHIP;
	public static SoundEvent STARTING_THE_PORTAL;

	public static SoundEvent SONG_1;
	public static SoundEvent SONG_2;
	public static SoundEvent SONG_3;
	public static SoundEvent SONG_4;
	public static SoundEvent SONG_5;
	public static SoundEvent SONG_6;
	public static SoundEvent SONG_7;


	static
	{
		RECORD_THEMESONG = registerSound("record.themesong");
		RECORD_THEMESONG2 = registerSound("record.themesong2");
		RECORD_WEIRDMAGEDDON = registerSound("record.weirdmageddon");
		RECORD_STRAIGHTBLANCHIN = registerSound("record.straightblanchin");
		RECORD_DISCOGIRL = registerSound("record.discogirl");
		RECORD_MEETAGAIN = registerSound("record.meetagain");

	}
	
	public static void registerSounds()
	{
		ENTITY_GNOME_AMBIENT = registerSound("entity.gnome.ambient");
		ENTITY_GNOME_HURT = registerSound("entity.gnome.hurt");
		
		ENTITY_BILL_DEATH = registerSound("entity.bill.death");
		ENTITY_BILL_SUMMONED = registerSound("entity.bill.summoned");

		ENTITY_DROID_AMBIENT = registerSound("entity.securitydroid.ambient");
		ENTITY_DROID_DEATH = registerSound("entity.securitydroid.death");
		
		ENTITY_UNICORN_AMBIENT = registerSound("entity.unicorn.ambient");
		
		ENTITY_TIMEBABY_CRYING = registerSound("entity.timebaby.crying");

		BLOCK_PASSWORD = registerSound("block.password");
		
		PORTAL_WORKING = registerSound("portal.working");
		PORTAL_FINISHED = registerSound("portal.finished");
		
		ITEM_MAGNETGUN = registerSound("item.magnetgun");
		ITEM_RAISEDEAD = registerSound("item.raisedead");
		ITEM_LASER_ARM_CANNON = registerSound("item.laser_arm_cannon");
		SMILE_DIP = registerSound("item.smile_dip");
		EMP = registerSound("item.emp");

		JOURNAL_3 = registerSound("ambient.journal_3");
		ALIEN_SHIP = registerSound("ambient.alien_ship");
		STARTING_THE_PORTAL = registerSound("ambient.portal_start");

		SONG_1 = registerSound("music.song1");
		SONG_2 = registerSound("music.song2");
		SONG_3 = registerSound("music.song3");
		SONG_4 = registerSound("music.song4");
		SONG_5 = registerSound("music.song5");
		SONG_6 = registerSound("music.song6");
		SONG_7 = registerSound("music.song7");

	}

	private static SoundEvent registerSound(String name)
	{
		ResourceLocation location = new ResourceLocation(Reference.MODID, name);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(event);

		return event;
	}
}