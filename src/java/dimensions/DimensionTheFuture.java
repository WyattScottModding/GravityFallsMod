package dimensions;

import init.BiomeInit;
import init.DimensionInit;
import net.minecraft.init.Biomes;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;

public class DimensionTheFuture extends WorldProvider
{

	public DimensionTheFuture()
	{
		this.biomeProvider = new BiomeProviderSingle(Biomes.PLAINS);
	}

	@Override
	public DimensionType getDimensionType() 
	{
		return DimensionInit.THEFUTURE;
	}

	@Override
	public IChunkGenerator createChunkGenerator() 
	{
		return new ChunkGeneratorTheFuture(world, true, world.getSeed());
	}

	@Override
	public boolean canRespawnHere() 
	{
		return false;
	}
	
	@Override
	public boolean isSurfaceWorld() 
	{
		return false;
	}
}
