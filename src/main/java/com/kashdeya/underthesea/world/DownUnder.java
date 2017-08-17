package com.kashdeya.underthesea.world;

import java.util.Random;

import com.kashdeya.underthesea.blocks.RockBlock;
import com.kashdeya.underthesea.blocks.SeaweedBlock;
import com.kashdeya.underthesea.handlers.ConfigHandler;
import com.kashdeya.underthesea.main.UnderTheSea;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class DownUnder implements IWorldGenerator {
	
    private WorldGenerator genBigRockCluster;
    private Block genBlock = null;

    public DownUnder() {
    	genBigRockCluster = new WorldGenBigRockCluster();
    	setupBlockforGeneration();
    }

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimension() == 0) {
            this.runGenerator(world, rand, chunkX, chunkZ, 0, world.getSeaLevel() - 4);
        }
    }

    private void runGenerator(World world, Random rand, int chunk_X, int chunk_Z, int minHeight, int maxHeight) {
        if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
            throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
        if (rand.nextInt(ConfigHandler.rockSpread) == 0) {
            int x = chunk_X * 16 + rand.nextInt(16) + 8;
            int z = chunk_Z * 16 + rand.nextInt(16) + 8;
            int y = MathHelper.clamp_int(getTopSolidOrLiquidBlock(world, new BlockPos(x, 64, z)).getY(), minHeight, maxHeight);
            BlockPos position = new BlockPos(x, y, z);
            genBigRockCluster.generate(world, rand, position);
        }
    }

    /*
     * Setup the block the seaweed generates on based on confighandler
     */
    private void setupBlockforGeneration()
    {
        if(genBlock == null)
        {
        	Block getBlock = Block.getBlockFromName(ConfigHandler.placedOnBlock);
        	
        	if(getBlock != null) genBlock = getBlock;
        	else genBlock = Blocks.BEDROCK;
        }
    }
    
    public class WorldGenBigRockCluster extends WorldGenerator {
        @Override
        public boolean generate(World worldIn, Random rand, BlockPos position) 
        {

        	
            int r = MathHelper.clamp_int(rand.nextInt(16), 4, 16);
            int k = MathHelper.clamp_int(rand.nextInt(6), 2, 16);
            int l = MathHelper.clamp_int(rand.nextInt(3), 1, 16);
            for (int x = -r; x <= r; x++)
                for (int z = -r; z <= r; z++) 
                {
                    BlockPos pos = getTopSolidOrLiquidBlock(worldIn, new BlockPos(position.getX()+x,position.getY(),position.getZ()+z));
                    if(((x*x)/l)+((z*z)/k) > r*r || rand.nextInt(r) < r/2 || pos.getY() > worldIn.getSeaLevel()-5)
                        continue;
                    
                    Block block = worldIn.getBlockState(pos).getBlock();

                    
                    if (block == genBlock) 
                    {
                    	boolean placedStone = false;
                    	// First lay the stone down... 
                    	// int j sets first block pos to place
                    	// Code Fixed by GlenDeathrow Kappa
                    	int j = 1;
                    	for ( j = 1; j <= rand.nextInt(ConfigHandler.rockHeight); j++) 
                    	{
                    		// You may need to add more checks.. I don't know, I don't do generation much.
                    		if(worldIn.setBlockState(pos.up(j), UnderTheSea.rock.getDefaultState().withProperty(RockBlock.VARIANTS, rand.nextInt(2))))
                    			placedStone = true;
                    	}
                    	
                    	if(placedStone)
                    	{
                    		// now set the seaweed. 
                    		IBlockState variant = UnderTheSea.seaweed.getDefaultState().withProperty(SeaweedBlock.VARIANTS, rand.nextInt(16));
                    		for (int i = 0; i <= rand.nextInt(ConfigHandler.seaweedHeight); i++) 
                    		{
                    			// Not necessary but easier to read
                    			BlockPos seaweedpos = pos.up(j+i);
                        	
                    			if (UnderTheSea.seaweed.canBlockStay(worldIn, seaweedpos, variant)) 
                    			{
                    				worldIn.setBlockState(seaweedpos, variant);
                    			}
                    		}
                    	}
                    }
                    
                }
            return true;

        }
    }

    public static BlockPos getTopSolidOrLiquidBlock(World world, BlockPos pos) {
        Chunk chunk = world.getChunkFromBlockCoords(pos);
        BlockPos blockpos;
        BlockPos blockpos1;

        for (blockpos = new BlockPos(pos.getX(), chunk.getTopFilledSegment() + 16, pos.getZ()); blockpos.getY() >= 0; blockpos = blockpos1) {
            blockpos1 = blockpos.down();
            IBlockState state = chunk.getBlockState(blockpos1);
            if (state.getMaterial().isSolid() && !state.getMaterial().isLiquid() && !state.getBlock().isLeaves(state, world, blockpos1) && !state.getBlock().isFoliage(world, blockpos1)) {
                break;
            }
        }
        return blockpos.down();
    }
}
