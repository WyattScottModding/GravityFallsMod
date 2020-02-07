package updates;

import java.io.File;

import entity.EntityEightBall;
import entity.EntityEyeBatHuge;
import entity.EntityKeyhole;
import init.BiomeInit;
import main.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class WeirdmageddonEvent {
	
	public static boolean eventActive = false;

	public static void startEvent(World world) {
        Blocks.FIRE.setFireInfo(Blocks.GRASS, 100, 100);
        
       //Spawn Bill's Castle
        
        
        //Bubbles of Pure Maddness
        
        
        //Spawn Nightmare Realm Mobs in Overworld
        spawnMobs();
        
        //Time is inconsistant
        
        
        //Weirdness Wave
        	//Mobs are altered like their size
        	//Flying Mobs
        
        //Make Bill Stronger
        
        
        //Weirdmageddon Sky
      //  createSky(world);
        
        eventActive = true;
	}

	public static void endEvent(World world) {
        Blocks.FIRE.setFireInfo(Blocks.GRASS, 0, 0);
		//world.provider.setAllowedSpawnTypes(true, true);

        removeMobs();
        
        eventActive = false;
	}

	public static void spawnMobs() {		
     //  	EntityRegistry.addSpawn(EntityEightBall.class, 1, 1, 1, EnumCreatureType.MONSTER, BiomeInit.GRAVITYFALLS);
      // 	EntityRegistry.addSpawn(EntityKeyhole.class, 1, 1, 1, EnumCreatureType.MONSTER, BiomeInit.GRAVITYFALLS);
       //	EntityRegistry.addSpawn(EntityEyeBatHuge.class, 30, 1, 5, EnumCreatureType.MONSTER, BiomeInit.GRAVITYFALLS);
	}
	
	public static void removeMobs() {		
       //	EntityRegistry.removeSpawn(EntityEightBall.class, EnumCreatureType.MONSTER, BiomeInit.GRAVITYFALLS);
       //	EntityRegistry.removeSpawn(EntityKeyhole.class, EnumCreatureType.MONSTER, BiomeInit.GRAVITYFALLS);
       //	EntityRegistry.removeSpawn(EntityEyeBatHuge.class, EnumCreatureType.MONSTER, BiomeInit.GRAVITYFALLS);
	}
	
	public static void createSky(World world) {
		File file = new File(":minecraft/textures/newfolder.txt");
		file.mkdirs();
		File file2 = new File(":minecraft/textures/newfolder2");
		file2.mkdirs();
		world.provider.setAllowedSpawnTypes(true, false);
		
		//System.out.println(Minecraft.getMinecraft().gameSettings.)
	}
}
