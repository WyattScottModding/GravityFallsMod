package main;

public class Reference {

	public static final String NAME = "Gravity Falls";
	public static final String VERSION = "0.1";
	public static final String MODID = "gravityfalls";
	public static final String ACCEPTED_VERSIONS = "2.12.2";
	
	public static final String CLIENTPROXY = "proxy.ClientProxy";
	public static final String SERVERPROXY = "proxy.ServerProxy";
	
	public static final int ENTITY_GNOME = 300;
	public static final int ENTITY_FORGET = 301;
	public static final int ENTITY_HOOK = 302;
	public static final int ENTITY_TIMECOP_LOLPH = 303;
	public static final int ENTITY_TIMECOP_DUNDGREN = 304;
	
	public static final int GUI_URANIUM_FURNACE = 400;
	public static final int GUI_JOURNAL1 = 401;
	public static final int GUI_JOURNAL2 = 402;
	public static final int GUI_JOURNAL3 = 403;
	public static final int SCOPE = 404;

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
