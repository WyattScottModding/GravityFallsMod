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
	private int next = 1;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider)
	{
		switch(world.provider.getDimension())
		{
		case -1:
			break;
		case 0:

			if(next == 1)
				this.generateStructureBunker(new WorldGenStructure("bunker"), world, random, chunkX, chunkZ, 100, Blocks.GRASS, BiomeGravityFalls.class);

			if(next == 2)
				this.generateStructureJournal3(new WorldGenStructure("journal3"), world, random, chunkX, chunkZ, 100, Blocks.GRASS, BiomeGravityFalls.class);

			if(next == 3)
				this.generateStructureShack(new WorldGenStructure("mysteryshack"), world, random, chunkX, chunkZ, 100, Blocks.GRASS, BiomeGravityFalls.class);

			if(next == 4)
				this.generateStructureUFO(new WorldGenStructure("ufo"), world, random, chunkX, chunkZ, 100, Blocks.GRASS, BiomeGravityFalls.class);

			this.generateStructureCrystal(new WorldGenStructure("crystal"), world, random, chunkX, chunkZ, 400, Blocks.GRASS, BiomeGravityFalls.class);
			this.generateStructureTrees(new WorldGenStructure("redwoodtrees"), world, random, chunkX, chunkZ, Blocks.GRASS, BiomeGravityFalls.class);
			this.generateStructureUranium(new WorldGenStructure("uranium"), world, random, chunkX, chunkZ);
			this.generateStructureNowYouSeeIt(new WorldGenStructure("nowyouseeitnowyoudontium"), world, random, chunkX, chunkZ, BiomeGravityFalls.class);
			this.generateStructureCopper(new WorldGenStructure("copper"), world, random, chunkX, chunkZ);
			//this.generateStructureGlobnar(new WorldGenStructure("globnar1"), world, random, chunkX, chunkZ, Blocks.GRASS, BiomeTheFuture.class);


			break;
		case 1:
			break;

		}
	}

	private void generateStructureTrees(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, Block topBlock, Class<?>...classes)
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
					generator.generate(world, random, pos);
				}
			}
		}
	}

	private void generateStructureJournal3(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>...classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>> (Arrays.asList(classes));

		int x = (chunkX * 16) + random.nextInt(15) + 8;
		int z = (chunkZ * 16) + random.nextInt(15) + 8;
		int y = calculateGenerationHeight(world, x, z, topBlock) - 1;


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
					next = 3;
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
		int y = calculateGenerationHeight(world, x, z, topBlock) - 15;


		BlockPos pos = new BlockPos(x, y, z);



		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

		if(world.getWorldType() != WorldType.FLAT)
		{
			if(classesList.contains(biome))
			{
				if(random.nextInt(chance) == 0)
				{
					generator.generate(world, random, pos);
					next = 2;
				}
			}
		}

	}

	private void generateStructureShack(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>...classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>> (Arrays.asList(classes));

		int x = (chunkX * 16) + random.nextInt(15) + 8;
		int z = (chunkZ * 16) + random.nextInt(15) + 8;
		int y = calculateGenerationHeight(world, x, z, topBlock) - 17;


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
					next = 4;
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
					next = 1;
				}
			}
		}

	}

	private void generateStructureGlobnar(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, Block topBlock, Class<?>...classes)
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

	private void generateStructureUranium(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ)
	{
		for(int i = 0; i < 4; i++)
		{
			int x = (chunkX * 16) + random.nextInt(15) + 8;
			int z = (chunkZ * 16) + random.nextInt(15) + 8;
			int y = random.nextInt(11) + 2;


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
	}

	private void generateStructureCopper(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ)
	{
		for(int q = 0; q < 20; q++)
		{
			int x = (chunkX * 16) + random.nextInt(15) + 8;
			int z = (chunkZ * 16) + random.nextInt(15) + 8;
			int y = random.nextInt(60) + 4;

			for(int i = 0; i < 2; i++)
			{
				for(int k = 0; k < 2; k++)
				{
					for(int j = 0; j < 2; j++)
					{
						x += i - 1;
						z += j - 1;
						y += k - 1;


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
				}
			}
		}
	}

	private void generateStructureNowYouSeeIt(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, Class<?>...classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>> (Arrays.asList(classes));

		int x = (chunkX * 16) + random.nextInt(15) + 8;
		int z = (chunkZ * 16) + random.nextInt(15) + 8;
		int y = random.nextInt(11) + 2;


		BlockPos pos = new BlockPos(x, y, z);


		if(world.getWorldType() != WorldType.FLAT)
		{
			Block block = world.getBlockState(pos).getBlock();

			if(block == Blocks.STONE)
			{
				Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

				if(classesList.contains(biome))
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
