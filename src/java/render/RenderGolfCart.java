package render;

import entity.EntityGnome;
import entity.EntityGolfCart;
import entity.EntityTimeCopLolph;
import main.Reference;
import models.ModelGnome;
import models.ModelGolfCart;
import models.ModelTimeCopLolph;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderGolfCart extends RenderLiving<EntityGolfCart>
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/golfcart.png");
	

	public RenderGolfCart(RenderManager manager) 
	{
		super(manager, new ModelGolfCart(), 0.6F);
		
	}

	protected ResourceLocation getEntityTexture(EntityGolfCart entity) {
		
		return TEXTURES;
	}
	
	@Override
	protected void applyRotations(EntityGolfCart entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}

}
