package worldgen;

import java.util.Random;

import blocks.Uranium;
import init.BlockInit;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;


//Uranium Ore is now generated as a structrue
public class WorldGenOres implements IWorldGenerator{

	private WorldGenerator uranium;
	private WorldGenerator crystal;


	public WorldGenOres()
	{
		//The number is how many in a vein
	//	uranium = new WorldGenMinable(BlockInit.URANIUM.getDefaultState(), 2);
	}

	private void runGenerator(WorldGenerator gen, World world, Random rand, int chunkX, int chunkZ, int chance, int minHeight, int maxHeight)
	{
		if(minHeight > maxHeight || minHeight < 0 || maxHeight > 256) 
			throw new IllegalArgumentException("Ore generated out of bounds");
		
		int heightDiff = maxHeight - minHeight + 1;
		for(int i = 0; i < chance; i++)
		{
			int x = chunkX * 16 + rand.nextInt(16);
			int y = minHeight + rand.nextInt(heightDiff);
			int z = chunkZ * 16 + rand.nextInt(16);
			
			gen.generate(world, rand, new BlockPos(x,y,z));
		}
	}


	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) 
	{

		//chance   minheight   maxheight

		switch(world.provider.getDimension())
		{
		case -1:
			break;
		case 0:
		//	runGenerator(uranium, world, random, chunkX, chunkZ, 90, 3, 80);  
			break;
		case 1:
			break;
		}
	}

}
