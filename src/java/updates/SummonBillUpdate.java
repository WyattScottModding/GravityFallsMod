package updates;

import entity.EntityBill;
import handlers.SoundsHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SummonBillUpdate
{
	public static NBTTagCompound nbt = new NBTTagCompound();

	public static boolean billSummoning = false;
	public static BlockPos billPos = null;
	
	public static void init(World world, EntityPlayer player)
	{
		if(billSummoning && billPos != null)
			summonBill(billPos, world);
	}
	
	
	public static void summonBill(BlockPos pos, World world)
	{
		EntityBill bill = new EntityBill(world);
		bill.setPosition(pos.getX(), pos.getY(), pos.getZ());
		world.spawnEntity(bill);

		world.setBlockState(pos.north(), Blocks.AIR.getDefaultState());
		world.setBlockState(pos.south(), Blocks.AIR.getDefaultState());
		world.setBlockState(pos.east(), Blocks.AIR.getDefaultState());
		world.setBlockState(pos.west(), Blocks.AIR.getDefaultState());

		world.playSound(null, pos.up(), SoundsHandler.ENTITY_BILL_SUMMONED, SoundCategory.VOICE, 1.0F, 1.0F);

		billSummoning = false;
		billPos = null;
	}
}
