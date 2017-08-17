package com.kashdeya.underthesea.main;

import com.kashdeya.underthesea.blocks.RockBlock;
import com.kashdeya.underthesea.blocks.SeaweedBlock;
import com.kashdeya.underthesea.config.Config;
import com.kashdeya.underthesea.items.BlueBerry;
import com.kashdeya.underthesea.proxy.CommonProxy;
import com.kashdeya.underthesea.util.OreDict;
import com.kashdeya.underthesea.util.Recipes;
import com.kashdeya.underthesea.world.DownUnder;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)

public class UnderTheSea {
	
	@Instance(Reference.MOD_ID)
    public static UnderTheSea instance;
	
    public static SeaweedBlock seaweed;
    public static RockBlock rock;
    public static BlueBerry berry;
    
    @SidedProxy(clientSide=Reference.PROXY_CLIENT, serverSide=Reference.PROXY_COMMON)
	public static CommonProxy PROXY;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        // Configs
    	Config.initMainConfigs();
    	// Recipes
    	Recipes.registerRecipes();
        // Blocks
        seaweed = new SeaweedBlock();
        rock = new RockBlock();
        // Food
        berry = new BlueBerry(1, 0.3F, false);
        // Register items
        PROXY.registerItems();
        // Events
        MinecraftForge.EVENT_BUS.register(instance);
        // World generation
        GameRegistry.registerWorldGenerator(new DownUnder(),0);
        // Ore Dict
        OreDict.init();
    }
    
}
