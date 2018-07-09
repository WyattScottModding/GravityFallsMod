package render;

import entity.EntityEyeBat;
import entity.EntityGnome;
import main.Reference;
import models.ModelEyeBat;
import models.ModelGnome;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEyeBat extends RenderLiving<EntityEyeBat>
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/eyebat.png");
	

	public RenderEyeBat(RenderManager manager) 
	{
		super(manager, new ModelEyeBat(), 0.5F);
		
	}

	protected ResourceLocation getEntityTexture(EntityEyeBat entity) {
		
		return TEXTURES;
	}
	
	@Override
	protected void applyRotations(EntityEyeBat entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}

}
