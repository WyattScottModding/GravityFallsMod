package render;

import entity.EntityTimeCopDundgren;
import main.Reference;
import models.ModelTimeCopDundgren;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderTimeCopDundgren extends RenderLiving<EntityTimeCopDundgren>
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/timecopdundgren.png");
	

	public RenderTimeCopDundgren(RenderManager manager) 
	{
		super(manager, new ModelTimeCopDundgren(), 0.6F);
	}

	protected ResourceLocation getEntityTexture(EntityTimeCopDundgren entity) {
		
		return TEXTURES;
	}
	
	@Override
	protected void applyRotations(EntityTimeCopDundgren entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}

}
