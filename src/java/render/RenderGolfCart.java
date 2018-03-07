package render;

import entity.EntityGnome;
import entity.EntityGolfCart;
import main.Reference;
import models.ModelGnome;
import models.ModelGolfCart;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderGolfCart extends RenderEntity
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/golfcart.png");
	

	public RenderGolfCart(RenderManager manager) 
	{
		super(manager);
		
	}

	protected ResourceLocation getEntityTexture(EntityGolfCart entity) {
		
		return TEXTURES;
	}
	

}
