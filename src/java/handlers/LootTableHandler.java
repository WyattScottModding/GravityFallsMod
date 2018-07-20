package handlers;

import main.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class LootTableHandler 
{
	public static final ResourceLocation GNOME = LootTableList.register(new ResourceLocation(Reference.MODID, "gnome"));
	public static final ResourceLocation TIMECOP = LootTableList.register(new ResourceLocation(Reference.MODID, "timecop"));
	public static final ResourceLocation BILL = LootTableList.register(new ResourceLocation(Reference.MODID, "bill"));
	public static final ResourceLocation DROID = LootTableList.register(new ResourceLocation(Reference.MODID, "securitydroid"));
	public static final ResourceLocation UNICORN = LootTableList.register(new ResourceLocation(Reference.MODID, "unicorn"));

}
