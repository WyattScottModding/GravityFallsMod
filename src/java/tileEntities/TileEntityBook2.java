package tileEntities;

import java.util.List;

import entity.EntityBill;
import handlers.SoundsHandler;
import init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.EnumDifficulty;

public class TileEntityBook2 extends TileEntity implements ITickable{

	public boolean billSummoned = false;
	private int wait = -1;
	
	public TileEntityBook2() {
	}

	@Override
	public void update() {
		if(!billSummoned && !world.getDifficulty().equals(EnumDifficulty.PEACEFUL))
		{
			Block block1 = world.getBlockState(pos.north()).getBlock();
			Block block2 = world.getBlockState(pos.south()).getBlock();
			Block block3 = world.getBlockState(pos.west()).getBlock();
			Block block4 = world.getBlockState(pos.east()).getBlock();

			Block torch  = Blocks.TORCH;

			if(block1 == torch && block2 == torch && block3 == torch && block4 == torch)
			{
				AxisAlignedBB LargeBox = new AxisAlignedBB(pos.getX() - 200, 0, pos.getZ() - 200, pos.getX() + 200, world.getHeight(), pos.getZ() + 200);
				List<EntityBill> bill = world.getEntitiesWithinAABB(EntityBill.class, LargeBox);

				//Will not summon a Bill if there is already a Bill nearby
				if(bill.isEmpty()) {

					AxisAlignedBB Box = new AxisAlignedBB(pos.getX() - 50, pos.getY() - 50, pos.getZ() - 50, pos.getX() + 50, pos.getY() + 50, pos.getZ() + 50);
					List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, Box);

					for (EntityPlayer element : players) {
						element.playSound(SoundsHandler.ENTITY_BILL_SUMMONED, 1.0F, 1.0F);
					}

					world.setBlockState(pos.north(), BlockInit.UNLIT_TORCH.getDefaultState());
					world.setBlockState(pos.south(), BlockInit.UNLIT_TORCH.getDefaultState());
					world.setBlockState(pos.east(), BlockInit.UNLIT_TORCH.getDefaultState());
					world.setBlockState(pos.west(), BlockInit.UNLIT_TORCH.getDefaultState());

					wait = 260;

					billSummoned = true;
				}
			}
		}

		if(wait > 0)
			wait--;

		if(wait == 0 && !world.isRemote) {
			EntityBill bill = new EntityBill(world);
			bill.setPosition(pos.getX(), pos.getY(), pos.getZ());
			world.spawnEntity(bill);

			wait = -1;
		}
	}
}