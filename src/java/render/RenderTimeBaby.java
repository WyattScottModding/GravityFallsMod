package render;

import entity.EntityBill;
import entity.EntityGnome;
import entity.EntityTimeBaby;
import main.Reference;
import models.ModelBill;
import models.ModelTimeBaby;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderTimeBaby extends RenderLiving<EntityTimeBaby>
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/timebaby.png");
	

	public RenderTimeBaby(RenderManager manager) 
	{
		super(manager, new ModelTimeBaby(), 8.0F);
		
	}

	protected ResourceLocation getEntityTexture(EntityTimeBaby entity) {
		
		return TEXTURES;
	}
	
	@Override
	protected void applyRotations(EntityTimeBaby entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}

}
