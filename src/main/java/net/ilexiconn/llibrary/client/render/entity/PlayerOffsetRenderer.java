package net.ilexiconn.llibrary.client.render.entity;

import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Maps;

@SideOnly(Side.CLIENT)
public class PlayerOffsetRenderer extends EntityRenderer
{
    public Minecraft mc;
    private static Map<EntityPlayer, Float> offsetY = Maps.newHashMap();

    public PlayerOffsetRenderer(Minecraft minecraft)
    {
        super(minecraft, minecraft.getResourceManager());
        mc = minecraft;
    }

    public void updateCameraAndRender(float partialTick)
    {
        EntityPlayerSP player = mc.thePlayer;

        if (player == null || player.isPlayerSleeping())
        {
            super.updateCameraAndRender(partialTick);
            return;
        }

        Float offsetForPlayer = offsetY.get(player);

        if (offsetForPlayer == null)
        {
            offsetForPlayer = 1.62f;
            offsetY.put(player, 1.62f);
        }

        mc.setRenderViewEntity(new EntityCameraPlayer(player));
        super.updateCameraAndRender(partialTick);
        mc.setRenderViewEntity(player);
    }

    public void getMouseOver(float partialTick)
    {
        if (mc.thePlayer == null || mc.thePlayer.isPlayerSleeping())
        {
            super.getMouseOver(partialTick);
            return;
        }

        Float offsetForPlayer = offsetY.get(mc.thePlayer);

        if (offsetForPlayer == null)
        {
            offsetForPlayer = 1.62f;
            offsetY.put(mc.thePlayer, 1.62f);
        }

        mc.thePlayer.posY += offsetForPlayer;
        mc.thePlayer.prevPosY += offsetForPlayer;
        mc.thePlayer.lastTickPosY += offsetForPlayer;
        super.getMouseOver(partialTick);
        mc.thePlayer.posY -= offsetForPlayer;
        mc.thePlayer.prevPosY -= offsetForPlayer;
        mc.thePlayer.lastTickPosY -= offsetForPlayer;
    }

    public static void setOffsetY(EntityPlayer player, float f)
    {
        offsetY.put(player, f);
    }

    public static float getOffsetY(EntityPlayer entityPlayer)
    {
        return offsetY.get(entityPlayer);
    }
}
