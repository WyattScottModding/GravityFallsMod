package gui;

import java.io.IOException;
import java.util.Map.Entry;

import handlers.PortalBlocks;
import main.Reference;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import network.MessageTeleportPlayer;
import network.Messages;

@SideOnly(Side.CLIENT)
public class GuiReturnDevice extends GuiScreen
{
	private static final ResourceLocation RETURN_DEVICE_BACKGROUND = new ResourceLocation(Reference.MODID + ":textures/gui/background.png");

	protected int xSize = 176;
	protected int ySize = 170;

	public GuiReturnDevice() 
	{
		super();
	}

	@Override
	public void initGui() 
	{
		int i = 0;
		int j = 0;
		int maxNumPortals = 0;
		for (Entry<BlockPos, Integer> entry : PortalBlocks.portalMap.entrySet())  
		{
			if(maxNumPortals <= 20) {
				//Check to see if the portal is on
				if(entry.getValue() == 0) {

					if(i == 8) {
						j = 1;
						i = 0;
					}

					BlockPos pos = entry.getKey();
					String text = "Teleport to: (" + (pos.getX() + 2) + ", " + pos.getY() + ", " + pos.getZ() + ")";

					buttonList.add(new GuiButtonExt(i, (width / 2) - (xSize) + 18 + (j * 160), (height / 2) - (ySize / 2) + 1 + (i * 20), 160, 20, text));
					i++;
					maxNumPortals++;
				}
			}
		}

		if(buttonList.size() == 0) {
			String string = "There are no active portals to the Overworld";
			this.fontRenderer.drawString(string, ((width / 2) - (xSize) - this.fontRenderer.getStringWidth(string) / 2), (height / 2) - (ySize / 2) - 5, 4210752); 
		}

		super.initGui();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException 
	{
		String name = button.displayString.substring(14, button.displayString.length() - 1);

		int posX = Integer.valueOf(name.substring(0, name.indexOf(",")));
		name = name.substring(name.indexOf(",") + 2, name.length());

		int posY = Integer.valueOf(name.substring(0, name.indexOf(",")));
		name = name.substring(name.indexOf(",") + 2, name.length());

		int posZ = Integer.valueOf(name.substring(0, name.length()));


		Messages.INSTANCE.sendToServer(new MessageTeleportPlayer(posX, posY, posZ));
		mc.displayGuiScreen(null);

		super.actionPerformed(button);

		this.onGuiClosed();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) 
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.getTextureManager().bindTexture(RETURN_DEVICE_BACKGROUND);
		Gui.drawModalRectWithCustomSizedTexture((width / 2) - (xSize) + 1, (height / 2) - (ySize / 2) - 3, 0, 0, xSize * 2, ySize, 355, 170);

		for (int i = 0; i < buttonList.size(); i++)  
		{
			((GuiButton)this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY, partialTicks);
		}
	}

	@Override
	protected void renderToolTip(ItemStack stack, int x, int y) {
		super.renderToolTip(stack, x, y);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}