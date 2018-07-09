package render;

import entity.EntityHideBehind;
import main.Reference;
import models.ModelHideBehind;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderHideBehind extends RenderLiving<EntityHideBehind>
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/hidebehind.png");
	

	public RenderHideBehind(RenderManager manager) 
	{
		super(manager, new ModelHideBehind(), 0.6F);
	}

	protected ResourceLocation getEntityTexture(EntityHideBehind entity) {
		
		return TEXTURES;
	}
	
	@Override
	protected void applyRotations(EntityHideBehind entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}

}
