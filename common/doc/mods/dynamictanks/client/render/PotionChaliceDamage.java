package doc.mods.dynamictanks.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

import org.lwjgl.opengl.GL11;

import doc.mods.dynamictanks.helpers.CPotionHelper;

public class PotionChaliceDamage implements IItemRenderer
{
    private static RenderItem renderItem = new RenderItem();

    float actualPercent = 100;

    int[] cRed = { 56, 248, 217 };
    int[] cGreen = { 212, 255, 0};
    int[] cBlue = { 0, 59, 0 };

    @Override
    public boolean handleRenderType(ItemStack itemStack, ItemRenderType type)
    {
        return type == ItemRenderType.INVENTORY;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data)
    {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        float percent = 15;

        if (itemStack.stackTagCompound != null && itemStack.stackTagCompound.hasKey("damage"))
        {
            percent = (((itemStack.stackTagCompound.getFloat("damage") / CPotionHelper.maxExistance) * 15));
            actualPercent = (100 - ((itemStack.stackTagCompound.getFloat("damage") / CPotionHelper.maxExistance) * 100));
        }

        // ====================== Render item texture ======================
        Icon icon = itemStack.getIconIndex();
        renderItem.renderIcon(1, 0, icon, 16, 16);
        // ====================== Render OpenGL square shape ======================
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Tessellator tessellator = Tessellator.instance;
        // ====================== Black Bar ==========================
        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.setColorRGBA(0, 0, 0, 192);
        tessellator.addVertex(1, 1, 0);
        tessellator.addVertex(1, 15, 0);
        tessellator.addVertex(2, 15, 0);
        tessellator.addVertex(2, 1, 0);
        tessellator.draw();
        // ====================== Damage Bar ==========================
        tessellator.startDrawing(GL11.GL_QUADS);
        tessellator.setColorRGBA(whatColor(actualPercent)[0], whatColor(actualPercent)[1],
                                 whatColor(actualPercent)[2], 256);
        tessellator.addVertex(1, percent + 0.98, 0);
        tessellator.addVertex(1, 15, 0);
        tessellator.addVertex(2, 15, 0);
        tessellator.addVertex(2, percent + 0.98, 0);
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public int[] whatColor(float percent)
    {
        if (percent <= 100 && percent >= 70)
            return new int[] { cRed[0], cGreen[0], cBlue[0] };
        else if (percent <= 69 && percent >= 30)
            return new int[] { cRed[1], cGreen[1], cBlue[1] };

        return new int[] { cRed[2], cGreen[2], cBlue[2] };
    }
}
