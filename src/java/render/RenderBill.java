package render;

import entity.EntityBill;
import entity.EntityGnome;
import main.Reference;
import models.ModelBill;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBill extends RenderLiving<EntityBill>
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/bill.png");
	

	public RenderBill(RenderManager manager) 
	{
		super(manager, new ModelBill(), 0.3F);
		
	}

	protected ResourceLocation getEntityTexture(EntityBill entity) {
		
		return TEXTURES;
	}
	
	@Override
	protected void applyRotations(EntityBill entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}

}
