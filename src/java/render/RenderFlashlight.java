package render;

import entity.EntityForget;
import entity.EntityGnome;
import entity.EntityLight;
import main.Reference;
import models.ModelFlashlight;
import models.ModelGnome;
import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.IRenderHandler;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.ResourceLocation;

public class RenderFlashlight extends Render<EntityLight>
{
	private ModelFlashlight model = new ModelFlashlight();
	
	public static final ResourceLocation TEXTUREON = new ResourceLocation(Reference.MODID + ":textures/entity/flashlight_on.png");
	public static final ResourceLocation TEXTUREOFF = new ResourceLocation(Reference.MODID + ":textures/entity/flashlight_off.png");

	

	public RenderFlashlight(RenderManager manager) 
	{
		super(manager);

	}

	protected ResourceLocation getEntityTexture(EntityForget entity) 
	{

		return TEXTUREON;
	}
	/*
	@Override
	protected void applyRotations(EntityForget entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	 */


	@Override
	protected ResourceLocation getEntityTexture(EntityLight entity)
	{
		return null;
	}
}
