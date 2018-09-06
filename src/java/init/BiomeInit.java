package init;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import worldgen.BiomeGravityFalls;
import worldgen.BiomeNightmareRealm;


public class BiomeInit 
{
	public static final Biome GRAVITYFALLS = new BiomeGravityFalls();
	public static final Biome NIGHTMAREREALM = new BiomeNightmareRealm();

	
	public static void registerBiomes()
	{
		initBiome(GRAVITYFALLS, "GravityFalls", BiomeType.COOL, Type.MAGICAL, Type.CONIFEROUS, Type.FOREST, Type.DEAD, Type.DRY);
		initBiome(NIGHTMAREREALM, "NightmareRealm", BiomeType.DESERT, Type.MAGICAL, Type.HOT, Type.NETHER, Type.SPOOKY);

	}

	private static Biome initBiome(Biome biome, String name, BiomeType biometype, Type... types)
	{
		biome.setRegistryName(name);
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, types);
		BiomeManager.addBiome(biometype, new BiomeEntry(biome, 10));
		BiomeManager.addSpawnBiome(biome);
		
		
		return biome;
	}
}
