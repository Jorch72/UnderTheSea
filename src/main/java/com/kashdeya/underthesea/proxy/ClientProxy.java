package com.kashdeya.underthesea.proxy;

import com.kashdeya.underthesea.main.Reference;
import com.kashdeya.underthesea.main.UnderTheSea;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerItems() {
		for(int i = 0; i <=15;i++) {
            registerItemModel(Item.getItemFromBlock(UnderTheSea.seaweed), i, Reference.MOD_ID+":seaweed"+(i));
        }
		for(int i = 0; i <=1;i++) {
            registerItemModel(Item.getItemFromBlock(UnderTheSea.rock), i, Reference.MOD_ID+":rock"+(i+1));
        }
		registerItemModel(UnderTheSea.berry, 0, Reference.MOD_ID+":berry");
    }
	
	public static void registerItemModel(Item item, int meta, String name) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(name, "inventory"));
    }
	
	public static void InventoryBlockRender(Block block, int meta, String name) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(name, "inventory"));
	}
	
}
