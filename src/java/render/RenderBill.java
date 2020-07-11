package render;

import java.util.Random;

import entity.EntityBill;
import entity.EntityGnome;
import main.Reference;
import models.ModelBill;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBill extends RenderLiving<EntityBill>
{
	public static final ResourceLocation BILL = new ResourceLocation(Reference.MODID + ":textures/entity/bill.png");
	public static final ResourceLocation BILL_WATCHING = new ResourceLocation(Reference.MODID + ":textures/entity/bill_watching.png");

	public static final ResourceLocation BILL_DYING_1 = new ResourceLocation(Reference.MODID + ":textures/entity/bill1.png");
	public static final ResourceLocation BILL_DYING_2 = new ResourceLocation(Reference.MODID + ":textures/entity/bill2.png");
	public static final ResourceLocation BILL_DYING_3 = new ResourceLocation(Reference.MODID + ":textures/entity/bill3.png");
	public static final ResourceLocation BILL_DYING_4 = new ResourceLocation(Reference.MODID + ":textures/entity/bill4.png");
	public static final ResourceLocation BILL_DYING_5 = new ResourceLocation(Reference.MODID + ":textures/entity/bill5.png");
	private int texture = 1;

	public RenderBill(RenderManager manager) 
	{
		super(manager, new ModelBill(), 0.3F);
	}

	protected ResourceLocation getEntityTexture(EntityBill entity) {
		
		if(entity.getBillDying())
		{
			Random rand = new Random();
			
			if(entity.getTime() % 8 == 0)
				texture = rand.nextInt(5) + 1;
			
			if(texture == 1)
				return BILL_DYING_1;
			else if(texture == 2)
				return BILL_DYING_2;
			else if(texture == 3)
				return BILL_DYING_3;
			else if(texture == 4)
				return BILL_DYING_4;
			else
				return BILL_DYING_5;
		}
		else if(entity.isWatching())
			return BILL_WATCHING;
		else
			return BILL;
	}
	
	@Override
	protected void applyRotations(EntityBill entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}
}