package dimensions;

import init.BiomeInit;
import init.DimensionInit;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.gen.IChunkGenerator;

public class DimensionTheFuture extends WorldProvider
{
	public static boolean babySpawned = false;

	public void init()
	{
		this.biomeProvider = new BiomeProviderSingle(Biomes.SKY);
		NBTTagCompound nbttagcompound = this.world.getWorldInfo().getDimensionData(this.world.provider.getDimension());
		DimensionTheFuture.babySpawned = this.world instanceof WorldServer ? nbttagcompound.getBoolean("BabySpawned") : false;
	}

	public DimensionTheFuture()
	{
		this.biomeProvider = new BiomeProviderSingle(BiomeInit.GRAVITYFALLS);
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

	/**
	 * Called when the world is performing a save. Only used to save the state of the Dragon Boss fight in
	 * WorldProviderEnd in Vanilla.
	 */
	public void onWorldSave()
	{
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setBoolean("BabySpawned", this.babySpawned);

		this.world.getWorldInfo().setDimensionData(this.world.provider.getDimension(), nbttagcompound);
	}

	public BlockPos getSpawnCoordinate()
	{
		return new BlockPos(-18.5, 61, -18.5);
	}
}
