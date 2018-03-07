package render;

import entity.EntityTimeCopLolph;
import main.Reference;
import models.ModelTimeCopDundgren;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderTimeCopLolph extends RenderLiving<EntityTimeCopLolph>
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/timecoplolph.png");
	

	public RenderTimeCopLolph(RenderManager manager) 
	{
		super(manager, new ModelTimeCopDundgren(), 0.5F);
		
	}

	protected ResourceLocation getEntityTexture(EntityTimeCopLolph entity) {
		
		return TEXTURES;
	}
	
	@Override
	protected void applyRotations(EntityTimeCopLolph entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}

}
