package init;

import main.ConfigHandler;
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
	public static final Biome NIGHTMAREREALM2 = new BiomeNightmareRealm();


	public static void registerBiomes()
	{
		initGravityFalls(GRAVITYFALLS, "GravityFalls", BiomeType.COOL, Type.MAGICAL, Type.CONIFEROUS, Type.FOREST, Type.DRY, Type.DENSE);
		initNightmareRealm(NIGHTMAREREALM, "NightmareRealm", BiomeType.DESERT, Type.MAGICAL, Type.HOT, Type.NETHER, Type.SPOOKY);
		initNightmareRealm(NIGHTMAREREALM2, "NightmareRealm2", BiomeType.ICY, Type.MAGICAL, Type.COLD, Type.NETHER, Type.SPOOKY, Type.HILLS);
	}

	private static Biome initGravityFalls(Biome biome, String name, BiomeType biometype, Type... types)
	{
		biome.setRegistryName(name);
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, types);
		BiomeManager.addBiome(biometype, new BiomeEntry(biome, ConfigHandler.GRAVITY_FALLS_BIOME_WEIGHT));
		BiomeManager.addVillageBiome(biome, true);
		BiomeManager.addStrongholdBiome(biome);

		BiomeManager.addSpawnBiome(biome);

		return biome;
	}
	
	private static Biome initNightmareRealm(Biome biome, String name, BiomeType biometype, Type... types)
	{
		biome.setRegistryName(name);
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, types);
		BiomeManager.addBiome(biometype, new BiomeEntry(biome, 0));

		BiomeManager.addSpawnBiome(biome);

		return biome;
	}
}