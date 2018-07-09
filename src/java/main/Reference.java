package main;

import net.minecraft.world.DimensionType;

public class Reference {

	public static final String NAME = "Gravity Falls";
	public static final String VERSION = "0.82";
	public static final String MODID = "gravityfalls";
	public static final String ACCEPTED_VERSIONS = "1.12.2";
	
	public static final String CLIENTPROXY = "proxy.ClientProxy";
	public static final String SERVERPROXY = "proxy.ServerProxy";
	
	public static final int ENTITY_GNOME = 300;
	public static final int ENTITY_FORGET = 301;
	public static final int ENTITY_HOOK = 302;
	public static final int ENTITY_TIMECOP_LOLPH = 303;
	public static final int ENTITY_TIMECOP_DUNDGREN = 304;
	public static final int ENTITY_GOLFCART = 305;
	public static final int ENTITY_EYEBAT = 306;
	public static final int ENTITY_HIDEBEHIND = 307;
	public static final int ENTITY_SECURITYDROID = 308;
	public static final int ENTITY_BILL = 309;
	public static final int ENTITY_EIGHTBALL = 310;
	public static final int ENTITY_EYEBATHUGE = 311;
	public static final int ENTITY_KEYHOLE = 312;




	
	public static final int GUI_URANIUM_FURNACE = 400;
	public static final int GUI_JOURNAL1 = 401;
	public static final int GUI_JOURNAL2 = 402;
	public static final int GUI_JOURNAL3 = 403;
	public static final int SCOPE = 404;
	public static final int COMPUTER = 405;
	
	public static final int THE_FUTURE = 40;
	public static final int NIGHTMARE_REALM = 41;
	

	public static enum Items
	{
		CRYSTAL("crystal", "Crystal"),
		URANIUM("uranium", "Uranium"),
		URANIUMTANK("uraniumtank", "UraniumTank"),
		SMILEDIP("smiledip", "SmileDip");
		
		private String unlocalizedName;
		private String registryName;
		
		Items(String unlocalizedName, String registryName)
		{
			this.unlocalizedName = unlocalizedName;
			this.registryName = registryName;
		}
		
		public String getUnlocalizedName()
		{
			return unlocalizedName;
		}
		
		public String getRegistryName()
		{
			return registryName;
		}
		
		
	}
}
