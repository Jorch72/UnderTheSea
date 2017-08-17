package com.kashdeya.underthesea.config;

import java.io.File;

import com.kashdeya.underthesea.handlers.ConfigHandler;
import com.kashdeya.underthesea.main.Reference;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;

public class Config {

public static Configuration config;
	
	public static final File configDir = new File("config");

	public static final String configVersion = Reference.VERSION;
	
	public static void initMainConfigs()
	{
		
		File f = new File(configDir, "UnderTheSea.cfg");
        config = new Configuration(f, configVersion);
        
        config.load();
        String category;
		
		category = "UnderTheSea";
		
		config.addCustomCategoryComment(category, "Darling it's better, Down where it's wetter, Take it from me!");
		
		ConfigHandler.rockHeight = config.getInt("Rock Height", category,5,1,10,"Rock Height");
		ConfigHandler.seaweedHeight = config.getInt("Seaweed Height", category,15,1,15,"Seaweed Height");
		ConfigHandler.rockSpread = config.getInt("Rock Spread", category,5,2,10,"Rock Spread");
		ConfigHandler.seaweedGlow = config.getInt("Seaweed  Glow", category,8,0,15,"Seaweed Glow");
		ConfigHandler.placedOnBlock = config.getString("Spawnable Block", category, Blocks.BEDROCK.getRegistryName().toString(), "Allows you to change what block you are using at the bottom of your water world, if you are not using bedrock!\r\n" + 
				"");
		if (config.hasChanged())
	        config.save();    
		}
}
