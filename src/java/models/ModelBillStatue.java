package models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

// Made with Blockbench 3.5.4
// Exported for Minecraft version 1.12
// Paste this class into your mod and generate all required imports


public class ModelBillStatue extends ModelBase {
	private final ModelRenderer EastSide;
	private final ModelRenderer Base;
	private final ModelRenderer WestSide;
	private final ModelRenderer Hat;
	private final ModelRenderer EyeBase;
	private final ModelRenderer Hand1;
	private final ModelRenderer Thumb;
	private final ModelRenderer Finger1;
	private final ModelRenderer Finger2;
	private final ModelRenderer Finger3;
	private final ModelRenderer Hand;

	public ModelBillStatue() {
		textureWidth = 64;
		textureHeight = 64;

		EastSide = new ModelRenderer(this);
		EastSide.setRotationPoint(0.0F, 24.0F, -5.0F);
		setRotationAngle(EastSide, -0.3143F, 0.1501F, 0.5236F);
		EastSide.cubeList.add(new ModelBox(EastSide, 38, 38, -8.58F, -16.78F, -0.19F, 2, 21, 1, 0.0F, false));

		Base = new ModelRenderer(this);
		Base.setRotationPoint(-10.0F, 23.5F, 0.5F);
		setRotationAngle(Base, -0.3491F, 0.0F, 0.0873F);
		Base.cubeList.add(new ModelBox(Base, 19, 51, 0.44F, 1.5F, -5.5F, 20, 1, 1, 0.0F, false));
		Base.cubeList.add(new ModelBox(Base, 28, 44, 2.0F, -0.5F, -5.5F, 17, 2, 1, 0.0F, false));
		Base.cubeList.add(new ModelBox(Base, 32, 54, 3.0F, -2.5F, -5.5F, 15, 2, 1, 0.0F, false));
		Base.cubeList.add(new ModelBox(Base, 36, 38, 4.0F, -4.5F, -5.5F, 13, 2, 1, 0.0F, false));
		Base.cubeList.add(new ModelBox(Base, 0, 0, 5.0F, -6.5F, -5.5F, 11, 2, 1, 0.0F, false));
		Base.cubeList.add(new ModelBox(Base, 0, 0, 6.0F, -8.5F, -5.5F, 9, 2, 1, 0.0F, false));
		Base.cubeList.add(new ModelBox(Base, 40, 41, 7.0F, -10.5F, -5.5F, 7, 2, 1, 0.0F, false));
		Base.cubeList.add(new ModelBox(Base, 51, 50, 8.0F, -12.5F, -5.5F, 5, 2, 1, 0.0F, false));
		Base.cubeList.add(new ModelBox(Base, 46, 35, 9.0F, -14.5F, -5.5F, 3, 2, 1, 0.0F, false));

		WestSide = new ModelRenderer(this);
		WestSide.setRotationPoint(0.0F, 23.7F, -5.0F);
		setRotationAngle(WestSide, -0.3142F, -0.1501F, -0.3491F);
		WestSide.cubeList.add(new ModelBox(WestSide, 48, 42, 7.26F, -16.13F, -0.08F, 2, 21, 1, 0.0F, true));

		Hat = new ModelRenderer(this);
		Hat.setRotationPoint(0.0F, 24.0F, 0.0F);
		setRotationAngle(Hat, -0.3491F, 0.0F, 0.0873F);
		Hat.cubeList.add(new ModelBox(Hat, 2, 36, -2.1F, -17.5F, -6.9F, 5, 1, 5, 0.0F, false));
		Hat.cubeList.add(new ModelBox(Hat, 45, 11, -0.6F, -24.0F, -5.4F, 2, 7, 2, 0.0F, false));

		EyeBase = new ModelRenderer(this);
		EyeBase.setRotationPoint(1.8063F, 26.3012F, -2.9054F);
		setRotationAngle(EyeBase, -0.3491F, 0.0F, 0.0873F);
		EyeBase.cubeList.add(new ModelBox(EyeBase, 0, 0, -5.1063F, -12.3012F, -3.7946F, 7, 5, 1, 0.0F, false));

		Hand1 = new ModelRenderer(this);
		Hand1.setRotationPoint(-4.0F, 16.0F, -1.0F);
		setRotationAngle(Hand1, -0.3491F, -0.6109F, 0.4363F);
		Hand1.cubeList.add(new ModelBox(Hand1, 6, 46, -12.6F, -1.0F, -1.0F, 12, 1, 1, 0.0F, false));

		Thumb = new ModelRenderer(this);
		Thumb.setRotationPoint(-1.8047F, 25.4267F, 2.5444F);
		setRotationAngle(Thumb, -0.2618F, -0.3491F, 0.0F);
		Thumb.cubeList.add(new ModelBox(Thumb, 42, 38, -14.5753F, -15.7567F, -10.5244F, 1, 3, 1, 0.0F, false));

		Finger1 = new ModelRenderer(this);
		Finger1.setRotationPoint(-1.0359F, 24.7202F, -0.2704F);
		setRotationAngle(Finger1, -0.2618F, -0.4363F, 0.0F);
		Finger1.cubeList.add(new ModelBox(Finger1, 44, 29, -17.4641F, -14.3202F, -6.2296F, 3, 1, 1, 0.0F, false));

		Finger2 = new ModelRenderer(this);
		Finger2.setRotationPoint(-15.0F, 12.0F, -9.0F);
		setRotationAngle(Finger2, -0.1745F, -0.4363F, -0.2618F);
		Finger2.cubeList.add(new ModelBox(Finger2, 8, 33, -1.5F, -1.2F, -0.95F, 3, 1, 1, 0.0F, false));

		Finger3 = new ModelRenderer(this);
		Finger3.setRotationPoint(-15.0F, 12.0F, -9.0F);
		setRotationAngle(Finger3, -0.1745F, -0.5236F, -0.5236F);
		Finger3.cubeList.add(new ModelBox(Finger3, 55, 5, -1.5F, 0.1F, -0.95F, 3, 1, 1, 0.0F, false));

		Hand = new ModelRenderer(this);
		Hand.setRotationPoint(-1.0988F, 27.6357F, 0.605F);
		setRotationAngle(Hand, -0.2618F, -0.3491F, 0.0F);
		Hand.cubeList.add(new ModelBox(Hand, 38, 22, -15.5708F, -15.9357F, -9.105F, 2, 2, 1, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		EastSide.render(f5);
		Base.render(f5);
		WestSide.render(f5);
		Hat.render(f5);
		EyeBase.render(f5);
		Hand1.render(f5);
		Thumb.render(f5);
		Finger1.render(f5);
		Finger2.render(f5);
		Finger3.render(f5);
		Hand.render(f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}