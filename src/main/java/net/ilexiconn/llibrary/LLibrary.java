package net.ilexiconn.llibrary;

import net.ilexiconn.llibrary.command.CommandLLibrary;
import net.ilexiconn.llibrary.config.ConfigHelper;
import net.ilexiconn.llibrary.config.LLibraryConfigHandler;
import net.ilexiconn.llibrary.proxy.ServerProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = "llibrary", name = "LLibrary", version = "${version}", guiFactory = "net.ilexiconn.llibrary.config.LLibraryConfigFactory")
public class LLibrary
{
    @Mod.Instance("llibrary")
    public static LLibrary instance;

    @SidedProxy(serverSide = "net.ilexiconn.llibrary.proxy.ServerProxy", clientSide = "net.ilexiconn.llibrary.proxy.ClientProxy")
    public static ServerProxy proxy;

    @Mod.EventHandler
    private void preInit(FMLPreInitializationEvent event)
    {
        ConfigHelper.registerConfigHandler("llibrary", event.getSuggestedConfigurationFile(), new LLibraryConfigHandler());
        proxy.preInit();
    }

    @Mod.EventHandler
    private void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandLLibrary());
    }
}
