package render;

import entity.EntityForget;
import entity.EntityGnome;
import main.Reference;
import models.ModelGnome;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.ResourceLocation;

public class RenderForget extends Render<EntityForget>
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/forget.png");
	

	public RenderForget(RenderManager manager) 
	{
		super(manager);
		
	}

	protected ResourceLocation getEntityTexture(EntityForget entity) 
	{
		
		return TEXTURES;
	}
	/*
	@Override
	protected void applyRotations(EntityForget entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
	 */
}
