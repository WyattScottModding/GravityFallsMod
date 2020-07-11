package tileEntities;

import java.util.List;
import java.util.Random;

import entity.EntityBill;
import handlers.SoundsHandler;
import init.BiomeInit;
import init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.EnumDifficulty;

public class TileEntityBook2 extends TileEntity implements ITickable{

	private int wait = -100;
	
	public TileEntityBook2() {
	}

	@Override
	public void update() {
		if(!world.getDifficulty().equals(EnumDifficulty.PEACEFUL))
		{
			Block block1 = world.getBlockState(pos.north()).getBlock();
			Block block2 = world.getBlockState(pos.south()).getBlock();
			Block block3 = world.getBlockState(pos.west()).getBlock();
			Block block4 = world.getBlockState(pos.east()).getBlock();

			Block torch  = Blocks.TORCH;

			if(block1 == torch && block2 == torch && block3 == torch && block4 == torch && world.getBiome(this.pos) == BiomeInit.GRAVITYFALLS)
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
				}
			}
		}

		if(wait > -100)
			wait--;

		if(wait == 0 && !world.isRemote) {
			EntityBill bill = new EntityBill(world);
			bill.setPosition(pos.getX() + .5, pos.getY() + .25, pos.getZ() + .5);
			world.spawnEntity(bill);
		}
		else if(wait < 20 && wait > -100)
		{
			Random rand = new Random();
			
			for (int l = 0; l < 3; ++l)
	        {
	            double d10 = this.pos.getX() + .5;
	            double d2 = this.pos.getY() + .25;
	            double d4 = this.pos.getZ() + .5;
	            this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d10 + rand.nextGaussian() * 0.30000001192092896D, d2 + rand.nextGaussian() * 0.30000001192092896D, d4 + rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
	            this.world.spawnParticle(EnumParticleTypes.FLAME, d10 + rand.nextGaussian() * 0.30000001192092896D, d2 + rand.nextGaussian() * 0.30000001192092896D, d4 + rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);

	        }
		}
	}
}