package models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelFlashlight
  extends ModelBase
{
  public ModelRenderer handle;
  public ModelRenderer light;
  public ModelRenderer button;
  
  public ModelFlashlight()
  {
    this.textureWidth = 32;
    this.textureHeight = 16;
    this.light = new ModelRenderer(this, 0, 0);
    this.light.setRotationPoint(0.0F, 0.0F, -5.0F);
    this.light.addBox(-1.5F, -1.5F, -3.0F, 3, 3, 3, 0.0F);
    this.button = new ModelRenderer(this, 0, 6);
    this.button.setRotationPoint(0.0F, -0.5F, -2.5F);
    this.button.addBox(-1.0F, -1.0F, -1.5F, 2, 1, 3, 0.0F);
    this.handle = new ModelRenderer(this, 2, 1);
    this.handle.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.handle.addBox(-1.0F, -1.0F, -5.0F, 2, 2, 10, 0.0F);
    this.handle.addChild(this.light);
    this.handle.addChild(this.button);
  }
  
  public void render()
  {
    this.handle.postRender(0.0625F);
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
  {
    modelRenderer.rotationPointX = x;
    modelRenderer.rotateAngleY = y;
    modelRenderer.rotateAngleZ = z;
  }
}