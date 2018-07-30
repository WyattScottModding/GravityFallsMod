package worldgen;

import java.util.Random;

import entity.EntityGnome;
import entity.EntityTimeCopDundgren;
import entity.EntityTimeCopLolph;
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
import net.minecraft.entity.passive.EntityBat;
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
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;

public class BiomeTheFuture extends Biome
{

	protected static final WorldGenAbstractTree TREE = new WorldGenBigTree(true);

	public BiomeTheFuture()
	{
		super(new BiomeProperties("TheFuture").setBaseHeight(0.4F).setRainDisabled().setHeightVariation(0.005F).setTemperature(0.8F));
		topBlock = Blocks.STONE.getDefaultState();
		fillerBlock = Blocks.STONE.getDefaultState();

		//adds a block in place of another block
		//this.decorator.coalGen = new WorldGenMinable(BlockInit.EXAMPLE.getDefualtState(), 10);
		this.decorator.treesPerChunk = 0;

		//Clears all mobs that noramlly spawn here
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();



	}



	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand)
	{
		return TREE;
	}

}
