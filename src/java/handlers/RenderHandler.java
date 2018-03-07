package handlers;

import entity.EntityForget;
import entity.EntityGnome;
import entity.EntityGolfCart;
import entity.EntityHook;
import entity.EntityLight;
import entity.EntityTimeCopDundgren;
import entity.EntityTimeCopLolph;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import render.RenderFlashlight;
import render.RenderForget;
import render.RenderGnome;
import render.RenderGolfCart;
import render.RenderHook;
import render.RenderTimeCopDundgren;
import render.RenderTimeCopLolph;

public class RenderHandler {
	

	public static void registerEntityRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityGnome.class, new IRenderFactory<EntityGnome>()
		{
			@Override
			public Render<? super EntityGnome> createRenderFor(RenderManager manager)
			{
				return new RenderGnome(manager);
			}
			
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityForget.class, new IRenderFactory<EntityForget>()
		{
			@Override
			public Render<? super EntityForget> createRenderFor(RenderManager manager)
			{
				return new RenderForget(manager);
			}
			
		});

		
		RenderingRegistry.registerEntityRenderingHandler(EntityHook.class, new IRenderFactory<EntityHook>()
		{
			@Override
			public Render<? super EntityHook> createRenderFor(RenderManager manager)
			{
				return new RenderHook(manager);
			}
			
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityLight.class, new IRenderFactory<EntityLight>()
		{
			@Override
			public Render<? super EntityLight> createRenderFor(RenderManager manager)
			{
				return new RenderFlashlight(manager);
			}
			
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityTimeCopLolph.class, new IRenderFactory<EntityTimeCopLolph>()
		{
			@Override
			public Render<? super EntityTimeCopLolph> createRenderFor(RenderManager manager)
			{
				return new RenderTimeCopLolph(manager);
			}
			
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityTimeCopDundgren.class, new IRenderFactory<EntityTimeCopDundgren>()
		{
			@Override
			public Render<? super EntityTimeCopDundgren> createRenderFor(RenderManager manager)
			{
				return new RenderTimeCopDundgren(manager);
			}
			
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityGolfCart.class, new IRenderFactory<EntityGolfCart>()
		{
			@Override
			public Render<? super EntityGolfCart> createRenderFor(RenderManager manager)
			{
				return new RenderGolfCart(manager);
			}
			
		});
		
	}
}
