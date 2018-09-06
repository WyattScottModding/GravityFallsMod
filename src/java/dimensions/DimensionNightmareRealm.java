package dimensions;

import java.util.Iterator;

import init.BiomeInit;
import init.DimensionInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;

public class DimensionNightmareRealm extends WorldProvider
{

	public DimensionNightmareRealm()
	{
		this.biomeProvider = new BiomeProviderSingle(BiomeInit.NIGHTMAREREALM);
	}

	@Override
	public DimensionType getDimensionType() 
	{
		return DimensionInit.NIGHTMAREREALM;
	}

	@Override
	public IChunkGenerator createChunkGenerator() 
	{
		return new ChunkGeneratorNightmareRealm(world, true, world.getSeed());
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
	
	@Override
	public void setSpawnPoint(BlockPos pos) 
	{
		Iterator iter = world.playerEntities.iterator();
		
		for(EntityPlayer element : world.playerEntities)
		{
			element.setSpawnPoint(element.getPosition(), true);
		}
	}
}
