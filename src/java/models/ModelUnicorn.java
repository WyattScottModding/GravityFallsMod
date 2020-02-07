package models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelUnicorn extends ModelBase
{
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
	
	
	public ModelUnicorn()
	{
		textureWidth = 128;
		textureHeight = 128;

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 11.0F, 9.0F);
		Body.cubeList.add(new ModelBox(Body, 60, 95, -5.0F, -8.0F, -19.0F, 10, 9, 24, 0.0F, false));

		TailA = new ModelRenderer(this);
		TailA.setRotationPoint(0.0F, 3.0F, 14.0F);
		setRotation(TailA, -1.1345F, 0.0F, 0.0F);
		TailA.cubeList.add(new ModelBox(TailA, 220, 0, -1.0F, -1.5F, 0.0F, 2, 2, 3, 0.0F, false));

		TailB = new ModelRenderer(this);
		TailB.setRotationPoint(0.0F, 3.0F, 14.0F);
		setRotation(TailB, -1.1345F, 0.0F, 0.0F);
		TailB.cubeList.add(new ModelBox(TailB, 190, 35, -1.5F, -2.0F, 3.0F, 3, 3, 7, 0.0F, false));

		TailC = new ModelRenderer(this);
		TailC.setRotationPoint(0.0F, 3.0F, 14.0F);
		setRotation(TailC, -1.4022F, 0.0F, 0.0F);
		TailC.cubeList.add(new ModelBox(TailC, 39, 40, -1.5F, -4.5F, 9.0F, 3, 3, 7, 0.0F, false));

		Leg1A = new ModelRenderer(this);
		Leg1A.setRotationPoint(4.0F, 9.0F, 11.0F);
		Leg1A.cubeList.add(new ModelBox(Leg1A, 390, 145, -2.5F, 0.0F, -2.0F, 4, 9, 3, 0.0F, false));

		Leg1B = new ModelRenderer(this);
		Leg1B.setRotationPoint(4.0F, 16.0F, 11.0F);
		Leg1B.cubeList.add(new ModelBox(Leg1B, 390, 215, -2.0F, 8.0F, -1.5F, 3, 5, 2, 0.0F, false));

		Leg1C = new ModelRenderer(this);
		Leg1C.setRotationPoint(4.0F, 16.0F, 11.0F);
		Leg1C.cubeList.add(new ModelBox(Leg1C, 390, 215, -2.5F, 13.1F, -2.5F, 4, 3, 4, 0.0F, false));

		Leg2A = new ModelRenderer(this);
		Leg2A.setRotationPoint(-4.0F, 9.0F, 11.0F);
		Leg2A.cubeList.add(new ModelBox(Leg2A, 390, 215, -1.5F, 0.0F, -2.0F, 4, 9, 3, 0.0F, false));

		Leg2B = new ModelRenderer(this);
		Leg2B.setRotationPoint(-4.0F, 16.0F, 11.0F);
		Leg2B.cubeList.add(new ModelBox(Leg2B, 390, 215, -1.0F, 8.0F, -1.5F, 3, 5, 2, 0.0F, false));

		Leg2C = new ModelRenderer(this);
		Leg2C.setRotationPoint(-4.0F, 16.0F, 11.0F);
		Leg2C.cubeList.add(new ModelBox(Leg2C, 390, 215, -1.5F, 13.1F, -2.0F, 4, 3, 3, 0.0F, false));

		Leg3A = new ModelRenderer(this);
		Leg3A.setRotationPoint(4.0F, 9.0F, -8.0F);
		Leg3A.cubeList.add(new ModelBox(Leg3A, 390, 215, -1.9F, 0.0F, -1.1F, 3, 8, 3, 0.0F, false));

		Leg3B = new ModelRenderer(this);
		Leg3B.setRotationPoint(4.0F, 16.0F, -8.0F);
		Leg3B.cubeList.add(new ModelBox(Leg3B, 390, 215, -1.9F, 8.0F, -0.6F, 3, 5, 2, 0.0F, false));

		Leg3C = new ModelRenderer(this);
		Leg3C.setRotationPoint(4.0F, 16.0F, -8.0F);
		Leg3C.cubeList.add(new ModelBox(Leg3C, 390, 215, -2.4F, 13.1F, -1.1F, 4, 3, 3, 0.0F, false));

		Leg4A = new ModelRenderer(this);
		Leg4A.setRotationPoint(-4.0F, 9.0F, -8.0F);
		Leg4A.cubeList.add(new ModelBox(Leg4A, 390, 215, -1.1F, 0.0F, -1.0F, 3, 8, 3, 0.0F, false));

		Leg4B = new ModelRenderer(this);
		Leg4B.setRotationPoint(-4.0F, 16.0F, -8.0F);
		Leg4B.cubeList.add(new ModelBox(Leg4B, 390, 215, -1.1F, 8.0F, -0.6F, 3, 5, 2, 0.0F, false));

		Leg4C = new ModelRenderer(this);
		Leg4C.setRotationPoint(-4.0F, 16.0F, -8.0F);
		Leg4C.cubeList.add(new ModelBox(Leg4C, 390, 215, -1.6F, 13.1F, -1.1F, 4, 3, 3, 0.0F, false));

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 4.0F, -10.0F);
		setRotation(Head, 0.5236F, 0.0F, 0.0F);
		Head.cubeList.add(new ModelBox(Head, 17, 94, -2.5F, -10.0F, -1.5F, 5, 5, 7, 0.0F, false));

		UMouth = new ModelRenderer(this);
		UMouth.setRotationPoint(0.0F, 3.95F, -10.0F);
		setRotation(UMouth, 0.5236F, 0.0F, 0.0F);
		UMouth.cubeList.add(new ModelBox(UMouth, 18, 102, -2.0F, -10.0F, -7.0F, 4, 2, 6, 0.0F, false));

		LMouth = new ModelRenderer(this);
		LMouth.setRotationPoint(0.0F, 4.0F, -10.0F);
		setRotation(LMouth, 0.5236F, 0.0F, 0.0F);
		LMouth.cubeList.add(new ModelBox(LMouth, 120, 135, -2.0F, -8.0F, -6.5F, 4, 1, 5, 0.0F, false));

		Ear1 = new ModelRenderer(this);
		Ear1.setRotationPoint(0.0F, 4.0F, -10.0F);
		setRotation(Ear1, 0.5236F, 0.0F, 0.0F);

		Ear2 = new ModelRenderer(this);
		Ear2.setRotationPoint(0.0F, 4.0F, -10.0F);
		setRotation(Ear2, 0.5236F, 0.0F, 0.0F);

		MuleEarL = new ModelRenderer(this);
		MuleEarL.setRotationPoint(0.0F, 4.0F, -10.0F);
		setRotation(MuleEarL, 0.5236F, 0.0F, 0.2618F);
		MuleEarL.cubeList.add(new ModelBox(MuleEarL, 70, 1, -2.0F, -14.0F, 4.0F, 2, 5, 1, 0.0F, false));

		MuleEarR = new ModelRenderer(this);
		MuleEarR.setRotationPoint(0.0F, 4.0F, -10.0F);
		setRotation(MuleEarR, 0.5236F, 0.0F, -0.2618F);
		MuleEarR.cubeList.add(new ModelBox(MuleEarR, 70, 1, 0.0F, -14.0F, 4.0F, 2, 5, 1, 0.0F, false));

		Neck = new ModelRenderer(this);
		Neck.setRotationPoint(0.0F, 4.0F, -10.0F);
		setRotation(Neck, 0.5236F, 0.0F, 0.0F);
		Neck.cubeList.add(new ModelBox(Neck, 0, 60, -2.05F, -9.8F, -2.0F, 4, 14, 8, 0.0F, false));

		Bag1 = new ModelRenderer(this);
		Bag1.setRotationPoint(-7.5F, 3.0F, 10.0F);
		setRotation(Bag1, 0.0F, 1.5708F, 0.0F);

		Bag2 = new ModelRenderer(this);
		Bag2.setRotationPoint(4.5F, 3.0F, 10.0F);
		setRotation(Bag2, 0.0F, 1.5708F, 0.0F);

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
		setRotation(SaddleMouthL, 0.5236F, 0.0F, 0.0F);

		SaddleMouthR = new ModelRenderer(this);
		SaddleMouthR.setRotationPoint(0.0F, 4.0F, -10.0F);
		setRotation(SaddleMouthR, 0.5236F, 0.0F, 0.0F);

		SaddleMouthLine = new ModelRenderer(this);
		SaddleMouthLine.setRotationPoint(0.0F, 4.0F, -10.0F);

		SaddleMouthLineR = new ModelRenderer(this);
		SaddleMouthLineR.setRotationPoint(0.0F, 4.0F, -10.0F);

		Mane = new ModelRenderer(this);
		Mane.setRotationPoint(0.0F, 4.0F, -10.0F);
		setRotation(Mane, 0.5236F, 0.0F, 0.0F);
		Mane.cubeList.add(new ModelBox(Mane, 5, 44, -3.0F, -10.5F, 5.0F, 6, 15, 2, 0.0F, true));

		HeadSaddle = new ModelRenderer(this);
		HeadSaddle.setRotationPoint(0.0F, 4.0F, -10.0F);
		setRotation(HeadSaddle, 0.5236F, 0.0F, 0.0F);

		Horn = new ModelRenderer(this);
		Horn.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotation(Horn, 0.6981F, 0.0F, 0.0F);
		Horn.cubeList.add(new ModelBox(Horn, 105, 1, -0.5F, -38.0F, 9.5F, 1, 7, 1, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Body.render(f5);
		TailA.render(f5);
		TailB.render(f5);
		TailC.render(f5);
		Leg1A.render(f5);
		Leg1B.render(f5);
		Leg1C.render(f5);
		Leg2A.render(f5);
		Leg2B.render(f5);
		Leg2C.render(f5);
		Leg3A.render(f5);
		Leg3B.render(f5);
		Leg3C.render(f5);
		Leg4A.render(f5);
		Leg4B.render(f5);
		Leg4C.render(f5);
		Head.render(f5);
		UMouth.render(f5);
		LMouth.render(f5);
		Ear1.render(f5);
		Ear2.render(f5);
		MuleEarL.render(f5);
		MuleEarR.render(f5);
		Neck.render(f5);
		Bag1.render(f5);
		Bag2.render(f5);
		Saddle.render(f5);
		SaddleB.render(f5);
		SaddleC.render(f5);
		SaddleL2.render(f5);
		SaddleL.render(f5);
		SaddleR2.render(f5);
		SaddleR.render(f5);
		SaddleMouthL.render(f5);
		SaddleMouthR.render(f5);
		SaddleMouthLine.render(f5);
		SaddleMouthLineR.render(f5);
		Mane.render(f5);
		HeadSaddle.render(f5);
		Horn.render(f5);
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

		this.Leg1A.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
		this.Leg1B.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
		this.Leg1C.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);

		this.Leg4A.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
		this.Leg4B.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
		this.Leg4C.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);

		this.Leg1A.rotationPointY = 8.0F;
		this.Leg1B.rotationPointY = 8.0F;
		this.Leg1C.rotationPointY = 8.0F;

		this.Leg4A.rotationPointY = 8.0F;
		this.Leg4B.rotationPointY = 8.0F;
		this.Leg4C.rotationPointY = 8.0F;

		this.Leg3A.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
		this.Leg3B.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
		this.Leg3C.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);

		this.Leg2A.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
		this.Leg2B.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
		this.Leg2C.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);

		this.Leg3A.rotationPointY = 8.0F;
		this.Leg3B.rotationPointY = 8.0F;
		this.Leg3C.rotationPointY = 8.0F;

		this.Leg2A.rotationPointY = 8.0F;
		this.Leg2B.rotationPointY = 8.0F;
		this.Leg2C.rotationPointY = 8.0F;


		this.TailA.rotateAngleZ = (MathHelper.cos(f * 0.6662F) * 0.8F * f1);
		this.TailB.rotateAngleZ = (MathHelper.cos(f * 0.6662F) * 0.8F * f1);
		this.TailC.rotateAngleZ = (MathHelper.cos(f * 0.6662F) * 0.8F * f1);

		this.TailA.rotationPointY = 3.0F;
		this.TailB.rotationPointY = 3.0F;
		this.TailC.rotationPointY = 3.0F;

	}

}
