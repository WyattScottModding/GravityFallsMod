package models;

import javax.vecmath.Matrix4f;

import org.lwjgl.util.vector.Quaternion;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSecurityDroid extends ModelBase 
{

	ModelRenderer droidBody;
	

	public ModelSecurityDroid()
	{
		this.textureWidth = 256;
		this.textureHeight = 256;
		
		this.droidBody = new ModelRenderer(this, 0, 0);
	    this.droidBody.addBox(15.0F, -20.0F, 15.0F, 30, 30, 30);
	    this.droidBody.setRotationPoint(-30.0F, -15.0F, -30.0F);
	    this.droidBody.setTextureSize(256, 256);
	    this.droidBody.showModel = true;
		
		
	}

	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		this.droidBody.renderWithRotation(scale);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		this.droidBody.offsetY = MathHelper.cos(ageInTicks * 1.3F) * (float)Math.PI * 0.25F;

	}
}