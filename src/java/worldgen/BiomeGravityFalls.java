package worldgen;

import java.util.Random;

import entity.EntityGnome;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityWeatherEffect;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
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

public class BiomeGravityFalls extends Biome
{
	protected static final WorldGenAbstractTree TREE = new WorldGenRedWood();
	
	public BiomeGravityFalls()
	{
		super(new BiomeProperties("GravityFalls").setBaseHeight(0.2F).setRainDisabled().setHeightVariation(0.01F).setTemperature(1.0F));
	
		topBlock = Blocks.GRASS.getDefaultState();
		fillerBlock = Blocks.DIRT.getDefaultState();
		
			//adds a block in place of another block
		//this.decorator.coalGen = new WorldGenMinable(BlockInit.EXAMPLE.getDefualtState(), 10);
		this.decorator.treesPerChunk = 0;
		
			//Clears all mobs that noramlly spawn here
		//this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		//this.spawnableWaterCreatureList.clear();
		
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 8, 0, 3));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityGnome.class, 10, 10, 20));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityGolem.class, 1, 0, 1));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 1, 0, 5));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityParrot.class, 4, 1, 2));
		this.spawnableCreatureList.add(new SpawnListEntry(EntitySheep.class, 5, 0, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 8, 0, 5));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityBat.class, 5, 0, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityRabbit.class, 3, 0, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityLlama.class, 3, 0, 1));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityWitch.class, 3, 0, 5));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 5, 0, 5));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 3, 0, 1));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityShulker.class, 1, 0, 1));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 8, 0, 2));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 3, 0, 5));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombieHorse.class, 1, 0, 2));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeletonHorse.class, 1, 0, 2));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityWitherSkeleton.class, 1, 0, 1));



	}
	
	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand)
	{
		return TREE;
	}
	
	

}
