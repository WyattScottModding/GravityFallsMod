package models;

import java.util.HashMap;

import javax.vecmath.Matrix4f;

import org.lwjgl.util.vector.Quaternion;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;


public class ModelTimeCopLolph extends ModelBase {

	ModelRenderer leftFoot;
	ModelRenderer rightFoot;
	ModelRenderer leftLeg;
	ModelRenderer rightLeg;
	ModelRenderer body;
	ModelRenderer leftArm;
	ModelRenderer rightArm;
	ModelRenderer head;
	ModelRenderer metalEye;

	public ModelTimeCopLolph()
	{
		this.textureHeight = 256;
		this.textureWidth = 256;

		this.leftFoot = new ModelRenderer(this, 64, 0);
		this.leftFoot.addBox(-6.0F, 14.0F, -2.0F, 4, 2, 8);
		this.leftFoot.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftFoot.setTextureSize(16, 16);
	    this.leftFoot.showModel = true;
		setRotation(this.leftFoot, 0.0F, 0.0F, 0.0F);
		
		this.rightFoot = new ModelRenderer(this, 64, 0);
		this.rightFoot.addBox(6.0F, 14.0F, -2.0F, 4, 2, 8);
		this.rightFoot.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightFoot.setTextureSize(16, 16);
	    this.rightFoot.showModel = true;
		setRotation(this.rightFoot, 0.0F, 0.0F, 0.0F);

		this.leftLeg = new ModelRenderer(this, 28, 33);
		this.leftLeg.addBox(-6.0F, -2.0F, 2.0F, 4, 16, 4);
		this.leftLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftLeg.setTextureSize(16, 16);
	    this.leftLeg.showModel = true;
		setRotation(this.leftLeg, 0.0F, 0.0F, 0.0F);

		this.rightLeg = new ModelRenderer(this, 28, 33);
		this.rightLeg.addBox(6.0F, -2.0F, 2.0F, 4, 16, 4);
		this.rightLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightLeg.setTextureSize(16, 16);
	    this.rightLeg.showModel = true;
		setRotation(this.rightLeg, 0.0F, 0.0F, 0.0F);

		this.body = new ModelRenderer(this, 0, 0);
		this.body.addBox(-7.0F, -12.0F, 0.0F, 18, 22, 6);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.setTextureSize(16, 16);
	    this.body.showModel = true;
		setRotation(this.body, 0.0F, 0.0F, 0.0F);

		this.leftArm = new ModelRenderer(this, 48, 0);
		this.leftArm.addBox(-11.0F, 0.0F, 1.0F, 4, 16, 4);
		this.leftArm.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftArm.setTextureSize(16, 16);
	    this.leftArm.showModel = true;
		setRotation(this.leftArm, 0.0F, 0.0F, 0.0F);

		this.rightArm = new ModelRenderer(this, 48, 0);
		this.rightArm.addBox(11.0F, 0.0F, 1.0F, 4, 16, 4);
		this.rightArm.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightArm.setTextureSize(16, 16);
	    this.rightArm.showModel = true;
		setRotation(this.rightArm, 0.0F, 0.0F, 0.0F);
		
		this.head = new ModelRenderer(this, 0, 33);
		this.head.addBox(-1.0F, -24.0F, -1.0F, 6, 12, 8);
		this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.head.setTextureSize(16, 16);
	    this.head.showModel = true;
		setRotation(this.head, 0.0F, 0.0F, 0.0F);
		
		this.metalEye = new ModelRenderer(this, 0, 64);
		this.metalEye.addBox(3.0F, -20.0F, -2.0F, 3, 3, 2);
		this.metalEye.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.metalEye.setTextureSize(16, 16);
	    this.metalEye.showModel = true;
		setRotation(this.metalEye, 0.0F, 0.0F, 0.0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) 
	{
		super.render(entity, f, f1, f2, f3, f4, f5);

		this.leftFoot.renderWithRotation(f5);
		this.rightFoot.renderWithRotation(f5);
		this.leftLeg.renderWithRotation(f5);
		this.rightLeg.renderWithRotation(f5);
		this.body.renderWithRotation(f5);
		this.leftArm.renderWithRotation(f5);
		this.rightArm.renderWithRotation(f5);
		this.head.renderWithRotation(f5);
		this.metalEye.renderWithRotation(f5);

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
		this.leftArm.rotationPointY = -11.0F;
		this.rightArm.rotationPointY = -11.0F;
		
		this.rightLeg.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
		this.leftLeg.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
		this.rightLeg.rotationPointY = 8.0F;
		this.leftLeg.rotationPointY = 8.0F;
		
		this.rightFoot.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
		this.leftFoot.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
		this.rightFoot.rotationPointY = 8.0F;
		this.leftFoot.rotationPointY = 8.0F;
		
	}

}