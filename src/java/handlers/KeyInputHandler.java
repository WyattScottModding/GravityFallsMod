package handlers;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyInputHandler 
{

	private KeyBindings getPressedKey()
	{
		for(KeyBindings key : KeyBindings.values())
		{
			if(key.isPressed())
				return key;
		}
		return null;
	}

	@SubscribeEvent
	public void handleKeyInputEvent(InputEvent.KeyInputEvent event)
	{
		KeyBindings key = getPressedKey();
		if(key != null)
		{
			switch(key)
			{
			case ITEM1:
			{
				break;
			}
			case ITEM2:
			{
				break;
			}
			case ARMOR1:
			{
				break;
			}
			case ARMOR2:
			{
				break;
			}
			case ARMOR3:
			{
				break;
			}
			case BATTERY:
			{
				break;
			}
			} 
		}
	}
}
