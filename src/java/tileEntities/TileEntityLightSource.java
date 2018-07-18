package tileEntities;

import blocks.LightSource;
import init.BlockInit;
import main.GravityFalls;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.Instance;

public class TileEntityLightSource extends TileEntity implements ITickable
{
    public EntityPlayer thePlayer;
    
    public TileEntityLightSource()
    {
        // after constructing the tile entity instance, remember to call 
        // the setPlayer() method.
    }
    
    /**
     * This controls whether the tile entity gets replaced whenever the block state 
     * is changed. Normally only want this when block actually is replaced.
     */
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return (oldState.getBlock() != newSate.getBlock());
    }
    
    @Override
    public void update()
    {
        // check if player has moved away from the tile entity
        EntityPlayer thePlayer = world.getClosestPlayer(getPos().getX()+0.5D, getPos().getY()+0.5D, getPos().getZ()+0.5D, 2.0D, false);
      
        if (thePlayer == null)
        {
            if (world.getBlockState(getPos()).getBlock() == BlockInit.LIGHT_SOURCE)
            {
                world.setBlockToAir(getPos());
            }
        }
        else if (thePlayer.getHeldItemMainhand().getItem() != null && !LightSource.isLightEmittingItem(thePlayer.getHeldItemMainhand().getItem()))
        {
            if (world.getBlockState(getPos()).getBlock() == BlockInit.LIGHT_SOURCE)
            {
                world.setBlockToAir(getPos());
            }            
        }
        
        setPlayer(thePlayer);
    }  
    
    public void setPlayer(EntityPlayer parPlayer)
    {
        thePlayer = parPlayer;
    }
}