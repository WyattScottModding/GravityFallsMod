package worldgen;

import java.util.Random;

import entity.EntityEyeBat;
import entity.EntityGnome;
import entity.EntityHideBehind;
import entity.EntityShapeShifter;
import entity.EntityTimeCopDundgren;
import entity.EntityTimeCopLolph;
import entity.EntityUnicorn;
import main.GravityFalls;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class BiomeGravityFalls extends Biome
{
	protected static final WorldGenAbstractTree OAK_TREE = new WorldGenTrees(true);
	protected static final WorldGenAbstractTree SPRUSE_TREE1 = new WorldGenTaiga1();
	protected static final WorldGenAbstractTree SPRUSE_TREE2 = new WorldGenTaiga2(true);
	protected static final WorldGenAbstractTree BIRCH_TREE_EYE = new WorldGenBirchTreeEye(true, true);
	protected static final WorldGenAbstractTree REDWOOD_TREE = new WorldGenRedwoodTree(true);

	public BiomeGravityFalls()
	{
		super(new BiomeProperties("Gravity Falls").setBaseHeight(0.2F).setRainDisabled().setHeightVariation(0.005F).setTemperature(1.5F));

		topBlock = Blocks.GRASS.getDefaultState();
		fillerBlock = Blocks.DIRT.getDefaultState();

		//adds a block in place of another block
		//this.decorator.coalGen = new WorldGenMinable(BlockInit.EXAMPLE.getDefualtState(), 10);
		this.decorator.treesPerChunk = 5;

		//Clears all mobs that noramlly spawn here
		//this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		//this.spawnableWaterCreatureList.clear();

		//Mobs that spawn during the day
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityHideBehind.class, 1, 0, 1));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityShapeShifter.class, 1, 0, 1));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityGolem.class, 1, 0, 1));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityRabbit.class, 15, 0, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityLlama.class, 10, 0, 5));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityParrot.class, 30, 2, 4));
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityWolf.class, 5, 2, 6));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityGnome.class, 50, 8, 16));
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntitySheep.class, 40, 2, 4));
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityPig.class, 50, 2, 4));
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityChicken.class, 40, 2, 4));
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCow.class, 50, 2, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityZombieHorse.class, 10, 0, 1));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityPigZombie.class, 10, 0, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityShulker.class, 3, 2, 2));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySkeletonHorse.class, 10, 0, 1));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityTimeCopDundgren.class, 3, 0, 1));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityTimeCopLolph.class, 3, 0, 1));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityUnicorn.class, 5, 1, 5));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEyeBat.class, 50, 5, 10));

		//Mobs that spawn at night
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySpider.class, 60, 2, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityZombie.class, 80, 2, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityZombieVillager.class, 30, 1, 1));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySkeleton.class, 60, 4, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySlime.class, 10, 4, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 10, 1, 4));
		this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityWitch.class, 8, 1, 1));

		this.addDefaultFlowers();
	}

	@Override
	public float getSpawningChance() {
		return 0.001F;
	}

	public WorldGenAbstractTree getRandomTreeFeature(Random rand)
	{
		if (rand.nextInt(4) == 0)
		{
			if (rand.nextInt(2) == 0)
				return OAK_TREE;
			else
			{
				if (rand.nextInt(2) == 0)
					return SPRUSE_TREE1;
				else
					return SPRUSE_TREE2;
			}
		}
		else if(rand.nextInt(20) == 0)
		{
			return BIRCH_TREE_EYE;
		}
		else
		{
			return REDWOOD_TREE;
		}
	}
}