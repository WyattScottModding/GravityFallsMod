package models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelGideonBot extends ModelBase
{
    ModelRenderer LeftLeg1;
    ModelRenderer LeftFoot1;
    ModelRenderer LeftLeg3;
    ModelRenderer LeftLeg2;
    ModelRenderer RightFoot1;
    ModelRenderer RightLeg1;
    ModelRenderer RightLeg3;
    ModelRenderer RightLeg2;
    ModelRenderer RightFoot2;
    ModelRenderer LeftFoot2;
    ModelRenderer Body1;
    ModelRenderer Body2;
    ModelRenderer Body3;
    ModelRenderer Body4;
    ModelRenderer LeftShoulder;
    ModelRenderer RightShoulder;
    ModelRenderer RightArm1;
    ModelRenderer RightArm2;
    ModelRenderer RightArm3;
    ModelRenderer RightArm4;
    ModelRenderer RightArm5;
    ModelRenderer LeftArm1;
    ModelRenderer LeftArm2;
    ModelRenderer LeftArm3;
    ModelRenderer LeftArm4;
    ModelRenderer LeftArm5;
    ModelRenderer RightHand;
    ModelRenderer RightPinky;
    ModelRenderer RightRing;
    ModelRenderer RightMiddle;
    ModelRenderer RightIndex;
    ModelRenderer RightThumb1;
    ModelRenderer RightThumb2;
    ModelRenderer LeftHand;
    ModelRenderer LeftPinky;
    ModelRenderer LeftRing;
    ModelRenderer LeftMiddle;
    ModelRenderer LeftIndex;
    ModelRenderer LeftThumb1;
    ModelRenderer LeftThumb2;
    ModelRenderer Head;
    ModelRenderer Hair1;
    ModelRenderer Hair2;
    ModelRenderer Hair3;
    ModelRenderer Hair4;
  
  public ModelGideonBot()
  {
    textureWidth = 650;
    textureHeight = 900;
    
      LeftLeg1 = new ModelRenderer(this, 0, 352);
      LeftLeg1.addBox(0F, -1F, -16F, 32, 38, 32);
      LeftLeg1.setRotationPoint(-62F, -32F, 0F);
      LeftLeg1.setTextureSize(650, 900);
      setRotation(LeftLeg1, 0F, 0F, 0F);
      
      LeftLeg2 = new ModelRenderer(this, 0, 352);
      LeftLeg2.addBox(0F, 40F, -16F, 32, 43, 32);
      LeftLeg2.setRotationPoint(-62F, -75F, 0F);
      LeftLeg2.setTextureSize(650, 900);
      setRotation(LeftLeg2, 0F, 0F, 0F);
      
      LeftLeg3 = new ModelRenderer(this, 202, 338);
      LeftLeg3.addBox(0F, 37F, -16F, 30, 3, 30);
      LeftLeg3.setRotationPoint(-61F, -35F, 1F);
      LeftLeg3.setTextureSize(650, 900);
      setRotation(LeftLeg3, 0F, 0F, 0F);
      
      LeftFoot1 = new ModelRenderer(this, 0, 265);
      LeftFoot1.addBox(0F, 82F, -48F, 32, 18, 68);
      LeftFoot1.setRotationPoint(-62F, 6F, -36F);
      LeftFoot1.setTextureSize(650, 900);
      setRotation(LeftFoot1, 0F, 0F, 0F);
      
      LeftFoot2 = new ModelRenderer(this, 0, 333);
      LeftFoot2.addBox(0F, 85F, -51F, 24, 12, 3);
      LeftFoot2.setRotationPoint(36F, 9F, -39F);
      LeftFoot2.setTextureSize(650, 900);
      setRotation(LeftFoot2, 0F, 0F, 0F);
      
      RightFoot1 = new ModelRenderer(this, 0, 265);
      RightFoot1.addBox(0F, 82F, -48F, 32, 18, 68);
      RightFoot1.setRotationPoint(32F, 6F, -36F);
      RightFoot1.setTextureSize(650, 900);
      setRotation(RightFoot1, 0F, 0F, 0F);

      RightFoot2 = new ModelRenderer(this, 0, 333);
      RightFoot2.addBox(0F, 85F, -51F, 24, 12, 3);
      RightFoot2.setRotationPoint(-58F, 9F, -39F);
      RightFoot2.setTextureSize(650, 900);
      setRotation(RightFoot2, 0F, 0F, 0F);
      
      RightLeg1 = new ModelRenderer(this, 0, 352);
      RightLeg1.addBox(0F, -1F, -16F, 32, 38, 32);
      RightLeg1.setRotationPoint(32F, -32F, 0F);
      RightLeg1.setTextureSize(650, 900);
      setRotation(RightLeg1, 0F, 0F, 0F);
      
      RightLeg2 = new ModelRenderer(this, 0, 352);
      RightLeg2.addBox(0F, 40F, -16F, 32, 43, 32);
      RightLeg2.setRotationPoint(32F, -75F, 0F);
      RightLeg2.setTextureSize(650, 900);
      setRotation(RightLeg2, 0F, 0F, 0F);
      
      RightLeg3 = new ModelRenderer(this, 202, 338);
      RightLeg3.addBox(0F, 37F, -16F, 30, 3, 30);
      RightLeg3.setRotationPoint(33F, -35F, 1F);
      RightLeg3.setTextureSize(650, 900);
      setRotation(RightLeg3, 0F, 0F, 0F);
            
      Body1 = new ModelRenderer(this, 203, 265);
      Body1.addBox(0F, -2F, 0F, 94, 16, 56);
      Body1.setRotationPoint(-45F, -75F, -12F);
      Body1.setTextureSize(650, 900);
      setRotation(Body1, 0F, 0F, 0F);
      
      Body2 = new ModelRenderer(this, 10, 15);
      Body2.addBox(0F, -1F, -1F, 142, 155, 96);
      Body2.setRotationPoint(-68F, -230F, -35F);
      Body2.setTextureSize(650, 900);
      setRotation(Body2, 0F, 0F, 0F);
      
      Body3 = new ModelRenderer(this, 0, 0);
      Body3.addBox(0F, 0F, 0F, 120, 134, 118);
      Body3.setRotationPoint(-56F, -224F, -46F);
      Body3.setTextureSize(650, 900);
      setRotation(Body3, 0F, 0F, 0F);
      
      Body4 = new ModelRenderer(this, 17, 38);
      Body4.addBox(0F, 0F, 0F, 154, 134, 80);
      Body4.setRotationPoint(-74F, -224F, -28F);
      Body4.setTextureSize(650, 900);
      setRotation(Body4, 0F, 0F, 0F);
      
      LeftShoulder = new ModelRenderer(this, 344, 341);
      LeftShoulder.addBox(0F, 0F, 0F, 24, 48, 48);
      LeftShoulder.setRotationPoint(-90F, -240F, -12F);
      LeftShoulder.setTextureSize(650, 900);
      setRotation(LeftShoulder, 0F, 0F, 0F);
      
      RightShoulder = new ModelRenderer(this, 344, 341);
      RightShoulder.addBox(0F, 0F, 0F, 24, 48, 48);
      RightShoulder.setRotationPoint(95F, -240F, 36F);
      RightShoulder.setTextureSize(650, 900);
      setRotation(RightShoulder, 0F, 3.141593F, 0F);
      
      RightArm1 = new ModelRenderer(this, 0, 0);
      RightArm1.addBox(0F, -16F, -20F, 32, 32, 32);
      RightArm1.setRotationPoint(-122F, -232F, -4F);
      RightArm1.setTextureSize(650, 900);
      setRotation(RightArm1, 0F, 0F, 0F);
      
      RightArm2 = new ModelRenderer(this, 0, 0);
      RightArm2.addBox(0F, 19F, -20F, 32, 22, 32);
      RightArm2.setRotationPoint(-122F, -197F, -4F);
      RightArm2.setTextureSize(650, 900);
      setRotation(RightArm2, 0F, 0F, 0F);
      
      RightArm3 = new ModelRenderer(this, 0, 0);
      RightArm3.addBox(0F, 44F, -20F, 32, 22, 32);
      RightArm3.setRotationPoint(-122F, -172F, -4F);
      RightArm3.setTextureSize(650, 900);
      setRotation(RightArm3, 0F, 0F, 0F);
      
      RightArm4 = new ModelRenderer(this, 203, 338);
      RightArm4.addBox(0F, 16F, -20F, 30, 3, 30);
      RightArm4.setRotationPoint(-121F, -200F, -3F);
      RightArm4.setTextureSize(650, 900);
      setRotation(RightArm4, 0F, 0F, 0F);
      
      RightArm5 = new ModelRenderer(this, 203, 338);
      RightArm5.addBox(0F, 41F, -20F, 30, 3, 30);
      RightArm5.setRotationPoint(-121F, -175F, -3F);
      RightArm5.setTextureSize(650, 900);
      setRotation(RightArm5, 0F, 0F, 0F);
      
      LeftArm1 = new ModelRenderer(this, 0, 0);
      LeftArm1.addBox(0F, -16F, -20F, 32, 32, 32);
      LeftArm1.setRotationPoint(95F, -232F, -3F);
      LeftArm1.setTextureSize(650, 900);
      setRotation(LeftArm1, 0F, 0F, 0F);
      
      LeftArm2 = new ModelRenderer(this, 0, 0);
      LeftArm2.addBox(0F, 19F, -20F, 32, 22, 32);
      LeftArm2.setRotationPoint(95F, -197F, -3F);
      LeftArm2.setTextureSize(650, 900);
      setRotation(LeftArm2, 0F, 0F, 0F);
      
      LeftArm3 = new ModelRenderer(this, 0, 0);
      LeftArm3.addBox(0F, 44F, -20F, 32, 22, 32);
      LeftArm3.setRotationPoint(95F, -171.9778F, -3F);
      LeftArm3.setTextureSize(650, 900);
      setRotation(LeftArm3, 0F, 0F, 0F);
      
      LeftArm4 = new ModelRenderer(this, 203, 338);
      LeftArm4.addBox(0F, 16F, -20F, 30, 3, 30);
      LeftArm4.setRotationPoint(96F, -200F, -2F);
      LeftArm4.setTextureSize(650, 900);
      setRotation(LeftArm4, 0F, 0F, 0F);
      
      LeftArm5 = new ModelRenderer(this, 203, 339);
      LeftArm5.addBox(0F, 41F, -20F, 30, 3, 30);
      LeftArm5.setRotationPoint(96F, -175F, -2F);
      LeftArm5.setTextureSize(650, 900);
      setRotation(LeftArm5, 0F, 0F, 0F);
      
      RightHand = new ModelRenderer(this, 0, 433);
      RightHand.addBox(0F, 66F, -16F, 20, 24, 24);
      RightHand.setRotationPoint(-116F, -150F, 0F);
      RightHand.setTextureSize(650, 900);
      setRotation(RightHand, 0F, 0F, 0F);
      
      RightPinky = new ModelRenderer(this, 526, 13);
      RightPinky.addBox(0F, 90F, 3F, 4, 13, 4);
      RightPinky.setRotationPoint(-115F, -126F, 19F);
      RightPinky.setTextureSize(650, 900);
      setRotation(RightPinky, 0F, 0F, 0F);
      
      RightRing = new ModelRenderer(this, 526, 29);
      RightRing.addBox(0F, 90F, -3F, 4, 16, 4);
      RightRing.setRotationPoint(-115F, -126F, 13F);
      RightRing.setTextureSize(650, 900);
      setRotation(RightRing, 0F, 0F, 0F);
      
      RightMiddle = new ModelRenderer(this, 526, 28);
      RightMiddle.addBox(0F, 90F, -9F, 4, 18, 4);
      RightMiddle.setRotationPoint(-115F, -126F, 7F);
      RightMiddle.setTextureSize(650, 900);
      setRotation(RightMiddle, 0F, 0F, 0F);
      
      RightIndex = new ModelRenderer(this, 526, 11);
      RightIndex.addBox(0F, 90F, -15F, 4, 15, 4);
      RightIndex.setRotationPoint(-115F, -126F, 1F);
      RightIndex.setTextureSize(650, 900);
      setRotation(RightIndex, 0F, 0F, 0F);
      
      RightThumb1 = new ModelRenderer(this, 526, 26);
      RightThumb1.addBox(0F, 86F, -20F, 4, 3, 4);
      RightThumb1.setRotationPoint(-111F, -130F, -3F);
      RightThumb1.setTextureSize(650, 900);
      setRotation(RightThumb1, 1.570796F, 0F, 0F);
      
      RightThumb2 = new ModelRenderer(this, 526, 26);
      RightThumb2.addBox(0F, 86F, -24F, 4, 13, 4);
      RightThumb2.setRotationPoint(-111F, -134F, -7F);
      RightThumb2.setTextureSize(650, 900);
      setRotation(RightThumb2, 0F, 0F, 0F);
      
      LeftHand = new ModelRenderer(this, 0, 433);
      LeftHand.addBox(0F, 66F, -16F, 20, 24, 24);
      LeftHand.setRotationPoint(101F, -150F, 1F);
      LeftHand.setTextureSize(650, 900);
      setRotation(LeftHand, 0F, 0F, 0F);
      
      LeftPinky = new ModelRenderer(this, 526, 13);
      LeftPinky.addBox(0F, 90F, 3F, 4, 13, 4);
      LeftPinky.setRotationPoint(116F, -126F, 20F);
      LeftPinky.setTextureSize(650, 900);
      setRotation(LeftPinky, 0F, 0F, 0F);
      
      LeftRing = new ModelRenderer(this, 526, 29);
      LeftRing.addBox(0F, 90F, -3F, 4, 16, 4);
      LeftRing.setRotationPoint(116.3556F, -126F, 14F);
      LeftRing.setTextureSize(650, 900);
      setRotation(LeftRing, 0F, 0F, 0F);
      
      LeftMiddle = new ModelRenderer(this, 526, 28);
      LeftMiddle.addBox(0F, 90F, -9F, 4, 18, 4);
      LeftMiddle.setRotationPoint(116F, -126F, 8F);
      LeftMiddle.setTextureSize(650, 900);
      setRotation(LeftMiddle, 0F, 0F, 0F);
      
      LeftIndex = new ModelRenderer(this, 526, 11);
      LeftIndex.addBox(0F, 90F, -15F, 4, 15, 4);
      LeftIndex.setRotationPoint(116F, -126F, 2F);
      LeftIndex.setTextureSize(650, 900);
      setRotation(LeftIndex, 0F, 0F, 0F);
      
      LeftThumb1 = new ModelRenderer(this, 526, 52);
      LeftThumb1.addBox(0F, 86F, -20F, 4, 3, 4);
      LeftThumb1.setRotationPoint(112F, -130F, -2F);
      LeftThumb1.setTextureSize(650, 900);
      setRotation(LeftThumb1, 1.570796F, 0F, 0F);
      
      LeftThumb2 = new ModelRenderer(this, 526, 26);
      LeftThumb2.addBox(0F, 86F, -24F, 4, 13, 4);
      LeftThumb2.setRotationPoint(112F, -134F, -5F);
      LeftThumb2.setTextureSize(650, 900);
      setRotation(LeftThumb2, 0F, 0F, 0F);
      
      Head = new ModelRenderer(this, 0, 441);
      Head.addBox(0F, 0F, 0F, 140, 100, 140);
      Head.setRotationPoint(-67F, -330F, -60F);
      Head.setTextureSize(650, 900);
      setRotation(Head, 0F, 0F, 0F);
      
      Hair1 = new ModelRenderer(this, 0, 700);
      Hair1.addBox(0F, 0F, 0F, 160, 110, 80);
      Hair1.setRotationPoint(-77F, -440F, -84F);
      Hair1.setTextureSize(650, 900);
      setRotation(Hair1, 0F, 0F, 0F);
      
      Hair2 = new ModelRenderer(this, 250, 700);
      Hair2.addBox(0F, 0F, 0F, 132, 16, 64);
      Hair2.setRotationPoint(-63F, -455.9778F, -76F);
      Hair2.setTextureSize(650, 900);
      setRotation(Hair2, 0F, 0F, 0F);
      
      Hair3 = new ModelRenderer(this, 0, 700);
      Hair3.addBox(0F, 0F, 0F, 180, 86, 60);
      Hair3.setRotationPoint(-88F, -428F, -75F);
      Hair3.setTextureSize(650, 900);
      setRotation(Hair3, 0F, 0F, 0F);

      Hair4 = new ModelRenderer(this, 0, 700);
      Hair4.addBox(0F, 0F, 0F, 160, 80, 92);
      Hair4.setRotationPoint(-77F, -354F, -4F);
      Hair4.setTextureSize(650, 900);
      setRotation(Hair4, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    LeftLeg1.render(f5);
    LeftLeg2.render(f5);
    LeftLeg3.render(f5);
    LeftFoot1.render(f5);
    LeftFoot2.render(f5);

    RightLeg1.render(f5);
    RightLeg2.render(f5);
    RightLeg3.render(f5);
    RightFoot1.render(f5);
    RightFoot2.render(f5);
    
    Body1.render(f5);
    Body2.render(f5);
    Body3.render(f5);
    Body4.render(f5);
    
    LeftShoulder.render(f5);
    RightShoulder.render(f5);
    
    RightArm1.render(f5);
    RightArm2.render(f5);
    RightArm3.render(f5);
    RightArm4.render(f5);
    RightArm5.render(f5);
    
    LeftArm1.render(f5);
    LeftArm2.render(f5);
    LeftArm3.render(f5);
    LeftArm4.render(f5);
    LeftArm5.render(f5);
    
    RightHand.render(f5);
    RightPinky.render(f5);
    RightRing.render(f5);
    RightMiddle.render(f5);
    RightIndex.render(f5);
    RightThumb1.render(f5);
    RightThumb2.render(f5);
    
    LeftHand.render(f5);
    LeftPinky.render(f5);
    LeftRing.render(f5);
    LeftMiddle.render(f5);
    LeftIndex.render(f5);
    LeftThumb1.render(f5);
    LeftThumb2.render(f5);
    
    Head.render(f5);
    Hair1.render(f5);
    Hair2.render(f5);
    Hair3.render(f5);
    Hair4.render(f5);
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
    
    float rotation = 1.0f;
    float armPointY = -216.0F;
    float legPointY = -75.0F;
    float armPointZ = 16F;
    float legPointZ = 16F;
    
    this.LeftArm1.rotateAngleX = (MathHelper.cos(f * 0.6662F) * rotation * f1);
	this.LeftArm1.rotationPointY = armPointY;
	this.LeftArm1.rotationPointZ = armPointZ;
	this.LeftArm2.rotateAngleX = (MathHelper.cos(f * 0.6662F) * rotation * f1);
	this.LeftArm2.rotationPointY = armPointY;
	this.LeftArm2.rotationPointZ = armPointZ;
	this.LeftArm3.rotateAngleX = (MathHelper.cos(f * 0.6662F) * rotation * f1);
	this.LeftArm3.rotationPointY = armPointY;
	this.LeftArm3.rotationPointZ = armPointZ;
	this.LeftArm4.rotateAngleX = (MathHelper.cos(f * 0.6662F) * rotation * f1);
	this.LeftArm4.rotationPointY = armPointY;
	this.LeftArm4.rotationPointZ = armPointZ;
	this.LeftArm5.rotateAngleX = (MathHelper.cos(f * 0.6662F) * rotation * f1);
	this.LeftArm5.rotationPointY = armPointY;
	this.LeftArm5.rotationPointZ = armPointZ;
	
	this.RightHand.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * rotation * f1);
	this.RightHand.rotationPointY = armPointY;
	this.RightHand.rotationPointZ = armPointZ;
	this.RightThumb1.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * rotation * f1);
	this.RightThumb1.rotationPointY = armPointY;
	this.RightThumb1.rotationPointZ = armPointZ;
	this.RightThumb2.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * rotation * f1);
	this.RightThumb2.rotationPointY = armPointY;
	this.RightThumb2.rotationPointZ = armPointZ;
	this.RightIndex.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * rotation * f1);
	this.RightIndex.rotationPointY = armPointY;
	this.RightIndex.rotationPointZ = armPointZ;
	this.RightMiddle.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * rotation * f1);
	this.RightMiddle.rotationPointY = armPointY;
	this.RightMiddle.rotationPointZ = armPointZ;
	this.RightRing.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * rotation * f1);
	this.RightRing.rotationPointY = armPointY;
	this.RightRing.rotationPointZ = armPointZ;
	this.RightPinky.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * rotation * f1);
	this.RightPinky.rotationPointY = armPointY;
	this.RightPinky.rotationPointZ = armPointZ;
	
	this.LeftHand.rotateAngleX = (MathHelper.cos(f * 0.6662F) * rotation * f1);
	this.LeftHand.rotationPointY = armPointY;
	this.LeftHand.rotationPointZ = armPointZ;
	this.LeftThumb1.rotateAngleX = (MathHelper.cos(f * 0.6662F) * rotation * f1);
	this.LeftThumb1.rotationPointY = armPointY;
	this.LeftThumb1.rotationPointZ = armPointZ;
	this.LeftThumb2.rotateAngleX = (MathHelper.cos(f * 0.6662F) * rotation * f1);
	this.LeftThumb2.rotationPointY = armPointY;
	this.LeftThumb2.rotationPointZ = armPointZ;
	this.LeftIndex.rotateAngleX = (MathHelper.cos(f * 0.6662F) * rotation * f1);
	this.LeftIndex.rotationPointY = armPointY;
	this.LeftIndex.rotationPointZ = armPointZ;
	this.LeftMiddle.rotateAngleX = (MathHelper.cos(f * 0.6662F) * rotation * f1);
	this.LeftMiddle.rotationPointY = armPointY;
	this.LeftMiddle.rotationPointZ = armPointZ;
	this.LeftRing.rotateAngleX = (MathHelper.cos(f * 0.6662F) * rotation * f1);
	this.LeftRing.rotationPointY = armPointY;
	this.LeftRing.rotationPointZ = armPointZ;
	this.LeftPinky.rotateAngleX = (MathHelper.cos(f * 0.6662F) * rotation * f1);
	this.LeftPinky.rotationPointY = armPointY;
	this.LeftPinky.rotationPointZ = armPointZ;
	
	this.RightArm1.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * rotation * f1);
	this.RightArm1.rotationPointY = armPointY;
	this.RightArm1.rotationPointZ = armPointZ;
	this.RightArm2.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * rotation * f1);
	this.RightArm2.rotationPointY = armPointY;
	this.RightArm2.rotationPointZ = armPointZ;
	this.RightArm3.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * rotation * f1);
	this.RightArm3.rotationPointY = armPointY;
	this.RightArm3.rotationPointZ = armPointZ;
	this.RightArm4.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * rotation * f1);
	this.RightArm4.rotationPointY = armPointY;
	this.RightArm4.rotationPointZ = armPointZ;
	this.RightArm5.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * rotation * f1);
	this.RightArm5.rotationPointY = armPointY;
	this.RightArm5.rotationPointZ = armPointZ;
	
	this.RightLeg1.rotateAngleX = (MathHelper.cos(f * 0.6662F) * rotation * f1);
	this.RightLeg1.rotationPointY = legPointY;
	this.RightLeg1.rotationPointZ = legPointZ;
	this.RightLeg2.rotateAngleX = (MathHelper.cos(f * 0.6662F) * rotation * f1);
	this.RightLeg2.rotationPointY = legPointY;
	this.RightLeg2.rotationPointZ = legPointZ;
	this.RightLeg3.rotateAngleX = (MathHelper.cos(f * 0.6662F) * rotation * f1);
	this.RightLeg3.rotationPointY = legPointY;
	this.RightLeg3.rotationPointZ = legPointZ;
	
	this.LeftLeg1.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * rotation * f1);
	this.LeftLeg1.rotationPointY = legPointY;
	this.LeftLeg1.rotationPointZ = legPointZ;
	this.LeftLeg2.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * rotation * f1);
	this.LeftLeg2.rotationPointY = legPointY;
	this.LeftLeg2.rotationPointZ = legPointZ;
	this.LeftLeg3.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * rotation * f1);
	this.LeftLeg3.rotationPointY = legPointY;
	this.LeftLeg3.rotationPointZ = legPointZ;
	
	this.RightFoot1.rotateAngleX = (MathHelper.cos(f * 0.6662F) * rotation * f1);
	this.RightFoot1.rotationPointY = legPointY;
	this.RightFoot1.rotationPointZ = legPointZ;
	this.RightFoot2.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * rotation * f1);
	this.RightFoot2.rotationPointY = legPointY;
	this.RightFoot2.rotationPointZ = legPointZ;
	
	this.LeftFoot1.rotateAngleX = (MathHelper.cos(f * 0.6662F + 3.1415927F) * rotation * f1);
	this.LeftFoot1.rotationPointY = legPointY;
	this.LeftFoot1.rotationPointZ = legPointZ;
	this.LeftFoot2.rotateAngleX = (MathHelper.cos(f * 0.6662F) * rotation * f1);
	this.LeftFoot2.rotationPointY = legPointY;
	this.LeftFoot2.rotationPointZ = legPointZ;
  }

}
