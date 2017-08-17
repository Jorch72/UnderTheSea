package com.kashdeya.underthesea.util;

import com.kashdeya.underthesea.main.UnderTheSea;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Recipes {
	
	public static void registerRecipes(){
		GameRegistry.addShapelessRecipe(new ItemStack(UnderTheSea.berry), new Object[] {new ItemStack(UnderTheSea.seaweed, 1, 4)});
	}

}
