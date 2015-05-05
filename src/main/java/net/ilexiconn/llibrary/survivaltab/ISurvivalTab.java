package net.ilexiconn.llibrary.survivaltab;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Interface for custom survival tabs, register with TabRegistry.registerSurvivalTab().
 * 
 * @author iLexiconn
 */
public interface ISurvivalTab
{
    /**
     * @return unlocalized name string
     */
    public String getTabName();

    /**
     * @return itemstack with item or block to be displayed
     */
    public ItemStack getTabIcon();

    /**
     * Called when the survival tab is clicked.
     * 
     * @param player
     *            the player opening the gui
     */
    @SideOnly(Side.CLIENT)
    public void openContainerGui(EntityPlayer player);

    /**
     * @return class of the container gui.
     * @see net.minecraft.client.gui.inventory.GuiContainer
     */
    @SideOnly(Side.CLIENT)
    public Class<? extends GuiContainer> getContainerGuiClass();
}
