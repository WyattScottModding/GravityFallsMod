package potions;

import main.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class Radiation extends Potion
{

	public Radiation(String name, boolean isBadEffectIn, int liquidColorIn, int iconIndexX, int iconIndexY) 
	{
		super(isBadEffectIn, liquidColorIn);
		this.setPotionName("effect." + name);
		this.setIconIndex(iconIndexX, iconIndexY);
		this.setRegistryName(new ResourceLocation(Reference.MODID + ":" + name));
	}
	
	@Override
	public boolean hasStatusIcon() 
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID + ":textures/gui/potion_effects.png"));
		return true;
	}
}