package models;
// Made with Blockbench 3.5.4
// Exported for Minecraft version 1.12
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelEvilTree extends ModelBase {
	private final ModelRenderer Body;
	private final ModelRenderer LegR;
	private final ModelRenderer ToeR2;
	private final ModelRenderer ToeR1;
	private final ModelRenderer LegL;
	private final ModelRenderer ToeL2;
	private final ModelRenderer ToeL1;

	public ModelEvilTree() {
		textureWidth = 256;
		textureHeight = 256;

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 16.0F, 0.0F);
		Body.cubeList.add(new ModelBox(Body, 0, 0, -8.0F, -16.0F, -8.0F, 16, 16, 16, 0.0F, false));

		LegR = new ModelRenderer(this);
		LegR.setRotationPoint(-4.0F, 16.0F, 0.0F);
		LegR.cubeList.add(new ModelBox(LegR, 21, 3, -1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));

		ToeR2 = new ModelRenderer(this);
		ToeR2.setRotationPoint(-4.0F, 16.0F, -1.0F);
		setRotationAngle(ToeR2, 0.0F, -0.5236F, 0.0F);
		ToeR2.cubeList.add(new ModelBox(ToeR2, 49, 24, 0.0F, 7.0F, -4.0F, 1, 1, 5, 0.0F, false));

		ToeR1 = new ModelRenderer(this);
		ToeR1.setRotationPoint(-4.0F, 16.0F, -1.0F);
		setRotationAngle(ToeR1, 0.0F, 0.5236F, 0.0F);
		ToeR1.cubeList.add(new ModelBox(ToeR1, 49, 24, -1.0F, 7.0F, -4.0F, 1, 1, 5, 0.0F, false));

		LegL = new ModelRenderer(this);
		LegL.setRotationPoint(4.0F, 16.0F, 0.0F);
		LegL.cubeList.add(new ModelBox(LegL, 21, 3, -1.0F, 0.0F, -1.0F, 2, 8, 2, 0.0F, false));

		ToeL2 = new ModelRenderer(this);
		ToeL2.setRotationPoint(4.0F, 16.0F, -1.0F);
		setRotationAngle(ToeL2, 0.0F, -0.5236F, 0.0F);
		ToeL2.cubeList.add(new ModelBox(ToeL2, 49, 24, 0.0F, 7.0F, -4.0F, 1, 1, 5, 0.0F, false));

		ToeL1 = new ModelRenderer(this);
		ToeL1.setRotationPoint(4.0F, 16.0F, -1.0F);
		setRotationAngle(ToeL1, 0.0F, 0.5236F, 0.0F);
		ToeL1.cubeList.add(new ModelBox(ToeL1, 49, 24, -1.0F, 7.0F, -4.0F, 1, 1, 5, 0.0F, false));
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		Body.render(f5);
		LegR.render(f5);
		ToeR2.render(f5);
		ToeR1.render(f5);
		LegL.render(f5);
		ToeL2.render(f5);
		ToeL1.render(f5);
	}
	
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		this.LegR.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.0F * f1);
		this.ToeR1.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.0F * f1);
		this.ToeR2.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.0F * f1);

		this.LegL.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.0F * f1);
		this.ToeL1.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.0F * f1);
		this.ToeL2.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.0F * f1);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}