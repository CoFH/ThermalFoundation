package cofh.thermal.core.client.renderer.tileentity;

import cofh.core.event.CoreClientEvents;
import cofh.thermal.core.tileentity.storage.ItemCellTile;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

public class ItemCellRenderer extends TileEntityRenderer<ItemCellTile> {

    public ItemCellRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {

        super(rendererDispatcherIn);
    }

    @Override
    public void render(ItemCellTile tile, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        ItemStack item = tile.getRenderItem();

        if (!item.isEmpty()) {
            matrixStackIn.push();
            float spin = (float) ((CoreClientEvents.renderTime + partialTicks) / Math.PI);
            float bob = (float) (Math.sin(((float) CoreClientEvents.renderTime + spin) / 20.0F) * 0.1F + 0.1F);

            matrixStackIn.translate(0.5, bob + 0.25, 0.5);
            matrixStackIn.rotate(Vector3f.YP.rotation(((float) CoreClientEvents.renderTime + spin) / 30.0F));

            Minecraft.getInstance().getItemRenderer().renderItem(item, ItemCameraTransforms.TransformType.GROUND, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
            matrixStackIn.pop();
        }
    }

}