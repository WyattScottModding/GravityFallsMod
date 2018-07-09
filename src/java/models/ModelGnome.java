package models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelGnome extends ModelBase
{
  ModelRenderer Arm1;
  ModelRenderer Arm2;
  ModelRenderer Leg1;
  ModelRenderer Leg2;
  ModelRenderer Beard1;
  ModelRenderer Beard2;
  ModelRenderer Beard3;
  ModelRenderer Beard4;
  ModelRenderer Beard5;
  ModelRenderer Beard6;
  ModelRenderer Beard7;
  ModelRenderer Beard8;
  ModelRenderer Beard9;
  ModelRenderer Hat1;
  ModelRenderer Hat2;
  ModelRenderer Hat3;
  ModelRenderer Hat4;
  ModelRenderer Eye1;
  ModelRenderer Eye2;
  ModelRenderer Eyebrow1;
  ModelRenderer Eyebrow2;
  ModelRenderer Nose;
  
  public ModelGnome()
  {
    this.textureHeight = 256;
    this.textureWidth = 256;
    
    this.Arm1 = new ModelRenderer(this, 145, 14);
    this.Arm1.addBox(0.0F, 0.0F, -2.0F, 2, 4, 2);
    this.Arm1.setRotationPoint(-3.0F, 17.0F, -1.0F);
    this.Arm1.setTextureSize(256, 256);
    this.Arm1.showModel = true;
    setRotation(this.Arm1, 0.0F, -3.141593F, -0.1047198F);
    this.Leg1 = new ModelRenderer(this, 142, 27);
    this.Leg1.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);
    this.Leg1.setRotationPoint(-3.0F, 20.0F, -1.0F);
    this.Leg1.setTextureSize(256, 256);
    this.Leg1.showModel = true;
    setRotation(this.Leg1, 0.0F, 0.0F, 0.0F);
    this.Beard1 = new ModelRenderer(this, 43, 34);
    this.Beard1.addBox(0.0F, 0.0F, 0.0F, 8, 1, 6);
    this.Beard1.setRotationPoint(-4.0F, 16.5F, -3.0F);
    this.Beard1.setTextureSize(256, 256);
    this.Beard1.showModel = true;
    setRotation(this.Beard1, 0.0F, 0.0F, 0.0F);
    this.Beard2 = new ModelRenderer(this, 37, 31);
    this.Beard2.addBox(0.0F, 0.0F, 0.0F, 7, 2, 6);
    this.Beard2.setRotationPoint(-3.5F, 17.5F, -3.0F);
    this.Beard2.setTextureSize(256, 256);
    this.Beard2.showModel = true;
    setRotation(this.Beard2, 0.0F, 0.0F, 0.0F);
    this.Beard3 = new ModelRenderer(this, 36, 37);
    this.Beard3.addBox(0.0F, 0.0F, -1.0F, 2, 1, 1);
    this.Beard3.setRotationPoint(0.0F, 18.0F, -2.5F);
    this.Beard3.setTextureSize(256, 256);
    this.Beard3.showModel = true;
    setRotation(this.Beard3, 0.0F, 0.0F, -0.0698132F);
    this.Hat1 = new ModelRenderer(this, 105, 11);
    this.Hat1.addBox(0.0F, 0.0F, 0.0F, 4, 3, 4);
    this.Hat1.setRotationPoint(-2.0F, 10.0F, -2.0F);
    this.Hat1.setTextureSize(256, 256);
    this.Hat1.showModel = true;
    setRotation(this.Hat1, 0.0F, 0.0F, 0.0F);
    this.Hat2 = new ModelRenderer(this, 105, 11);
    this.Hat2.addBox(0.0F, 0.0F, 0.0F, 5, 3, 5);
    this.Hat2.setRotationPoint(-2.5F, 13.0F, -2.5F);
    this.Hat2.setTextureSize(256, 256);
    this.Hat2.showModel = true;
    setRotation(this.Hat2, 0.0F, 0.0F, 0.0F);
    this.Hat3 = new ModelRenderer(this, 105, 11);
    this.Hat3.addBox(0.0F, 0.0F, 2.0F, 2, 2, 2);
    this.Hat3.setRotationPoint(-1.0F, 8.5F, -3.0F);
    this.Hat3.setTextureSize(256, 256);
    this.Hat3.showModel = true;
    setRotation(this.Hat3, 0.0F, 0.0F, 0.0F);
    this.Hat4 = new ModelRenderer(this, 102, 8);
    this.Hat4.addBox(1.0F, -1.0F, 3.0F, 1, 1, 1);
    this.Hat4.setRotationPoint(-1.5F, 9.0F, -3.0F);
    this.Hat4.setTextureSize(256, 256);
    this.Hat4.showModel = true;
    setRotation(this.Hat4, 0.0F, 0.0F, 0.0F);
    this.Beard4 = new ModelRenderer(this, 41, 29);
    this.Beard4.addBox(0.0F, 0.0F, 0.0F, 4, 4, 2);
    this.Beard4.setRotationPoint(-2.0F, 18.0F, -3.0F);
    this.Beard4.setTextureSize(256, 256);
    this.Beard4.showModel = true;
    setRotation(this.Beard4, 0.0F, 0.0F, 0.0F);
    this.Beard5 = new ModelRenderer(this, 32, 30);
    this.Beard5.addBox(0.0F, 0.0F, 0.0F, 5, 3, 2);
    this.Beard5.setRotationPoint(-2.5F, 18.0F, 1.0F);
    this.Beard5.setTextureSize(256, 256);
    this.Beard5.showModel = true;
    setRotation(this.Beard5, 0.0F, 0.0F, 0.0F);
    this.Eye1 = new ModelRenderer(this, 51, 2);
    this.Eye1.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1);
    this.Eye1.setRotationPoint(-2.5F, 15.0F, -3.5F);
    this.Eye1.setTextureSize(256, 256);
    this.Eye1.showModel = true;
    setRotation(this.Eye1, 0.0F, 0.0F, 0.0F);
    this.Eye2 = new ModelRenderer(this, 62, 2);
    this.Eye2.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1);
    this.Eye2.setRotationPoint(0.5F, 15.0F, -3.5F);
    this.Eye2.setTextureSize(256, 256);
    this.Eye2.showModel = true;
    setRotation(this.Eye2, 0.0F, 0.0F, 0.0F);
    this.Beard6 = new ModelRenderer(this, 36, 32);
    this.Beard6.addBox(0.0F, 0.0F, 0.0F, 6, 4, 6);
    this.Beard6.setRotationPoint(-3.0F, 16.0F, -3.0F);
    this.Beard6.setTextureSize(256, 256);
    this.Beard6.showModel = true;
    setRotation(this.Beard6, 0.0F, 0.0F, 0.0F);
    this.Leg2 = new ModelRenderer(this, 142, 27);
    this.Leg2.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);
    this.Leg2.setRotationPoint(1.0F, 20.0F, -1.0F);
    this.Leg2.setTextureSize(256, 256);
    this.Leg2.showModel = true;
    setRotation(this.Leg2, 0.0F, 0.0F, 0.0F);
    this.Arm2 = new ModelRenderer(this, 145, 14);
    this.Arm2.addBox(0.0F, 0.0F, 0.0F, 2, 4, 2);
    this.Arm2.setRotationPoint(3.0F, 17.0F, -1.0F);
    this.Arm2.setTextureSize(256, 256);
    this.Arm2.showModel = true;
    setRotation(this.Arm2, 0.0F, 0.0F, -0.1047198F);
    this.Eyebrow1 = new ModelRenderer(this, 39, 36);
    this.Eyebrow1.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.Eyebrow1.setRotationPoint(-2.5F, 13.5F, -3.5F);
    this.Eyebrow1.setTextureSize(256, 256);
    this.Eyebrow1.showModel = true;
    setRotation(this.Eyebrow1, 0.0F, 0.0F, -0.0872665F);
    this.Eyebrow2 = new ModelRenderer(this, 39, 36);
    this.Eyebrow2.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1);
    this.Eyebrow2.setRotationPoint(2.5F, 13.5F, -2.5F);
    this.Eyebrow2.setTextureSize(256, 256);
    this.Eyebrow2.showModel = true;
    setRotation(this.Eyebrow2, 0.0F, -3.141593F, -0.0872665F);
    this.Beard7 = new ModelRenderer(this, 36, 37);
    this.Beard7.addBox(0.0F, 0.0F, -1.0F, 5, 1, 1);
    this.Beard7.setRotationPoint(-2.5F, 17.5F, -2.5F);
    this.Beard7.setTextureSize(256, 256);
    this.Beard7.showModel = true;
    setRotation(this.Beard7, 0.0F, 0.0F, 0.0F);
    this.Beard8 = new ModelRenderer(this, 36, 37);
    this.Beard8.addBox(0.0F, 0.0F, -1.0F, 2, 1, 1);
    this.Beard8.setRotationPoint(-2.0F, 18.0F, -2.5F);
    this.Beard8.setTextureSize(256, 256);
    this.Beard8.showModel = true;
    setRotation(this.Beard8, 0.0F, 0.0F, 0.0872665F);
    this.Beard9 = new ModelRenderer(this, 38, 28);
    this.Beard9.addBox(0.0F, 0.0F, 0.0F, 5, 3, 2);
    this.Beard9.setRotationPoint(-2.5F, 18.0F, -3.0F);
    this.Beard9.setTextureSize(256, 256);
    this.Beard9.showModel = true;
    setRotation(this.Beard9, 0.0F, 0.0F, 0.0F);
    this.Nose = new ModelRenderer(this, 138, 79);
    this.Nose.addBox(0.0F, 0.0F, 0.0F, 2, 2, 1);
    this.Nose.setRotationPoint(-1.0F, 16.0F, -4.0F);
    this.Nose.setTextureSize(256, 256);
    this.Nose.showModel = true;
    setRotation(this.Nose, -0.1396263F, 0.0F, 0.0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.Arm1.renderWithRotation(f5);
    this.Arm2.renderWithRotation(f5);
    this.Leg1.renderWithRotation(f5);
    this.Leg2.renderWithRotation(f5);
    this.Beard1.renderWithRotation(f5);
    this.Beard2.renderWithRotation(f5);
    this.Beard3.renderWithRotation(f5);
    this.Beard4.renderWithRotation(f5);
    this.Beard5.renderWithRotation(f5);
    this.Beard6.renderWithRotation(f5);
    this.Beard7.renderWithRotation(f5);
    this.Beard8.renderWithRotation(f5);
    this.Beard9.renderWithRotation(f5);
    this.Hat1.renderWithRotation(f5);
    this.Hat2.renderWithRotation(f5);
    this.Hat3.renderWithRotation(f5);
    this.Hat4.renderWithRotation(f5);
    this.Eye1.renderWithRotation(f5);
    this.Eye2.renderWithRotation(f5);
    this.Eyebrow1.renderWithRotation(f5);
    this.Eyebrow2.renderWithRotation(f5);

    this.Nose.renderWithRotation(f5);
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
    this.Arm2.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * -2.0F * f1 * 0.5F);
    this.Arm1.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F);
    this.Arm2.rotateAngleZ = 0.0F;
    this.Arm1.rotateAngleZ = 0.0F;
    this.Leg2.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
    this.Leg1.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
    this.Leg2.rotateAngleY = 0.0F;
    this.Leg1.rotateAngleY = 0.0F;
  }
  
  
}
