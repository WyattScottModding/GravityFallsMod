package init;

import java.util.ArrayList;
import java.util.List;

import blocks.Asteroid;
import blocks.BlockTeleporter;
import blocks.Book_1;
import blocks.Book_2;
import blocks.Book_3;
import blocks.ColoredLights;
import blocks.ComputerClosed;
import blocks.ComputerOpen;
import blocks.Copper;
import blocks.CopperBlock;
import blocks.CryogenicTubeOff;
import blocks.CryogenicTubeOn;
import blocks.Crystal;
import blocks.HiddenElement;
import blocks.HyperDrive;
import blocks.MetalTree;
import blocks.OutHouse;
import blocks.PowerCord;
import blocks.RedWoodLeaves;
import blocks.RedWoodLogs;
import blocks.RedWoodPlanks;
import blocks.RedWoodSapling;
import blocks.RedWoodStairs;
import blocks.Uranium;
import blocks.UraniumFurnace;
import blocks.UraniumTank;
import blocks.UraniumTankFilled;
import blocks.UraniumTankHalfFilled;
import blocks.portalcontrol.PortalControl;
import blocks.portalcontrol.PortalControlAllBooks;
import blocks.portalcontrol.PortalControlBook1;
import blocks.portalcontrol.PortalControlBook1Book2;
import blocks.portalcontrol.PortalControlBook1Book3;
import blocks.portalcontrol.PortalControlBook2;
import blocks.portalcontrol.PortalControlBook2Book3;
import blocks.portalcontrol.PortalControlBook3;
import blocks.slabs.RedWoodDoubleSlab;
import blocks.slabs.RedWoodHalfSlab;
import main.GravityFalls;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BlockInit 
{

	public static final List<Block> BLOCKS = new ArrayList<Block>();

	//public static final Block LIGHT_SOURCE = new LightSource("lightsource");

	public static final Block CRYSTAL = new Crystal("crystal", Material.ROCK);

	public static final Block URANIUM = new Uranium("uranium");

	public static final Block URANIUM_TANK = new UraniumTank("uraniumtank", Material.ROCK);

	public static final Block URANIUM_TANK_HALFFILLED = new UraniumTankHalfFilled("uraniumtankhalffilled", Material.ROCK);

	public static final Block URANIUM_TANK_FILLED = new UraniumTankFilled("uraniumtankfilled", Material.ROCK);

	public static final Block POWER_CORD = new PowerCord("powercord");

	public static final Block URANIUM_FURNACE = new UraniumFurnace("uranium_furnace");

	public static final Block HYPER_DRIVE = new HyperDrive("hyperdrive", Material.IRON);

	public static final Block CRYOGENIC_TUBE_ON = new CryogenicTubeOn("cryogenictube_on", Material.IRON);

	public static final Block CRYOGENIC_TUBE_OFF = new CryogenicTubeOff("cryogenictube_off", Material.IRON);

	public static final Block METAL_TREE = new MetalTree("metaltree", Material.BARRIER);

	public static final Block HIDDEN_ELEMENT = new HiddenElement("hiddenelement", Material.ROCK);

	public static final Block ASTEROID = new Asteroid("asteroid", Material.ROCK);
	
	public static final Block COPPER = new Copper("copper_ore");
	
	public static final Block COMPUTER_CLOSED = new ComputerClosed("computer", Material.CLOTH);

	public static final Block COMPUTER_OPEN = new ComputerOpen("computer_open", Material.CLOTH);

	public static final Block BLOCKTELEPORTER = new BlockTeleporter("block_teleporter");
	
	public static final Block OUTHOUSE = new OutHouse("out_house", Material.WOOD);
	
	public static final Block Book1 = new Book_1("book_1", Material.CLOTH);
	
	public static final Block Book2 = new Book_2("book_2", Material.CLOTH);
	
	public static final Block Book3 = new Book_3("book_3", Material.CLOTH);
	
	public static final Block COPPER_BLOCK = new CopperBlock("copper_block");


	//PortalControls
	public static final Block PORTAL_CONTROL = new PortalControl("portalcontrol", Material.ANVIL);
	public static final Block PORTAL_CONTROLBOOK1 = new PortalControlBook1("portalcontrolbook1", Material.ANVIL);
	public static final Block PORTAL_CONTROLBOOK2 = new PortalControlBook2("portalcontrolbook2", Material.ANVIL);
	public static final Block PORTAL_CONTROLBOOK3 = new PortalControlBook3("portalcontrolbook3", Material.ANVIL);
	public static final Block PORTAL_CONTROLBOOK1BOOK2 = new PortalControlBook1Book2("portalcontrolbook1book2", Material.ANVIL);
	public static final Block PORTAL_CONTROLBOOK2BOOK3 = new PortalControlBook2Book3("portalcontrolbook2book3", Material.ANVIL);
	public static final Block PORTAL_CONTROLBOOK1BOOK3 = new PortalControlBook1Book3("portalcontrolbook1book3", Material.ANVIL);
	public static final Block PORTAL_CONTROLALLBOOKS = new PortalControlAllBooks("portalcontrolallbooks", Material.ANVIL);

	
	//Lights
	public static final Block LIGHT_BLACK = new ColoredLights("lightblack");
	public static final Block LIGHT_BLUE = new ColoredLights("lightblue");
	public static final Block LIGHT_BROWN = new ColoredLights("lightbrown");
	public static final Block LIGHT_CYAN = new ColoredLights("lightcyan");
	public static final Block LIGHT_GRAY = new ColoredLights("lightgray");
	public static final Block LIGHT_GREEN = new ColoredLights("lightgreen");
	public static final Block LIGHT_LIGHT_GRAY = new ColoredLights("lightlightgray");
	public static final Block LIGHT_LIGHT_BLUE = new ColoredLights("lightlightblue");
	public static final Block LIGHT_LIME = new ColoredLights("lightlime");
	public static final Block LIGHT_MAGENTA = new ColoredLights("lightmagenta");
	public static final Block LIGHT_ORANGE = new ColoredLights("lightorange");
	public static final Block LIGHT_PINK = new ColoredLights("lightpink");
	public static final Block LIGHT_PURPLE = new ColoredLights("lightpurple");
	public static final Block LIGHT_RED = new ColoredLights("lightred");
	public static final Block LIGHT_WHITE = new ColoredLights("lightwhite");
	public static final Block LIGHT_YELLOW = new ColoredLights("lightyellow");
	

	//Trees
	public static final Block REDWOODPLANKS = new RedWoodPlanks("redwoodplanks", Material.WOOD);
	public static final Block REDWOODLOGS = new RedWoodLogs("redwoodlogs", Material.WOOD);
	public static final Block REDWOODLEAVES = new RedWoodLeaves("redwoodleaves", Material.PLANTS);
	public static final Block REDWOODSAPLING = new RedWoodSapling("redwoodsapling");

	public static final Block REDWOOD_STAIRS = new RedWoodStairs("redwood_stairs", REDWOODPLANKS.getDefaultState());
	public static final RedWoodHalfSlab REDWOOD_SLAB_HALF = new RedWoodHalfSlab("redwood_slab_half", Material.WOOD);
	public static final RedWoodDoubleSlab REDWOOD_SLAB_DOUBLE = new RedWoodDoubleSlab("redwood_slab_double", Material.WOOD);
	
	public static void register()
	{
		registerBlock(REDWOOD_SLAB_HALF, new ItemSlab(REDWOODPLANKS, REDWOOD_SLAB_HALF, REDWOOD_SLAB_DOUBLE));
		ForgeRegistries.BLOCKS.register(REDWOOD_SLAB_DOUBLE);

	}

	public static void registerBlock(Block block, ItemSlab itemBlock)
	{
		ForgeRegistries.BLOCKS.register(block);
		block.setCreativeTab(GravityFalls.gravityfallsblocks);
		itemBlock.setRegistryName(block.getRegistryName());
		ForgeRegistries.ITEMS.register(itemBlock);

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));

	}
}