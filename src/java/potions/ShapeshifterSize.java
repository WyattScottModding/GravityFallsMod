package potions;

import java.util.List;

import init.AttributeInit;
import init.ItemInit;
import main.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class ShapeshifterSize extends Potion {

	public ShapeshifterSize(String name, boolean isBadEffectIn, int liquidColorIn, int iconIndexX, int iconIndexY) {
		super(isBadEffectIn, liquidColorIn);
		this.setPotionName("effect." + name);

		this.registerPotionAttributeModifier(AttributeInit.SHAPESHIFTER_HEIGHT, MathHelper.getRandomUUID().toString(), 0.01D, 1);
		this.registerPotionAttributeModifier(AttributeInit.SHAPESHIFTER_WIDTH, MathHelper.getRandomUUID().toString(), 0.01D, 1);

		this.setRegistryName(new ResourceLocation(Reference.MODID + ":" + name));
	}

	@Override
	public boolean hasStatusIcon() 
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID + ":textures/gui/potion_effects.png"));
		return true;
	}
	
	@Override
	public List<ItemStack> getCurativeItems() {
        java.util.ArrayList<net.minecraft.item.ItemStack> ret = new java.util.ArrayList<net.minecraft.item.ItemStack>();
		return ret;
	}
}