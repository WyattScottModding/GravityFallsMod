package worldgen;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import init.BlockInit;
import main.GravityFalls;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenHugeTrees;
import net.minecraftforge.common.IPlantable;

public class WorldGenBirchTreeEye extends WorldGenAbstractTree
{
	public static final PropertyDirection FACING = BlockDirectional.FACING;

	private static final IBlockState LOG = BlockInit.BIRCH_LOG_EYE.getDefaultState();
	private static final IBlockState LOG_NORMAL = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH);
	private static final IBlockState LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.BIRCH).withProperty(BlockOldLeaf.CHECK_DECAY, Boolean.valueOf(false));
	private final boolean useExtraRandomHeight;

	public WorldGenBirchTreeEye(boolean notify, boolean useExtraRandomHeightIn)
	{
		super(notify);
		this.useExtraRandomHeight = useExtraRandomHeightIn;
	}

	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
		int i = rand.nextInt(3) + 5;

		if (this.useExtraRandomHeight)
		{
			i += rand.nextInt(7);
		}

		boolean flag = true;

		if (position.getY() >= 1 && position.getY() + i + 1 <= 256)
		{
			for (int j = position.getY(); j <= position.getY() + 1 + i; ++j)
			{
				int k = 1;

				if (j == position.getY())
				{
					k = 0;
				}

				if (j >= position.getY() + 1 + i - 2)
				{
					k = 2;
				}

				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

				for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l)
				{
					for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1)
					{
						if (j >= 0 && j < worldIn.getHeight())
						{
							if (!this.isReplaceable(worldIn, blockpos$mutableblockpos.setPos(l, j, i1)))
							{
								flag = false;
							}
						}
						else
						{
							flag = false;
						}
					}
				}
			}

			if (!flag)
			{
				return false;
			}
			else
			{
				BlockPos down = position.down();
				IBlockState state = worldIn.getBlockState(down);
				boolean isSoil = state.getBlock().canSustainPlant(state, worldIn, down, net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling)Blocks.SAPLING);

				if (isSoil && position.getY() < worldIn.getHeight() - i - 1)
				{
					state.getBlock().onPlantGrow(state, worldIn, down, position);

					for (int i2 = position.getY() - 3 + i; i2 <= position.getY() + i; ++i2)
					{
						int k2 = i2 - (position.getY() + i);
						int l2 = 1 - k2 / 2;

						for (int i3 = position.getX() - l2; i3 <= position.getX() + l2; ++i3)
						{
							int j1 = i3 - position.getX();

							for (int k1 = position.getZ() - l2; k1 <= position.getZ() + l2; ++k1)
							{
								int l1 = k1 - position.getZ();

								if (Math.abs(j1) != l2 || Math.abs(l1) != l2 || rand.nextInt(2) != 0 && k2 != 0)
								{
									BlockPos blockpos = new BlockPos(i3, i2, k1);
									IBlockState state2 = worldIn.getBlockState(blockpos);

									if (state2.getBlock().isAir(state2, worldIn, blockpos) || state2.getBlock().isAir(state2, worldIn, blockpos))
									{
										this.setBlockAndNotifyAdequately(worldIn, blockpos, LEAF);
									}
								}
							}
						}
					}

					for (int j2 = 0; j2 < i; ++j2)
					{
						BlockPos upN = position.up(j2);
						IBlockState state2 = worldIn.getBlockState(upN);

						if (state2.getBlock().isAir(state2, worldIn, upN) || state2.getBlock().isLeaves(state2, worldIn, upN))
						{
							if(rand.nextInt(4) == 0)
							{			
								int randomInt = rand.nextInt(4);

								if(randomInt == 0)
									this.setBlockAndNotifyAdequately(worldIn, position.up(j2), LOG.withProperty(FACING, EnumFacing.NORTH));
								else if(randomInt == 1)
									this.setBlockAndNotifyAdequately(worldIn, position.up(j2), LOG.withProperty(FACING, EnumFacing.SOUTH));
								else if(randomInt == 2)
									this.setBlockAndNotifyAdequately(worldIn, position.up(j2), LOG.withProperty(FACING, EnumFacing.EAST));
								else if(randomInt == 3)
									this.setBlockAndNotifyAdequately(worldIn, position.up(j2), LOG.withProperty(FACING, EnumFacing.WEST));
							}
							else
							{
								this.setBlockAndNotifyAdequately(worldIn, position.up(j2), LOG_NORMAL);
							}
						}
					}

					return true;
				}
				else
				{
					return false;
				}
			}
		}
		else
		{
			return false;
		}
	}
}