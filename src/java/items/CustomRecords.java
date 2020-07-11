package items;

import java.util.Map;

import com.google.common.collect.Maps;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;

public class CustomRecords extends ItemRecord implements IHasModel
{
	private final String displayName;
	private static final Map<SoundEvent, ItemRecord> RECORDS = Maps.<SoundEvent, ItemRecord>newHashMap();
	private final SoundEvent sound;

	public CustomRecords(String name, SoundEvent soundIn) 
	{
		super(name, soundIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);
		this.sound = soundIn;
		this.displayName = "item.record." + name + ".desc";
		RECORDS.put(this.sound, this);

		ItemInit.ITEMS.add(this);
	}

	@Override
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}	
}