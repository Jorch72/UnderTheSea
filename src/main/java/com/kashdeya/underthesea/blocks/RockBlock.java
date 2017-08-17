package com.kashdeya.underthesea.blocks;

import java.util.List;
import java.util.Random;

import com.kashdeya.underthesea.util.ItemBlockMeta;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RockBlock extends Block  {
	
	public static final PropertyInteger VARIANTS = PropertyInteger.create("variants", 0, 1);
    
    public RockBlock() {
        super(Material.ROCK);
        setCreativeTab(CreativeTabs.DECORATIONS);
        setHardness(1.05F);
        setResistance(10.0F);
        setHarvestLevel("pickaxe", 0);
        setSoundType(SoundType.STONE);
        setUnlocalizedName("rock");
        setRegistryName("rock");
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlockMeta(this),getRegistryName());
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i <=1; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
    
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this,1,this.getMetaFromState(state));
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{VARIANTS});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(VARIANTS, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANTS);
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(Blocks.DIRT);
    }
	
	@Override
    public boolean canDropFromExplosion(Explosion explosionIn)
    {
        return true;
    }
	
	@Override
    public int quantityDropped(Random rand)
    {
        return 1;
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return plantable.getPlantType(world,pos.offset(direction)) == SeaweedBlock.SEAWEED;
    }
}
