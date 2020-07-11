package updates;

import dimensions.DimensionTheFuture;
import entity.EntityTimeBaby;
import init.ItemInit;
import items.TimeTape;
import main.ConfigHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class GlobnarUpdate
{
	public static NBTTagCompound nbt = new NBTTagCompound();

	public static boolean messageSent = false;
	public static boolean recievedTimeWish = false;

	public static EntityTimeBaby timebaby = null;

	public static void init(World world, EntityPlayer player)
	{
		globnarRender(player, world);
	}

	public static void globnarRender(EntityPlayer player, World world)
	{
		if(player.dimension == ConfigHandler.THE_FUTURE && world.provider.getDimension() == ConfigHandler.THE_FUTURE)
		{
			if(!DimensionTheFuture.babySpawned) {

				for(Entity element : world.loadedEntityList) {
					if(element instanceof EntityTimeBaby) {
						element.setDead();
					}
				}

				timebaby = new EntityTimeBaby(world, -50, 61, -18.5);
				timebaby.setLocationAndAngles(-50, 61, -18.5, 0, 0);				
				DimensionTheFuture.babySpawned = world.spawnEntity(timebaby);
			}

			if(!messageSent && DimensionTheFuture.babySpawned)
			{
				ITextComponent msg = new TextComponentString("Welcome to Globnar!");
				player.sendMessage(msg);
				msg = new TextComponentString("You must defeat Time Baby to win the Time Wish!");
				player.sendMessage(msg);

				messageSent = true;


				//Confiscate all Time Tapes
				for(int i = 0; i < player.inventory.getSizeInventory(); i++)
				{
					if(player.inventory.getStackInSlot(i).getItem() instanceof TimeTape)
					{
						player.inventory.removeStackFromSlot(i);
					}
				}

				msg = new TextComponentString("All Time Tapes have been confiscated.");
				player.sendMessage(msg);

				recievedTimeWish = false;
			}

			//Give the player a Time Wish and a Time Tape
			if(DimensionTheFuture.babySpawned && timebaby.isDead && !recievedTimeWish)
			{
				if(world.provider.getDimension() == ConfigHandler.THE_FUTURE) {
					for(EntityPlayer element : world.playerEntities) {
						EntityItem entityTape = new EntityItem(world);
						entityTape.setItem(new ItemStack(ItemInit.TIME_TAPE));

						double randX = (Math.random() * 20) - 28.5;
						double randZ = (Math.random() * 20) - 28.5;

						entityTape.setPosition(randX, 61, randZ);
						world.spawnEntity(entityTape);
					}
				}

				EntityItem entityWish = new EntityItem(world);
				entityWish.setItem(new ItemStack(ItemInit.TIME_WISH));
				entityWish.setPosition(-18.5, 61, -18.5);
				world.spawnEntity(entityWish);
				recievedTimeWish = true;

				ITextComponent msg = new TextComponentString("Time Baby has been defeated!");
				player.sendMessage(msg);
			}
		}
	}
}