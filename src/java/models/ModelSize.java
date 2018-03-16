package models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSize extends ModelBiped
{
  ModelRenderer head;
  ModelRenderer body;
  ModelRenderer rightarm;
  ModelRenderer leftarm;
  ModelRenderer rightleg;
  ModelRenderer leftleg;
  
  public ModelSize(float expand)
  {
	super(expand, 0.0F, 64, 64);
	  
    this.head = new ModelRenderer(this, 0, 0);
    this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);
    this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.head.setTextureSize(64, 64);
    this.head.showModel = true;
    setRotation(this.head, 0.0F, 0.0F, 0.0F);
    this.body = new ModelRenderer(this, 16, 16);
    this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4);
    this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.body.setTextureSize(64, 64);
    this.body.showModel = true;
    setRotation(this.body, 0.0F, 0.0F, 0.0F);
    this.rightarm = new ModelRenderer(this, 40, 16);
    this.rightarm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4);
    this.rightarm.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.rightarm.setTextureSize(64, 64);
    this.rightarm.showModel = true;
    setRotation(this.rightarm, 0.0F, 0.0F, 0.0F);
    this.leftarm = new ModelRenderer(this, 40, 16);
    this.leftarm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4);
    this.leftarm.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.leftarm.setTextureSize(64, 64);
    this.leftarm.showModel = true;
    setRotation(this.leftarm, 0.0F, 0.0F, 0.0F);
    this.rightleg = new ModelRenderer(this, 0, 16);
    this.rightleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
    this.rightleg.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.rightleg.setTextureSize(64, 64);
    this.rightleg.showModel = true;
    setRotation(this.rightleg, 0.0F, 0.0F, 0.0F);
    this.leftleg = new ModelRenderer(this, 0, 16);
    this.leftleg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
    this.leftleg.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.leftleg.setTextureSize(64, 64);
    this.leftleg.showModel = true;
    setRotation(this.leftleg, 0.0F, 0.0F, 0.0F);

    
    this.bipedHead.addChild(this.head);
    
    this.bipedRightArm.addChild(this.rightarm);
    
    this.bipedLeftArm.addChild(this.leftarm);
    
    this.bipedRightArm.addChild(this.rightleg);
    
    this.bipedLeftLeg.addChild(this.leftleg);
    
    this.bipedBody.addChild(this.body);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
}
