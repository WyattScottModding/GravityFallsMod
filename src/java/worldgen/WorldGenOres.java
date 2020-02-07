package worldgen;

import java.util.Random;

import init.BlockInit;
import main.ConfigHandler;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenOres implements IWorldGenerator{

	private WorldGenerator uranium;
	private WorldGenerator NowYouSeeIt;
	private WorldGenerator copper;
	private WorldGenerator copper2;
	private WorldGenerator iron;
	private WorldGenerator gold;


	public WorldGenOres()
	{
		//The number is how many in a vein
		uranium = new WorldGenMinable(BlockInit.URANIUM.getDefaultState(), 3);
		NowYouSeeIt = new WorldGenMinable(BlockInit.HIDDEN_ELEMENT.getDefaultState(), 2);
		copper = new WorldGenMinable(BlockInit.COPPER.getDefaultState(), 8);
		copper2 = new WorldGenMinable(BlockInit.COPPER_NIGHTMARE.getDefaultState(), 8, BlockMatcher.forBlock(BlockInit.ASTEROID));
		iron = new WorldGenMinable(BlockInit.IRON_NIGHTMARE.getDefaultState(), 8, BlockMatcher.forBlock(BlockInit.ASTEROID));
		gold = new WorldGenMinable(BlockInit.GOLD_NIGHTMARE.getDefaultState(), 8, BlockMatcher.forBlock(BlockInit.ASTEROID));

	}

	private void runGenerator(WorldGenerator gen, World world, Random rand, int chunkX, int chunkZ, int chance, int minHeight, int maxHeight)
	{		
		if(minHeight > maxHeight || minHeight < 0 || maxHeight > 256) 
			throw new IllegalArgumentException("Ore generated out of bounds");

		int heightDiff = maxHeight - minHeight + 1;
		for(int i = 0; i < chance; i++)
		{
			int x = (chunkX * 16) + rand.nextInt(16);
			int y = minHeight + rand.nextInt(heightDiff);
			int z = (chunkZ * 16) + rand.nextInt(16);

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
			runGenerator(copper, world, random, chunkX, chunkZ, 8, 0, 80);  
			runGenerator(uranium, world, random, chunkX, chunkZ, 2, 0, 10);  

			break;
		case 1:
			break;
		}

		if(world.provider.getDimension() == ConfigHandler.NIGHTMARE_REALM) {
			runGenerator(copper2, world, random, chunkX, chunkZ, 3, 2, 100);  
			runGenerator(iron, world, random, chunkX, chunkZ, 3, 2, 100);  
			runGenerator(gold, world, random, chunkX, chunkZ, 2, 2, 100);  
			runGenerator(NowYouSeeIt, world, random, chunkX, chunkZ, 1, 0, 100);  
		}
	}

}
