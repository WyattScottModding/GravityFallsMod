package handlers;

import main.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class LootTableHandler 
{
	public static final ResourceLocation GNOME = LootTableList.register(new ResourceLocation(Reference.MODID, "gnome"));
	public static final ResourceLocation TIMECOP = LootTableList.register(new ResourceLocation(Reference.MODID, "timecop"));

}
