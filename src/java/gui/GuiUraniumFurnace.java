package gui;

import containers.ContainerUraniumFurnace;
import main.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
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
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	
		/*
		if(TileEntityUraniumFurnace.isBurning(tileentity))
		{
			int k = this.getBurnLeftScaled(13);
			this.drawTexturedModalRect(this.guiLeft + 8, this.guiTop + 54 + 12 - k, 176, 12 - k, 14, k + 1);
		}
		*/
		
		int l = this.getCookProgressScaled(26);
		this.drawTexturedModalRect(this.guiLeft + 98, this.guiTop + 46, 177, 1, 7, l);
	}

	/*
	private int getBurnLeftScaled(int pixels)
	{
		int i = this.tileentity.getField(1);
		if(i == 0)
			i = 200;
		return this.tileentity.getField(0) * pixels / i;
				
	}
	*/
	
	private int getCookProgressScaled(int pixels)
	{
		int j = this.tileentity.getField(3);

		return j != 0 ? j * pixels / 500 : 0;	
	}
}