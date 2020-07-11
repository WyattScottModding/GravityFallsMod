package worldgen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import init.BlockInit;
import main.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenCustomStructures implements IWorldGenerator
{
	private int next = 1;
	private boolean generated1 = false;
	private boolean generated2 = false;
	private boolean generated3= false;
	private boolean generated4 = false;
	private boolean generated5 = false;
	private boolean generated6 = false;
	private boolean generated7 = false;
	private boolean generated8 = false;
	private boolean generated9 = false;
	private boolean generated10 = false;
	private boolean generated11 = false;
	private boolean generated12 = false;
	private boolean generated13 = false;
	private boolean generated14 = false;
	private boolean generated15 = false;
	private boolean generated16 = false;

	private IChunkGenerator chunkGenerator = null;


	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider)
	{
		this.chunkGenerator = chunkGenerator;

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

			this.generateStructureCrystal(new WorldGenStructure("crystal"), world, random, chunkX, chunkZ, 120, Blocks.GRASS, BiomeGravityFalls.class);
			//this.generateStructureTrees(new WorldGenStructure("redwoodtrees"), world, random, chunkX, chunkZ, Blocks.GRASS, BiomeGravityFalls.class);
			this.generateStructureCursedDoor(new WorldGenStructure("cursed_door1"), world, random, chunkX, chunkZ, 1000);

			break;
		case 1:
			break;
		}
		if(world.provider.getDimension() == ConfigHandler.THE_FUTURE) {

			if(!generated1)
			{
				BlockPos pos = new BlockPos(0, 60, 0);
				this.generateStructureGlobnar(new WorldGenStructure("globnar1"), world, random, pos);
				generated1 = true;
			}
			if(!generated2)
			{
				BlockPos pos = new BlockPos(0, 60, -32);
				this.generateStructureGlobnar(new WorldGenStructure("globnar2"), world, random, pos);
				generated2 = true;
			}
			if(!generated3)
			{
				BlockPos pos = new BlockPos(0, 60, -64);
				this.generateStructureGlobnar(new WorldGenStructure("globnar3"), world, random, pos);
				generated3 = true;
			}
			if(!generated4)
			{
				BlockPos pos = new BlockPos(0, 60, -69);
				this.generateStructureGlobnar(new WorldGenStructure("globnar4"), world, random, pos);
				generated4 = true;
			}
			if(!generated5)
			{
				BlockPos pos = new BlockPos(-32, 60, 0);
				this.generateStructureGlobnar(new WorldGenStructure("globnar5"), world, random, pos);
				generated5 = true;
			}
			if(!generated6)
			{
				BlockPos pos = new BlockPos(-32, 60, -32);
				this.generateStructureGlobnar(new WorldGenStructure("globnar6"), world, random, pos);
				generated6 = true;
			}
			if(!generated7)
			{
				BlockPos pos = new BlockPos(-32, 60, -64);
				this.generateStructureGlobnar(new WorldGenStructure("globnar7"), world, random, pos);
				generated7 = true;
			}
			if(!generated8)
			{
				BlockPos pos = new BlockPos(-32, 60, -69);
				this.generateStructureGlobnar(new WorldGenStructure("globnar8"), world, random, pos);
				generated8 = true;
			}
			if(!generated9)
			{
				BlockPos pos = new BlockPos(-64, 60, 0);
				this.generateStructureGlobnar(new WorldGenStructure("globnar9"), world, random, pos);
				generated9 = true;
			}
			if(!generated10)
			{
				BlockPos pos = new BlockPos(-64, 60, -32);
				this.generateStructureGlobnar(new WorldGenStructure("globnar10"), world, random, pos);
				generated10 = true;
			}
			if(!generated11)
			{
				BlockPos pos = new BlockPos(-64, 60, -64);
				this.generateStructureGlobnar(new WorldGenStructure("globnar11"), world, random, pos);
				generated11 = true;
			}
			if(!generated12)
			{
				BlockPos pos = new BlockPos(-64, 60, -69);
				this.generateStructureGlobnar(new WorldGenStructure("globnar12"), world, random, pos);
				generated12 = true;
			}
			if(!generated13)
			{
				BlockPos pos = new BlockPos(-70, 60, 0);
				this.generateStructureGlobnar(new WorldGenStructure("globnar13"), world, random, pos);
				generated13 = true;
			}
			if(!generated14)
			{
				BlockPos pos = new BlockPos(-70, 60, -32);
				this.generateStructureGlobnar(new WorldGenStructure("globnar14"), world, random, pos);
				generated14 = true;
			}
			if(!generated15)
			{
				BlockPos pos = new BlockPos(-70, 60, -64);
				this.generateStructureGlobnar(new WorldGenStructure("globnar15"), world, random, pos);
				generated15 = true;
			}
			if(!generated16)
			{
				BlockPos pos = new BlockPos(-70, 60, -69);
				this.generateStructureGlobnar(new WorldGenStructure("globnar16"), world, random, pos);
				generated16 = true;
			}
		}
		if(world.provider.getDimension() == ConfigHandler.NIGHTMARE_REALM) {
			this.generateChest(new WorldGenStructure("chest"), world, random, chunkX, chunkZ, 150, BlockInit.ASTEROID, BiomeNightmareRealm.class);
			this.generateStructureCursedDoor(new WorldGenStructure("cursed_door2"), world, random, chunkX, chunkZ, 150);
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
		int y = calculateGenerationHeight(world, x + 4, z + 4, topBlock) - 2;


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
		int y = calculateGenerationHeight(world, x + 16, z + 16, topBlock) - 16;

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
		int y = calculateGenerationHeight(world, x + 16, z + 16, topBlock) - 17;


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
	}

	private void generateStructureCrystal(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>...classes)
	{
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>> (Arrays.asList(classes));

		int x = (chunkX * 16) + random.nextInt(15) + 8;
		int z = (chunkZ * 16) + random.nextInt(15) + 8;
		int y = calculateGenerationHeight(world, x, z, topBlock) + 1;


		BlockPos pos = new BlockPos(x, y, z);

		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

		if(world.getWorldType() != WorldType.FLAT  && world.getBlockState(pos).getBlock() == Blocks.AIR)
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

	private void generateStructureCursedDoor(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance)
	{
		int x = (chunkX * 16) + random.nextInt(15) + 8;
		int z = (chunkZ * 16) + random.nextInt(15) + 8;
		int y = calculateGenerationHeightDoor(world, x + 2, z + 2) - 1;


		if(y != -1) {
			BlockPos pos = new BlockPos(x, y, z);

			if(world.getWorldType() != WorldType.FLAT  && world.getBlockState(pos).getBlock() == Blocks.AIR)
			{
				if(random.nextInt(chance) == 0)
				{ 
					generator.generate(world, random, pos);
				}
			}
		}
	}

	private static int calculateGenerationHeightDoor(World world, int x, int z)
	{
		int y = world.getHeight();
		int minHeight = 62;

		if(world.provider.getDimension() == ConfigHandler.NIGHTMARE_REALM) {
			y = 100;
			minHeight = 5;
		}

		boolean foundGround = false;

		while(!foundGround && y-- >= minHeight)
		{
			Block block = world.getBlockState(new BlockPos(x - 2, y, z + 4)).getBlock();
			Block block2 = world.getBlockState(new BlockPos(x - 1, y, z + 4)).getBlock();
			Block block3 = world.getBlockState(new BlockPos(x - 3, y, z + 4)).getBlock();

			if((block == Blocks.GRASS || block == Blocks.SAND || block == BlockInit.ASTEROID) && (block2 == Blocks.GRASS || block2 == Blocks.SAND || block2 == BlockInit.ASTEROID) && (block3 == Blocks.GRASS || block3 == Blocks.SAND || block3 == BlockInit.ASTEROID))
				foundGround = true;
		}

		if(foundGround)
			return y;
		else
			return -1;
	}


	private void generateChest(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>...classes)
	{
		if(random.nextInt(chance) == 0)
		{
			ArrayList<Class<?>> classesList = new ArrayList<Class<?>> (Arrays.asList(classes));
			BlockPos pos = findGround(chunkX, chunkZ, topBlock, world, 0);
			Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

			if(!pos.equals(new BlockPos(0, 0, 0))) {
				if(world.getWorldType() != WorldType.FLAT  && world.getBlockState(pos).getBlock() == Blocks.AIR)
				{
					if(classesList.contains(biome))
					{
						generator.generate(world, random, pos);
					}
				}
			}
		}
	}

	private BlockPos findGround(int chunkX, int chunkZ, Block topBlock, World world, int counter) {
		int y = 100;
		boolean foundGround = false;

		Random random = new Random();

		int x = (chunkX * 16) + random.nextInt(15) + 8;
		int z = (chunkZ * 16) + random.nextInt(15) + 8;

		while(!foundGround && y-- >= 0)
		{
			Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
			Block blockUp = world.getBlockState(new BlockPos(x, y + 1, z)).getBlock();

			foundGround = (block == topBlock && blockUp == Blocks.AIR);
		}

		if(foundGround)
			return new BlockPos(x, y + 1, z);
		else if(counter > 64)
			return new BlockPos(0, 0, 0);
		else
			return findGround(chunkX, chunkZ, topBlock, world, counter++);
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

	private void generateStructureGlobnar(WorldGenerator generator, World world, Random random, BlockPos pos)
	{	
		generator.generate(world, random, pos);
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

	public BlockPos locate(World world, String name, BlockPos pos)
	{
		if(chunkGenerator != null)
		{
			return chunkGenerator.getNearestStructurePos(world, name, pos, false);
		}

		return new BlockPos(-1, -1, -1);
	}



}