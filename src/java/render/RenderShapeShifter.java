package render;

import entity.EntityShapeShifter;
import main.GravityFalls;
import main.Reference;
import models.ModelBill;
import models.ModelEightBall;
import models.ModelEyeBat;
import models.ModelEyeBatHuge;
import models.ModelGnome;
import models.ModelHideBehind;
import models.ModelKeyhole;
import models.ModelShapeShifter;
import models.ModelTimeBaby;
import models.ModelTimeCopDundgren;
import models.ModelTimeCopLolph;
import models.ModelUnicorn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBat;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.model.ModelEvokerFangs;
import net.minecraft.client.model.ModelGhast;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelIllager;
import net.minecraft.client.model.ModelLlama;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelParrot;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRabbit;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.model.ModelShulker;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.model.ModelVex;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.model.ModelWither;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderShapeShifter extends RenderLiving<EntityShapeShifter>
{	

	public RenderShapeShifter(RenderManager manager) 
	{
		super(manager, new ModelShapeShifter(), 0.5F);
	}

	
	@Override
	protected void applyRotations(EntityShapeShifter entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}


	@Override
	protected ResourceLocation getEntityTexture(EntityShapeShifter entity) {
		if(entity.currentModel instanceof ModelPlayer)
			return Minecraft.getMinecraft().player.getLocationSkin();
		
		return new ResourceLocation(textureLocation(entity));
	}
	
	
	public String textureLocation(EntityShapeShifter entity) {
		
		ModelBase model = entity.currentModel;
		
		if(model instanceof ModelShapeShifter) {
			return Reference.MODID + ":textures/entity/shapeshifter.png";
		}
		else if(model instanceof ModelBill) {
			return Reference.MODID + ":textures/entity/bill.png";
		}
		else if(model instanceof ModelEightBall) {
			return Reference.MODID + ":textures/entity/eightball.png";
		}
		else if(model instanceof ModelEyeBat) {
			return Reference.MODID + ":textures/entity/eyebat.png";
		}
		else if(model instanceof ModelEyeBatHuge) {
			return Reference.MODID + ":textures/entity/eyebathuge.png";
		}
		else if(model instanceof ModelGnome) {
			return Reference.MODID + ":textures/entity/gnome.png";
		}
		else if(model instanceof ModelHideBehind) {
			return Reference.MODID + ":textures/entity/hidebehind.png";
		}
		else if(model instanceof ModelKeyhole) {
			return Reference.MODID + ":textures/entity/keyhole.png";
		}
		else if(model instanceof ModelTimeBaby) {
			return Reference.MODID + ":textures/entity/timebaby.png";
		}
		else if(model instanceof ModelTimeCopDundgren) {
			return Reference.MODID + ":textures/entity/timecopdundgren.png";
		}
		else if(model instanceof ModelTimeCopLolph) {
			return Reference.MODID + ":textures/entity/timecoplolph.png";
		}
		else if(model instanceof ModelUnicorn) {
			return Reference.MODID + ":textures/entity/unicorn.png";
		}
		
		else if(model instanceof ModelBat) {
			return "minecraft:textures/entity/bat.png";
		}
		else if(model instanceof ModelBlaze) {
			return "minecraft:textures/entity/blaze.png";
		}
		else if(model instanceof ModelChicken) {
			return "minecraft:textures/entity/chicken.png";
		}
		else if(model instanceof ModelCow) {
			return "minecraft:textures/entity/cow/cow.png";
		}
		else if(model instanceof ModelCreeper) {
			return "minecraft:textures/entity/creeper/creeper.png";
		}
		else if(model instanceof ModelEnderman) {
			return "minecraft:textures/entity/enderman/enderman.png";
		}
		else if(model instanceof ModelGhast) {
			return "minecraft:textures/entity/ghast/ghast.png";
		}
		else if(model instanceof ModelHorse) {
			return "minecraft:textures/entity/horse/horse.png";
		}
		else if(model instanceof ModelIllager) {
			return "minecraft:textures/entity/illager/vindicator.png";
		}
		else if(model instanceof ModelVex) {
			return "minecraft:textures/entity/illager/vex.png";
		}
		else if(model instanceof ModelEvokerFangs) {
			return "minecraft:textures/entity/illager/evoker.png";
		}
		else if(model instanceof ModelOcelot) {
			return "minecraft:textures/entity/cat/ocelot.png";
		}
		else if(model instanceof ModelLlama) {
			return "minecraft:textures/entity/llama/llama.png";
		}
		else if(model instanceof ModelParrot) {
			return "minecraft:textures/entity/parrot/parrot_red.png";
		}
		else if(model instanceof ModelPig) {
			return "minecraft:textures/entity/pig/pig.png";
		}
		else if(model instanceof ModelRabbit) {
			return "minecraft:textures/entity/rabbit/brown.png";
		}
		else if(model instanceof ModelSheep1) {
			return "minecraft:textures/entity/sheep/sheep.png";
		}
		else if(model instanceof ModelShulker) {
			return "minecraft:textures/entity/shulker/shulker_magenta.png";
		}
		else if(model instanceof ModelSkeleton) {
			return "minecraft:textures/entity/skeleton/skeleton.png";
		}
		else if(model instanceof ModelSlime) {
			return "minecraft:textures/entity/slime/slime.png";
		}
		else if(model instanceof ModelSpider) {
			return "minecraft:textures/entity/spider/spider.png";
		}
		else if(model instanceof ModelSquid) {
			return "minecraft:textures/entity/squid.png";
		}
		else if(model instanceof ModelVillager) {
			return "minecraft:textures/entity/villager/farmer.png";
		}
		else if(model instanceof ModelWither) {
			return "minecraft:textures/entity/wither/wither.png";
		}
		else if(model instanceof ModelWolf) {
			return "minecraft:textures/entity/wolf/wolf.png";
		}
		else if(model instanceof ModelZombie) {
			return entity.texture;
		}
		else if(model instanceof ModelZombieVillager) {
			return "minecraft:textures/entity/zombie_villager/zombie_librarian.png";
		}
		
		return Reference.MODID + ":textures/entity/shapeshifter.png";
	}
}