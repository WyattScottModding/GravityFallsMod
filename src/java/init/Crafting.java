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
		GameRegistry.addShapedRecipe(new ResourceLocation("flashlight"), new ResourceLocation("item"), new ItemStack(ItemInit.FLASHLIGHT, 1), new Object[]{" L ", "GBI", "III", 'I', Items.IRON_INGOT, 'G', Blocks.GLOWSTONE, 'L', Blocks.LEVER, 'B', ItemInit.BATTERY});
		GameRegistry.addShapedRecipe(new ResourceLocation("smiledip"), new ResourceLocation("item"), new ItemStack(ItemInit.SMILE_DIP, 1), new Object[]{"SPS", "SSS", "SMS", 'S', Items.SUGAR, 'M', Items.MELON, 'P', new ItemStack(Items.DYE, 1, EnumDyeColor.PINK.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("memorygun"), new ResourceLocation("magic"), new ItemStack(ItemInit.MEMORY_GUN, 1), new Object[]{" RU", "LRS", " BG", 'B', Blocks.STONE_BUTTON, 'S', ItemInit.BATTERY, 'U', BlockInit.URANIUM, 'R', new ItemStack(Blocks.STAINED_GLASS_PANE, 1, EnumDyeColor.RED.getDyeDamage()), 'G', Items.GOLD_INGOT, 'L', ItemInit.LIGHT_BULB});
		
		GameRegistry.addShapedRecipe(new ResourceLocation("grapplinghook"), new ResourceLocation("item"), new ItemStack(ItemInit.GRAPPLING_HOOK, 1), new Object[]{"I  ", "IPO", "I O", 'I', Items.IRON_INGOT, 'O', Blocks.OBSIDIAN, 'P', Blocks.STICKY_PISTON});
		GameRegistry.addShapedRecipe(new ResourceLocation("latex1"), new ResourceLocation("material"), new ItemStack(ItemInit.LATEX, 1), new Object[]{" F ", "FGF", " F ", 'F', Blocks.YELLOW_FLOWER, 'G', Items.GUNPOWDER});
		GameRegistry.addShapedRecipe(new ResourceLocation("latex2"), new ResourceLocation("material"), new ItemStack(ItemInit.LATEX, 1), new Object[]{" F ", "FGF", " F ", 'F', Blocks.RED_FLOWER, 'G', Items.GUNPOWDER});
		GameRegistry.addShapedRecipe(new ResourceLocation("uraniumtank"), new ResourceLocation("block"), new ItemStack(BlockInit.URANIUM_TANK, 1), new Object[]{"I I", "I I", "BBB", 'B', Blocks.IRON_BLOCK, 'I', Items.IRON_INGOT});
	
	    GameRegistry.addShapedRecipe(new ResourceLocation("powercord"), new ResourceLocation("block"), new ItemStack(BlockInit.POWER_CORD, 1), new Object[]{"III", "RRR", "III", 'R', Items.REDSTONE, 'I', ItemInit.RUBBER_ITEM});
		GameRegistry.addShapelessRecipe(new ResourceLocation("redwoodplanks"), new ResourceLocation("block"), new ItemStack(BlockInit.REDWOODPLANKS, 4), new Ingredient[]{Ingredient.fromStacks(new ItemStack(BlockInit.REDWOODLOGS))});
		GameRegistry.addShapedRecipe(new ResourceLocation("lightbulb"), new ResourceLocation("item"), new ItemStack(ItemInit.LIGHT_BULB, 1), new Object[]{"GGG", "GSG", "RIR", 'G', Blocks.GLASS_PANE, 'S', Blocks.GLOWSTONE, 'I', Items.IRON_INGOT, 'R', Items.REDSTONE});
		GameRegistry.addShapedRecipe(new ResourceLocation("uraniumfurnace"), new ResourceLocation("block"), new ItemStack(BlockInit.URANIUM_FURNACE, 1), new Object[]{"III", "RFR", "CCC", 'F', Blocks.FURNACE, 'C', Blocks.CONCRETE, 'I', Items.IRON_INGOT, 'R', Items.REDSTONE});
	
		GameRegistry.addShapedRecipe(new ResourceLocation("quantumdestabilizer"), new ResourceLocation("item"), new ItemStack(ItemInit.QUANTTUM_DESTABILIZER, 1), new Object[]{" GO", "DEO", " OO", 'E', BlockInit.HIDDEN_ELEMENT, 'G', Blocks.GLASS, 'O', Blocks.OBSIDIAN, 'D', Items.DIAMOND});
		GameRegistry.addShapedRecipe(new ResourceLocation("goldknuckles"), new ResourceLocation("item"), new ItemStack(ItemInit.GOLD_KNUCKLES, 1), new Object[]{"B B", "III", "   ", 'I', Items.GOLD_INGOT, 'B', Blocks.GOLD_BLOCK});
		GameRegistry.addShapedRecipe(new ResourceLocation("battery"), new ResourceLocation("item"), new ItemStack(ItemInit.BATTERY, 1), new Object[]{"CCC", "RRR", "III", 'R', Items.REDSTONE, 'I', Items.IRON_INGOT, 'C', ItemInit.COPPER_INGOT});
		GameRegistry.addShapedRecipe(new ResourceLocation("computer"), new ResourceLocation("block"), new ItemStack(BlockInit.COMPUTER_CLOSED, 1), new Object[]{"DGD", "RBR", "III", 'R', Items.REDSTONE, 'B', ItemInit.BATTERY, 'I', Blocks.IRON_BLOCK, 'G', Blocks.GLASS_PANE, 'D', Items.DIAMOND});

		GameRegistry.addShapedRecipe(new ResourceLocation("mysticamulet"), new ResourceLocation("magic"), new ItemStack(ItemInit.MYSTIC_AMULET, 1), new Object[]{"WGW", "GDG", "WGW", 'W', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.BLACK.getDyeDamage()), 'G', Blocks.GOLD_BLOCK, 'D', Blocks.DIAMOND_BLOCK});
		GameRegistry.addShapedRecipe(new ResourceLocation("leafblower"), new ResourceLocation("item"), new ItemStack(ItemInit.LEAFBLOWER, 1), new Object[]{"SI ", "BII", "RR ", 'R', Items.REDSTONE, 'B', ItemInit.BATTERY, 'I', Items.IRON_INGOT, 'S', Blocks.STONE_BUTTON});
		GameRegistry.addShapedRecipe(new ResourceLocation("copper_block"), new ResourceLocation("block"), new ItemStack(BlockInit.COPPER_BLOCK, 1), new Object[]{"CCC", "CCC", "CCC", 'C', ItemInit.COPPER_INGOT});
		GameRegistry.addShapelessRecipe(new ResourceLocation("copper_ingot"), new ResourceLocation("item"), new ItemStack(ItemInit.COPPER_INGOT, 9), new Ingredient[]{Ingredient.fromStacks(new ItemStack(BlockInit.COPPER_BLOCK))});

		GameRegistry.addShapedRecipe(new ResourceLocation("golfcart"), new ResourceLocation("item"), new ItemStack(ItemInit.GOLF_CART, 1), new Object[]{"CCC", "LLL", "IBI", 'C',BlockInit.COPPER_BLOCK, 'B', ItemInit.BATTERY, 'I', Blocks.IRON_BLOCK, 'L', Items.LEATHER});
		GameRegistry.addShapedRecipe(new ResourceLocation("presidentkey1"), new ResourceLocation("item"), new ItemStack(ItemInit.PRESIDENT_KEY, 1), new Object[]{"GIG", "   ", "   ",'G', Items.GOLD_INGOT, 'I', Items.IRON_INGOT});
		GameRegistry.addShapedRecipe(new ResourceLocation("presidentkey2"), new ResourceLocation("item"), new ItemStack(ItemInit.PRESIDENT_KEY, 1), new Object[]{"   ", "GIG", "   ", 'G', Items.GOLD_INGOT, 'I', Items.IRON_INGOT});
		GameRegistry.addShapedRecipe(new ResourceLocation("presidentkey3"), new ResourceLocation("item"), new ItemStack(ItemInit.PRESIDENT_KEY, 1), new Object[]{"   ", "   ", "GIG", 'G', Items.GOLD_INGOT, 'I', Items.IRON_INGOT});

		GameRegistry.addShapedRecipe(new ResourceLocation("blacklight1"), new ResourceLocation("item"), new ItemStack(ItemInit.BLACK_LIGHT, 1), new Object[]{"OOO", "LLL", "   ", 'L', BlockInit.LIGHT_PURPLE, 'O', Blocks.OBSIDIAN});
		GameRegistry.addShapedRecipe(new ResourceLocation("blacklight2"), new ResourceLocation("item"), new ItemStack(ItemInit.BLACK_LIGHT, 1), new Object[]{"   ", "OOO", "LLL", 'L', BlockInit.LIGHT_PURPLE, 'O', Blocks.OBSIDIAN});

		
		//Time Wishes
		GameRegistry.addShapelessRecipe(new ResourceLocation("infinitypizza"), new ResourceLocation("magic"), new ItemStack(ItemInit.INFINITY_PIZZA, 1), new Ingredient[]{Ingredient.fromItem(ItemInit.TIME_WISH), Ingredient.fromItem(Items.BREAD)});
		GameRegistry.addShapelessRecipe(new ResourceLocation("diamondblocks"), new ResourceLocation("block"), new ItemStack(Blocks.DIAMOND_BLOCK, 5), new Ingredient[]{Ingredient.fromItem(ItemInit.TIME_WISH), Ingredient.fromStacks (new ItemStack(Blocks.DIRT))});
		GameRegistry.addShapelessRecipe(new ResourceLocation("firehelmet"), new ResourceLocation("magic"), new ItemStack(ItemInit.FIRE_HELMET, 1), new Ingredient[]{Ingredient.fromItem(ItemInit.TIME_WISH), Ingredient.fromItem(Items.MAGMA_CREAM)});
		GameRegistry.addShapelessRecipe(new ResourceLocation("strengthchestplate"), new ResourceLocation("magic"), new ItemStack(ItemInit.STRENGTH_CHESTPLATE, 1), new Ingredient[]{Ingredient.fromItem(ItemInit.TIME_WISH), Ingredient.fromItem(Items.COOKED_BEEF)});
		GameRegistry.addShapelessRecipe(new ResourceLocation("regenerationlegs"), new ResourceLocation("magic"), new ItemStack(ItemInit.REGENERATION_LEGS, 1), new Ingredient[]{Ingredient.fromItem(ItemInit.TIME_WISH), Ingredient.fromItem(Items.RABBIT_FOOT)});
		GameRegistry.addShapelessRecipe(new ResourceLocation("speedboots"), new ResourceLocation("magic"), new ItemStack(ItemInit.SPEED_BOOTS, 1), new Ingredient[]{Ingredient.fromItem(ItemInit.TIME_WISH), Ingredient.fromItem(Items.SUGAR)});

		
		//Armor Crafting
		GameRegistry.addShapedRecipe(new ResourceLocation("dipperhat1"), new ResourceLocation("dipper"), new ItemStack(ItemInit.PINE_HAT, 1), new Object[]{"BWB", "WBW", "   ", 'B', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.LIGHT_BLUE.getDyeDamage()), 'W', new ItemStack(Blocks.WOOL, 0, EnumDyeColor.WHITE.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("dipperhat2"), new ResourceLocation("dipper"), new ItemStack(ItemInit.PINE_HAT, 1), new Object[]{"   ", "BWB", "WBW", 'B', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.LIGHT_BLUE.getDyeDamage()), 'W', new ItemStack(Blocks.WOOL, 0, EnumDyeColor.WHITE.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("dippervest"), new ResourceLocation("dipper"), new ItemStack(ItemInit.PINE_SHIRT, 1), new Object[]{"B B", "BOB", "BOB", 'B', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.BLUE.getDyeDamage()), 'O', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.ORANGE.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("dipperpants"), new ResourceLocation("dipper"), new ItemStack(ItemInit.PINE_PANTS, 1), new Object[]{"GGG", "G G", "G G", 'G', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.GRAY.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("dippershoes1"), new ResourceLocation("dipper"), new ItemStack(ItemInit.PINE_SHOES, 1), new Object[]{"   ", "B B", "W W", 'B', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.BLACK.getDyeDamage()), 'W', new ItemStack(Blocks.WOOL, 0, EnumDyeColor.WHITE.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("dippershoes2"), new ResourceLocation("dipper"), new ItemStack(ItemInit.PINE_SHOES, 1), new Object[]{"B B", "W W", "   ", 'B', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.BLACK.getDyeDamage()), 'W', new ItemStack(Blocks.WOOL, 0, EnumDyeColor.WHITE.getDyeDamage())});
		
		GameRegistry.addShapedRecipe(new ResourceLocation("mabelbandana1"), new ResourceLocation("mabel"), new ItemStack(ItemInit.MABEL_BANDANA, 1), new Object[]{"MMM", "M M", "   ", 'M', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.MAGENTA.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("mabelbandana2"), new ResourceLocation("mabel"), new ItemStack(ItemInit.MABEL_BANDANA, 1), new Object[]{"   ", "MMM", "M M", 'M', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.MAGENTA.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("mabelsweater"), new ResourceLocation("mabel"), new ItemStack(ItemInit.MABEL_SWEATER, 1), new Object[]{"M M", "MGM", "MMM", 'M', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.MAGENTA.getDyeDamage()), 'G', new ItemStack(Blocks.WOOL, 4, EnumDyeColor.YELLOW.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("mabelpants"), new ResourceLocation("mabel"), new ItemStack(ItemInit.MABEL_PANTS, 1), new Object[]{"PPP", "P P", "P P", 'P', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.PURPLE.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("mabelshoes1"), new ResourceLocation("mabel"), new ItemStack(ItemInit.MABEL_SHOES, 1), new Object[]{"   ", "W W", "B B", 'B', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.BLACK.getDyeDamage()), 'W', new ItemStack(Blocks.WOOL, 0, EnumDyeColor.WHITE.getDyeDamage())});
		GameRegistry.addShapedRecipe(new ResourceLocation("mabelshoes2"), new ResourceLocation("mabel"), new ItemStack(ItemInit.MABEL_SHOES, 1), new Object[]{"W W", "B B", "   ", 'B', new ItemStack(Blocks.WOOL, 1, EnumDyeColor.BLACK.getDyeDamage()), 'W', new ItemStack(Blocks.WOOL, 0, EnumDyeColor.WHITE.getDyeDamage())});

		
		
		GameRegistry.addShapedRecipe(new ResourceLocation("rubberhelmet1"), new ResourceLocation("rubber"), new ItemStack(ItemInit.RUBBER_HAT, 1), new Object[]{"RRR", "RGR", "   ", 'R', ItemInit.RUBBER_ITEM, 'G', Blocks.GLASS});
		GameRegistry.addShapedRecipe(new ResourceLocation("rubberhelmet2"), new ResourceLocation("rubber"), new ItemStack(ItemInit.RUBBER_HAT, 1), new Object[]{"   ", "RRR", "RGR", 'R', ItemInit.RUBBER_ITEM, 'G', Blocks.GLASS});
		GameRegistry.addShapedRecipe(new ResourceLocation("rubberchestplate"), new ResourceLocation("rubber"), new ItemStack(ItemInit.RUBBER_CHESTPLATE, 1), new Object[]{"R R", "RRR", "RRR", 'R', ItemInit.RUBBER_ITEM});
		GameRegistry.addShapedRecipe(new ResourceLocation("rubberleggings"), new ResourceLocation("rubber"), new ItemStack(ItemInit.RUBBER_LEGGINGS, 1), new Object[]{"RRR", "R R", "R R", 'R', ItemInit.RUBBER_ITEM});
		GameRegistry.addShapedRecipe(new ResourceLocation("rubberboots1"), new ResourceLocation("rubber"), new ItemStack(ItemInit.RUBBER_BOOTS, 1), new Object[]{"   ", "R R", "R R", 'R', ItemInit.RUBBER_ITEM});
		GameRegistry.addShapedRecipe(new ResourceLocation("rubberboots2"), new ResourceLocation("rubber"), new ItemStack(ItemInit.RUBBER_BOOTS, 1), new Object[]{"R R", "R R", "   ", 'R', ItemInit.RUBBER_ITEM});
	
		//RedWood Crafting
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodpressureplate"), new ResourceLocation("block"), new ItemStack(Blocks.WOODEN_PRESSURE_PLATE, 1), new Object[]{"RR ", "   ", "   ", 'R', BlockInit.REDWOODPLANKS});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodcraftingtable"), new ResourceLocation("block"), new ItemStack(Blocks.CRAFTING_TABLE, 1), new Object[]{"RR ", "RR ", "   ", 'R', BlockInit.REDWOODPLANKS});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodstick"), new ResourceLocation("item"), new ItemStack(Items.STICK, 4), new Object[]{"   ", "R  ", "R  ", 'R', BlockInit.REDWOODPLANKS});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodstick2"), new ResourceLocation("item"), new ItemStack(Items.STICK, 4), new Object[]{"   ", " R ", " R ", 'R', BlockInit.REDWOODPLANKS});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodstick3"), new ResourceLocation("item"), new ItemStack(Items.STICK, 4), new Object[]{"   ", "  R", "  R", 'R', BlockInit.REDWOODPLANKS});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodstick4"), new ResourceLocation("item"), new ItemStack(Items.STICK, 4), new Object[]{"R  ", "R  ", "   ", 'R', BlockInit.REDWOODPLANKS});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodstick5"), new ResourceLocation("item"), new ItemStack(Items.STICK, 4), new Object[]{" R ", " R ", "   ", 'R', BlockInit.REDWOODPLANKS});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodstick6"), new ResourceLocation("item"), new ItemStack(Items.STICK, 4), new Object[]{"  R", "  R", "   ", 'R', BlockInit.REDWOODPLANKS});
		GameRegistry.addShapelessRecipe(new ResourceLocation("redwoodbutton"), new ResourceLocation("block"), new ItemStack(Blocks.WOODEN_BUTTON, 1), new Ingredient[]{Ingredient.fromStacks (new ItemStack(BlockInit.REDWOODPLANKS))});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodslab"), new ResourceLocation("block"), new ItemStack(BlockInit.REDWOOD_SLAB_HALF, 6), new Object[]{"   ", "   ", "RRR", 'R', BlockInit.REDWOODPLANKS});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodpressureplate"), new ResourceLocation("block"), new ItemStack(Blocks.WOODEN_PRESSURE_PLATE, 1), new Object[]{"   ", "   ", "RR ", 'R', BlockInit.REDWOODPLANKS});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodslab2"), new ResourceLocation("block"), new ItemStack(BlockInit.REDWOOD_SLAB_HALF, 6), new Object[]{"   ", "RRR", "   ", 'R', BlockInit.REDWOODPLANKS});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodpressureplate2"), new ResourceLocation("block"), new ItemStack(Blocks.WOODEN_PRESSURE_PLATE, 1), new Object[]{"   ", " RR", "   ", 'R', BlockInit.REDWOODPLANKS});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodslab3"), new ResourceLocation("block"), new ItemStack(BlockInit.REDWOOD_SLAB_HALF, 6), new Object[]{"RRR", "   ", "   ", 'R', BlockInit.REDWOODPLANKS});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodpressureplate3"), new ResourceLocation("block"), new ItemStack(Blocks.WOODEN_PRESSURE_PLATE, 1), new Object[]{"RR ", "   ", "   ", 'R', BlockInit.REDWOODPLANKS});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodstairs"), new ResourceLocation("block"), new ItemStack(BlockInit.REDWOOD_STAIRS, 4), new Object[]{"R  ", "RR ", "RRR", 'R', BlockInit.REDWOODPLANKS});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodchest"), new ResourceLocation("block"), new ItemStack(Blocks.CHEST, 1), new Object[]{"RRR", "R R", "RRR", 'R', BlockInit.REDWOODPLANKS});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodpiston"), new ResourceLocation("block"), new ItemStack(Blocks.PISTON, 1), new Object[]{"WWW", "SIS", "SRS", 'W', BlockInit.REDWOODPLANKS, 'I', Items.IRON_INGOT, 'R', Items.REDSTONE, 'S', Blocks.COBBLESTONE});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodjukebox"), new ResourceLocation("block"), new ItemStack(Blocks.JUKEBOX, 1), new Object[]{"WWW", "WDW", "WWW", 'W', BlockInit.REDWOODPLANKS, 'D', Items.DIAMOND});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodbookshelf"), new ResourceLocation("block"), new ItemStack(Blocks.BOOKSHELF, 1), new Object[]{"WWW", "BBB", "WWW", 'W', BlockInit.REDWOODPLANKS, 'B', Items.BOOK});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodbed"), new ResourceLocation("block"), new ItemStack(Blocks.BED, 1), new Object[]{"BBB", "WWW", "   ", 'W', BlockInit.REDWOODPLANKS, 'B', Blocks.WOOL});
		GameRegistry.addShapedRecipe(new ResourceLocation("redwoodbed2"), new ResourceLocation("block"), new ItemStack(Blocks.BED, 1), new Object[]{"   ", "BBB", "WWW", 'W', BlockInit.REDWOODPLANKS, 'B', Blocks.WOOL});

		
		//Lights
		GameRegistry.addShapelessRecipe(new ResourceLocation("lightblack"), new ResourceLocation("block"), new ItemStack(BlockInit.LIGHT_BLACK, 1), new Ingredient[]{Ingredient.fromStacks (new ItemStack(Blocks.STAINED_GLASS, 1, 15)), Ingredient.fromStacks(new ItemStack(Blocks.GLOWSTONE, 1))});
		GameRegistry.addShapelessRecipe(new ResourceLocation("lightblue"), new ResourceLocation("block"), new ItemStack(BlockInit.LIGHT_BLUE, 1), new Ingredient[]{Ingredient.fromStacks (new ItemStack(Blocks.STAINED_GLASS, 1, 11)), Ingredient.fromStacks(new ItemStack(Blocks.GLOWSTONE, 1))});
		GameRegistry.addShapelessRecipe(new ResourceLocation("lightbrown"), new ResourceLocation("block"), new ItemStack(BlockInit.LIGHT_BROWN, 1), new Ingredient[]{Ingredient.fromStacks (new ItemStack(Blocks.STAINED_GLASS, 1, 12)), Ingredient.fromStacks(new ItemStack(Blocks.GLOWSTONE, 1))});
		GameRegistry.addShapelessRecipe(new ResourceLocation("lightcyan"), new ResourceLocation("block"), new ItemStack(BlockInit.LIGHT_CYAN, 1), new Ingredient[]{Ingredient.fromStacks (new ItemStack(Blocks.STAINED_GLASS, 1, 9)), Ingredient.fromStacks(new ItemStack(Blocks.GLOWSTONE, 1))});
		GameRegistry.addShapelessRecipe(new ResourceLocation("lightgray"), new ResourceLocation("block"), new ItemStack(BlockInit.LIGHT_GRAY, 1), new Ingredient[]{Ingredient.fromStacks (new ItemStack(Blocks.STAINED_GLASS, 1, 7)), Ingredient.fromStacks(new ItemStack(Blocks.GLOWSTONE, 1))});
		GameRegistry.addShapelessRecipe(new ResourceLocation("lightgreen"), new ResourceLocation("block"), new ItemStack(BlockInit.LIGHT_GREEN, 1), new Ingredient[]{Ingredient.fromStacks (new ItemStack(Blocks.STAINED_GLASS, 1, 13)), Ingredient.fromStacks(new ItemStack(Blocks.GLOWSTONE, 1))});
		GameRegistry.addShapelessRecipe(new ResourceLocation("lightlightblue"), new ResourceLocation("block"), new ItemStack(BlockInit.LIGHT_LIGHT_BLUE, 1), new Ingredient[]{Ingredient.fromStacks (new ItemStack(Blocks.STAINED_GLASS, 1, 3)), Ingredient.fromStacks(new ItemStack(Blocks.GLOWSTONE, 1))});
		GameRegistry.addShapelessRecipe(new ResourceLocation("lightlightgray"), new ResourceLocation("block"), new ItemStack(BlockInit.LIGHT_LIGHT_GRAY, 1), new Ingredient[]{Ingredient.fromStacks (new ItemStack(Blocks.STAINED_GLASS, 1, 8)), Ingredient.fromStacks(new ItemStack(Blocks.GLOWSTONE, 1))});
		GameRegistry.addShapelessRecipe(new ResourceLocation("lightlime"), new ResourceLocation("block"), new ItemStack(BlockInit.LIGHT_LIME, 1), new Ingredient[]{Ingredient.fromStacks (new ItemStack(Blocks.STAINED_GLASS, 1, 5)), Ingredient.fromStacks(new ItemStack(Blocks.GLOWSTONE, 1))});
		GameRegistry.addShapelessRecipe(new ResourceLocation("lightmagenta"), new ResourceLocation("block"), new ItemStack(BlockInit.LIGHT_MAGENTA, 1), new Ingredient[]{Ingredient.fromStacks (new ItemStack(Blocks.STAINED_GLASS, 1, 2)), Ingredient.fromStacks(new ItemStack(Blocks.GLOWSTONE, 1))});
		GameRegistry.addShapelessRecipe(new ResourceLocation("lightorange"), new ResourceLocation("block"), new ItemStack(BlockInit.LIGHT_ORANGE, 1), new Ingredient[]{Ingredient.fromStacks (new ItemStack(Blocks.STAINED_GLASS, 1, 1)), Ingredient.fromStacks(new ItemStack(Blocks.GLOWSTONE, 1))});
		GameRegistry.addShapelessRecipe(new ResourceLocation("lightpink"), new ResourceLocation("block"), new ItemStack(BlockInit.LIGHT_PINK, 1), new Ingredient[]{Ingredient.fromStacks (new ItemStack(Blocks.STAINED_GLASS, 1, 6)), Ingredient.fromStacks(new ItemStack(Blocks.GLOWSTONE, 1))});
		GameRegistry.addShapelessRecipe(new ResourceLocation("lightpurple"), new ResourceLocation("block"), new ItemStack(BlockInit.LIGHT_PURPLE, 1), new Ingredient[]{Ingredient.fromStacks (new ItemStack(Blocks.STAINED_GLASS, 1, 10)), Ingredient.fromStacks(new ItemStack(Blocks.GLOWSTONE, 1))});
		GameRegistry.addShapelessRecipe(new ResourceLocation("lightred"), new ResourceLocation("block"), new ItemStack(BlockInit.LIGHT_RED, 1), new Ingredient[]{Ingredient.fromStacks (new ItemStack(Blocks.STAINED_GLASS, 1, 14)), Ingredient.fromStacks(new ItemStack(Blocks.GLOWSTONE, 1))});
		GameRegistry.addShapelessRecipe(new ResourceLocation("lightwhite"), new ResourceLocation("block"), new ItemStack(BlockInit.LIGHT_WHITE, 1), new Ingredient[]{Ingredient.fromStacks (new ItemStack(Blocks.STAINED_GLASS)), Ingredient.fromStacks(new ItemStack(Blocks.GLOWSTONE, 1))});
		GameRegistry.addShapelessRecipe(new ResourceLocation("lightyellow"), new ResourceLocation("block"), new ItemStack(BlockInit.LIGHT_YELLOW, 1), new Ingredient[]{Ingredient.fromStacks (new ItemStack(Blocks.STAINED_GLASS, 1, 4)), Ingredient.fromStacks(new ItemStack(Blocks.GLOWSTONE, 1))});

		
		
		//Furnace
		GameRegistry.addSmelting(ItemInit.LATEX, new ItemStack(ItemInit.RUBBER_ITEM), 10);
		GameRegistry.addSmelting(BlockInit.COPPER, new ItemStack(ItemInit.COPPER_INGOT), 15);

	}
}
