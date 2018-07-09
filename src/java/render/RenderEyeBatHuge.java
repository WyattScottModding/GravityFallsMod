package render;

import entity.EntityEyeBat;
import entity.EntityEyeBatHuge;
import entity.EntityGnome;
import main.Reference;
import models.ModelEyeBat;
import models.ModelEyeBatHuge;
import models.ModelGnome;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEyeBatHuge extends RenderLiving<EntityEyeBatHuge>
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/eyebathuge.png");
	

	public RenderEyeBatHuge(RenderManager manager) 
	{
		super(manager, new ModelEyeBatHuge(), 2.0F);
		
	}

	protected ResourceLocation getEntityTexture(EntityEyeBatHuge entity) {
		
		return TEXTURES;
	}
	
	@Override
	protected void applyRotations(EntityEyeBatHuge entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}

}
