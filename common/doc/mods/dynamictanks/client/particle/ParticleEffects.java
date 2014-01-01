package doc.mods.dynamictanks.client.particle;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class ParticleEffects
{
    private static Minecraft mc = Minecraft.getMinecraft();
    private static World theWorld = mc.theWorld;

    public static EntityFX spawnParticle(String particleName, double par2, double par4, double par6, double par8, double par10, double par12, float red, float green, float blue)
    {
        if (mc != null && mc.renderViewEntity != null && mc.effectRenderer != null)
        {
            int var14 = mc.gameSettings.particleSetting;

            if (var14 == 1 && theWorld.rand.nextInt(3) == 0)
            {
                var14 = 2;
            }

            double var15 = mc.renderViewEntity.posX - par2;
            double var17 = mc.renderViewEntity.posY - par4;
            double var19 = mc.renderViewEntity.posZ - par6;
            EntityFX var21 = null;
            double var22 = 16.0D;

            if (var15 * var15 + var17 * var17 + var19 * var19 > var22 * var22)
            {
                return null;
            }
            else if (var14 > 1)
            {
                return null;
            }
            else
            {
                if (particleName.equals("coloredSwirl"))
                {
                    var21 = new MultiColorStarFX(theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12);
                }
                else if (particleName.equals("coloredSmoke")) 
                {
                	var21 = new ColoredGasFX(theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12, red, green, blue);
                }

                mc.effectRenderer.addEffect((EntityFX)var21);
                return (EntityFX)var21;
            }
        }

        return null;
    }
    
    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }
    
    public static String int2Rgb(int intColor) {
    	return String.format("#%06X", (0xFFFFFF & intColor));
    }
}
