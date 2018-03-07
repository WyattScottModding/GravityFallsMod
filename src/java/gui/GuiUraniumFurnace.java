package gui;

import containers.ContainerUraniumFurnace;
import main.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import tileEntities.TileEntityUraniumFurnace;

public class GuiUraniumFurnace extends GuiContainer
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/gui/uranium_furnace_gui.png");
	private final InventoryPlayer player;
	private final TileEntityUraniumFurnace tileentity;
	
	public GuiUraniumFurnace(InventoryPlayer player, TileEntityUraniumFurnace tileentity) 
	{
		super(new ContainerUraniumFurnace(player, tileentity));
		this.player = player;
		this.tileentity = tileentity;
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String tileName = this.tileentity.getDisplayName().getUnformattedText();
		this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) + 3, 7, 4210752);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 122, this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	
		if(TileEntityUraniumFurnace.isBurning(tileentity))
		{
			int k = this.getBurnLeftScaled(27);
			this.drawTexturedModalRect(this.guiLeft + 98, this.guiTop + 37 + 12 - k, 176, 12 - k, 14, k + 1);
			
		}
		
	}
	
	private int getBurnLeftScaled(int pixels)
	{
		int i = this.tileentity.getField(1);
		if(i == 0)
			i = 200;
		return this.tileentity.getField(0) * pixels / i;
				
	}
	
	
	private int getCookProgressScaled(int pixels)
	{
		int i = this.tileentity.getField(0);
		int j = this.tileentity.getField(4);
		return j != 0 && i != 0 ? i * pixels / j : 0;
		
		
	}
	
	
}
