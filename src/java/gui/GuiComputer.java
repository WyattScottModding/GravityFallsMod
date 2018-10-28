package gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import containers.ContainerBook1;
import containers.ContainerComputer;
import containers.ContainerUraniumFurnace;
import handlers.RegistryHandler;
import handlers.SoundsHandler;
import io.netty.buffer.Unpooled;
import main.Reference;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tileEntities.TileEntityBook1;
import tileEntities.TileEntityComputer;
import tileEntities.TileEntityUraniumFurnace;

@SideOnly(Side.CLIENT)
public class GuiComputer extends GuiContainer
{
	private static final ResourceLocation TEXTFIELD = new ResourceLocation(Reference.MODID + ":textures/gui/textfield.png");
	private static final ResourceLocation TEXTFIELDRED = new ResourceLocation(Reference.MODID + ":textures/gui/textfieldred.png");
	private static final ResourceLocation SCREENON = new ResourceLocation(Reference.MODID + ":textures/gui/screenon.png");
	private static final ResourceLocation COUNTDOWN = new ResourceLocation(Reference.MODID + ":textures/gui/countdownscreen.png");
	private static final ResourceLocation ZERO = new ResourceLocation(Reference.MODID + ":textures/gui/numbers/zero.png");
	private static final ResourceLocation ONE = new ResourceLocation(Reference.MODID + ":textures/gui/numbers/one.png");
	private static final ResourceLocation TWO = new ResourceLocation(Reference.MODID + ":textures/gui/numbers/two.png");
	private static final ResourceLocation THREE = new ResourceLocation(Reference.MODID + ":textures/gui/numbers/three.png");
	private static final ResourceLocation FOUR = new ResourceLocation(Reference.MODID + ":textures/gui/numbers/four.png");
	private static final ResourceLocation FIVE = new ResourceLocation(Reference.MODID + ":textures/gui/numbers/five.png");
	private static final ResourceLocation SIX = new ResourceLocation(Reference.MODID + ":textures/gui/numbers/six.png");
	private static final ResourceLocation SEVEN = new ResourceLocation(Reference.MODID + ":textures/gui/numbers/seven.png");
	private static final ResourceLocation EIGHT = new ResourceLocation(Reference.MODID + ":textures/gui/numbers/eight.png");
	private static final ResourceLocation NINE = new ResourceLocation(Reference.MODID + ":textures/gui/numbers/nine.png");
	private static final ResourceLocation COLON = new ResourceLocation(Reference.MODID + ":textures/gui/numbers/colon.png");
	private static final ResourceLocation BAR = new ResourceLocation(Reference.MODID + ":textures/gui/numbers/bar.png");

	private GuiTextField textField;
	private String countdownText = "";
	private final TileEntityComputer tileentity;

	public boolean wrongPassword = false;
	private int counter = 0;

	public boolean computerOn = false;

	public int countdown = 36000;

	public GuiComputer(InventoryPlayer playerInventory, TileEntityComputer tileentity) 
	{
		super(new ContainerComputer(playerInventory, tileentity));
		this.tileentity = tileentity;
	}

	@Override
	public void initGui()
	{
		this.textField = new GuiTextField(2, this.fontRenderer, 30, 105, 100, 12);
		this.textField.setMaxStringLength(8);
		this.textField.setFocused(true);
		this.textField.setEnabled(true);
		this.textField.setTextColor(1113860);

		Keyboard.enableRepeatEvents(true);

		super.initGui();
	}

	@Override
	public void updateScreen()
	{
		this.textField.updateCursorCounter();

		if(Keyboard.isKeyDown(Keyboard.KEY_RETURN))
		{
			if(this.textField.getText().equals("STANFORD"))
			{
				computerOn = true;
			}
			else
			{
				BlockPos pos = new BlockPos(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ);

				wrongPassword = true;
				this.mc.world.playSound(pos, SoundsHandler.BLOCK_PASSWORD, SoundCategory.BLOCKS, 0.5F, 1.0F, true);
				this.textField.setEnabled(false);
			}
		}
		if(wrongPassword)
			counter++;

		if(counter == 30)
		{
			counter = 0;
			wrongPassword = false;
			this.textField.setText("");
			this.textField.setEnabled(true);

		}

		String text = textField.getText().toUpperCase();

		textField.setText(text);

		countdown = RegistryHandler.getCountdown();

		int minute = countdown / 2400;
		int second = (countdown % 2400) / 40;

		if(second < 10 && minute < 10)
			countdownText = "0"  + minute + " : 0" + second;
		else if(second < 10)
			countdownText = minute + " : 0" + second;
		else if(minute < 10)
			countdownText = "0"  + minute + " : " + second;
		else
			countdownText = minute + " : " + second;

		super.updateScreen();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) 
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		if(!computerOn)
		{
			this.textField.drawTextBox();
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		boolean active = false;
		
		if(RegistryHandler.nbt.hasKey("portalActive"))
			active = RegistryHandler.nbt.getBoolean("portalActive");
			
		if(computerOn && active && RegistryHandler.countdown != -1)
		{
			int height2 = (height / 2) + 14;
			double percent = 0.0;
			if(countdown < 36000)
				percent = (double)(36000.0 - (double)countdown) / 36000.0;
			
			int barWidth = (int) (193 * percent);
			
			this.mc.getTextureManager().bindTexture(COUNTDOWN);

			this.drawModalRectWithCustomSizedTexture((width / 2) - (xSize) - 10, (height / 2) - (ySize / 2) - 10, 0, 0, 370, 190, 370, 200);

			this.mc.getTextureManager().bindTexture(COLON);
			this.drawModalRectWithCustomSizedTexture(width / 2, height2 + 5, 0, 0, 8, 18, 8, 18);
			
			this.mc.getTextureManager().bindTexture(BAR);
			this.drawModalRectWithCustomSizedTexture((width / 2) - 99, height2 + 34, 0, 0, barWidth, 11, barWidth, 11);

			
			if(countdownText.length() > 6)
			{
				String string = countdownText.substring(0, 1);
				int num = (width / 2) - 50;
				drawNumber(string, num, height2);

				string = countdownText.substring(1, 2);
				num = (width / 2) - 26;
				drawNumber(string, num, height2);

				string = countdownText.substring(5, 6);
				num = (width / 2) + 16;
				drawNumber(string, num, height2);

				string = countdownText.substring(6, 7);
				num = (width / 2) + 40;
				drawNumber(string, num, height2);
			}
		}
		else
		{
			if(wrongPassword)
			{
				this.mc.getTextureManager().bindTexture(TEXTFIELDRED);
			}
			else if(computerOn)
			{
				this.mc.getTextureManager().bindTexture(SCREENON);
			}
			else
			{
				this.mc.getTextureManager().bindTexture(TEXTFIELD);
			}

			this.drawModalRectWithCustomSizedTexture((width / 2) - (xSize) - 10, (height / 2) - (ySize / 2) - 10, 0, 0, 370, 190, 370, 200);
		}
	}

	public void drawNumber(String number, int xPos, int height2)
	{
		if(number.equals("0"))
		{
			this.mc.getTextureManager().bindTexture(ZERO);
			this.drawModalRectWithCustomSizedTexture(xPos, height2, 0, 0, 18, 28, 18, 28);
		}
		else if(number.equals("1"))
		{
			this.mc.getTextureManager().bindTexture(ONE);
			this.drawModalRectWithCustomSizedTexture(xPos, height2, 0, 0, 18, 28, 18, 28);
		}
		else if(number.equals("2"))
		{
			this.mc.getTextureManager().bindTexture(TWO);
			this.drawModalRectWithCustomSizedTexture(xPos, height2, 0, 0, 18, 28, 18, 28);
		}
		else if(number.equals("3"))
		{
			this.mc.getTextureManager().bindTexture(THREE);
			this.drawModalRectWithCustomSizedTexture(xPos, height2, 0, 0, 18, 28, 18, 28);
		}
		else if(number.equals("4"))
		{
			this.mc.getTextureManager().bindTexture(FOUR);
			this.drawModalRectWithCustomSizedTexture(xPos, height2, 0, 0, 18, 28, 18, 28);
		}
		else if(number.equals("5"))
		{
			this.mc.getTextureManager().bindTexture(FIVE);
			this.drawModalRectWithCustomSizedTexture(xPos, height2, 0, 0, 18, 28, 18, 28);
		}
		else if(number.equals("6"))
		{
			this.mc.getTextureManager().bindTexture(SIX);
			this.drawModalRectWithCustomSizedTexture(xPos, height2, 0, 0, 18, 28, 18, 28);
		}
		else if(number.equals("7"))
		{
			this.mc.getTextureManager().bindTexture(SEVEN);
			this.drawModalRectWithCustomSizedTexture(xPos, height2, 0, 0, 18, 28, 18, 28);
		}
		else if(number.equals("8"))
		{
			this.mc.getTextureManager().bindTexture(EIGHT);
			this.drawModalRectWithCustomSizedTexture(xPos, height2, 0, 0, 18, 28, 18, 28);
		}
		else if(number.equals("9"))
		{
			this.mc.getTextureManager().bindTexture(NINE);
			this.drawModalRectWithCustomSizedTexture(xPos, height2, 0, 0, 18, 28, 18, 28);
		}
	}


	@Override
	protected void actionPerformed(GuiButton button) throws IOException 
	{
		if (button.enabled && button.id == 0)
		{
			PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
			packetbuffer.writeString(this.textField.getText());
		}

		super.actionPerformed(button);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException 
	{
		this.textField.textboxKeyTyped(typedChar, keyCode);

		super.keyTyped(typedChar, keyCode);
	}
}