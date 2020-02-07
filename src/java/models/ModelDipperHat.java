package models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDipperHat extends ModelBiped
{
	//fields
	ModelRenderer HatTop;
	ModelRenderer HatBack;
	ModelRenderer HatRight;
	ModelRenderer HatLeft;
	ModelRenderer HatFront;
	ModelRenderer HatBill;

	public ModelDipperHat()
	{
		textureWidth = 64;
		textureHeight = 64;

		HatTop = new ModelRenderer(this, 20, 54);
		HatTop.addBox(0F, 0F, 0F, 8, 1, 8);
		HatTop.setRotationPoint(-4F, -11F, -4F);
		HatTop.setTextureSize(64, 64);
		HatTop.mirror = true;
		this.bipedHead.addChild(HatTop);

		HatBack = new ModelRenderer(this, 0, 40);
		HatBack.addBox(0F, 0F, 0F, 10, 6, 1);
		HatBack.setRotationPoint(-5F, -11F, 4F);
		HatBack.setTextureSize(64, 64);
		HatBack.mirror = true;
		this.bipedHead.addChild(HatBack);

		HatRight = new ModelRenderer(this, 13, 40);
		HatRight.addBox(0F, 0F, 0F, 1, 6, 9);
		HatRight.setRotationPoint(-5F, -11F, -5F);
		HatRight.setTextureSize(64, 64);
		HatRight.mirror = true;
		this.bipedHead.addChild(HatRight);

		HatLeft = new ModelRenderer(this, 17, 40);
		HatLeft.addBox(0F, 0F, 0F, 1, 6, 9);
		HatLeft.setRotationPoint(4F, -11F, -5F);
		HatLeft.setTextureSize(64, 64);
		HatLeft.mirror = true;
		this.bipedHead.addChild(HatLeft);

		HatFront = new ModelRenderer(this, 0, 54);
		HatFront.addBox(0F, 0F, 0F, 8, 6, 1);
		HatFront.setRotationPoint(-4F, -11F, -5F);
		HatFront.setTextureSize(64, 64);
		HatFront.mirror = true;
		this.bipedHead.addChild(HatFront);

		HatBill = new ModelRenderer(this, 31, 40);
		HatBill.addBox(0F, 0F, 0F, 10, 1, 5);
		HatBill.setRotationPoint(-5F, -6F, -10F);
		HatBill.setTextureSize(64, 64);
		HatBill.mirror = true;
		this.bipedHead.addChild(HatBill);
	}
	

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
	}

	
	public void setRotationAngle(ModelRenderer modelrenderer, float x, float y, float z)
	{
		modelrenderer.rotateAngleX  = x;
		modelrenderer.rotateAngleX  = y;
		modelrenderer.rotateAngleX  = z;
	}

}