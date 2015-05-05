package net.ilexiconn.llibrary.boundingbox;

import net.minecraft.util.AxisAlignedBB;

public class BoundingBoxHelper
{
    public static AxisAlignedBB copy(AxisAlignedBB box)
    {
        return AxisAlignedBB.fromBounds(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }
}
