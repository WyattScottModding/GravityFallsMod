package render;

import entity.EntityGnome;
import main.Reference;
import models.ModelGnome;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderGnome extends RenderLiving<EntityGnome>
{
	public static final ResourceLocation TEXTURES_1 = new ResourceLocation(Reference.MODID + ":textures/entity/gnome_1.png");
	public static final ResourceLocation TEXTURES_2 = new ResourceLocation(Reference.MODID + ":textures/entity/gnome_2.png");
	public static final ResourceLocation TEXTURES_3 = new ResourceLocation(Reference.MODID + ":textures/entity/gnome_3.png");
	public static final ResourceLocation TEXTURES_4 = new ResourceLocation(Reference.MODID + ":textures/entity/gnome_4.png");


	public RenderGnome(RenderManager manager) 
	{
		super(manager, new ModelGnome(), 0.5F);
	}

	protected ResourceLocation getEntityTexture(EntityGnome entity) {
		
		if(entity.variant == 1)
			return TEXTURES_1;
		else if(entity.variant == 2)
			return TEXTURES_2;
		else if(entity.variant == 3)
			return TEXTURES_3;
		else
			return TEXTURES_4;
	}
	
	@Override
	protected void applyRotations(EntityGnome entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
}