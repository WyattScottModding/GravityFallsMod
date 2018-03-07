package init;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class UraniumFurnaceRecipes
{
	public static final UraniumFurnaceRecipes INSTANCE = new UraniumFurnaceRecipes();
	public final Table<ItemStack, ItemStack, ItemStack> smeltingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
	public final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

	public static UraniumFurnaceRecipes getInstance()
	{
		return INSTANCE;
	}

	private UraniumFurnaceRecipes()
	{
		addUraniumFurnaceRecipes(new ItemStack(BlockInit.URANIUM), new ItemStack(BlockInit.URANIUM), new ItemStack(BlockInit.URANIUM), new ItemStack(ItemInit.URANIUM_BUCKET), 20.0F);
	}

	public void addUraniumFurnaceRecipes(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack result, float experience)
	{
		if(getUraniumFurnaceResult(input1, input2, input3) != ItemStack.EMPTY)
		{
			return;
		}
		this.smeltingList.put(input1, input2, result);
		this.experienceList.put(result, Float.valueOf(experience));
	}

	public ItemStack getUraniumFurnaceResult(ItemStack input1, ItemStack input2, ItemStack input3)
	{
		for(Entry<ItemStack, Map<ItemStack, ItemStack>>  entry : this.smeltingList.columnMap().entrySet())
		{
			if(this.compareItemStack(input1, input2, (ItemStack)entry.getKey()))
			{
				for(Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet())
				{
					if(this.compareItemStack(input1, (ItemStack)entry.getKey(), input3))
					{
						return new ItemStack(ItemInit.URANIUM_BUCKET);
					}
				}
			}
		}

		return ItemStack.EMPTY;
	}

	private boolean compareItemStack(ItemStack stack1, ItemStack stack2, ItemStack stack3)
	{
		return stack2.getItem() == stack1.getItem() && stack3.getItem() == stack2.getItem() && (stack2.getMetadata() == 32767 || (stack2.getMetadata() == stack1.getMetadata() && stack3.getMetadata() == stack2.getMetadata()));
	}
	
	public Table<ItemStack, ItemStack, ItemStack> getDualSmeltingList()
	{
		return this.smeltingList;
	}
	
	public float getUraniumFurnaceExperience(ItemStack stack)
	{
		for(Entry<ItemStack, Float> entry : this.experienceList.entrySet())
		{
			if(this.compareItemStack(stack, (ItemStack)entry.getKey(), stack))
			{
				return ((Float)entry.getValue()).floatValue();
			}
		}
		return 0.0F;
	}
}


