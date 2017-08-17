package com.kashdeya.underthesea.blocks;

import static net.minecraft.block.BlockLiquid.LEVEL;
import static net.minecraft.util.EnumFacing.WEST;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.kashdeya.underthesea.handlers.ConfigHandler;
import com.kashdeya.underthesea.util.ItemBlockMeta;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SeaweedBlock extends Block implements IPlantable {
	
    public static final EnumPlantType SEAWEED = EnumPlantType.getPlantType("Seaweed");
    public static final PropertyInteger VARIANTS = PropertyInteger.create("variants", 0, 15);
    protected static final AxisAlignedBB CORAL_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);

    public SeaweedBlock() {
        super(Material.WATER);
        setTickRandomly(true);
        setCreativeTab(CreativeTabs.DECORATIONS);
        setHardness(0.0F);
        setSoundType(SoundType.PLANT);
        setUnlocalizedName("seaweed");
        setRegistryName("seaweed");
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlockMeta(this), getRegistryName());
        setDefaultState(getDefaultState().withProperty(VARIANTS, 0).withProperty(LEVEL, 15));
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i <= 15; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this,1,state.getValue(VARIANTS));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{LEVEL, VARIANTS});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(VARIANTS, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return  state.getValue(VARIANTS);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CORAL_AABB;
    }
    
    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighborBlock) {
        this.checkAndDropBlock(world, pos, state);
    }
    
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        this.checkAndDropBlock(worldIn, pos, state);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(VARIANTS);
    }
    
    protected boolean checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (this.canBlockStay(worldIn, pos, state)) {
            return true;
        } else {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockState(pos, Blocks.WATER.getDefaultState(), 3);
            return false;
        }
    }
    
    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState down = worldIn.getBlockState(pos.down());
        return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos.down());
        Block block = state.getBlock();
        
        if (worldIn.getBlockState(pos.up(2)).getMaterial() != Material.WATER) return false;
        if (block.canSustainPlant(state, worldIn, pos.down(), EnumFacing.UP, this)) return true;
        if (block == this) {
            int variant = state.getValue(VARIANTS);
            return variant > 3;
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand) {
        if (world.getBlockState(pos.up()).getMaterial() == Material.WATER) {
            double offset = 0.05D;
            for (int i = 0; i < 3; i++) {
                double x1 = (pos.getX() + rand.nextDouble());
                double y1 = (pos.getY() + rand.nextDouble());
                double z1 = (pos.getZ() + rand.nextDouble());
                if (i == 0 && !world.getBlockState(pos.up()).isOpaqueCube()) {
                    y1 = (double) (pos.getY() + 1) + offset;
                }

                if (i == 1 && !world.getBlockState(pos.down()).isOpaqueCube()) {
                    y1 = (double) (pos.getY() + 0) - offset;
                }

                if (i == 2 && !world.getBlockState(pos.offset(EnumFacing.SOUTH)).isOpaqueCube()) {
                    z1 = (double) (pos.getZ() + 1) + offset;
                }

                if (i == 3 && !world.getBlockState(pos.offset(EnumFacing.NORTH)).isOpaqueCube()) {
                    z1 = (double) (pos.getZ() + 0) - offset;
                }

                if (i == 4 && !world.getBlockState(pos.offset(EnumFacing.EAST)).isOpaqueCube()) {
                    x1 = (double) (pos.getX() + 1) + offset;
                }

                if (i == 5 && !world.getBlockState(pos.offset(WEST)).isOpaqueCube()) {
                    x1 = (double) (pos.getX() + 0) - offset;
                }

                if (x1 < (double) pos.getX() || x1 > (double) (pos.getX() + 1) || y1 < 0.0D || y1 > (double) (pos.getY() + 1) || z1 < (double) pos.getZ() || z1 > (double) (pos.getZ() + 1)) {
                    world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, x1, y1, z1, 0, 0, 0);
                }
            }
        }
    }


    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        return canPlaceBlockAt(world, pos);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return ConfigHandler.seaweedGlow;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return SEAWEED;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos);
    }


}
