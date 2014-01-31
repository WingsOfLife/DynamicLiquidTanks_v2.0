package doc.mods.dynamictanks.client.render;

import doc.mods.dynamictanks.helpers.NumberHelper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class GeneratorModel extends ModelBase
{
	//fields
	ModelRenderer Shape1;
	ModelRenderer Shape2;
	ModelRenderer Shape3;
	ModelRenderer Shape4;
	ModelRenderer Shape5;
	ModelRenderer Shape6;
	ModelRenderer Shape7;
	ModelRenderer Shape8;
	ModelRenderer Shape9;
	ModelRenderer Shape10;
	ModelRenderer Shape11;
	ModelRenderer Shape12;

	ModelRenderer[] voidParticles = new ModelRenderer[5];

	public GeneratorModel()
	{
		textureWidth = 256;
		textureHeight = 256;

		for (int i = 0; i < voidParticles.length; i++) {
			voidParticles[i] = new ModelRenderer(this, 0, 16);
			voidParticles[i].addBox(-1F, -1F, -1F, 1, 1, 1);
			voidParticles[i].setRotationPoint(3F, 13F, 0F);
			voidParticles[i].setTextureSize(256, 256);
			voidParticles[i].mirror = true;
			setRotation(voidParticles[i], -0.8308387F, 0F, 0.9666439F);
		}

		Shape1 = new ModelRenderer(this, 0, 0);
		Shape1.addBox(-2F, 0F, -2F, 4, 8, 4);
		Shape1.setRotationPoint(0F, 2F, 0F);
		Shape1.setTextureSize(256, 256);
		Shape1.mirror = true;
		setRotation(Shape1, 0F, 0F, 0F);
		Shape2 = new ModelRenderer(this, 21, 0);
		Shape2.addBox(-4F, 0F, -4F, 8, 0, 8);
		Shape2.setRotationPoint(0F, 6F, 0F);
		Shape2.setTextureSize(256, 256);
		Shape2.mirror = true;
		setRotation(Shape2, 0F, 0F, 0F);
		Shape3 = new ModelRenderer(this, 0, 16);
		Shape3.addBox(-1F, -1F, -1F, 2, 2, 2);
		Shape3.setRotationPoint(0F, 13F, 0F);
		Shape3.setTextureSize(256, 256);
		Shape3.mirror = true;
		setRotation(Shape3, -0.8308387F, 0F, 0.9666439F);
		Shape4 = new ModelRenderer(this, 22, 13);
		Shape4.addBox(-2F, 5F, -2F, 4, 0, 8);
		Shape4.setRotationPoint(0F, 15F, 0F);
		Shape4.setTextureSize(256, 256);
		Shape4.mirror = true;
		setRotation(Shape4, -1.264073F, 0F, 0F);
		Shape5 = new ModelRenderer(this, 22, 13);
		Shape5.addBox(-2F, 4F, -2F, 4, 0, 8);
		Shape5.setRotationPoint(0F, 15F, 0F);
		Shape5.setTextureSize(256, 256);
		Shape5.mirror = true;
		setRotation(Shape5, -1.226894F, -3.141593F, 0F);
		Shape6 = new ModelRenderer(this, 21, 0);
		Shape6.addBox(-4F, 0F, -4F, 8, 0, 8);
		Shape6.setRotationPoint(0F, 8F, 0F);
		Shape6.setTextureSize(256, 256);
		Shape6.mirror = true;
		setRotation(Shape6, 0F, 0F, 0F);
		Shape7 = new ModelRenderer(this, 21, 0);
		Shape7.addBox(-4F, 0F, -4F, 8, 0, 8);
		Shape7.setRotationPoint(0F, 4F, 0F);
		Shape7.setTextureSize(256, 256);
		Shape7.mirror = true;
		setRotation(Shape7, 0F, 0F, 0F);
		Shape8 = new ModelRenderer(this, 22, 13);
		Shape8.addBox(-2F, 4F, -4F, 4, 0, 8);
		Shape8.setRotationPoint(0F, 15F, 0F);
		Shape8.setTextureSize(256, 256);
		Shape8.mirror = true;
		setRotation(Shape8, -1.226894F, -1.580091F, 0F);
		Shape9 = new ModelRenderer(this, 22, 13);
		Shape9.addBox(-2F, 4F, -4F, 4, 0, 8);
		Shape9.setRotationPoint(0F, 15F, 0F);
		Shape9.setTextureSize(256, 256);
		Shape9.mirror = true;
		setRotation(Shape9, -1.226894F, 1.580091F, 0F);
		Shape10 = new ModelRenderer(this, 0, 41);
		Shape10.addBox(-4F, 0F, -4F, 8, 3, 8);
		Shape10.setRotationPoint(0F, 21F, 0F);
		Shape10.setTextureSize(256, 256);
		Shape10.mirror = true;
		setRotation(Shape10, 0F, 0F, 0F);
		Shape11 = new ModelRenderer(this, 5, 27);
		Shape11.addBox(-3F, 0F, -3F, 6, 1, 6);
		Shape11.setRotationPoint(0F, 20F, 0F);
		Shape11.setTextureSize(256, 256);
		Shape11.mirror = true;
		setRotation(Shape11, 0F, 0F, 0F);
		Shape12 = new ModelRenderer(this, 33, 24);
		Shape12.addBox(-1F, -4F, -1F, 2, 4, 2);
		Shape12.setRotationPoint(0F, 2F, 0F);
		Shape12.setTextureSize(256, 256);
		Shape12.mirror = true;
		setRotation(Shape12, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		Shape1.render(f5);
		Shape2.render(f5);
		Shape3.render(f5);
		Shape4.render(f5);
		Shape5.render(f5);
		Shape6.render(f5);
		Shape7.render(f5);
		Shape8.render(f5);
		Shape9.render(f5);
		Shape10.render(f5);
		Shape11.render(f5);
		Shape12.render(f5);
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
