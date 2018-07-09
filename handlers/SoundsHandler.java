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
	public static SoundEvent ENTITY_GNOME_DEATH;
	public static SoundEvent ENTITY_GNOME_ANGRY;
	
	public static SoundEvent ENTITY_BILL_DEATH;
	
	public static SoundEvent ENTITY_DROID_AMBIENT;
	public static SoundEvent ENTITY_DROID_DEATH;

	public static final SoundEvent RECORD_THEMESONG;
	public static final SoundEvent RECORD_THEMESONG2;
	public static final SoundEvent RECORD_WEIRDMAGEDDON;
	public static final SoundEvent RECORD_STRAIGHTBLANCHIN;
	public static final SoundEvent RECORD_DISCOGIRL;
	
	public static SoundEvent BLOCK_PASSWORD;
	
	public static SoundEvent ITEM_MAGNETGUN;



	static
	{
		RECORD_THEMESONG = registerSound("record.themesong");
		RECORD_THEMESONG2 = registerSound("record.themesong2");
		RECORD_WEIRDMAGEDDON = registerSound("record.weirdmageddon");
		RECORD_STRAIGHTBLANCHIN = registerSound("record.straightblanchin");
		RECORD_DISCOGIRL = registerSound("record.discogirl");
	}
	
	public static void registerSounds()
	{
		ENTITY_GNOME_AMBIENT = registerSound("entity.gnome.ambient");
		ENTITY_GNOME_HURT = registerSound("entity.gnome.hurt");
		ENTITY_GNOME_DEATH = registerSound("entity.gnome.death");
		ENTITY_GNOME_ANGRY = registerSound("entity.gnome.angry");
		
		ENTITY_BILL_DEATH = registerSound("entity.bill.death");
		
		ENTITY_DROID_AMBIENT = registerSound("entity.securitydroid.ambient");
		ENTITY_DROID_DEATH = registerSound("entity.securitydroid.death");
		
		BLOCK_PASSWORD = registerSound("block.password");
		
		ITEM_MAGNETGUN = registerSound("item.magnetgun");
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