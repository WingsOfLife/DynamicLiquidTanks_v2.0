
package doc.mods.dynamictanks.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class PotionMixerModel extends ModelBase
{
  //fields
    ModelRenderer Arm1;
    ModelRenderer Top;
    ModelRenderer Arm2;
    ModelRenderer Arm3;
    ModelRenderer Arm4;
    ModelRenderer Core;
    ModelRenderer Base;
  
  public PotionMixerModel()
  {
    textureWidth = 128;
    textureHeight = 64;
    
      Arm1 = new ModelRenderer(this, 0, 26);
      Arm1.addBox(-8F, -7F, -8F, 2, 9, 2);
      Arm1.setRotationPoint(0F, 26F, 0F);
      Arm1.setTextureSize(128, 64);
      Arm1.mirror = true;
      setRotation(Arm1, -0.3490659F, 0.0523599F, 0.3490659F);
      Top = new ModelRenderer(this, 64, 46);
      Top.addBox(-6F, -10F, -6F, 12, 2, 12);
      Top.setRotationPoint(0F, 24F, 0F);
      Top.setTextureSize(128, 64);
      Top.mirror = true;
      setRotation(Top, 0F, 0F, 0F);
      Arm2 = new ModelRenderer(this, 0, 15);
      Arm2.addBox(-8F, -7F, -8F, 2, 9, 2);
      Arm2.setRotationPoint(0F, 26F, 0F);
      Arm2.setTextureSize(128, 64);
      Arm2.mirror = true;
      setRotation(Arm2, -0.3490659F, 1.64061F, 0.3490659F);
      Arm3 = new ModelRenderer(this, 12, 26);
      Arm3.addBox(-8F, -7F, -8F, 2, 9, 2);
      Arm3.setRotationPoint(0F, 26F, 0F);
      Arm3.setTextureSize(128, 64);
      Arm3.mirror = true;
      setRotation(Arm3, -0.3490659F, -1.500983F, 0.3490659F);
      Arm4 = new ModelRenderer(this, 12, 15);
      Arm4.addBox(-8F, -7F, -8F, 2, 9, 2);
      Arm4.setRotationPoint(0F, 26F, 0F);
      Arm4.setTextureSize(128, 64);
      Arm4.mirror = true;
      setRotation(Arm4, -0.3490659F, -3.089233F, 0.3490659F);
      Core = new ModelRenderer(this, 26, 15);
      Core.addBox(-1F, -11F, -1F, 2, 9, 2);
      Core.setRotationPoint(0F, 24F, 0F);
      Core.setTextureSize(128, 64);
      Core.mirror = true;
      setRotation(Core, 0F, 0.7853982F, 0F);
      Base = new ModelRenderer(this, 0, 46);
      Base.addBox(-8F, -2F, -8F, 16, 2, 16);
      Base.setRotationPoint(0F, 24F, 0F);
      Base.setTextureSize(128, 64);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    Arm1.render(f5);
    Top.render(f5);
    Arm2.render(f5);
    Arm3.render(f5);
    Arm4.render(f5);
    Core.render(f5);
    Base.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
  }

}
