package net.ilexiconn.llibrary.structure.util;

import net.minecraft.util.BlockPos;

/**
 * Stores all data needed for post-gen processing, specifically for custom 'hooks'
 * 
 * @author coolAlias
 */
public class BlockData
{
	private final BlockPos pos;
    private final int blockId, customData1, customData2;

    public BlockData(BlockPos p, int id, int data1, int data2)
    {
    	pos = p;
        blockId = id;
        customData1 = data1;
        customData2 = data2;
    }

    public final BlockPos getPos()
    {
        return this.pos;
    }

    public final int getBlockID()
    {
        return this.blockId;
    }

    public final int getCustomData1()
    {
        return this.customData1;
    }

    public final int getCustomData2()
    {
        return this.customData2;
    }
}
