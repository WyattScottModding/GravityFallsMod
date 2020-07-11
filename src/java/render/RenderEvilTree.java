package render;

import java.util.Random;

import entity.EntityBill;
import entity.EntityEvilTree;
import entity.EntityGnome;
import main.Reference;
import models.ModelBill;
import models.ModelEvilTree;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEvilTree extends RenderLiving<EntityEvilTree>
{
	public static final ResourceLocation TREE_OPEN = new ResourceLocation(Reference.MODID + ":textures/entity/evil_tree_open.png");
	public static final ResourceLocation TREE_HALF = new ResourceLocation(Reference.MODID + ":textures/entity/evil_tree_half.png");
	public static final ResourceLocation TREE_CLOSED = new ResourceLocation(Reference.MODID + ":textures/entity/evil_tree_closed.png");

	public RenderEvilTree(RenderManager manager) 
	{
		super(manager, new ModelEvilTree(), 0.2F);
	}

	protected ResourceLocation getEntityTexture(EntityEvilTree entity) {
		
		if(entity.texture == 0)
			return TREE_OPEN;
		else if(entity.texture == 1)
			return TREE_HALF;
		else
			return TREE_CLOSED;
	}
	
	@Override
	protected void applyRotations(EntityEvilTree entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
}