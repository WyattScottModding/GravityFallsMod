package init;

import dimensions.DimensionNightmareRealm;
import dimensions.DimensionTheFuture;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionInit
{
	public static final DimensionType THEFUTURE = DimensionType.register("The Future", "_thefuture", 2, DimensionTheFuture.class, false);
	public static final DimensionType NIGHTMAREREALM = DimensionType.register("Nightmare Realm", "_nightmarerealm", 3, DimensionNightmareRealm.class, false);

	public static void registerDimensions()
	{
		DimensionManager.registerDimension(2, THEFUTURE);
		DimensionManager.registerDimension(3, NIGHTMAREREALM);
	}
}
