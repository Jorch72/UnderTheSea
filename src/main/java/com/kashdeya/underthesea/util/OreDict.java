package com.kashdeya.underthesea.util;

import com.kashdeya.underthesea.main.UnderTheSea;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDict {
	
	public static void init(){
		OreDictionary.registerOre("dyeBlack", new ItemStack(UnderTheSea.seaweed,1,0));
        OreDictionary.registerOre("dyeRed", new ItemStack(UnderTheSea.seaweed,1,1));
        OreDictionary.registerOre("dyeGreen", new ItemStack(UnderTheSea.seaweed,1,2));
        OreDictionary.registerOre("dyeBrown", new ItemStack(UnderTheSea.seaweed,1,3));
        OreDictionary.registerOre("dyeBlue", new ItemStack(UnderTheSea.seaweed,1,4));
        OreDictionary.registerOre("dyePurple", new ItemStack(UnderTheSea.seaweed,1,5));
        OreDictionary.registerOre("dyeCyan", new ItemStack(UnderTheSea.seaweed,1,6));
        OreDictionary.registerOre("dyeLightGray", new ItemStack(UnderTheSea.seaweed,1,7));
        OreDictionary.registerOre("dyeGray", new ItemStack(UnderTheSea.seaweed,1,8));
        OreDictionary.registerOre("dyePink", new ItemStack(UnderTheSea.seaweed,1,9));
        OreDictionary.registerOre("dyeLime", new ItemStack(UnderTheSea.seaweed,1,10));
        OreDictionary.registerOre("dyeYellow", new ItemStack(UnderTheSea.seaweed,1,11));
        OreDictionary.registerOre("dyeLightBlue", new ItemStack(UnderTheSea.seaweed,1,12));
        OreDictionary.registerOre("dyeMagenta", new ItemStack(UnderTheSea.seaweed,1,13));
        OreDictionary.registerOre("dyeOragne", new ItemStack(UnderTheSea.seaweed,1,14));
        OreDictionary.registerOre("dyeWhite", new ItemStack(UnderTheSea.seaweed,1,15));
	}

}
