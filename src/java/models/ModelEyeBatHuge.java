package models;

import java.util.HashMap;

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
public class ModelEyeBatHuge extends ModelBase 
{
	ModelRenderer Body1;
	ModelRenderer Body2;
	ModelRenderer Body3;
	ModelRenderer LeftWing1;
	ModelRenderer LeftWing2;
	ModelRenderer LeftWing3;
	ModelRenderer LeftWing4;
	ModelRenderer LeftWing5;
	ModelRenderer LeftWing6;
	ModelRenderer LeftWing7;
	ModelRenderer RightWing1;
	ModelRenderer RightWing2;
	ModelRenderer RightWing3;
	ModelRenderer RightWing4;
	ModelRenderer RightWing5;
	ModelRenderer RightWing6;
	ModelRenderer RightWing7;

	public ModelEyeBatHuge()
	{
		this.textureHeight = 128;
	    this.textureWidth = 128;
	    
	    
	    this.Body1 = new ModelRenderer(this, 0, 48);
	    this.Body1.addBox(-2.0F, 14.0F, -8.0F, 24, 24, 33);
	    this.Body1.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.Body1.showModel = true;
	    
	    this.Body2 = new ModelRenderer(this, 0, 0);
	    this.Body2.addBox(-2.0F, 9.0F, -3.0F, 24, 33, 24);
	    this.Body2.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.Body2.showModel = true;
	    
	    this.Body3 = new ModelRenderer(this, 0, 0);
	    this.Body3.addBox(-7.0F, 14.0F, -3.0F, 33, 24, 24);
	    this.Body3.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.Body3.showModel = true;
	    
	    this.LeftWing1 = new ModelRenderer(this, 0, 105);
	    this.LeftWing1.addBox(-16.0F, 15.0F, 1.0F, 12, 4, 16);
	    this.LeftWing1.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.LeftWing1.showModel = true;
	    
	    this.LeftWing2 = new ModelRenderer(this, 0, 105);
	    this.LeftWing2.addBox(-25.0F, 11.0F, 1.0F, 12, 4, 16);
	    this.LeftWing2.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.LeftWing2.showModel = true;
	    
	    this.LeftWing3 = new ModelRenderer(this, 0, 105);
	    this.LeftWing3.addBox(-34.0F, 7.0F, 1.0F, 12, 4, 16);
	    this.LeftWing3.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.LeftWing3.showModel = true;
	    
	    this.LeftWing4 = new ModelRenderer(this, 0, 105);
	    this.LeftWing4.addBox(-43.0F, 11.0F, 1.0F, 12, 4, 16);
	    this.LeftWing4.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.LeftWing4.showModel = true;
	    
	    this.LeftWing5 = new ModelRenderer(this, 0, 105);
	    this.LeftWing5.addBox(-46.0F, 11.0F, 1.0F, 3, 4, 12);
	    this.LeftWing5.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.LeftWing5.showModel = true;
	    
	    this.LeftWing6 = new ModelRenderer(this, 0, 105);
	    this.LeftWing6.addBox(-49.0F, 11.0F, 1.0F, 3, 4, 8);
	    this.LeftWing6.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.LeftWing6.showModel = true;
	    
	    this.LeftWing7 = new ModelRenderer(this, 0, 105);
	    this.LeftWing7.addBox(-52.0F, 11.0F, 1.0F, 3, 4, 4);
	    this.LeftWing7.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.LeftWing7.showModel = true;
	    
	    this.RightWing1 = new ModelRenderer(this, 0, 105);
	    this.RightWing1.addBox(23.0F, 15.0F, 1.0F, 12, 4, 16);
	    this.RightWing1.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.RightWing1.showModel = true;
	    
	    this.RightWing2 = new ModelRenderer(this, 0, 105);
	    this.RightWing2.addBox(32.0F, 11.0F, 1.0F, 12, 4, 16);
	    this.RightWing2.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.RightWing2.showModel = true;
	    
	    this.RightWing3 = new ModelRenderer(this, 0, 105);
	    this.RightWing3.addBox(41.0F, 7.0F, 1.0F, 12, 4, 16);
	    this.RightWing3.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.RightWing3.showModel = true;
	    
	    this.RightWing4 = new ModelRenderer(this, 0, 105);
	    this.RightWing4.addBox(50.0F, 11.0F, 1.0F, 12, 4, 16);
	    this.RightWing4.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.RightWing4.showModel = true;
	    
	    this.RightWing5 = new ModelRenderer(this, 0, 105);
	    this.RightWing5.addBox(62.0F, 11.0F, 1.0F, 3, 4, 12);
	    this.RightWing5.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.RightWing5.showModel = true;
	    
	    this.RightWing6 = new ModelRenderer(this, 0, 105);
	    this.RightWing6.addBox(65.0F, 11.0F, 1.0F, 3, 4, 8);
	    this.RightWing6.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.RightWing6.showModel = true;
	    
	    this.RightWing7 = new ModelRenderer(this, 0, 105);
	    this.RightWing7.addBox(68.0F, 11.0F, 1.0F, 3, 4, 4);
	    this.RightWing7.setRotationPoint(0.0F, 0.0F, 0.0F);
	    this.RightWing7.showModel = true;
	}

	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
	    super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
		this.Body1.renderWithRotation(scale);
		this.Body2.renderWithRotation(scale);
		this.Body3.renderWithRotation(scale);
		this.LeftWing1.renderWithRotation(scale);
		this.LeftWing2.renderWithRotation(scale);
		this.LeftWing3.renderWithRotation(scale);
		this.LeftWing4.renderWithRotation(scale);
		this.LeftWing5.renderWithRotation(scale);
		this.LeftWing6.renderWithRotation(scale);
		this.LeftWing7.renderWithRotation(scale);
		this.RightWing1.renderWithRotation(scale);
		this.RightWing2.renderWithRotation(scale);
		this.RightWing3.renderWithRotation(scale);
		this.RightWing4.renderWithRotation(scale);
		this.RightWing5.renderWithRotation(scale);
		this.RightWing6.renderWithRotation(scale);
		this.RightWing7.renderWithRotation(scale);



	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		this.RightWing1.rotateAngleZ = (MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 0.7F * limbSwingAmount);
		this.RightWing2.rotateAngleZ = (MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 0.7F * limbSwingAmount);
		this.RightWing3.rotateAngleZ = (MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 0.7F * limbSwingAmount);
		this.RightWing4.rotateAngleZ = (MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 0.7F * limbSwingAmount);
		this.RightWing5.rotateAngleZ = (MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 0.7F * limbSwingAmount);
		this.RightWing6.rotateAngleZ = (MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 0.7F * limbSwingAmount);
		this.RightWing7.rotateAngleZ = (MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 0.7F * limbSwingAmount);
		
		this.LeftWing1.rotateAngleZ = (MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount);
		this.LeftWing2.rotateAngleZ = (MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount);
		this.LeftWing3.rotateAngleZ = (MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount);
		this.LeftWing4.rotateAngleZ = (MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount);
		this.LeftWing5.rotateAngleZ = (MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount);
		this.LeftWing6.rotateAngleZ = (MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount);
		this.LeftWing7.rotateAngleZ = (MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount);

	}
}