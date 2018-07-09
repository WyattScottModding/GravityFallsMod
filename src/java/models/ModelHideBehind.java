package models;

import java.util.HashMap;

import javax.vecmath.Matrix4f;

import org.lwjgl.util.vector.Quaternion;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;


public class ModelHideBehind extends ModelBase {

	ModelRenderer leftFoot;
	ModelRenderer rightFoot;
	ModelRenderer leftFoot2;
	ModelRenderer rightFoot2;
	ModelRenderer leftFoot3;
	ModelRenderer rightFoot3;
	ModelRenderer leftLeg;
	ModelRenderer rightLeg;
	ModelRenderer body;
	ModelRenderer leftArm;
	ModelRenderer rightArm;
	ModelRenderer head;

	public ModelHideBehind()
	{
		this.textureHeight = 256;
		this.textureWidth = 256;

		this.leftFoot = new ModelRenderer(this, 64, 0);
		this.leftFoot.addBox(-6.0F, 14.0F, 2.0F, 4, 2, 10);
		this.leftFoot.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftFoot.setTextureSize(16, 16);
	    this.leftFoot.showModel = true;
		setRotation(this.leftFoot, 0.0F, 0.0F, 0.0F);
		
		this.rightFoot = new ModelRenderer(this, 64, 0);
		this.rightFoot.addBox(6.0F, 14.0F, 2.0F, 4, 2, 10);
		this.rightFoot.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightFoot.setTextureSize(16, 16);
	    this.rightFoot.showModel = true;
		setRotation(this.rightFoot, 0.0F, 0.0F, 0.0F);
		
		this.leftFoot2 = new ModelRenderer(this, 64, 0);
		this.leftFoot2.addBox(-3.0F, 14.0F, 12.0F, 1, 2, 2);
		this.leftFoot2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftFoot2.setTextureSize(16, 16);
	    this.leftFoot2.showModel = true;
		setRotation(this.leftFoot2, 0.0F, 0.0F, 0.0F);
		
		this.rightFoot2 = new ModelRenderer(this, 64, 0);
		this.rightFoot2.addBox(6.0F, 14.0F, 12.0F, 1, 2, 2);
		this.rightFoot2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightFoot2.setTextureSize(16, 16);
	    this.rightFoot2.showModel = true;
		setRotation(this.rightFoot2, 0.0F, 0.0F, 0.0F);
		
		this.leftFoot3 = new ModelRenderer(this, 64, 0);
		this.leftFoot3.addBox(-6.0F, 14.0F, 12.0F, 1, 2, 2);
		this.leftFoot3.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftFoot3.setTextureSize(16, 16);
	    this.leftFoot3.showModel = true;
		setRotation(this.leftFoot3, 0.0F, 0.0F, 0.0F);
		
		this.rightFoot3 = new ModelRenderer(this, 64, 0);
		this.rightFoot3.addBox(9.0F, 14.0F, 12.0F, 1, 2, 2);
		this.rightFoot3.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightFoot3.setTextureSize(16, 16);
	    this.rightFoot3.showModel = true;
		setRotation(this.rightFoot3, 0.0F, 0.0F, 0.0F);

		this.leftLeg = new ModelRenderer(this, 28, 33);
		this.leftLeg.addBox(-6.0F, -6.0F, 2.0F, 4, 26, 4);
		this.leftLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftLeg.setTextureSize(16, 16);
	    this.leftLeg.showModel = true;
		setRotation(this.leftLeg, 0.0F, 0.0F, 0.0F);

		this.rightLeg = new ModelRenderer(this, 28, 33);
		this.rightLeg.addBox(6.0F, -6.0F, 2.0F, 4, 26, 4);
		this.rightLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightLeg.setTextureSize(16, 16);
	    this.rightLeg.showModel = true;
		setRotation(this.rightLeg, 0.0F, 0.0F, 0.0F);

		this.body = new ModelRenderer(this, 30, 30);
		this.body.addBox(-7.0F, -22.0F, 0.0F, 18, 20, 6);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.setTextureSize(16, 16);
	    this.body.showModel = true;
		setRotation(this.body, 0.0F, 0.0F, 0.0F);

		this.leftArm = new ModelRenderer(this, 48, 0);
		this.leftArm.addBox(-11.0F, -2.0F, 1.0F, 4, 26, 4);
		this.leftArm.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftArm.setTextureSize(16, 16);
	    this.leftArm.showModel = true;
		setRotation(this.leftArm, 0.0F, 0.0F, 0.0F);

		this.rightArm = new ModelRenderer(this, 48, 0);
		this.rightArm.addBox(11.0F, -2.0F, 1.0F, 4, 26, 4);
		this.rightArm.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightArm.setTextureSize(16, 16);
	    this.rightArm.showModel = true;
		setRotation(this.rightArm, 0.0F, 0.0F, 0.0F);
		
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-2F, -30.0F, -1.0F, 8, 10, 8);
		this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.head.setTextureSize(16, 16);
	    this.head.showModel = true;
		setRotation(this.head, 0.0F, 0.0F, 0.0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) 
	{
		super.render(entity, f, f1, f2, f3, f4, f5);

		this.leftFoot.renderWithRotation(f5);
		this.rightFoot.renderWithRotation(f5);
		this.leftFoot2.renderWithRotation(f5);
		this.rightFoot2.renderWithRotation(f5);
		this.leftFoot3.renderWithRotation(f5);
		this.rightFoot3.renderWithRotation(f5);
		this.leftLeg.renderWithRotation(f5);
		this.rightLeg.renderWithRotation(f5);
		this.body.renderWithRotation(f5);
		this.leftArm.renderWithRotation(f5);
		this.rightArm.renderWithRotation(f5);
		this.head.renderWithRotation(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		this.leftArm.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
		this.rightArm.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
		this.leftArm.rotationPointY = -17.0F;
		this.rightArm.rotationPointY = -17.0F;
		
		this.rightLeg.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
		this.leftLeg.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
		this.rightLeg.rotationPointY = 2.0F;
		this.leftLeg.rotationPointY = 2.0F;
		
		this.rightFoot.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
		this.leftFoot.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
		this.rightFoot.rotationPointY = 8.0F;
		this.leftFoot.rotationPointY = 8.0F;
		
		this.rightFoot2.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
		this.leftFoot2.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
		this.rightFoot2.rotationPointY = 8.0F;
		this.leftFoot2.rotationPointY = 8.0F;
		
		this.rightFoot3.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
		this.leftFoot3.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
		this.rightFoot3.rotationPointY = 8.0F;
		this.leftFoot3.rotationPointY = 8.0F;
		
	}

}