package models;

import entity.EntityUnicorn;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class ModelUnicorn extends ModelBase {
	private final ModelRenderer Body;
	private final ModelRenderer TailA;
	private final ModelRenderer TailB;
	private final ModelRenderer TailC;
	private final ModelRenderer Leg1A;
	private final ModelRenderer Leg1B;
	private final ModelRenderer Leg1C;
	private final ModelRenderer Leg2A;
	private final ModelRenderer Leg2B;
	private final ModelRenderer Leg2C;
	private final ModelRenderer Leg3A;
	private final ModelRenderer Leg3B;
	private final ModelRenderer Leg3C;
	private final ModelRenderer Leg4A;
	private final ModelRenderer Leg4B;
	private final ModelRenderer Leg4C;
	private final ModelRenderer Head;
	private final ModelRenderer UMouth;
	private final ModelRenderer LMouth;
	private final ModelRenderer Ear1;
	private final ModelRenderer Ear2;
	private final ModelRenderer MuleEarL;
	private final ModelRenderer MuleEarR;
	private final ModelRenderer Neck;
	private final ModelRenderer Bag1;
	private final ModelRenderer Bag2;
	private final ModelRenderer Saddle;
	private final ModelRenderer SaddleB;
	private final ModelRenderer SaddleC;
	private final ModelRenderer SaddleL2;
	private final ModelRenderer SaddleL;
	private final ModelRenderer SaddleR2;
	private final ModelRenderer SaddleR;
	private final ModelRenderer SaddleMouthL;
	private final ModelRenderer SaddleMouthR;
	private final ModelRenderer SaddleMouthLine;
	private final ModelRenderer SaddleMouthLineR;
	private final ModelRenderer Mane;
	private final ModelRenderer HeadSaddle;
	private final ModelRenderer Horn;
	
	public ModelUnicorn() {
		textureWidth = 128;
		textureHeight = 128;

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 11.0F, 9.0F);
		Body.setTextureOffset(60, 95).addBox(-5.0F, -8.0F, -19.0F, 10, 9, 24, false);

		TailA = new ModelRenderer(this);
		TailA.setRotationPoint(0.0F, 3.0F, 14.0F);
		setRotationAngle(TailA, -1.1345F, 0.0F, 0.0F);
		TailA.setTextureOffset(220, 0).addBox(-1.0F, -1.0F, 0.0F, 2, 2, 3, false);

		TailB = new ModelRenderer(this);
		TailB.setRotationPoint(0.0F, 3.0F, 14.0F);
		setRotationAngle(TailB, -1.1345F, 0.0F, 0.0F);
		TailB.setTextureOffset(190, 35).addBox(-1.5F, -2.0F, 3.0F, 3, 3, 7, false);

		TailC = new ModelRenderer(this);
		TailC.setRotationPoint(0.0F, 3.0F, 14.0F);
		setRotationAngle(TailC, -1.4022F, 0.0F, 0.0F);
		TailC.setTextureOffset(39, 40).addBox(-1.5F, -4.5F, 9.0F, 3, 3, 7, false);

		Leg1A = new ModelRenderer(this);
		Leg1A.setRotationPoint(4.0F, 9.0F, 11.0F);
		Leg1A.setTextureOffset(390, 145).addBox(-2.5F, -2.0F, -2.0F, 4, 9, 3, false);

		Leg1B = new ModelRenderer(this);
		Leg1B.setRotationPoint(4.0F, 9.0F, 11.0F);
		Leg1B.setTextureOffset(390, 215).addBox(-2.0F, 7.0F, -1.5F, 3, 5, 2, false);

		Leg1C = new ModelRenderer(this);
		Leg1C.setRotationPoint(4.0F, 9.0F, 11.0F);
		Leg1C.setTextureOffset(0, 0).addBox(-2.5F, 12.0F, -2.5F, 4, 3, 4, false);

		Leg2A = new ModelRenderer(this);
		Leg2A.setRotationPoint(-4.0F, 9.0F, 11.0F);
		Leg2A.setTextureOffset(480, 145).addBox(-1.5F, -2.0F, -2.0F, 4, 9, 3, false);

		Leg2B = new ModelRenderer(this);
		Leg2B.setRotationPoint(-4.0F, 9.0F, 11.0F);
		Leg2B.setTextureOffset(480, 215).addBox(-1.0F, 7.0F, -1.5F, 3, 5, 2, false);

		Leg2C = new ModelRenderer(this);
		Leg2C.setRotationPoint(-4.0F, 9.0F, 11.0F);
		Leg2C.setTextureOffset(0, 0).addBox(-1.5F, 12.0F, -2.0F, 4, 3, 3, false);

		Leg3A = new ModelRenderer(this);
		Leg3A.setRotationPoint(4.0F, 9.0F, -8.0F);
		Leg3A.setTextureOffset(220, 145).addBox(-1.5F, -1.0F, -1.1F, 3, 8, 3, false);

		Leg3B = new ModelRenderer(this);
		Leg3B.setRotationPoint(4.0F, 9.0F, -8.0F);
		Leg3B.setTextureOffset(220, 205).addBox(-1.5F, 7.0F, -0.6F, 3, 5, 2, false);

		Leg3C = new ModelRenderer(this);
		Leg3C.setRotationPoint(4.0F, 9.0F, -8.0F);
		Leg3C.setTextureOffset(0, 0).addBox(-2.0F, 12.0F, -1.1F, 4, 3, 3, false);

		Leg4A = new ModelRenderer(this);
		Leg4A.setRotationPoint(-4.0F, 9.0F, -8.0F);
		Leg4A.setTextureOffset(300, 145).addBox(-1.5F, -1.0F, -1.0F, 3, 8, 3, false);

		Leg4B = new ModelRenderer(this);
		Leg4B.setRotationPoint(-4.0F, 9.0F, -8.0F);
		Leg4B.setTextureOffset(300, 205).addBox(-1.5F, 7.0F, -0.6F, 3, 5, 2, false);

		Leg4C = new ModelRenderer(this);
		Leg4C.setRotationPoint(-4.0F, 9.0F, -8.0F);
		Leg4C.setTextureOffset(0, 0).addBox(-2F, 12.0F, -1.1F, 4, 3, 3, false);

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 8.0F, -9.0F);
		setRotationAngle(Head, 0.5236F, 0.0F, 0.0F);
		Head.setTextureOffset(17, 94).addBox(-2.5F, -14.866F, -2.0F, 5, 5, 7, false);

		UMouth = new ModelRenderer(this);
		UMouth.setRotationPoint(0.0F, 8.0F, -9.0F);
		setRotationAngle(UMouth, 0.5236F, 0.0F, 0.0F);
		UMouth.setTextureOffset(18, 102).addBox(-2.0F, -14F, -7.134F, 4, 2, 6, false);

		LMouth = new ModelRenderer(this);
		LMouth.setRotationPoint(0.0F, 8.0F, -9.0F);
		setRotationAngle(LMouth, 0.5236F, 0.0F, 0.0F);
		LMouth.setTextureOffset(120, 135).addBox(-2.0F, -12F, -6.5F, 4, 1, 5, false);

		Ear1 = new ModelRenderer(this);
		Ear1.setRotationPoint(0.0F, 4.0F, -10.0F);
		setRotationAngle(Ear1, 0.5236F, 0.0F, 0.0F);
		

		Ear2 = new ModelRenderer(this);
		Ear2.setRotationPoint(0.0F, 4.0F, -10.0F);
		setRotationAngle(Ear2, 0.5236F, 0.0F, 0.0F);
		

		MuleEarL = new ModelRenderer(this);
		MuleEarL.setRotationPoint(-1.0F, 7.0F, -7.0F);
		setRotationAngle(MuleEarL, 0.5236F, 0.0F, 0.2618F);
		MuleEarL.setTextureOffset(70, 1).addBox(-2.0F, -18.0F, 3.0F, 2, 5, 1, false);

		MuleEarR = new ModelRenderer(this);
		MuleEarR.setRotationPoint(1.0F, 7.0F, -7.0F);
		setRotationAngle(MuleEarR, 0.5236F, 0.0F, -0.2618F);
		MuleEarR.setTextureOffset(70, 1).addBox(0.0F, -18.0F, 3.0F, 2, 5, 1, false);

		Neck = new ModelRenderer(this);
		Neck.setRotationPoint(0.0F, 8.0F, -9.0F);
		setRotationAngle(Neck, 0.5236F, 0.0F, 0.0F);
		Neck.setTextureOffset(0, 60).addBox(-2.05F, -13.3F, -0.134F, 4, 14, 6, false);

		Bag1 = new ModelRenderer(this);
		Bag1.setRotationPoint(-7.5F, 3.0F, 10.0F);
		setRotationAngle(Bag1, 0.0F, 1.5708F, 0.0F);
		

		Bag2 = new ModelRenderer(this);
		Bag2.setRotationPoint(4.5F, 3.0F, 10.0F);
		setRotationAngle(Bag2, 0.0F, 1.5708F, 0.0F);
		

		Saddle = new ModelRenderer(this);
		Saddle.setRotationPoint(0.0F, 2.0F, 2.0F);
		

		SaddleB = new ModelRenderer(this);
		SaddleB.setRotationPoint(0.0F, 2.0F, 2.0F);
		

		SaddleC = new ModelRenderer(this);
		SaddleC.setRotationPoint(0.0F, 2.0F, 2.0F);
		

		SaddleL2 = new ModelRenderer(this);
		SaddleL2.setRotationPoint(5.0F, 3.0F, 2.0F);
		

		SaddleL = new ModelRenderer(this);
		SaddleL.setRotationPoint(5.0F, 3.0F, 2.0F);
		

		SaddleR2 = new ModelRenderer(this);
		SaddleR2.setRotationPoint(-5.0F, 3.0F, 2.0F);
		

		SaddleR = new ModelRenderer(this);
		SaddleR.setRotationPoint(-5.0F, 3.0F, 2.0F);
		

		SaddleMouthL = new ModelRenderer(this);
		SaddleMouthL.setRotationPoint(0.0F, 4.0F, -10.0F);
		setRotationAngle(SaddleMouthL, 0.5236F, 0.0F, 0.0F);
		

		SaddleMouthR = new ModelRenderer(this);
		SaddleMouthR.setRotationPoint(0.0F, 4.0F, -10.0F);
		setRotationAngle(SaddleMouthR, 0.5236F, 0.0F, 0.0F);
		

		SaddleMouthLine = new ModelRenderer(this);
		SaddleMouthLine.setRotationPoint(0.0F, 4.0F, -10.0F);
		

		SaddleMouthLineR = new ModelRenderer(this);
		SaddleMouthLineR.setRotationPoint(0.0F, 4.0F, -10.0F);
		

		Mane = new ModelRenderer(this);
		Mane.setRotationPoint(0.0F, 8.0F, -9.0F);
		setRotationAngle(Mane, 0.5236F, 0.0F, 0.0F);
		Mane.setTextureOffset(5, 44).addBox(-3.0F, -15.5F, 5.0F, 6, 16, 2, true);

		HeadSaddle = new ModelRenderer(this);
		HeadSaddle.setRotationPoint(0.0F, 4.0F, -10.0F);
		setRotationAngle(HeadSaddle, 0.5236F, 0.0F, 0.0F);
		

		Horn = new ModelRenderer(this);
		Horn.setRotationPoint(0.0F, 8.0F, -9.0F);
		setRotationAngle(Horn, 0.6981F, 0.0F, 0.0F);
		Horn.setTextureOffset(105, 1).addBox(-0.5F, -20.1246F, 5.1777F, 1, 7, 1, false);
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		this.Leg1A.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
		this.Leg1B.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
		this.Leg1C.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);

		this.Leg4A.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
		this.Leg4B.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
		this.Leg4C.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);

		this.Leg3A.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
		this.Leg3B.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
		this.Leg3C.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);

		this.Leg2A.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
		this.Leg2B.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
		this.Leg2C.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);

		this.TailA.rotateAngleZ = (MathHelper.cos(f * 0.6662F) * 0.8F * f1);
		this.TailB.rotateAngleZ = (MathHelper.cos(f * 0.6662F) * 0.8F * f1);
		this.TailC.rotateAngleZ = (MathHelper.cos(f * 0.6662F) * 0.8F * f1);
	}

	@Override
	public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTickTime) {
		
		super.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTickTime);
		this.Head.rotationPointY = 8 + ((EntityUnicorn)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 7.0F;
		this.Neck.rotationPointY = 8 + ((EntityUnicorn)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 7.0F;
		this.Horn.rotationPointY = 8 + ((EntityUnicorn)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 7.0F;
		this.SaddleMouthL.rotationPointY = 8 + ((EntityUnicorn)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 7.0F;
		this.SaddleMouthR.rotationPointY = 8 + ((EntityUnicorn)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 7.0F;
		this.SaddleMouthLine.rotationPointY = 8 + ((EntityUnicorn)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 7.0F;
		this.SaddleMouthLineR.rotationPointY = 8 + ((EntityUnicorn)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 7.0F;
		this.LMouth.rotationPointY = 8 + ((EntityUnicorn)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 7.0F;
		this.UMouth.rotationPointY = 8 + ((EntityUnicorn)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 7.0F;
		this.MuleEarL.rotationPointY = 8 + ((EntityUnicorn)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 7.0F;
		this.MuleEarR.rotationPointY = 8 + ((EntityUnicorn)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 7.0F;
		this.Mane.rotationPointY = 8 + ((EntityUnicorn)entitylivingbaseIn).getHeadRotationPointY(partialTickTime) * 7.0F;

        float headRotationAngleX = ((EntityUnicorn)entitylivingbaseIn).getHeadRotationAngleX(partialTickTime) + 0.5236F;
        
        this.Head.rotateAngleX = headRotationAngleX;
		this.Neck.rotateAngleX = headRotationAngleX;
		this.Horn.rotateAngleX = headRotationAngleX;
		this.SaddleMouthL.rotateAngleX = headRotationAngleX;
		this.SaddleMouthR.rotateAngleX = headRotationAngleX;
		this.SaddleMouthLine.rotateAngleX = headRotationAngleX;
		this.SaddleMouthLineR.rotateAngleX = headRotationAngleX;
		this.LMouth.rotateAngleX = headRotationAngleX;
		this.UMouth.rotateAngleX = headRotationAngleX;
		this.MuleEarL.rotateAngleX = headRotationAngleX;
		this.MuleEarR.rotateAngleX = headRotationAngleX;
		this.Mane.rotateAngleX = headRotationAngleX;

	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) 
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		Body.renderWithRotation(f5);
		TailA.renderWithRotation(f5);
		TailB.renderWithRotation(f5);
		TailC.renderWithRotation(f5);
		Leg1A.renderWithRotation(f5);
		Leg1B.renderWithRotation(f5);
		Leg1C.renderWithRotation(f5);
		Leg2A.renderWithRotation(f5);
		Leg2B.renderWithRotation(f5);
		Leg2C.renderWithRotation(f5);
		Leg3A.renderWithRotation(f5);
		Leg3B.renderWithRotation(f5);
		Leg3C.renderWithRotation(f5);
		Leg4A.renderWithRotation(f5);
		Leg4B.renderWithRotation(f5);
		Leg4C.renderWithRotation(f5);
		Head.renderWithRotation(f5);
		UMouth.renderWithRotation(f5);
		LMouth.renderWithRotation(f5);
		Ear1.renderWithRotation(f5);
		Ear2.renderWithRotation(f5);
		MuleEarL.renderWithRotation(f5);
		MuleEarR.renderWithRotation(f5);
		Neck.renderWithRotation(f5);
		Bag1.renderWithRotation(f5);
		Bag2.renderWithRotation(f5);
		Saddle.renderWithRotation(f5);
		SaddleB.renderWithRotation(f5);
		SaddleC.renderWithRotation(f5);
		SaddleL2.renderWithRotation(f5);
		SaddleL.renderWithRotation(f5);
		SaddleR2.renderWithRotation(f5);
		SaddleR.renderWithRotation(f5);
		SaddleMouthL.renderWithRotation(f5);
		SaddleMouthR.renderWithRotation(f5);
		SaddleMouthLine.renderWithRotation(f5);
		SaddleMouthLineR.renderWithRotation(f5);
		Mane.renderWithRotation(f5);
		HeadSaddle.renderWithRotation(f5);
		Horn.renderWithRotation(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}