package com.kashdeya.underthesea.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlueBerry extends ItemFood {
	
	public BlueBerry(int amount, float saturation, boolean isWolfFood)
	  {
	    super(amount, saturation, isWolfFood);
	    this.setCreativeTab(CreativeTabs.FOOD);
	    this.setAlwaysEdible();
	    setUnlocalizedName("berry");
        setRegistryName("berry");
        GameRegistry.register(this);
	  }

}
