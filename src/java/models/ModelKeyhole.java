package models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelKeyhole extends ModelBase
{
	ModelRenderer Body;
    ModelRenderer Body2;
    ModelRenderer RightArm;
    ModelRenderer LeftArm;
    ModelRenderer RightLeg;
    ModelRenderer LeftLeg;
    ModelRenderer Head1;
    ModelRenderer Head2;
    ModelRenderer Head3;
    ModelRenderer Head4;
    ModelRenderer Head6;
    ModelRenderer Head7;
    ModelRenderer Head8;
    ModelRenderer Head9;
    ModelRenderer Head10;
    ModelRenderer Head12;
    ModelRenderer Head11;
    ModelRenderer RightEye;
    ModelRenderer LeftEye;

	public ModelKeyhole()
	{
		textureWidth = 32;
		textureHeight = 32;

		Body = new ModelRenderer(this, 1, 1);
		Body.addBox(0F, 0F, 0F, 4, 8, 6);
		Body.setRotationPoint(3F, 10F, -2.5F);
		Body.setTextureSize(32, 32);
		setRotation(Body, 0F, -1.570796F, 0F);

		RightArm = new ModelRenderer(this, 0, 0);
		RightArm.addBox(0F, 0F, 0F, 2, 5, 1);
		RightArm.setRotationPoint(-3.011111F, 14F, -1.5F);
		RightArm.setTextureSize(32, 32);
		setRotation(RightArm, 0F, -1.570796F, 0F);

		LeftArm = new ModelRenderer(this, 0, 0);
		LeftArm.addBox(0F, 0F, 0F, 2, 5, 1);
		LeftArm.setRotationPoint(4F, 14F, -1.5F);
		LeftArm.setTextureSize(32, 32);
		setRotation(LeftArm, 0F, -1.570796F, 0F);

		RightLeg = new ModelRenderer(this, 0, 0);
		RightLeg.addBox(0F, 0F, 0F, 2, 4, 1);
		RightLeg.setRotationPoint(-1F, 20F, -1.5F);
		RightLeg.setTextureSize(32, 32);
		setRotation(RightLeg, 0F, -1.570796F, 0F);

		LeftLeg = new ModelRenderer(this, 0, 0);
		LeftLeg.addBox(0F, 0F, 0F, 2, 4, 1);
		LeftLeg.setRotationPoint(2F, 20F, -1.5F);
		LeftLeg.setTextureSize(32, 32);
		setRotation(LeftLeg, 0F, -1.570796F, 0F);

		Head2 = new ModelRenderer(this, 18, 16);
		Head2.addBox(0F, 0F, 0F, 1, 5, 5);
		Head2.setRotationPoint(2.5F, 5F, 0.5F);
		Head2.setTextureSize(32, 32);
		setRotation(Head2, 0F, -1.570796F, 0F);

		Head3 = new ModelRenderer(this, 22, 4);
		Head3.addBox(0F, 0F, 0F, 4, 5, 1);
		Head3.setRotationPoint(-1.5F, 5F, -2.5F);
		Head3.setTextureSize(32, 32);
		setRotation(Head3, 0F, -1.570796F, 0F);

		Head1 = new ModelRenderer(this, 2, 22);
		Head1.addBox(0F, 0F, 0F, 4, 5, 1);
		Head1.setRotationPoint(2.5F, 5F, -2.5F);
		Head1.setTextureSize(32, 32);
		setRotation(Head1, 0F, -1.570796F, 0F);

		Head4 = new ModelRenderer(this, 17, 0);
		Head4.addBox(0F, 0F, 0F, 3, 1, 3);
		Head4.setRotationPoint(-1.5F, 4.311111F, -2F);
		Head4.setTextureSize(32, 32);
		setRotation(Head4, 0F, 0F, 0F);

		Head6 = new ModelRenderer(this, 0, 0);
		Head6.addBox(0F, 0F, 0F, 1, 3, 1);
		Head6.setRotationPoint(1.5F, 7.8F, -2.5F);
		Head6.setTextureSize(32, 32);
		setRotation(Head6, -0.2617994F, -1.570796F, 0F);

		Head7 = new ModelRenderer(this, 0, 0);
		Head7.addBox(0F, 0F, 0F, 1, 3, 1);
		Head7.setRotationPoint(-0.5F, 8F, -2.5F);
		Head7.setTextureSize(32, 32);
		setRotation(Head7, 0.2617994F, -1.570796F, 0F);


		Head8 = new ModelRenderer(this, 0, 0);
		Head8.addBox(0F, 0F, 0F, 1, 1, 2);
		Head8.setRotationPoint(-0.5F, 8F, -2.45F);
		Head8.setTextureSize(32, 32);
		setRotation(Head8, 1.047198F, -1.570796F, 0F);

		Head9 = new ModelRenderer(this, 0, 0);
		Head9.addBox(0F, 0F, 0F, 1, 1, 2);
		Head9.setRotationPoint(1.55F, 6.3F, -2.5F);
		Head9.setTextureSize(32, 32);
		setRotation(Head9, -1.047198F, -1.570796F, 0F);

		Head10 = new ModelRenderer(this, 0, 0);
		Head10.addBox(0F, 0F, 0F, 1, 1, 1);
		Head10.setRotationPoint(2F, 6F, -2.5F);
		Head10.setTextureSize(32, 32);
		setRotation(Head10, 0.7853982F, -1.570796F, 0F);

		Head12 = new ModelRenderer(this, 0, 0);
		Head12.addBox(0F, 0F, 0F, 1, 1, 3);
		Head12.setRotationPoint(1.5F, 5F, -2.5F);
		Head12.setTextureSize(32, 32);
		setRotation(Head12, 0F, -1.570796F, 0F);

		Head11 = new ModelRenderer(this, 0, 0);
		Head11.addBox(0F, 0F, 0F, 1, 1, 1);
		Head11.setRotationPoint(-1.3F, 5.3F, -2.45F);
		Head11.setTextureSize(32, 32);
		setRotation(Head11, -0.7853982F, -1.570796F, 0F);

		RightEye = new ModelRenderer(this, 1, 19);
		RightEye.addBox(0F, 0F, 0F, 1, 1, 1);
		RightEye.setRotationPoint(-0.1F, 11F, -3F);
		RightEye.setTextureSize(32, 32);
		setRotation(RightEye, 0F, -1.570796F, 0F);

		LeftEye = new ModelRenderer(this, 1, 19);
		LeftEye.addBox(0F, 0F, 0F, 1, 1, 1);
		LeftEye.setRotationPoint(1.1F, 11F, -3F);
		LeftEye.setTextureSize(32, 32);
		setRotation(LeftEye, 0F, -1.570796F, 0F);

		Body2 = new ModelRenderer(this, 1, 1);
		Body2.addBox(0F, 0F, 0F, 3, 2, 5);
		Body2.setRotationPoint(2.5F, 18.02222F, -2F);
		Body2.setTextureSize(32, 32);
		setRotation(Body2, 0F, -1.570796F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Body.render(f5);
		Body2.render(f5);
		RightArm.render(f5);
		LeftArm.render(f5);
		RightLeg.render(f5);
		LeftLeg.render(f5);
		Head2.render(f5);
		Head3.render(f5);
		Head1.render(f5);
		Head4.render(f5);
		Head6.render(f5);
		Head7.render(f5);
		Head8.render(f5);
		Head9.render(f5);
		Head10.render(f5);
		Head12.render(f5);
		Head11.render(f5);
		RightEye.render(f5);
		LeftEye.render(f5);
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

		this.LeftArm.rotateAngleZ = (MathHelper.cos(f * 0.6662F) * 0.6F * f1);
		this.RightArm.rotateAngleZ = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 0.6F * f1);
		this.LeftArm.rotationPointY = 11.0F;
		this.RightArm.rotationPointY = 11.0F;

		this.RightLeg.rotateAngleZ = (MathHelper.cos(f * 0.6662F) * 0.4F * f1);
		this.LeftLeg.rotateAngleZ = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 0.4F * f1);
		this.RightLeg.rotationPointY = 20.0F;
		this.LeftLeg.rotationPointY = 20.0F;
	}
}