package init;

import dimensions.DimensionNightmareRealm;
import dimensions.DimensionTheFuture;
import main.ConfigHandler;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import updates.WeirdmageddonWorld;

public class DimensionInit
{
	public static final DimensionType THEFUTURE = DimensionType.register("The Future", "_thefuture", ConfigHandler.THE_FUTURE, DimensionTheFuture.class, false);
	public static final DimensionType NIGHTMAREREALM = DimensionType.register("Nightmare Realm", "_nightmarerealm", ConfigHandler.NIGHTMARE_REALM, DimensionNightmareRealm.class, false);
	public static final DimensionType WEIRDMAGEDDON = DimensionType.register("Weirdmageddon", "_weirdmageddon", ConfigHandler.WEIRDMAGEDDON, WeirdmageddonWorld.class, false);

	public static void registerDimensions()
	{
		DimensionManager.registerDimension(ConfigHandler.THE_FUTURE, THEFUTURE);
		DimensionManager.registerDimension(ConfigHandler.NIGHTMARE_REALM, NIGHTMAREREALM);
		//DimensionManager.registerDimension(ConfigHandler.NIGHTMARE_REALM, WEIRDMAGEDDON);

	}
}
