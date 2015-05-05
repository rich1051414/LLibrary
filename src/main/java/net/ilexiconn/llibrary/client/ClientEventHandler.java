package net.ilexiconn.llibrary.client;

import java.util.List;

import net.ilexiconn.llibrary.block.IHighlightedBlock;
import net.ilexiconn.llibrary.boundingbox.BoundingBoxHelper;
import net.ilexiconn.llibrary.client.gui.GuiSurvivalTab;
import net.ilexiconn.llibrary.config.ConfigHelper;
import net.ilexiconn.llibrary.survivaltab.SurvivalTab;
import net.ilexiconn.llibrary.survivaltab.TabHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ClientEventHandler
{
    @SubscribeEvent
    public void blockHighlight(DrawBlockHighlightEvent event)
    {
        MovingObjectPosition target = event.target;

        if (target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
        {
            BlockPos blockPos = target.getBlockPos();
            int x = blockPos.getX();
            int y = blockPos.getY();
            int z = blockPos.getZ();

            Block block = event.player.worldObj.getBlockState(blockPos).getBlock();

            if (block instanceof IHighlightedBlock)
            {
                List<AxisAlignedBB> bounds = ((IHighlightedBlock) block).getHighlightedBoxes(event.player.worldObj, x, y, z, event.player);

                Vec3 pos = event.player.getPositionVector();

                GL11.glEnable(GL11.GL_BLEND);

                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glColor4f(0f, 0f, 0f, 0.4f);
                GL11.glLineWidth(2f);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDepthMask(false);

                for (AxisAlignedBB box : bounds)
                {
                    RenderGlobal.drawOutlinedBoundingBox(BoundingBoxHelper.copy(box).offset(x, y, z).offset(-pos.xCoord, -pos.yCoord, -pos.zCoord), -1);
                }

                GL11.glDepthMask(true);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_BLEND);

                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void itemTooltip(ItemTooltipEvent event)
    {
        if (Minecraft.getMinecraft().gameSettings.advancedItemTooltips)
        {
            event.toolTip.add(EnumChatFormatting.DARK_GRAY + "" + Item.itemRegistry.getNameForObject(event.itemStack.getItem()));
        }
    }

    @SubscribeEvent
    public void initGui(GuiScreenEvent.InitGuiEvent.Post event)
    {
        for (SurvivalTab survivalTab : TabHelper.getSurvivalTabs())
        {
            if (survivalTab.getSurvivalTab().getContainerGuiClass().isInstance(event.gui))
            {
                int count = 2;

                for (SurvivalTab tab : TabHelper.getSurvivalTabs())
                {
                    event.buttonList.add(new GuiSurvivalTab(count, tab));
                    count++;
                }
            }
        }
    }

    @SubscribeEvent
    public void saveConfig(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (ConfigHelper.hasConfiguration(event.modID))
        {
            ConfigHelper.getConfigContainer(event.modID).getConfigHandler().loadConfig(ConfigHelper.getConfigContainer(event.modID).getConfiguration());
            ConfigHelper.getConfigContainer(event.modID).getConfiguration().save();
        }
    }

    @SubscribeEvent
    public void renderTick(TickEvent.RenderTickEvent event)
    {
        /*
         * Minecraft mc = Minecraft.getMinecraft(); if (mc.theWorld != null) { if (event.phase == TickEvent.Phase.START) { if (renderer == null) renderer = new PlayerOffsetRenderer(mc); if (mc.entityRenderer != renderer) { prevRenderer = mc.entityRenderer; mc.entityRenderer = renderer; } } else if (prevRenderer != null && mc.entityRenderer != prevRenderer) mc.entityRenderer = prevRenderer; } else if (prevRenderer != null && mc.entityRenderer != prevRenderer) mc.entityRenderer = prevRenderer;
         */
    }

    @SubscribeEvent
    public void renderPlayerPre(RenderPlayerEvent.Pre event)
    {
        /*
         * if (PlayerOffsetRenderer.getOffsetY(Minecraft.getMinecraft().thePlayer) != 1.62f) { GL11.glPushMatrix(); GL11.glTranslatef(0f, -6.325f, 0f); }
         */
    }

    @SubscribeEvent
    public void renderPlayerPost(RenderPlayerEvent.Post event)
    {
        /*
         * if (PlayerOffsetRenderer.getOffsetY(Minecraft.getMinecraft().thePlayer) != 1.62f) GL11.glPopMatrix();
         */
    }
}
