package commands;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class Teleport extends Teleporter
{
	private final WorldServer world;
	private double x, y, z;
	
	public Teleport(WorldServer world, double x, double y, double z) 
	{
		super(world);
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void placeInPortal(Entity entityIn, float rotationYaw)
	{
		this.world.getBlockState(new BlockPos((int) this.x, (int) this.y, (int) this.z));
		entityIn.setPosition(x, y, z);
		entityIn.motionX = 0f;
		entityIn.motionY = 0f;
		entityIn.motionZ = 0f;
	}
	
	public static void teleportToDimension(EntityPlayer player, int dimension, double x, double y, double z)
	{
		int oldDimension = player.getEntityWorld().provider.getDimension();
		EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
		MinecraftServer server = player.getEntityWorld().getMinecraftServer();
		WorldServer worldServer = server.getWorld(dimension);
		
		if(worldServer == null || server == null)
			throw new IllegalArgumentException("Dimension: " + dimension + " doesn't exist");
		
		worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new Teleport(worldServer, x, y, z));
		player.setPositionAndUpdate(x, y, z);
	}
	
	public static void teleportToDimension(EntityPlayer player, int dimension, BlockPos pos)
	{
		int oldDimension = player.getEntityWorld().provider.getDimension();
		EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
		MinecraftServer server = player.getEntityWorld().getMinecraftServer();
		WorldServer worldServer = server.getWorld(dimension);
		
		if(worldServer == null || server == null)
			throw new IllegalArgumentException("Dimension: " + dimension + " doesn't exist");
		
		worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new Teleport(worldServer, pos.getX(), pos.getY(), pos.getZ()));
		player.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
	}
}
