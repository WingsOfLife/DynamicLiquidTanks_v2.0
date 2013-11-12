package doc.mods.dynamictanks.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import doc.mods.dynamictanks.tileentity.DuctTileEntity;

public class DuctModel extends ModelBase {

	public class directionValues {
		public static final int DOWN = 0;
		public static final int UP = 1;
		public static final int NORTH = 2;
		public static final int SOUTH = 3;
		public static final int WEST = 4;
		public static final int EAST = 5;
	}

	ModelRenderer Left;
	ModelRenderer Center;
	ModelRenderer Right;
	ModelRenderer Front;
	ModelRenderer Back;
	ModelRenderer Top;
	ModelRenderer Down;


	public DuctTileEntity duct = new DuctTileEntity();

	public DuctModel()
	{

		textureWidth = 64;
		textureHeight = 32;

		Left = new ModelRenderer(this, 0, 0);
		Left.addBox(0F, 0F, 0F, 6, 2, 2);
		Left.setRotationPoint(2F, 16F, -1F);
		Left.setTextureSize(64, 32);
		Left.mirror = true;
		setRotation(Left, 0F, 0F, 0F);
		Center = new ModelRenderer(this, 19, 0);
		Center.addBox(0F, 0F, 0F, 4, 4, 4);
		Center.setRotationPoint(-2F, 15F, -2F);
		Center.setTextureSize(64, 32);
		Center.mirror = true;
		setRotation(Center, 0F, 0F, 0F);
		Right = new ModelRenderer(this, 0, 0);
		Right.addBox(0F, 0F, 0F, 6, 2, 2);
		Right.setRotationPoint(-8F, 16F, -1F);
		Right.setTextureSize(64, 32);
		Right.mirror = true;
		setRotation(Right, 0F, 0F, 0F);
		Front = new ModelRenderer(this, 9, 11);
		Front.addBox(0F, 0F, 0F, 2, 2, 6);
		Front.setRotationPoint(-1F, 16F, -8F);
		Front.setTextureSize(64, 32);
		Front.mirror = true;
		setRotation(Front, 0F, 0F, 0F);
		Back = new ModelRenderer(this, 9, 11);
		Back.addBox(0F, 0F, 0F, 2, 2, 6);
		Back.setRotationPoint(-1F, 16F, 2F);
		Back.setTextureSize(64, 32);
		Back.mirror = true;
		setRotation(Back, 0F, 0F, 0F);
		Top = new ModelRenderer(this, 0, 11);
		Top.addBox(0F, 0F, 0F, 2, 7, 2);
		Top.setRotationPoint(-1F, 8F, -1F);
		Top.setTextureSize(64, 32);
		Top.mirror = true;
		setRotation(Top, 0F, 0F, 0F);
		Down = new ModelRenderer(this, 0, 11);
		Down.addBox(0F, 0F, 0F, 2, 7, 2);
		Down.setRotationPoint(-1F, 17F, -1F);
		Down.setTextureSize(64, 32);
		Down.mirror = true;
		setRotation(Down, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(null, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		Center.render(f5);
		if (duct.isBlockThere(directionValues.EAST)) Left.render(f5);
		if (duct.isBlockThere(directionValues.WEST)) Right.render(f5);
		if (duct.isBlockThere(directionValues.NORTH)) Front.render(f5);
		if (duct.isBlockThere(directionValues.SOUTH)) Back.render(f5);
		if (duct.isBlockThere(directionValues.DOWN)) Top.render(f5);
		if (duct.isBlockThere(directionValues.UP)) Down.render(f5);
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

	public void render(float f5)
	{
		Center.render(f5);
		Left.render(f5);
		Right.render(f5);
	}


}
