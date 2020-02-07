package models;

import entity.EntityShapeShifter;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelShapeShifter extends ModelBiped
{
	private final ModelRenderer body;
	private final ModelRenderer body2;
	private final ModelRenderer body3;
	private final ModelRenderer leg0B;
	private final ModelRenderer leg1B;
	private final ModelRenderer leg2B;
	private final ModelRenderer leg3B;
	private final ModelRenderer leg0A;
	private final ModelRenderer leg1A;
	private final ModelRenderer leg2A;
	private final ModelRenderer leg3A;
	private final ModelRenderer head;
	private final ModelRenderer neck;
	private final ModelRenderer ArmLeft;
	private final ModelRenderer ArmRight;

	public ModelShapeShifter()
	{
		textureWidth = 128;
		textureHeight = 128;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 24.0F, 0.0F);
		body.cubeList.add(new ModelBox(body, 16, 16, -6.0F, -30.0F, -6.0F, 12, 22, 12, 0.0F, false));

		body2 = new ModelRenderer(this);
		body2.setRotationPoint(0.0F, -18.0F, 0.0F);
		setRotation(body2, -0.0873F, 3.1416F, 0.0F);
		body.addChild(body2);
		body2.cubeList.add(new ModelBox(body2, 0, 0, -5.0F, -19.0F, -5.0F, 10, 8, 11, 0.0F, false));

		body3 = new ModelRenderer(this);
		body3.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotation(body3, -0.0873F, 3.1416F, 0.0F);
		body.addChild(body3);
		body3.cubeList.add(new ModelBox(body3, 0, 0, -9.0F, -55.0F, -9.0F, 18, 18, 19, 0.0F, false));
		body3.cubeList.add(new ModelBox(body3, 92, 119, 2.0F, -57.0F, -11.0F, 3, 2, 7, 0.0F, false));
		body3.cubeList.add(new ModelBox(body3, 92, 119, -5.0F, -57.0F, -11.0F, 3, 2, 7, 0.0F, false));

		leg0B = new ModelRenderer(this);
		leg0B.setRotationPoint(-2.0F, 18.0F, 4.0F);
		body.addChild(leg0B);
		leg0B.cubeList.add(new ModelBox(leg0B, 116, 115, -11.5F, -29.0F, -1.0F, 3, 10, 3, 0.0F, false));

		leg1B = new ModelRenderer(this);
		leg1B.setRotationPoint(-2.0F, 18.0F, 4.0F);
		body.addChild(leg1B);
		leg1B.cubeList.add(new ModelBox(leg1B, 116, 115, 12.5F, -30.0F, -1.0F, 3, 10, 3, 0.0F, false));

		leg2B = new ModelRenderer(this);
		leg2B.setRotationPoint(-2.0F, 18.0F, 4.0F);
		body.addChild(leg2B);
		leg2B.cubeList.add(new ModelBox(leg2B, 116, 115, -11.5F, -29.0F, -10.0F, 3, 10, 3, 0.0F, false));

		leg3B = new ModelRenderer(this);
		leg3B.setRotationPoint(-2.0F, 18.0F, 4.0F);
		body.addChild(leg3B);
		leg3B.cubeList.add(new ModelBox(leg3B, 116, 115, 12.5F, -29.0F, -10.0F, 3, 10, 3, 0.0F, false));

		leg0A = new ModelRenderer(this);
		leg0A.setRotationPoint(-2.0F, 18.0F, 4.0F);
		setRotation(leg0A, 0.0F, 0.0F, 0.1745F);
		leg0A.cubeList.add(new ModelBox(leg0A, 0, 16, -12.0F, -4.0F, -1.0F, 8, 3, 3, 0.0F, false));

		leg1A = new ModelRenderer(this);
		leg1A.setRotationPoint(-2.0F, 18.0F, 4.0F);
		setRotation(leg1A, 0.0F, 0.0F, 0.1745F);
		leg1A.cubeList.add(new ModelBox(leg1A, 0, 16, -12.0F, -4.0F, -10.0F, 8, 3, 3, 0.0F, false));

		leg2A = new ModelRenderer(this);
		leg2A.setRotationPoint(-2.0F, 18.0F, 4.0F);
		setRotation(leg2A, 0.0F, 0.0F, -0.1745F);
		leg2A.cubeList.add(new ModelBox(leg2A, 0, 16, 8.0F, -4.0F, -1.0F, 8, 3, 3, 0.0F, false));

		leg3A = new ModelRenderer(this);
		leg3A.setRotationPoint(-2.0F, 18.0F, 4.0F);
		setRotation(leg3A, 0.0F, 0.0F, -0.1745F);
		leg3A.cubeList.add(new ModelBox(leg3A, 0, 16, 8.0F, -4.0F, -10.0F, 8, 3, 3, 0.0F, false));

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotation(head, -0.0873F, 3.1416F, 0.0F);
		head.cubeList.add(new ModelBox(head, 29, 110, -6.0F, -53.0F, 12.0F, 12, 13, 6, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 70, 117, -3.0F, -46.0F, 18.0F, 6, 6, 5, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 116, 0, 4.0F, -50.0F, 16.0F, 3, 3, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 116, 0, -7.0F, -50.0F, 16.0F, 3, 3, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 120, 85, -2.5F, -45.5F, 22.0F, 1, 1, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 126, 87, -2.5F, -45.5F, 25.0F, 1, 2, 0, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 120, 85, 1.5F, -45.5F, 22.0F, 1, 1, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 126, 87, 1.5F, -45.5F, 25.0F, 1, 2, 0, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 120, 85, -2.5F, -41.5F, 22.0F, 1, 1, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 126, 87, -2.5F, -42.5F, 25.0F, 1, 2, 0, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 120, 85, 1.5F, -41.5F, 22.0F, 1, 1, 3, 0.0F, false));
		head.cubeList.add(new ModelBox(head, 126, 87, 1.5F, -42.5F, 25.0F, 1, 2, 0, 0.0F, false));

		neck = new ModelRenderer(this);
		neck.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotation(neck, -0.0873F, 3.1416F, 0.0F);
		neck.cubeList.add(new ModelBox(neck, 0, 0, -5.0F, -50.0F, 10.0F, 10, 9, 2, 0.0F, false));

		ArmLeft = new ModelRenderer(this);
		ArmLeft.setRotationPoint(0.0F, -20.0F, 0.0F);
		setRotation(ArmLeft, -0.0873F, 3.1416F, 0.0F);
		ArmLeft.cubeList.add(new ModelBox(ArmLeft, 0, 0, -26.0F, -9.0F, -3.0F, 17, 7, 7, 0.0F, false));
		ArmLeft.cubeList.add(new ModelBox(ArmLeft, 0, 0, -25.0F, -2.0F, -2.0F, 5, 19, 5, 0.0F, false));
		ArmLeft.cubeList.add(new ModelBox(ArmLeft, 112, 122, -23.0F, 16.0F, 3.0F, 1, 5, 1, 0.0F, false));
		ArmLeft.cubeList.add(new ModelBox(ArmLeft, 112, 122, -20.0F, 16.0F, -2.0F, 1, 5, 1, 0.0F, false));
		ArmLeft.cubeList.add(new ModelBox(ArmLeft, 112, 122, -26.0F, 16.0F, -2.0F, 1, 5, 1, 0.0F, false));

		ArmRight = new ModelRenderer(this);
		ArmRight.setRotationPoint(0.0F, -20.0F, 0.0F);
		setRotation(ArmRight, -0.0873F, 3.1416F, 0.0F);
		ArmRight.cubeList.add(new ModelBox(ArmRight, 0, 0, 9.0F, -9.0F, -3.0F, 17, 7, 7, 0.0F, false));
		ArmRight.cubeList.add(new ModelBox(ArmRight, 0, 0, 19.0F, -2.0F, -2.0F, 5, 19, 5, 0.0F, false));
		ArmRight.cubeList.add(new ModelBox(ArmRight, 112, 122, 21.0F, 16.0F, 3.0F, 1, 5, 1, 0.0F, false));
		ArmRight.cubeList.add(new ModelBox(ArmRight, 112, 122, 24.0F, 16.0F, -2.0F, 1, 5, 1, 0.0F, false));
		ArmRight.cubeList.add(new ModelBox(ArmRight, 112, 122, 18.0F, 16.0F, -2.0F, 1, 5, 1, 0.0F, false));
	}


	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		if(entity instanceof EntityShapeShifter) {
			EntityShapeShifter shapeShifter = (EntityShapeShifter) entity;
			ModelBase model = shapeShifter.currentModel;

			if(!(model instanceof ModelShapeShifter)) {

				model.render(entity, f, f1, f2, f3, f4, f5);
			}
			else {
				if(isChild) {
					model = new ModelShapeShifterBaby();

					model.render(entity, f, f1, f2, f3, f4, f5);
				}
				else {
					body.render(f5);
					leg0A.render(f5);
					leg1A.render(f5);
					leg2A.render(f5);
					leg3A.render(f5);
					head.render(f5);
					neck.render(f5);
					ArmLeft.render(f5);
					ArmRight.render(f5);
				}
			}
		}
	}


	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;		
	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

		float amount = 0.6F;

		this.leg0A.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * amount * limbSwingAmount;
		this.leg1A.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * amount * limbSwingAmount;
		this.leg2A.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * amount * limbSwingAmount;
		this.leg3A.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * amount * limbSwingAmount;

		this.leg0B.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * amount * limbSwingAmount;
		this.leg1B.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * amount * limbSwingAmount;
		this.leg2B.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * amount * limbSwingAmount;
		this.leg3B.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * amount * limbSwingAmount;


		this.ArmRight.rotateAngleX = (-0.2F + 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
		this.ArmLeft.rotateAngleX = (-0.2F - 1.5F * this.triangleWave(limbSwing, 13.0F)) * limbSwingAmount;
	}

	private float triangleWave(float p_78172_1_, float p_78172_2_)
	{
		return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
	}
}