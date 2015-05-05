package net.ilexiconn.llibrary.client.render.entity;

import net.ilexiconn.llibrary.boundingbox.BoundingBoxHelper;
import net.ilexiconn.llibrary.entity.multipart.EntityPart;
import net.ilexiconn.llibrary.entity.multipart.IEntityMultiPart;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

/**
 * Basic class for debugging entity classes with MultiParts
 * 
 * @author iLexiconn
 */
@SideOnly(Side.CLIENT)
public abstract class RenderMultiPart extends RenderLiving
{
    public RenderMultiPart(RenderManager renderManager, ModelBase model, float shadow)
    {
        super(renderManager, model, shadow);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float rotationYaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, rotationYaw, partialTicks);
        doRender((IEntityMultiPart) entity, x, y, z, rotationYaw, partialTicks);
    }

    public void doRender(IEntityMultiPart entity, double x, double y, double z, float rotationYaw, float partialTicks)
    {
        if (renderManager.isDebugBoundingBox())
        {
            GL11.glDepthMask(false);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            for (EntityPart e : entity.getParts())
                RenderGlobal.drawOutlinedBoundingBox(BoundingBoxHelper.copy(e.getEntityBoundingBox()).offset(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ), 0xffffff);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthMask(true);
        }
    }
}
