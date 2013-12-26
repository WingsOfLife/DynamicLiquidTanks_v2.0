package doc.mods.dynamictanks.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class FPCModel extends ModelBase
{
	//fields
	ModelRenderer Back;
	ModelRenderer Front;
	ModelRenderer Right;
	ModelRenderer Left;
	ModelRenderer Bottom;
	ModelRenderer Rotator2;

	public FPCModel()
	{
		textureWidth = 64;
		textureHeight = 128;

		Back = new ModelRenderer(this, 0, 0);
		Back.addBox(0F, 0F, 0F, 16, 12, 1);
		Back.setRotationPoint(-8F, 12F, 7F);
		Back.setTextureSize(64, 128);
		Back.mirror = true;
		setRotation(Back, 0F, 0F, 0F);
		Front = new ModelRenderer(this, 0, 0);
		Front.addBox(0F, 0F, 0F, 16, 12, 1);
		Front.setRotationPoint(-8F, 12F, -8F);
		Front.setTextureSize(64, 128);
		Front.mirror = true;
		setRotation(Front, 0F, 0F, 0F);
		Right = new ModelRenderer(this, 0, 28);
		Right.addBox(0F, 0F, 0F, 1, 12, 14);
		Right.setRotationPoint(-8F, 12F, -7F);
		Right.setTextureSize(64, 128);
		Right.mirror = true;
		setRotation(Right, 0F, 0F, 0F);
		Left = new ModelRenderer(this, 0, 28);
		Left.addBox(0F, 0F, 0F, 1, 12, 14);
		Left.setRotationPoint(7F, 12F, -7F);
		Left.setTextureSize(64, 128);
		Left.mirror = true;
		setRotation(Left, 0F, 0F, 0F);
		Bottom = new ModelRenderer(this, 0, 56);
		Bottom.addBox(0F, 0F, 0F, 14, 1, 14);
		Bottom.setRotationPoint(-7F, 18F, -7F);
		Bottom.setTextureSize(64, 128);
		Bottom.mirror = true;
		setRotation(Bottom, 0F, 0F, 0F);
		Rotator2 = new ModelRenderer(this, 0, 16);
		Rotator2.addBox(-1F, -1F, -1F, 2, 2, 2);
		Rotator2.setRotationPoint(0F, 10F, 0F);
		Rotator2.setTextureSize(64, 128);
		Rotator2.mirror = true;
		setRotation(Rotator2, 0F, 0.7853982F, 0.7853982F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		Back.render(f5);
		Front.render(f5);
		Right.render(f5);
		Left.render(f5);
		Bottom.render(f5);
		Rotator2.render(f5);
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
