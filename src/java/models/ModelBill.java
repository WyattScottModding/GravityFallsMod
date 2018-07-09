package models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelBill extends ModelBase
{
	ModelRenderer Body1;
	ModelRenderer Body2;
	ModelRenderer Body3;
	ModelRenderer Body4;
	ModelRenderer Body5;
	ModelRenderer Body6;
	ModelRenderer Body7;
	ModelRenderer Body8;
	ModelRenderer Body9;
	ModelRenderer Body10;
	ModelRenderer Body11;
	ModelRenderer Hat1;
	ModelRenderer Hat2;
	ModelRenderer LeftArm1;
	ModelRenderer LeftArm2;
	ModelRenderer RightArm1;
	ModelRenderer RightArm2;
	ModelRenderer LeftLeg1;
	ModelRenderer LeftLeg2;
	ModelRenderer RightLeg1;
	ModelRenderer RightLeg2;
	
 
  
  public ModelBill()
  {
    this.textureHeight = 128;
    this.textureWidth = 128;
    
    this.Body1 = new ModelRenderer(this, 0, 490);
    this.Body1.addBox(-19.0F, -2.0F, -7.0F, 24, 2, 1);
    this.Body1.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.Body1.showModel = true;
    
    this.Body2 = new ModelRenderer(this, 0, 485);
    this.Body2.addBox(-18.0F, -4.0F, -7.0F, 22, 2, 1);
    this.Body2.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.Body2.showModel = true;
    
    this.Body3 = new ModelRenderer(this, 0, 480);
    this.Body3.addBox(-17.0F, -6.0F, -7.0F, 20, 2, 1);
    this.Body3.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.Body3.showModel = true;
    
    this.Body4 = new ModelRenderer(this, 0, 475);
    this.Body4.addBox(-16.0F, -8.0F, -7.0F, 18, 2, 1);
    this.Body4.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.Body4.showModel = true;
    
    this.Body5 = new ModelRenderer(this, 0, 470);
    this.Body5.addBox(-15.0F, -10.0F, -7.0F, 16, 2, 1);
    this.Body5.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.Body5.setTextureSize(256, 256);
    this.Body5.showModel = true;
    
    this.Body6 = new ModelRenderer(this, 0, 465);
    this.Body6.addBox(-14.0F, -12.0F, -7.0F, 14, 2, 1);
    this.Body6.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.Body6.showModel = true;
    
    this.Body7 = new ModelRenderer(this, 0, 460);
    this.Body7.addBox(-13.0F, -14.0F, -7.0F, 12, 2, 1);
    this.Body7.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.Body7.showModel = true;
    
    this.Body8 = new ModelRenderer(this, 0, 455);
    this.Body8.addBox(-12.0F, -16.0F, -7.0F, 10, 2, 1);
    this.Body8.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.Body8.showModel = true;
    
    this.Body9 = new ModelRenderer(this, 0, 450);
    this.Body9.addBox(-11.0F, -18.0F, -7.0F, 8, 2, 1);
    this.Body9.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.Body9.showModel = true;
    
    this.Body10 = new ModelRenderer(this, 0, 445);
    this.Body10.addBox(-10.0F, -20.0F, -7.0F, 6, 2, 1);
    this.Body10.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.Body10.showModel = true;
    
    this.Body11 = new ModelRenderer(this, 0, 440);
    this.Body11.addBox(-9.0F, -22.0F, -7.0F, 4, 2, 1);
    this.Body11.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.Body11.showModel = true;
    
    this.Hat1 = new ModelRenderer(this, 0, 0);
    this.Hat1.addBox(-12.0F, -23.0F, -7.0F, 10, 1, 1);
    this.Hat1.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.Hat1.setTextureSize(256, 256);
    this.Hat1.showModel = true;
    
    this.Hat2 = new ModelRenderer(this, 0, 0);
    this.Hat2.addBox(-8.0F, -31.0F, -7.0F, 2, 8, 1);
    this.Hat2.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.Hat2.setTextureSize(256, 256);
    this.Hat2.showModel = true;
    
    this.LeftLeg1 = new ModelRenderer(this, 0, 0);
    this.LeftLeg1.addBox(-4.0F, 2.0F, -7.0F, 5, 2, 1);
    this.LeftLeg1.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.LeftLeg1.showModel = true;
    
    this.LeftLeg2 = new ModelRenderer(this, 0, 0);
    this.LeftLeg2.addBox(-4.0F, 0.0F, -7.0F, 2, 4, 1);
    this.LeftLeg2.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.LeftLeg2.showModel = true;
    
    this.RightLeg1 = new ModelRenderer(this, 0, 0);
    this.RightLeg1.addBox(-16.0F, 2.0F, -7.0F, 5, 2, 1);
    this.RightLeg1.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.RightLeg1.showModel = true;
    
    this.RightLeg2 = new ModelRenderer(this, 0, 0);
    this.RightLeg2.addBox(-13.0F, 0.0F, -7.0F, 2, 4, 1);
    this.RightLeg2.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.RightLeg2.showModel = true;
    
    this.LeftArm1 = new ModelRenderer(this, 0, 0);
    this.LeftArm1.addBox(1.0F, -10.0F, -7.0F, 6, 2, 1);
    this.LeftArm1.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.LeftArm1.showModel = true;
    
    this.LeftArm2 = new ModelRenderer(this, 0, 0);
    this.LeftArm2.addBox(5.0F, -9.0F, -7.0F, 2, 5, 1);
    this.LeftArm2.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.LeftArm2.showModel = true;
    
    this.RightArm1 = new ModelRenderer(this, 0, 0);
    this.RightArm1.addBox(-21.0F, -10.0F, -7.0F, 6, 2, 1);
    this.RightArm1.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.RightArm1.showModel = true;
    
    this.RightArm2 = new ModelRenderer(this, 0, 0);
    this.RightArm2.addBox(-21.0F, -9.0F, -7.0F, 2, 5, 1);
    this.RightArm2.setRotationPoint(8.0F, 10.0F, 7.0F);
    this.RightArm2.showModel = true;


   
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);

	this.Body1.renderWithRotation(f5);
	this.Body2.renderWithRotation(f5);
	this.Body3.renderWithRotation(f5);
	this.Body4.renderWithRotation(f5);
	this.Body5.renderWithRotation(f5);
	this.Body6.renderWithRotation(f5);
	this.Body7.renderWithRotation(f5);
	this.Body8.renderWithRotation(f5);
	this.Body9.renderWithRotation(f5);
	this.Body10.renderWithRotation(f5);
	this.Body11.renderWithRotation(f5);
	this.Hat1.renderWithRotation(f5);
	this.Hat2.renderWithRotation(f5);
	this.LeftLeg1.renderWithRotation(f5);
	this.LeftLeg2.renderWithRotation(f5);
	this.LeftArm1.renderWithRotation(f5);
	this.LeftArm2.renderWithRotation(f5);
	this.RightLeg1.renderWithRotation(f5);
	this.RightLeg2.renderWithRotation(f5);
	this.RightArm1.renderWithRotation(f5);
	this.RightArm2.renderWithRotation(f5);

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
    
  } 
}