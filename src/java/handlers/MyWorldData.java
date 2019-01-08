package handlers;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class MyWorldData extends WorldSavedData
{
	final static String key = "gravityfalls.data";

	// Fields containing your data here

	public MyWorldData()
	{
		super(key);
	}

	
	public static MyWorldData forWorld(World world)
	{
		// Retrieves the MyWorldData instance for the given world, creating it if necessary
		MapStorage storage = world.getMapStorage();
		MyWorldData result = (MyWorldData)storage.getOrLoadData(MyWorldData.class, key);
		
		if (result == null) 
		{
			result = new MyWorldData();
			storage.setData(key, result);
		}
		return result;
	}



	@Override
	public void readFromNBT(NBTTagCompound nbt) 
	{

	}


	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		return compound;
	}
}