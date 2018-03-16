package worldgen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeDesert;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.biome.BiomeHills;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenCustomStructures implements IWorldGenerator
{
	//	WorldGenStructure CRYSTAL = new WorldGenStructure("crystal");
	//	WorldGenStructure MYSTERYSHACK = new WorldGenStructure("mysteryshack");
	//	WorldGenStructure JOURNAL3 = new WorldGenStructure("journal3");
	//	WorldGenStructure BUNKER = new WorldGenStructure("bunker");
	//	WorldGenStructure REDWOODTREES = new WorldGenStructure("redwoodtrees");


	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider)
	{
		switch(world.provider.getDimension())
		{
		case -1:
			break;
		case 0:

			this.generateStructureBunker(new WorldGenStructure("bunker"), world, random, chunkX, chunkZ, 400, Blocks.GRASS, BiomeGravityFalls.class);
			this.generateStructureCrystal(new WorldGenStructure("crystal"), world, random, chunkX, chunkZ, 300, Blocks.GRASS, BiomeGravityFalls.class);
			this.generateStructureJournal3(new WorldGenStructure("journal3"), world, random, chunkX, chunkZ, 400, Blocks.GRASS, BiomeGravityFalls.class);
			this.generateStructureShack(new WorldGenStructure("mysteryshack"), world, random, chunkX, chunkZ, 400, Blocks.GRASS, BiomeGravityFalls.class);
			this.generateStructure(new WorldGenStructure("redwoodtrees"), world, random, chunkX, chunkZ, 1, Blocks.GRASS, BiomeGravityFalls.class);
			this.generateStructureUFO(new WorldGenStructure("ufo"), world, random, chunkX, chunkZ, 400, Blocks.GRASS, BiomeGravityFalls.class);
			this.generateStructureUranium(new WorldGenStructure("uranium"), world, random, chunkX, chunkZ);

			//Not sure why the code below doesn't work
			//	this.generateStructure(REDWOODTREES, world, random, chunkX, chunkZ, 3, Blocks.GRASS, BiomeGravityFalls.class);
			//	this.generateStructureBunker(BUNKER, world, random, chunkX, chunkZ, 25, Blocks.GRASS, BiomeGravityFalls.class);
			//	this.generateStructure(CRYSTAL, world, random, chunkX, chunkZ, 25, Blocks.GRASS, BiomeGravityFalls.class);
			//	this.generateStructureJournal3(JOURNAL3, world, random, chunkX, chunkZ, 25, Blocks.GRASS, BiomeGravityFalls.class);
			//	this.generateStructureShack(MYSTERYSHACK, world, random, chunkX, chunkZ, 25, Blocks.GRASS, BiomeGravityFalls.class);

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

		Block block = world.getBlockState(new BlockPos(pos.getX() - 7, pos.getY(), pos.getZ() - 6)).getBlock();

		if(block == topBlock)
		{
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
	}

	private void generateStructureJournal3(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>...classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>> (Arrays.asList(classes));

		int x = (chunkX * 16) + random.nextInt(15) + 8;
		int z = (chunkZ * 16) + random.nextInt(15) + 8;
		int y = calculateGenerationHeight(world, x, z, topBlock) - 2;


		BlockPos pos = new BlockPos(x, y, z);

		//	Block block = world.getBlockState(new BlockPos(pos.getX() + 9, pos.getY(), pos.getZ() + 17)).getBlock();

		//	if(block == topBlock)
		//	{
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
		//	}
	}

	private void generateStructureBunker(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>...classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>> (Arrays.asList(classes));

		int x = (chunkX * 16) + random.nextInt(15) + 8;
		int z = (chunkZ * 16) + random.nextInt(15) + 8;
		int y = calculateGenerationHeight(world, x, z, topBlock) - 17;


		BlockPos pos = new BlockPos(x, y, z);

		//	Block block = world.getBlockState(new BlockPos(pos.getX() + 20, pos.getY(), pos.getZ() - 20)).getBlock();

		//	if(block == topBlock)
		//	{
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
		//	}
	}

	private void generateStructureShack(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>...classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>> (Arrays.asList(classes));

		int x = (chunkX * 16) + random.nextInt(15) + 8;
		int z = (chunkZ * 16) + random.nextInt(15) + 8;
		int y = calculateGenerationHeight(world, x, z, topBlock) - 19;


		BlockPos pos = new BlockPos(x, y, z);

		//	Block block = world.getBlockState(new BlockPos(pos.getX() + 32, pos.getY(), pos.getZ() - 32)).getBlock();

		//	if(block == topBlock)
		//	{
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
		//	}
	}

	private void generateStructureCrystal(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>...classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>> (Arrays.asList(classes));

		int x = (chunkX * 16) + random.nextInt(15) + 8;
		int z = (chunkZ * 16) + random.nextInt(15) + 8;
		int y = calculateGenerationHeight(world, x, z, topBlock) + 1;


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

	private void generateStructureUranium(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ)
	{

		int x = (chunkX * 16) + random.nextInt(15) + 8;
		int z = (chunkZ * 16) + random.nextInt(15) + 8;
		int y = random.nextInt(11);


		BlockPos pos = new BlockPos(x, y, z);


		if(world.getWorldType() != WorldType.FLAT)
		{

			Block block = world.getBlockState(pos).getBlock();

			if(block == Blocks.STONE)
			{
				generator.generate(world, random, pos);
			}

		}
	}

	private void generateStructureUFO(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>...classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>> (Arrays.asList(classes));

		int x = (chunkX * 16) + random.nextInt(15) + 8;
		int z = (chunkZ * 16) + random.nextInt(15) + 8;
		int y = calculateGenerationHeight(world, x, z, topBlock) - 18;


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
