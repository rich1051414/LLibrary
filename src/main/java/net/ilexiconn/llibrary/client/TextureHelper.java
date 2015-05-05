package net.ilexiconn.llibrary.client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.commons.io.IOUtils;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

/**
 * Player skin tools, not stable and only works in 1.7.10.
 * 
 * @author iLexiconn
 */
@SideOnly(Side.CLIENT)
public class TextureHelper
{
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final TextureManager renderEngine = mc.renderEngine;

    public static BufferedImage getPlayerSkin(AbstractClientPlayer player)
    {
        BufferedImage bufferedImage = null;
        InputStream inputStream = null;
        Map map = mc.func_152342_ad().func_152788_a(player.getGameProfile());
        ITextureObject texture;

        try
        {
            if (map.containsKey(Type.SKIN))
                texture = renderEngine.getTexture(mc.func_152342_ad().func_152792_a((MinecraftProfileTexture) map.get(Type.SKIN), Type.SKIN));
            else
                texture = renderEngine.getTexture(player.getLocationSkin());

            if (texture instanceof ThreadDownloadImageData)
            {
                bufferedImage = ObfuscationReflectionHelper.getPrivateValue(ThreadDownloadImageData.class, (ThreadDownloadImageData) texture, "field_110560_d", "bufferedImage");
            }
            else if (texture instanceof DynamicTexture)
            {
                int width = ObfuscationReflectionHelper.getPrivateValue(DynamicTexture.class, (DynamicTexture) texture, "field_94233_j", "width");
                int height = ObfuscationReflectionHelper.getPrivateValue(DynamicTexture.class, (DynamicTexture) texture, "field_94234_k", "height");
                bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                bufferedImage.setRGB(0, 0, width, height, ((DynamicTexture) texture).getTextureData(), 0, width);
            }
            else
            {
                inputStream = mc.getResourceManager().getResource(AbstractClientPlayer.locationStevePng).getInputStream();
                bufferedImage = ImageIO.read(inputStream);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            IOUtils.closeQuietly(inputStream);
        }

        return bufferedImage;
    }

    public static void setPlayerSkin(AbstractClientPlayer entityPlayer, BufferedImage skin)
    {
        if (!hasBackup(entityPlayer))
            backupPlayerSkin(entityPlayer);
        uploadPlayerSkin(entityPlayer, skin);
    }

    public static void resetPlayerSkin(AbstractClientPlayer entityPlayer)
    {
        BufferedImage image = getOriginalPlayerSkin(entityPlayer);
        if (image != null)
            uploadPlayerSkin(entityPlayer, image);
    }

    public static boolean hasBackup(AbstractClientPlayer player)
    {
        return new File("llibrary" + File.separator + "skin-backups" + File.separator + player.getName() + ".png").exists();
    }

    private static void backupPlayerSkin(AbstractClientPlayer entityPlayer)
    {
        BufferedImage bufferedImage = getPlayerSkin(entityPlayer);

        File file = new File("llibrary" + File.separator + "skin-backups");
        file.mkdir();
        File skinFile = new File(file, entityPlayer.getName() + ".png");
        try
        {
            skinFile.createNewFile();
            if (bufferedImage != null)
                ImageIO.write(bufferedImage, "PNG", skinFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void uploadPlayerSkin(AbstractClientPlayer player, BufferedImage bufferedImage)
    {
        ITextureObject textureObject = renderEngine.getTexture(player.getLocationSkin());

        if (textureObject == null)
        {
            textureObject = new ThreadDownloadImageData(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtils.stripControlCodes(player.getName())), AbstractClientPlayer.locationStevePng, new ImageBufferDownload());
            renderEngine.loadTexture(player.getLocationSkin(), textureObject);
        }

        uploadTexture(textureObject, bufferedImage);
    }

    private static BufferedImage getOriginalPlayerSkin(AbstractClientPlayer entityPlayer)
    {
        File file = new File("llibrary" + File.separator + "skin-backups" + File.separator + entityPlayer.getName() + ".png");
        BufferedImage bufferedImage = null;

        try
        {
            if (file.exists())
                bufferedImage = ImageIO.read(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return bufferedImage;
    }

    private static void uploadTexture(ITextureObject textureObject, BufferedImage bufferedImage)
    {
        TextureUtil.uploadTextureImage(textureObject.getGlTextureId(), bufferedImage);
    }
}
