package models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelEightBall extends ModelBase 
{
	private final ModelRenderer body;
	private final ModelRenderer waist;
	private final ModelRenderer head;
	private final ModelRenderer rightLeg;
	private final ModelRenderer leftLeg;
	private final ModelRenderer leftArm;
	private final ModelRenderer rightArm;
	private final ModelRenderer LeftLegUpper;
	private final ModelRenderer RightLegUpper;

	public ModelEightBall() {
		textureWidth = 64;
		textureHeight = 64;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotation(body, 0.0873F, 0.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 0, 25, -6.0F, -42.6514F, -0.0152F, 12, 23, 9, 0.0F, false));
		body.cubeList.add(new ModelBox(body, 1, 18, -5.0F, -36.7385F, 5.9886F, 10, 11, 1, 0.0F, false));

		waist = new ModelRenderer(this);
		waist.setRotationPoint(0.0F, 12.0F, 0.0F);
		waist.cubeList.add(new ModelBox(waist, 7, 22, -3.0F, -32.0F, 1.0F, 6, 5, 4, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotation(head, 0.0873F, 0.0F, 0.0F);
		head.cubeList.add(new ModelBox(head, 0, 6, -4.0F, -42.7385F, -7.0114F, 8, 6, 6, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 9, -3.0F, -41.7385F, -1.0114F, 6, 4, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 39, 3, -2.5F, -36.7385F, -6.5F, 5, 5, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 2, 58, 4.0F, -43.7385F, -6.0114F, 3, 4, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 58, -7.0F, -43.7385F, -6.0114F, 3, 4, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 0, 0.0F, -41.7385F, -9.0114F, 3, 3, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 0, 0, -3.0F, -41.7385F, -9.0114F, 3, 3, 2, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 33, 0, 1.25F, -34.2385F, -7.0F, 0, 2, 0, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 33, 0, 0.125F, -32.2385F, -7.0F, 0, 0, 0, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 33, 0, -0.875F, -32.2385F, -7.0F, 0, 0, 0, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 33, 0, -1.75F, -34.2385F, -7.0F, 0, 2, 0, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 2, 58, 5.0F, -44.7385F, -6.0114F, 2, 1, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 2, 58, 6.0F, -45.7385F, -6.0114F, 1, 1, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 1, 58, -7.0F, -44.7385F, -6.0114F, 2, 1, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 2, 58, -7.0F, -45.7385F, -6.0114F, 1, 1, 1, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 19, 17, 2.0F, -36.7385F, -7.0F, 0, 5, 0, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 20, 17, -2.5F, -36.7385F, -7.0F, 0, 5, 0, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 16, 16, -2.0F, -31.7385F, -7.0F, 4, 0, 0, 0.0F, false));
		
		rightLeg = new ModelRenderer(this);
		rightLeg.setRotationPoint(-1.9F, -13.0F, 0.0F);
		rightLeg.cubeList.add(new ModelBox(rightLeg, 12, 0, -5.0F, 19.0F, -1.0F, 5, 16, 5, 0.0F, false));
		rightLeg.cubeList.add(new ModelBox(rightLeg, 42, 44, -1F, 35.0F, -8.0F, 1, 2, 5, 0.25F, false));
		rightLeg.cubeList.add(new ModelBox(rightLeg, 42, 44, -5F, 35.0F, -8.0F, 1, 2, 5, 0.25F, false));
		rightLeg.cubeList.add(new ModelBox(rightLeg, 6, 21, -5F, 35.0F, -2.5F, 5, 2, 6, 0.25F, false));

		leftLeg = new ModelRenderer(this);
		leftLeg.setRotationPoint(1.9F, -13.0F, 0.0F);
		leftLeg.cubeList.add(new ModelBox(leftLeg, 12, 0, 0.0F, 19.0F, -1.0F, 5, 16, 5, 0.0F, true));
		leftLeg.cubeList.add(new ModelBox(leftLeg, 42, 44, 0.0F, 35.0F, -8.0F, 1, 2, 5, 0.25F, false));
		leftLeg.cubeList.add(new ModelBox(leftLeg, 42, 44, 4F, 35.0F, -8.0F, 1, 2, 5, 0.25F, false));
		leftLeg.cubeList.add(new ModelBox(leftLeg, 7, 22, 0.0F, 35.0F, -2.5F, 5, 2, 6, 0.25F, false));
		
		leftArm = new ModelRenderer(this);
		leftArm.setRotationPoint(5.0F, -42.5F, 0.0F);
		leftArm.cubeList.add(new ModelBox(leftArm, 12, 0, 3.0F, 5 - 3.7385F, -1.0F, 4, 18, 6, 0.0F, false));
		leftArm.cubeList.add(new ModelBox(leftArm, 19, 23, 1.0F, 5 -3.5F, 0.0F, 2, 4, 4, 0.0F, false));
		leftArm.cubeList.add(new ModelBox(leftArm, 15, 0, 3.25F, 19.3F, -0.25F, 3, 9, 4, 0.0F, false));
		leftArm.cubeList.add(new ModelBox(leftArm, 60, 42, 7.0F, 29.3F, 2.75F, 1, 8, 1, 0.0F, false));
		leftArm.cubeList.add(new ModelBox(leftArm, 15, 0, 3.0F, 28.3F, -0.25F, 4, 3, 4, 0.0F, false));
		leftArm.cubeList.add(new ModelBox(leftArm, 60, 42, 7.0F, 29.3F, 1.25F, 1, 8, 1, 0.0F, false));
		leftArm.cubeList.add(new ModelBox(leftArm, 60, 42, 7.0F, 29.3F, -0.25F, 1, 8, 1, 0.0F, false));
		leftArm.cubeList.add(new ModelBox(leftArm, 57, 43, 2.5F, 29.3F, -1.0F, 1, 7, 1, 0.0F, false));

		rightArm = new ModelRenderer(this);
		rightArm.setRotationPoint(-5.0F, -42.5F, 0.0F);
		rightArm.cubeList.add(new ModelBox(rightArm, 12, 0, -7.0F, 5 - 3.7385F, -1.0F, 4, 18, 6, 0.0F, false));
		rightArm.cubeList.add(new ModelBox(rightArm, 18, 23, -3.0F, 5 - 3.5F, 0.0F, 2, 4, 4, 0.0F, false));
		rightArm.cubeList.add(new ModelBox(rightArm, 15, 0, -6.75F, 19.3F, -0.2F, 3, 9, 4, 0.0F, false));
		rightArm.cubeList.add(new ModelBox(rightArm, 15, 0, -7.0F, 28.3F, -0.25F, 4, 3, 4, 0.0F, false));
		rightArm.cubeList.add(new ModelBox(rightArm, 60, 42, -8.0F, 28.3F, 1.25F, 1, 8, 1, 0.0F, false));
		rightArm.cubeList.add(new ModelBox(rightArm, 60, 42, -8.0F, 29.3F, 2.75F, 1, 8, 1, 0.0F, false));
		rightArm.cubeList.add(new ModelBox(rightArm, 60, 42, -8.0F, 29.3F, -0.25F, 1, 8, 1, 0.0F, false));
		rightArm.cubeList.add(new ModelBox(rightArm, 57, 43, -4.5F, 29.3F, -1.0F, 1, 7, 1, 0.0F, false));
		
		LeftLegUpper = new ModelRenderer(this);
		LeftLegUpper.setRotationPoint(0.0F, -13.0F, 0.0F);
		//setRotation(LeftLegUpper, -0.0873F, 0.0F - test3, 0.0F);
		LeftLegUpper.cubeList.add(new ModelBox(LeftLegUpper, 14, 0, 2.4F, -5.0F, 0F, 4, 27, 4, 0.0F, true));

		RightLegUpper = new ModelRenderer(this);
		RightLegUpper.setRotationPoint(0.0F, -13.0F, 0.0F);
		//setRotation(RightLegUpper, -0.0873F, 0.0F - test3, 0.0F);
		RightLegUpper.cubeList.add(new ModelBox(RightLegUpper, 14, 0, -6.4F, -5.0F, 0F, 4, 27, 4, 0.0F, true));
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) 
	{
		super.render(entity, f, f1, f2, f3, f4, f5);

		body.renderWithRotation(f5);
		waist.renderWithRotation(f5);
		head.renderWithRotation(f5);
		rightLeg.renderWithRotation(f5);
		leftLeg.renderWithRotation(f5);
		leftArm.renderWithRotation(f5);
		rightArm.renderWithRotation(f5);
		LeftLegUpper.renderWithRotation(f5);
		RightLegUpper.renderWithRotation(f5);
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
	//	this.leftArm.rotationPointY = -47.0F;
	//	this.rightArm.rotationPointY = -47.0F;
		
		this.rightLeg.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
		this.RightLegUpper.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);

		this.leftLeg.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
		this.LeftLegUpper.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);

		//this.rightLeg.rotationPointY = 5.0F;
		//this.leftLeg.rotationPointY = 5.0F;
	}
}