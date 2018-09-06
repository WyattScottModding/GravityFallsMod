package handlers;

import java.util.ArrayList;

import net.minecraft.util.math.BlockPos;

public class PortalBlocks 
{
	public BlockPos pos;
	public ArrayList<BlockPos> triangleBlocks;
	public ArrayList<BlockPos> circleBlocks;
	public ArrayList<BlockPos> ringBlocks;
	public ArrayList<BlockPos> portalBlocks;

	public PortalBlocks(BlockPos leverPos, ArrayList<BlockPos> portal, ArrayList<BlockPos> ring, ArrayList<BlockPos> triangle, ArrayList<BlockPos> circle)
	{
		triangleBlocks = triangle;
		circleBlocks = circle;
		ringBlocks = ring;
		portalBlocks = portal;
		pos = leverPos;
	}
	
	public ArrayList<BlockPos> getTriangle()
	{
		return triangleBlocks;
	}
	
	public ArrayList<BlockPos> getCircle()
	{
		return circleBlocks;
	}
	
	public ArrayList<BlockPos> getRing()
	{
		return ringBlocks;
	}
	
	public ArrayList<BlockPos> getPortal()
	{
		return portalBlocks;
	}
	
	public BlockPos getLeverPos()
	{
		return pos;
	}
}