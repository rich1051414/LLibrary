package net.ilexiconn.llibrary.survivaltab;

import net.ilexiconn.llibrary.config.ConfigHelper;
import net.ilexiconn.llibrary.config.LLibraryConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.commons.lang3.math.NumberUtils;

public class SurvivalTabInventory implements ISurvivalTab
{
    public String getTabName()
    {
        return "container.inventory";
    }

    public ItemStack getTabIcon()
    {
        String[] array = LLibraryConfigHandler.survivalInventoryItem.split(":");
        if (array.length < 2)
            return resetDefaultStack();
        Item item = GameRegistry.findItem(array[0], array[1]);
        if (item == null)
            return resetDefaultStack();

        ItemStack stack = new ItemStack(item);
        if (array.length == 3)
            stack.setItemDamage(NumberUtils.toInt(array[2]));

        return stack;
    }

    public ItemStack resetDefaultStack()
    {
        ConfigHelper.setProperty("llibrary", Configuration.CATEGORY_GENERAL, "survivalInventoryItem", "minecraft:book", Property.Type.STRING);
        return new ItemStack(Items.book);
    }

    @SideOnly(Side.CLIENT)
    public void openContainerGui(EntityPlayer player)
    {
        Minecraft.getMinecraft().displayGuiScreen(new GuiInventory(player));
    }

    @SideOnly(Side.CLIENT)
    public Class<? extends GuiContainer> getContainerGuiClass()
    {
        return GuiInventory.class;
    }
}
