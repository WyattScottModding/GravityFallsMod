package init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Crafting {

	public static void register()
	{
		//Crafting
		GameRegistry.addShapelessRecipe(new ResourceLocation("magicflashlight"), new ResourceLocation("magic"), new ItemStack(ItemInit.MAGICFLASHLIGHT, 1), new Ingredient[]{Ingredient.fromItem(ItemInit.CRYSTALSHARD), Ingredient.fromItem(ItemInit.FLASHLIGHT)});
		GameRegistry.addShapedRecipe(new ResourceLocation("flashlight"), new ResourceLocation("item"), new ItemStack(ItemInit.FLASHLIGHT, 1), new Object[]{" L ", "GII", "III", 'I', Items.IRON_INGOT, 'G', Blocks.GLOWSTONE, 'L', Blocks.LEVER});
		GameRegistry.addShapedRecipe(new ResourceLocation("smiledip"), new ResourceLocation("item"), new ItemStack(ItemInit.SMILE_DIP, 1), new Object[]{"SPS", "SSS", "SMS", 'S', Items.SUGAR, 'M', Items.MELON, 'P', new ItemStack(Items.DYE, 1, EnumDyeColor.PINK.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("memorygun"), new ResourceLocation("magic"), new ItemStack(ItemInit.MEMORY_GUN, 1), new Object[]{"R U", "RSG", " BG", 'B', Blocks.STONE_BUTTON, 'S', Items.REDSTONE, 'U', BlockInit.URANIUM, 'R', new ItemStack(Blocks.STAINED_GLASS_PANE, 1, EnumDyeColor.RED.getDyeDamage()), 'G', Items.GOLD_INGOT});

		GameRegistry.addShapedRecipe(new ResourceLocation("memorygun"), new ResourceLocation("magic"), new ItemStack(ItemInit.MEMORY_GUN, 1), new Object[]{"R U", "RSG", " BG", 'B', Blocks.STONE_BUTTON, 'S', Items.REDSTONE, 'U', BlockInit.URANIUM, 'R', new ItemStack(Blocks.STAINED_GLASS_PANE, 1, EnumDyeColor.RED.getDyeDamage()), 'G', Items.GOLD_INGOT});
	//	GameRegistry.addShapedRecipe(new ResourceLocation("memorygun"), new ResourceLocation("magic"), new ItemStack(ItemInit.MEMORY_GUN, 1), new Object[]{"R U", "RPG", " BG", 'B', Blocks.STONE_BUTTON, 'P', BlockInit.POWER_CORD, 'U', BlockInit.URANIUM, 'R', new ItemStack(Blocks.STAINED_GLASS_PANE, 1, EnumDyeColor.RED.getDyeDamage()), 'G', Items.GOLD_INGOT});
		GameRegistry.addShapedRecipe(new ResourceLocation("grapplinghook"), new ResourceLocation("item"), new ItemStack(ItemInit.GRAPPLING_HOOK, 1), new Object[]{"I  ", "IPO", "I O", 'I', Items.IRON_INGOT, 'O', Blocks.OBSIDIAN, 'P', Blocks.PISTON});
	//	GameRegistry.addShapedRecipe(new ResourceLocation("blacklight"), new ResourceLocation("item"), new ItemStack(ItemInit.BLACK_LIGHT, 1), new Object[]{"PGO", "PGO", "PGO", 'G', Blocks.GLOWSTONE, 'O', Blocks.OBSIDIAN, 'P', new ItemStack(Blocks.STAINED_GLASS_PANE, 1, EnumDyeColor.PURPLE.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("latex1"), new ResourceLocation("material"), new ItemStack(ItemInit.LATEX, 1), new Object[]{" F ", "FGF", " F ", 'F', Blocks.YELLOW_FLOWER, 'G', Items.GUNPOWDER});
		GameRegistry.addShapedRecipe(new ResourceLocation("latex2"), new ResourceLocation("material"), new ItemStack(ItemInit.LATEX, 1), new Object[]{" F ", "FGF", " F ", 'F', Blocks.RED_FLOWER, 'G', Items.GUNPOWDER});
		GameRegistry.addShapedRecipe(new ResourceLocation("uraniumtank"), new ResourceLocation("block"), new ItemStack(BlockInit.URANIUM_TANK, 1), new Object[]{"I I", "I I", "BBB", 'B', Blocks.IRON_BLOCK, 'I', Items.IRON_INGOT});
	//	GameRegistry.addShapedRecipe(new ResourceLocation("powercord"), new ResourceLocation("block"), new ItemStack(BlockInit.POWER_CORD, 1), new Object[]{" I ", "IRI", " I ", 'R', Items.REDSTONE, 'I', ItemInit.RUBBER_ITEM});
		GameRegistry.addShapelessRecipe(new ResourceLocation("redwoodplanks"), new ResourceLocation("block"), new ItemStack(BlockInit.REDWOODPLANKS, 4), new Ingredient[]{Ingredient.fromStacks(new ItemStack(BlockInit.REDWOODLOGS))});
		GameRegistry.addShapedRecipe(new ResourceLocation("lightbulb"), new ResourceLocation("item"), new ItemStack(ItemInit.LIGHT_BULB, 1), new Object[]{"GGG", "GSG", "RIR", 'G', Blocks.GLASS_PANE, 'S', Blocks.GLOWSTONE, 'I', Items.IRON_INGOT, 'R', Items.REDSTONE});
		GameRegistry.addShapedRecipe(new ResourceLocation("uraniumfurnace"), new ResourceLocation("block"), new ItemStack(BlockInit.URANIUM_FURNACE, 1), new Object[]{"III", "RFR", "CCC", 'F', Blocks.FURNACE, 'C', Blocks.CONCRETE, 'I', Items.IRON_INGOT, 'R', Items.REDSTONE});
		
		//Time Wishes
		GameRegistry.addShapelessRecipe(new ResourceLocation("infinitypizza"), new ResourceLocation("magic"), new ItemStack(ItemInit.INFINITY_PIZZA, 1), new Ingredient[]{Ingredient.fromItem(ItemInit.TIME_WISH), Ingredient.fromItem(Items.BREAD)});
		GameRegistry.addShapelessRecipe(new ResourceLocation("diamondblocks"), new ResourceLocation("block"), new ItemStack(Blocks.DIAMOND_BLOCK, 5), new Ingredient[]{Ingredient.fromItem(ItemInit.TIME_WISH), Ingredient.fromStacks (new ItemStack(Blocks.DIRT))});
		GameRegistry.addShapelessRecipe(new ResourceLocation("firehelmet"), new ResourceLocation("magic"), new ItemStack(ItemInit.FIRE_HELMET, 1), new Ingredient[]{Ingredient.fromItem(ItemInit.TIME_WISH), Ingredient.fromItem(Items.MAGMA_CREAM)});
		GameRegistry.addShapelessRecipe(new ResourceLocation("strengthchestplate"), new ResourceLocation("magic"), new ItemStack(ItemInit.STRENGTH_CHESTPLATE, 1), new Ingredient[]{Ingredient.fromItem(ItemInit.TIME_WISH), Ingredient.fromItem(Items.COOKED_BEEF)});
		GameRegistry.addShapelessRecipe(new ResourceLocation("regenerationlegs"), new ResourceLocation("magic"), new ItemStack(ItemInit.REGENERATION_LEGS, 1), new Ingredient[]{Ingredient.fromItem(ItemInit.TIME_WISH), Ingredient.fromItem(Items.RABBIT_FOOT)});
		GameRegistry.addShapelessRecipe(new ResourceLocation("speedboots"), new ResourceLocation("magic"), new ItemStack(ItemInit.SPEED_BOOTS, 1), new Ingredient[]{Ingredient.fromItem(ItemInit.TIME_WISH), Ingredient.fromItem(Items.SUGAR)});

		
		//Armor Crafting
		GameRegistry.addShapedRecipe(new ResourceLocation("dipperhat1"), new ResourceLocation("dipper"), new ItemStack(ItemInit.PINE_HAT, 1), new Object[]{"BWB", "WBW", "   ", 'B', new ItemStack(Blocks.WOOL, 3, EnumDyeColor.LIGHT_BLUE.getDyeDamage()), 'W', new ItemStack(Blocks.WOOL, 0, EnumDyeColor.WHITE.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("dipperhat2"), new ResourceLocation("dipper"), new ItemStack(ItemInit.PINE_HAT, 1), new Object[]{"   ", "BWB", "WBW", 'B', new ItemStack(Blocks.WOOL, 3, EnumDyeColor.LIGHT_BLUE.getDyeDamage()), 'W', new ItemStack(Blocks.WOOL, 0, EnumDyeColor.WHITE.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("dippervest"), new ResourceLocation("dipper"), new ItemStack(ItemInit.PINE_SHIRT, 1), new Object[]{"B B", "BOB", "BOB", 'B', new ItemStack(Blocks.WOOL, 11, EnumDyeColor.BLUE.getDyeDamage()), 'O', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.ORANGE.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("dipperpants"), new ResourceLocation("dipper"), new ItemStack(ItemInit.PINE_PANTS, 1), new Object[]{"GGG", "G G", "G G", 'G', new ItemStack(Blocks.WOOL, 7, EnumDyeColor.GRAY.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("dippershoes1"), new ResourceLocation("dipper"), new ItemStack(ItemInit.PINE_SHOES, 1), new Object[]{"   ", "B B", "W W", 'B', new ItemStack(Blocks.WOOL, 15, EnumDyeColor.BLACK.getDyeDamage()), 'W', new ItemStack(Blocks.WOOL, 0, EnumDyeColor.WHITE.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("dippershoes2"), new ResourceLocation("dipper"), new ItemStack(ItemInit.PINE_SHOES, 1), new Object[]{"B B", "W W", "   ", 'B', new ItemStack(Blocks.WOOL, 15, EnumDyeColor.BLACK.getDyeDamage()), 'W', new ItemStack(Blocks.WOOL, 0, EnumDyeColor.WHITE.getDyeDamage())});
		
		GameRegistry.addShapedRecipe(new ResourceLocation("mabelbandana1"), new ResourceLocation("mabel"), new ItemStack(ItemInit.MABEL_BANDANA, 1), new Object[]{"MMM", "M M", "   ", 'M', new ItemStack(Blocks.WOOL, 2, EnumDyeColor.MAGENTA.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("mabelbandana2"), new ResourceLocation("mabel"), new ItemStack(ItemInit.MABEL_BANDANA, 1), new Object[]{"   ", "MMM", "M M", 'M', new ItemStack(Blocks.WOOL, 2, EnumDyeColor.MAGENTA.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("mabelsweater"), new ResourceLocation("mabel"), new ItemStack(ItemInit.MABEL_SWEATER, 1), new Object[]{"M M", "MGM", "MMM", 'M', new ItemStack(Blocks.WOOL, 2, EnumDyeColor.MAGENTA.getDyeDamage()), 'G', new ItemStack(Blocks.WOOL, 4, EnumDyeColor.YELLOW.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("mabelpants"), new ResourceLocation("mabel"), new ItemStack(ItemInit.MABEL_PANTS, 1), new Object[]{"PPP", "P P", "P P", 'P', new ItemStack(Blocks.WOOL, 10, EnumDyeColor.PURPLE.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("mabelshoes1"), new ResourceLocation("mabel"), new ItemStack(ItemInit.MABEL_SHOES, 1), new Object[]{"   ", "W W", "B B", 'B', new ItemStack(Blocks.WOOL, 15, EnumDyeColor.BLACK.getDyeDamage()), 'W', new ItemStack(Blocks.WOOL, 0, EnumDyeColor.WHITE.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("mabelshoes2"), new ResourceLocation("mabel"), new ItemStack(ItemInit.MABEL_SHOES, 1), new Object[]{"W W", "B B", "   ", 'B', new ItemStack(Blocks.WOOL, 15, EnumDyeColor.BLACK.getDyeDamage()), 'W', new ItemStack(Blocks.WOOL, 0, EnumDyeColor.WHITE.getDyeDamage())});

		
		
		GameRegistry.addShapedRecipe(new ResourceLocation("rubberhelmet1"), new ResourceLocation("rubber"), new ItemStack(ItemInit.RUBBER_HAT, 1), new Object[]{"RRR", "RGR", "   ", 'R', ItemInit.RUBBER_ITEM, 'G', Blocks.GLASS});
		GameRegistry.addShapedRecipe(new ResourceLocation("rubberhelmet2"), new ResourceLocation("rubber"), new ItemStack(ItemInit.RUBBER_HAT, 1), new Object[]{"   ", "RRR", "RGR", 'R', ItemInit.RUBBER_ITEM, 'G', Blocks.GLASS});
		GameRegistry.addShapedRecipe(new ResourceLocation("rubberchestplate"), new ResourceLocation("rubber"), new ItemStack(ItemInit.RUBBER_CHESTPLATE, 1), new Object[]{"R R", "RRR", "RRR", 'R', ItemInit.RUBBER_ITEM});
		GameRegistry.addShapedRecipe(new ResourceLocation("rubberleggings"), new ResourceLocation("rubber"), new ItemStack(ItemInit.RUBBER_LEGGINGS, 1), new Object[]{"RRR", "R R", "R R", 'R', ItemInit.RUBBER_ITEM});
		GameRegistry.addShapedRecipe(new ResourceLocation("rubberboots1"), new ResourceLocation("rubber"), new ItemStack(ItemInit.RUBBER_BOOTS, 1), new Object[]{"   ", "R R", "R R", 'R', ItemInit.RUBBER_ITEM});
		GameRegistry.addShapedRecipe(new ResourceLocation("rubberboots2"), new ResourceLocation("rubber"), new ItemStack(ItemInit.RUBBER_BOOTS, 1), new Object[]{"R R", "R R", "   ", 'R', ItemInit.RUBBER_ITEM});
	
		//Furnace
		GameRegistry.addSmelting(ItemInit.LATEX, new ItemStack(ItemInit.RUBBER_ITEM), 10);
	}
}
