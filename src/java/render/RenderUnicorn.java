package render;

import entity.EntityBill;
import entity.EntityGnome;
import entity.EntityUnicorn;
import main.Reference;
import models.ModelBill;
import models.ModelUnicorn;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderUnicorn extends RenderLiving<EntityUnicorn>
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/unicorn.png");
	

	public RenderUnicorn(RenderManager manager) 
	{
		super(manager, new ModelUnicorn(), 0.8F);
		
	}

	protected ResourceLocation getEntityTexture(EntityUnicorn entity) {
		
		return TEXTURES;
	}
	
	@Override
	protected void applyRotations(EntityUnicorn entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}

}
