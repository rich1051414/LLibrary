package net.ilexiconn.llibrary.event;

import net.minecraft.client.model.ModelBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class Render3dItemEvent extends Event
{
    public final Item item;
    public final ModelBase model;
    public final ResourceLocation texture;

    public final float x;
    public final float y;
    public final float z;

    public Render3dItemEvent(Item t, ModelBase m, ResourceLocation r, float i, float j, float k)
    {
        item = t;
        model = m;
        texture = r;

        x = i;
        y = j;
        z = k;
    }
}
