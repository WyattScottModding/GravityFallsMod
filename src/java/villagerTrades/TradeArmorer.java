package villagerTrades;

import java.util.Random;

import init.ItemInit;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class TradeArmorer implements ITradeList
{
	@Override
	public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random)
	{
	      recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, 5),new ItemStack(ItemInit.CLOAK_OF_INVISIBILITY, 1)));		
	}
}