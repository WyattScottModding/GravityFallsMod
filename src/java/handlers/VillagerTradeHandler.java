package handlers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import villagerTrades.TradeArmorer;

public class VillagerTradeHandler 
{
	public static void init()
	{
		VillagerRegistry.VillagerProfession armorer = ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation("minecraft:smith"));

		armorer.getCareer(0).addTrade(4, new TradeArmorer());
	}
}