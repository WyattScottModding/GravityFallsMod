package gui;

import containers.ContainerCartInventory;
import entity.EntityGolfCart;
import main.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiGolfCart extends GuiContainer
{
    /** The ResourceLocation containing the chest GUI texture. */
    private static final ResourceLocation GUI_GOLFCART = new ResourceLocation(Reference.MODID + ":textures/gui/golfcart.png");
    private final IInventory upperChestInventory;
    private final IInventory lowerChestInventory;
    /** window height is calculated with these values; the more rows, the heigher */
    private final int inventoryRows;
    private EntityGolfCart cart;
    private ContainerCartInventory cartContainer;

    public GuiGolfCart(ContainerCartInventory cartContainer)
    {
        super(cartContainer);
        this.upperChestInventory = cartContainer.upperInv;
        this.lowerChestInventory = cartContainer.lowerInv;
        this.allowUserInput = false;
        int i = 222;
        int j = 114;
        this.inventoryRows = cartContainer.lowerInv.getSizeInventory() / 9;
        this.ySize = 114 + this.inventoryRows * 18;
        this.cart = cartContainer.cart;
        this.cartContainer = cartContainer;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString(this.upperChestInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
        this.fontRenderer.drawString(this.lowerChestInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
    	GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(GUI_GOLFCART);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        
        int l = this.getBatteryLife(45);
		this.drawTexturedModalRect(this.guiLeft + 9, this.guiTop + 43, 176, 0, l, 3);
    }
    
    private int getBatteryLife(int pixels)
	{
		int j = this.cart.batteryLife;

		return j != 0 ? j * pixels / 100 : 0;	
	}
}