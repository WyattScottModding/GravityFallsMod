package models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelUnicorn extends ModelBase
{
    ModelRenderer Chin;
    ModelRenderer tail;
    ModelRenderer head;
    ModelRenderer body;
    ModelRenderer leg1;
    ModelRenderer leg2;
    ModelRenderer leg3;
    ModelRenderer leg4;
    ModelRenderer horn;
    ModelRenderer neck;
  
  public ModelUnicorn()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      Chin = new ModelRenderer(this, 6, 56);
      Chin.addBox(0F, 0F, 0F, 4, 2, 2);
      Chin.setRotationPoint(-2F, -9F, -14F);
      Chin.setTextureSize(64, 64);
      setRotation(Chin, 0F, 0F, 0F);
      
      tail = new ModelRenderer(this, 0, 0);
      tail.addBox(0F, -2F, 0F, 2, 18, 2);
      tail.setRotationPoint(-1F, 0F, 12F);
      tail.setTextureSize(64, 64);
      setRotation(tail, 0F, 0F, 0F);
      
      head = new ModelRenderer(this, 6, 47);
      head.addBox(-4F, -4F, -6F, 6, 7, 2);
      head.setRotationPoint(1F, -12F, -8F);
      head.setTextureSize(64, 64);
      setRotation(head, 0F, 0F, 0F);
      
      body = new ModelRenderer(this, 20, 23);
      body.addBox(-6F, -10F, -7F, 12, 24, 10);
      body.setRotationPoint(0F, 2F, -2F);
      body.setTextureSize(64, 64);
      setRotation(body, 1.570796F, 0F, 0F);
      
      leg1 = new ModelRenderer(this, 0, 0);
      leg1.addBox(-3F, 0F, -2F, 3, 16, 3);
      leg1.setRotationPoint(-3F, 8F, 11F);
      leg1.setTextureSize(64, 64);
      setRotation(leg1, 0F, 0F, 0F);
      
      leg2 = new ModelRenderer(this, 0, 0);
      leg2.addBox(-1F, 0F, -2F, 3, 16, 3);
      leg2.setRotationPoint(4F, 8F, 11F);
      leg2.setTextureSize(64, 64);
      setRotation(leg2, 0F, 0F, 0F);
      
      leg3 = new ModelRenderer(this, 0, 0);
      leg3.addBox(-3F, 0F, -3F, 3, 16, 3);
      leg3.setRotationPoint(-3F, 8F, -9F);
      leg3.setTextureSize(64, 64);
      setRotation(leg3, 0F, 0F, 0F);
      
      leg4 = new ModelRenderer(this, 0, 0);
      leg4.addBox(-1F, 0F, -3F, 3, 16, 3);
      leg4.setRotationPoint(4F, 8F, -9F);
      leg4.setTextureSize(64, 64);
      setRotation(leg4, 0F, 0F, 0F);
      
      horn = new ModelRenderer(this, 53, 0);
      horn.addBox(-4F, -5F, -4F, 1, 10, 1);
      horn.setRotationPoint(3.5F, -21F, -10.5F);
      horn.setTextureSize(64, 64);
      setRotation(horn, 0.5235988F, 0F, 0F);
      
      neck = new ModelRenderer(this, 0, 22);
      neck.addBox(0F, 0F, 0F, 6, 15, 4);
      neck.setRotationPoint(-3F, -16F, -12F);
      neck.setTextureSize(64, 64);
      setRotation(neck, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Chin.render(f5);
    tail.render(f5);
    head.render(f5);
    body.render(f5);
    leg1.render(f5);
    leg2.render(f5);
    leg3.render(f5);
    leg4.render(f5);
    horn.render(f5);
    neck.render(f5);
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
    
    this.leg1.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
	this.leg4.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
	this.leg1.rotationPointY = 8.0F;
	this.leg4.rotationPointY = 8.0F;
	
	this.leg3.rotateAngleX = (MathHelper.cos(f * 0.6662F) * 1.4F * f1);
	this.leg2.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * f1);
	this.leg3.rotationPointY = 8.0F;
	this.leg2.rotationPointY = 8.0F;
	
	this.tail.rotateAngleZ = (MathHelper.cos(f * 0.6662F) * 0.8F * f1);
	this.tail.rotationPointY = 3.0F;
  }

}
