package render;

import entity.EntityBill;
import entity.EntityGnome;
import entity.EntityKeyhole;
import main.Reference;
import models.ModelBill;
import models.ModelKeyhole;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderKeyhole extends RenderLiving<EntityKeyhole>
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/keyhole.png");
	

	public RenderKeyhole(RenderManager manager) 
	{
		super(manager, new ModelKeyhole(), 0.2F);
	}

	protected ResourceLocation getEntityTexture(EntityKeyhole entity) {
		
		return TEXTURES;
	}
	
	@Override
	protected void applyRotations(EntityKeyhole entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}


}
