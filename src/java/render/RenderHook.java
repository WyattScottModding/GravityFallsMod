package render;

import entity.EntityForget;
import entity.EntityGnome;
import entity.EntityHook;
import main.Reference;
import models.ModelGnome;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderFireball;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.ResourceLocation;

public class RenderHook extends Render<EntityHook>
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/hook.png");
	

	public RenderHook(RenderManager manager) 
	{
		super(manager);
		
	}

	protected ResourceLocation getEntityTexture(EntityHook entity) 
	{
		
		return TEXTURES;
	}


}
