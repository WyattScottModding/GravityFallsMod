package render;

import entity.EntityGnome;
import main.Reference;
import models.ModelGnome;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderGnome extends RenderLiving<EntityGnome>
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/gnome.png");
	

	public RenderGnome(RenderManager manager) 
	{
		super(manager, new ModelGnome(), 0.5F);
		
	}

	protected ResourceLocation getEntityTexture(EntityGnome entity) {
		
		return TEXTURES;
	}
	
	@Override
	protected void applyRotations(EntityGnome entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}

}
