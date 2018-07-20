package models;

import java.util.HashMap;

import javax.vecmath.Matrix4f;

import org.lwjgl.util.vector.Quaternion;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;


public class ModelEightBall extends ModelBase 
{

	ModelRenderer leftFoot;
	ModelRenderer rightFoot;
	ModelRenderer leftLeg;
	ModelRenderer rightLeg;
	ModelRenderer body;
	ModelRenderer leftArm;
	ModelRenderer rightArm;
	ModelRenderer head;
	ModelRenderer leftEye;
	ModelRenderer rightEye;
	ModelRenderer leftEar1;
	ModelRenderer leftEar2;
	ModelRenderer leftEar3;
	ModelRenderer leftEar4;
	ModelRenderer rightEar1;
	ModelRenderer rightEar2;
	ModelRenderer rightEar3;
	ModelRenderer rightEar4;


	public ModelEightBall()
	{
		this.textureHeight = 64;
		this.textureWidth = 64;

		this.leftFoot = new ModelRenderer(this, 100, 65);
		this.leftFoot.addBox(-7.0F, 22.0F, -2.0F, 6, 2, 8);
		this.leftFoot.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftFoot.setTextureSize(16, 16);
	    this.leftFoot.showModel = true;
		setRotation(this.leftFoot, 0.0F, 0.0F, 0.0F);
		
		this.rightFoot = new ModelRenderer(this, 100, 65);
		this.rightFoot.addBox(6.0F, 22.0F, -2.0F, 6, 2, 8);
		this.rightFoot.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightFoot.setTextureSize(16, 16);
	    this.rightFoot.showModel = true;
		setRotation(this.rightFoot, 0.0F, 0.0F, 0.0F);

		this.leftLeg = new ModelRenderer(this, 100, 65);
		this.leftLeg.addBox(-7.0F, -2.0F, 2.0F, 6, 26, 6);
		this.leftLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftLeg.setTextureSize(16, 16);
	    this.leftLeg.showModel = true;
		setRotation(this.leftLeg, 0.0F, 0.0F, 0.0F);

		this.rightLeg = new ModelRenderer(this, 100, 65);
		this.rightLeg.addBox(6.0F, -2.0F, 2.0F, 6, 26, 6);
		this.rightLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightLeg.setTextureSize(16, 16);
	    this.rightLeg.showModel = true;
		setRotation(this.rightLeg, 0.0F, 0.0F, 0.0F);

		this.body = new ModelRenderer(this, 100, 130);
		this.body.addBox(-10.0F, -54.0F, 0.0F, 24, 50, 8);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.setTextureSize(16, 16);
	    this.body.showModel = true;
		setRotation(this.body, 0.0F, 0.0F, 0.0F);

		this.leftArm = new ModelRenderer(this, 100, 65);
		this.leftArm.addBox(-13.0F, -2.0F, 1.0F, 5, 26, 5);
		this.leftArm.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftArm.setTextureSize(16, 16);
	    this.leftArm.showModel = true;
		setRotation(this.leftArm, 0.0F, 0.0F, 0.0F);

		this.rightArm = new ModelRenderer(this, 100, 65);
		this.rightArm.addBox(14.0F, -2.0F, 1.0F, 5, 26, 5);
		this.rightArm.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightArm.setTextureSize(16, 16);
	    this.rightArm.showModel = true;
		setRotation(this.rightArm, 0.0F, 0.0F, 0.0F);
		
		this.head = new ModelRenderer(this, 100, 70);
		this.head.addBox(-5.0F, -60.0F, -8.0F, 14, 16, 12);
		this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.head.setTextureSize(16, 16);
	    this.head.showModel = true;
		setRotation(this.head, 0.0F, 0.0F, 0.0F);
		
		this.leftEye = new ModelRenderer(this, 0, 0);
		this.leftEye.addBox(2.0F, -57.0F, -10.0F, 4, 4, 4);
		this.leftEye.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftEye.setTextureSize(16, 16);
	    this.leftEye.showModel = true;
		setRotation(this.leftEye, 0.0F, 0.0F, 0.0F);
		
		this.rightEye = new ModelRenderer(this, 0, 0);
		this.rightEye.addBox(-2.0F, -57.0F, -10.0F, 4, 4, 4);
		this.rightEye.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightEye.setTextureSize(16, 16);
	    this.rightEye.showModel = true;
		setRotation(this.rightEye, 0.0F, 0.0F, 0.0F);
		
		this.rightEar1 = new ModelRenderer(this, 100, 65);
		this.rightEar1.addBox(-9.0F, -58.0F, -6.0F, 4, 6, 1);
		this.rightEar1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightEar1.setTextureSize(16, 16);
	    this.rightEar1.showModel = true;
		setRotation(this.rightEye, 0.0F, 0.0F, 0.0F);
		
		this.rightEar2 = new ModelRenderer(this, 100, 65);
		this.rightEar2.addBox(-9.0F, -59.0F, -6.0F, 3, 2, 1);
		this.rightEar2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightEar2.setTextureSize(16, 16);
	    this.rightEar2.showModel = true;
		setRotation(this.rightEye, 0.0F, 0.0F, 0.0F);
		
		this.rightEar3 = new ModelRenderer(this, 100, 65);
		this.rightEar3.addBox(-9.0F, -61.0F, -6.0F, 2, 2, 1);
		this.rightEar3.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightEar3.setTextureSize(16, 16);
	    this.rightEar3.showModel = true;
		setRotation(this.rightEye, 0.0F, 0.0F, 0.0F);
		
		this.rightEar4 = new ModelRenderer(this, 100, 65);
		this.rightEar4.addBox(-9.0F, -63.0F, -6.0F, 1, 2, 1);
		this.rightEar4.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.rightEar4.setTextureSize(16, 16);
	    this.rightEar4.showModel = true;
		setRotation(this.rightEye, 0.0F, 0.0F, 0.0F);
		
		this.leftEar1 = new ModelRenderer(this, 100, 65);
		this.leftEar1.addBox(8.0F, -58.0F, -6.0F, 4, 6, 1);
		this.leftEar1.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftEar1.setTextureSize(16, 16);
	    this.leftEar1.showModel = true;
		setRotation(this.rightEye, 0.0F, 0.0F, 0.0F);
		
		this.leftEar2 = new ModelRenderer(this, 100, 65);
		this.leftEar2.addBox(9.0F, -59.0F, -6.0F, 3, 2, 1);
		this.leftEar2.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftEar2.setTextureSize(16, 16);
	    this.leftEar2.showModel = true;
		setRotation(this.rightEye, 0.0F, 0.0F, 0.0F);
		
		this.leftEar3 = new ModelRenderer(this, 100, 65);
		this.leftEar3.addBox(10.0F, -61.0F, -6.0F, 2, 2, 1);
		this.leftEar3.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftEar3.setTextureSize(16, 16);
	    this.leftEar3.showModel = true;
		setRotation(this.rightEye, 0.0F, 0.0F, 0.0F);
		
		this.leftEar4 = new ModelRenderer(this, 100, 65);
		this.leftEar4.addBox(11.0F, -63.0F, -6.0F, 1, 2, 1);
		this.leftEar4.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.leftEar4.setTextureSize(16, 16);
	    this.leftEar4.showModel = true;
		setRotation(this.rightEye, 0.0F, 0.0F, 0.0F);
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
		this.leftEye.renderWithRotation(f5);
		this.rightEye.renderWithRotation(f5);
		this.leftEar1.renderWithRotation(f5);
		this.leftEar2.renderWithRotation(f5);
		this.leftEar3.renderWithRotation(f5);
		this.leftEar4.renderWithRotation(f5);
		this.rightEar1.renderWithRotation(f5);
		this.rightEar2.renderWithRotation(f5);
		this.rightEar3.renderWithRotation(f5);
		this.rightEar4.renderWithRotation(f5);


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
		this.leftArm.rotationPointY = -47.0F;
		this.rightArm.rotationPointY = -47.0F;
		
		this.rightLeg.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
		this.leftLeg.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
		this.rightLeg.rotationPointY = -2.0F;
		this.leftLeg.rotationPointY = -2.0F;
		
		this.rightFoot.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
		this.leftFoot.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
		this.rightFoot.rotationPointY = -2.0F;
		this.leftFoot.rotationPointY = -2.0F;
		
	}

}