package models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelLaser extends ModelBase
{
    ModelRenderer Laser;
  
  public ModelLaser()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      Laser = new ModelRenderer(this, 0, 0);
      Laser.addBox(0F, 0F, 0F, 1, 1, 2);
      Laser.setRotationPoint(0F, 0F, 0F);
      Laser.setTextureSize(64, 64);
      Laser.mirror = true;
  }
}