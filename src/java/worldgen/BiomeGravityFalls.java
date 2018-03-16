package worldgen;

import java.util.Random;

import entity.EntityGnome;
import entity.EntityTimeCopDundgren;
import entity.EntityTimeCopLolph;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityWeatherEffect;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGiantZombie;
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
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;

public class BiomeGravityFalls extends Biome
{
	
	protected static final WorldGenAbstractTree TREE = new WorldGenBigTree(true);

	public BiomeGravityFalls()
	{
		super(new BiomeProperties("GravityFalls").setBaseHeight(0.2F).setRainDisabled().setHeightVariation(0.005F).setTemperature(1.5F));
	
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
		
		this.spawnableCreatureList.add(new SpawnListEntry(EntityGnome.class, 10, 10, 20));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityGolem.class, 1, 0, 1));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 2, 0, 5));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityParrot.class, 5, 1, 3));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityBat.class, 15, 2, 6));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityRabbit.class, 5, 0, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityLlama.class, 3, 0, 5));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityShulker.class, 1, 0, 1));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 6, 0, 8));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombieHorse.class, 1, 0, 1));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeletonHorse.class, 1, 0, 1));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityWitherSkeleton.class, 1, 0, 1));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityTimeCopDundgren.class, 1, 0, 1));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityTimeCopLolph.class, 1, 0, 1));
		
		this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntitySheep.class, 12, 4, 4));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityPig.class, 10, 4, 4));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityChicken.class, 10, 4, 4));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCow.class, 8, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySpider.class, 100, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityZombie.class, 95, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityZombieVillager.class, 5, 1, 1));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySkeleton.class, 100, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySlime.class, 100, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 10, 1, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityWitch.class, 5, 1, 1));
        this.addDefaultFlowers();



	}
	
	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand)
	{
		return TREE;
	}
	
	

}
