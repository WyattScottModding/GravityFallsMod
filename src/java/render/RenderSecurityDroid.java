package render;

import entity.EntityEyeBat;
import entity.EntityGnome;
import entity.EntitySecurityDroid;
import main.Reference;
import models.ModelEyeBat;
import models.ModelGnome;
import models.ModelSecurityDroid;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSecurityDroid extends RenderLiving<EntitySecurityDroid>
{
	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/securitydroid.png");
	

	public RenderSecurityDroid(RenderManager manager) 
	{
		super(manager, new ModelSecurityDroid(), 1.0F);
		
	}

	protected ResourceLocation getEntityTexture(EntitySecurityDroid entity) {
		
		return TEXTURES;
	}
	
	@Override
	protected void applyRotations(EntitySecurityDroid entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
	{
		super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
	}

}
