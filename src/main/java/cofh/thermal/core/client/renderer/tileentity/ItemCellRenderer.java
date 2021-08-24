package cofh.thermal.core.client.renderer.tileentity;

import cofh.thermal.core.tileentity.storage.ItemCellTile;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

public class ItemCellRenderer extends TileEntityRenderer<ItemCellTile> {

    private float rotation = 0;

    public ItemCellRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {

        super(rendererDispatcherIn);
    }

    @Override
    public void render(ItemCellTile tile, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        rotation += partialTicks;

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        Direction facing = tile.getFacing();
        //ItemStack item = tile.getItemInv().get(0);
        ItemStack item = new ItemStack(Items.WHEAT, 64);

        matrixStackIn.push();
        matrixStackIn.translate(0.5, 0.35, 0.5);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(rotation));
        //matrixStackIn.scale(1.5F, 1.5F, 1.5F);
        itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.GROUND, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
        matrixStackIn.pop();
    }

}
