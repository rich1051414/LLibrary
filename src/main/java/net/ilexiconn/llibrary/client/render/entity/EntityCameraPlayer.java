package net.ilexiconn.llibrary.client.render.entity;

import net.minecraft.entity.player.EntityPlayer;

public class EntityCameraPlayer extends EntityPlayer
{
    private EntityPlayer player;

    public EntityCameraPlayer(EntityPlayer player)
    {
        super(player.worldObj, player.getGameProfile());
        this.player = player;
    }

    public float getEyeHeight()
    {
        return super.getEyeHeight() + PlayerOffsetRenderer.getOffsetY(player);
    }

    @Override
    public boolean isSpectator()
    {
        return true;
    }
}
