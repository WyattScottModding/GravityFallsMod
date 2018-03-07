package worldgen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenCustomStructures implements IWorldGenerator
{
	public static final WorldGenStructure CRYSTAL = new WorldGenStructure("crystal");
	public static final WorldGenStructure MYSTERYSHACK = new WorldGenStructure("mysteryshack");
	public static final WorldGenStructure JOURNAL3 = new WorldGenStructure("journal3");
	public static final WorldGenStructure BUNKER = new WorldGenStructure("bunker");
	public static final WorldGenStructure REDWOODTREES = new WorldGenStructure("redwoodtrees");


	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider)
	{
		switch(world.provider.getDimension())
		{
		case -1:
			break;
		case 0:
			//generateStructureBunker(BUNKER, world, random, chunkX, chunkZ, 25, Blocks.GRASS, BiomeGravityFalls.class);
			//generateStructure(CRYSTAL, world, random, chunkX, chunkZ, 25, Blocks.GRASS, BiomeGravityFalls.class);
			//generateStructureJournal3(JOURNAL3, world, random, chunkX, chunkZ, 25, Blocks.GRASS, BiomeGravityFalls.class);
			//generateStructureShack(MYSTERYSHACK, world, random, chunkX, chunkZ, 25, Blocks.GRASS, BiomeGravityFalls.class);
			generateStructure(REDWOODTREES, world, random, chunkX, chunkZ, 3, Blocks.GRASS, BiomeGravityFalls.class);

			break;
		case 1:
			break;

		}
	}

	private void generateStructure(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>...classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>> (Arrays.asList(classes));

		int x = (chunkX * 16) + random.nextInt(15) + 8;
		int z = (chunkZ * 16) + random.nextInt(15) + 8;
		int y = calculateGenerationHeight(world, x, z, topBlock);


		BlockPos pos = new BlockPos(x, y, z);

		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

		if(world.getWorldType() != WorldType.FLAT)
		{
			if(classesList.contains(biome))
			{

				generator.generate(world, random, pos);

			}
		}
	}

	private void generateStructureJournal3(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>...classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>> (Arrays.asList(classes));

		int x = (chunkX * 16) + random.nextInt(15) + 8;
		int z = (chunkZ * 16) + random.nextInt(15) + 8;
		int y = calculateGenerationHeight(world, x, z, topBlock) - 2;


		BlockPos pos = new BlockPos(x, y, z);

		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

		if(world.getWorldType() != WorldType.FLAT)
		{
			if(classesList.contains(biome))
			{
				if(random.nextInt(chance) == 0)
				{
					generator.generate(world, random, pos);
				}
			}
		}
	}

	private void generateStructureBunker(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>...classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>> (Arrays.asList(classes));

		int x = (chunkX * 16) + random.nextInt(15) + 8;
		int z = (chunkZ * 16) + random.nextInt(15) + 8;
		int y = calculateGenerationHeight(world, x, z, topBlock) - 17;


		BlockPos pos = new BlockPos(x, y, z);

		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

		if(world.getWorldType() != WorldType.FLAT)
		{
			if(classesList.contains(biome))
			{
				if(random.nextInt(chance) == 0)
				{
					generator.generate(world, random, pos);
				}
			}
		}
	}

	private void generateStructureShack(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>...classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>> (Arrays.asList(classes));

		int x = (chunkX * 16) + random.nextInt(15) + 8;
		int z = (chunkZ * 16) + random.nextInt(15) + 8;
		int y = calculateGenerationHeight(world, x, z, topBlock) - 19;


		BlockPos pos = new BlockPos(x, y, z);

		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

		if(world.getWorldType() != WorldType.FLAT)
		{
			if(classesList.contains(biome))
			{
				if(random.nextInt(chance) == 0)
				{
					generator.generate(world, random, pos);
				}
			}
		}
	}

	private static int calculateGenerationHeight(World world, int x, int z, Block topBlock)
	{
		int y = world.getHeight();
		boolean foundGround = false;

		while(!foundGround && y-- >= 0)
		{
			Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
			foundGround = block == topBlock;
		}
		return y;
	}

}
