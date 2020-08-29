package network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;


public class Messages {

    public static SimpleNetworkWrapper INSTANCE;
    private static int ID = 0;
    
    public static int nextID() {
    	return ID++;
    }

    public static void registerMessages(String channelName) {
    	INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
    	
    	INSTANCE.registerMessage(MessageTeleportPlayer.Handler.class, MessageTeleportPlayer.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(MessageShapeShifterUpdate.Handler.class, MessageShapeShifterUpdate.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(MessageShapeShifterModel.Handler.class, MessageShapeShifterModel.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(MessageOpenFordWorkbench.Handler.class, MessageOpenFordWorkbench.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(MessagePlaySound.Handler.class, MessagePlaySound.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(MessageChangeSize.Handler.class, MessageChangeSize.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(MessageChangeSizeClient.Handler.class, MessageChangeSizeClient.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(MessageMoveEntity.Handler.class, MessageMoveEntity.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(MessageDamageItem.Handler.class, MessageDamageItem.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(MessageProcessLightSweaterClick.Handler.class, MessageProcessLightSweaterClick.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(MessageProcessBattery.Handler.class, MessageProcessBattery.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(MessageProcessMemoryWipe.Handler.class, MessageProcessMemoryWipe.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(MessageHandleGolfCart.Handler.class, MessageHandleGolfCart.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(MessageMysticAmulet.Handler.class, MessageMysticAmulet.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(MessageKillDroid.Handler.class, MessageKillDroid.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(MessageUpdateSpeedBoots.Handler.class, MessageUpdateSpeedBoots.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(MessageUpdatePlayerPosition.Handler.class, MessageUpdatePlayerPosition.class, nextID(), Side.SERVER);
    	INSTANCE.registerMessage(MessageUpdatePlayerMotionClient.Handler.class, MessageUpdatePlayerMotionClient.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(MessageOpenGUI.Handler.class, MessageOpenGUI.class, nextID(), Side.CLIENT);
    	INSTANCE.registerMessage(MessageUpdatePlayerPositionClient.Handler.class, MessageUpdatePlayerPositionClient.class, nextID(), Side.CLIENT);

    }
}