package render;

import entity.EntityBill;
import entity.EntityGnome;
import entity.EntityGideonBot;
import main.Reference;
import models.ModelBill;
import models.ModelGideonBot;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderGideonBot extends RenderLiving<EntityGideonBot>
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/gideonbot.png");
	

	public RenderGideonBot(RenderManager manager) 
	{
		super(manager, new ModelGideonBot(), 5.0F);
	}

	protected ResourceLocation getEntityTexture(EntityGideonBot entity) {
		
		return TEXTURES;
	}
	
	@Override
	protected void applyRotations(EntityGideonBot entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}

}
