package potions;

import java.util.List;

import main.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class Freeze extends Potion
{

	public Freeze(String name, boolean isBadEffectIn, int liquidColorIn, int iconIndexX, int iconIndexY) 
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
	
	
	@Override
	public List<ItemStack> getCurativeItems()
	{
        java.util.ArrayList<net.minecraft.item.ItemStack> ret = new java.util.ArrayList<net.minecraft.item.ItemStack>();
        ret.add(new net.minecraft.item.ItemStack(net.minecraft.init.Items.MILK_BUCKET));
        return ret;
	}

}
