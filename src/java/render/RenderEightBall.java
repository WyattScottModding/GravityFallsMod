package render;

import entity.EntityBill;
import entity.EntityEightBall;
import entity.EntityGnome;
import main.Reference;
import models.ModelBill;
import models.ModelEightBall;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEightBall extends RenderLiving<EntityEightBall>
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/eightball.png");
	

	public RenderEightBall(RenderManager manager) 
	{
		super(manager, new ModelEightBall(), 0.8F);
		
	}

	protected ResourceLocation getEntityTexture(EntityEightBall entity) {
		
		return TEXTURES;
	}
	
	@Override
	protected void applyRotations(EntityEightBall entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}

}
