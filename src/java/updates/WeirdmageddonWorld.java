package updates;

import init.DimensionInit;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.IRenderHandler;

public class WeirdmageddonWorld extends WorldProvider{

	@Override
	public DimensionType getDimensionType() {
		return DimensionInit.NIGHTMAREREALM;
	}

	@Override
	public boolean isSurfaceWorld() {
		return true;
	}


	@Override
	public void setSkyRenderer(IRenderHandler skyRenderer) {
		// TODO Auto-generated method stub
		super.setSkyRenderer(skyRenderer);
	}
}
