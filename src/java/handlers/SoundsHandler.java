package handlers;

import main.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundsHandler 
{
	public static SoundEvent ENTITY_GNOME_AMBIENT;
	public static SoundEvent ENTITY_GNOME_HURT;
	public static SoundEvent ENTITY_GNOME_DEATH;
	public static SoundEvent ENTITY_GNOME_ANGRY;
	
	public static void registerSounds()
	{
		ENTITY_GNOME_AMBIENT = registerSound("entity.gnome.ambient");
		ENTITY_GNOME_HURT = registerSound("entity.gnome.hurt");
		ENTITY_GNOME_DEATH = registerSound("entity.gnome.death");
		ENTITY_GNOME_ANGRY = registerSound("entity.gnome.angry");

		
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
