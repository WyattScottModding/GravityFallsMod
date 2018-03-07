package init;

import java.util.ArrayList;
import java.util.List;

import blocks.CryogenicTubeOff;
import blocks.CryogenicTubeOn;
import blocks.Crystal;
import blocks.LightSource;
import blocks.MetalTree;
import blocks.RedWoodLeaves;
import blocks.RedWoodLogs;
import blocks.RedWoodPlanks;
import blocks.RedWoodSapling;
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
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInit {

	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	//public static final Block LIGHT_SOURCE = new LightSource("lightsource");
		
	public static final Block CRYSTAL = new Crystal("crystal", Material.ROCK);
	
	public static final Block URANIUM = new Uranium("uranium");
	
	public static final Block URANIUM_TANK = new UraniumTank("uraniumtank", Material.ROCK);
	
	public static final Block URANIUM_TANK_HALFFILLED = new UraniumTankHalfFilled("uraniumtankhalffilled", Material.ROCK);

	public static final Block URANIUM_TANK_FILLED = new UraniumTankFilled("uraniumtankfilled", Material.ROCK);

	//public static final Block POWER_CORD = new PowerCord("powercord");
	
	public static final Block URANIUM_FURNACE = new UraniumFurnace("uranium_furnace");
	
	//public static final Block HYPER_DRIVE = new HyperDrive("hyperdrive", Material.IRON);

	public static final Block CRYOGENIC_TUBE_ON = new CryogenicTubeOn("cryogenictubeon", Material.GLASS);

	public static final Block CRYOGENIC_TUBE_OFF = new CryogenicTubeOff("cryogenictubeoff", Material.GLASS);

	public static final Block METAL_TREE = new MetalTree("metaltree", Material.BARRIER);

	
	//PortalControls
	public static final Block PORTAL_CONTROL = new PortalControl("portalcontrol", Material.ANVIL);
	public static final Block PORTAL_CONTROLBOOK1 = new PortalControlBook1("portalcontrolbook1", Material.ANVIL);
	public static final Block PORTAL_CONTROLBOOK2 = new PortalControlBook2("portalcontrolbook2", Material.ANVIL);
	public static final Block PORTAL_CONTROLBOOK3 = new PortalControlBook3("portalcontrolbook3", Material.ANVIL);
	public static final Block PORTAL_CONTROLBOOK1BOOK2 = new PortalControlBook1Book2("portalcontrolbook1book2", Material.ANVIL);
	public static final Block PORTAL_CONTROLBOOK2BOOK3 = new PortalControlBook2Book3("portalcontrolbook2book3", Material.ANVIL);
	public static final Block PORTAL_CONTROLBOOK1BOOK3 = new PortalControlBook1Book3("portalcontrolbook1book3", Material.ANVIL);
	public static final Block PORTAL_CONTROLALLBOOKS = new PortalControlAllBooks("portalcontrolallbooks", Material.ANVIL);


	
	//Trees
	public static final Block REDWOODPLANKS = new RedWoodPlanks("redwoodplanks", Material.WOOD);
	public static final Block REDWOODLOGS = new RedWoodLogs("redwoodlogs", Material.WOOD);
	public static final Block REDWOODLEAVES = new RedWoodLeaves("redwoodleaves", Material.PLANTS);
	public static final Block REDWOODSAPLING = new RedWoodSapling("redwoodsapling");
}
