package net.ilexiconn.llibrary.block;

import java.util.List;

import net.ilexiconn.llibrary.entity.EntityMountableBlock;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class BlockMountable extends BlockContainer
{
    private float mountPosX = 0.5f;
    private float mountPosY = 1f;
    private float mountPosZ = 0.5f;

    public BlockMountable(Material material)
    {
        super(material);
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();

            List<EntityMountableBlock> mountableBlocks = world.getEntitiesWithinAABB(EntityMountableBlock.class, AxisAlignedBB.fromBounds(x, y, z, x + 1f, y + 1f, z + 1f).expand(1f, 1f, 1f));
            for (EntityMountableBlock mountableBlock : mountableBlocks)
                if (mountableBlock.pos == pos)
                    return mountableBlock.interactFirst(player);

            float mountX = x + mountPosX;
            float mountY = y + mountPosY;
            float mountZ = z + mountPosZ;

            EntityMountableBlock mountableBlock = new EntityMountableBlock(world, pos, mountX, mountY, mountZ);
            world.spawnEntityInWorld(mountableBlock);
            return mountableBlock.interactFirst(player);
        }

        return true;
    }

    public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state)
    {
        if (!world.isRemote)
        {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();

            List<EntityMountableBlock> mountableBlocks = world.getEntitiesWithinAABB(EntityMountableBlock.class, AxisAlignedBB.fromBounds(x, y, z, x + 1f, y + 1f, z + 1f).expand(1f, 1f, 1f));
            for (EntityMountableBlock mountableBlock : mountableBlocks)
                mountableBlock.setDead();
        }
    }

    public void setMountingPosition(float x, float y, float z)
    {
        mountPosX = x;
        mountPosY = y;
        mountPosZ = z;
    }
}