package cofh.thermal.core.event;

import cofh.core.client.CoreRenderType;
import cofh.core.util.ProxyClient;
import cofh.lib.tileentity.IAreaEffectTile;
import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.thermal.core.item.WrenchItem;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.lib.util.constants.NBTTags.TAG_TYPE;
import static cofh.lib.util.helpers.StringHelper.*;
import static cofh.thermal.lib.common.ThermalAugmentRules.*;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ID_THERMAL)
public class TCoreClientEvents {

    private TCoreClientEvents() {

    }

    @SubscribeEvent
    public static void handleItemTooltipEvent(ItemTooltipEvent event) {

        List<ITextComponent> tooltip = event.getToolTip();
        if (tooltip.isEmpty()) {
            return;
        }
        ItemStack stack = event.getItemStack();

        if (AugmentDataHelper.hasAugmentData(stack)) {
            CompoundNBT augmentData = AugmentDataHelper.getAugmentData(stack);
            if (augmentData == null || augmentData.isEmpty()) {
                return;
            }
            String type = augmentData.getString(TAG_TYPE);
            if (!type.isEmpty()) {
                IFormattableTextComponent typeText = getTextComponent("info.thermal.augment.type." + type).withStyle(TextFormatting.WHITE);

                //                if (isTypeExclusive(type)) {
                //                    typeText.mergeStyle(TextFormatting.UNDERLINE);
                //                }
                tooltip.add(getTextComponent("info.cofh.type")
                        .withStyle(TextFormatting.YELLOW)
                        .append(": ")
                        .append(typeText)
                );
            }
            for (String mod : augmentData.getAllKeys()) {
                if (mod.equals(TAG_TYPE) || !canLocalize("info.thermal.augment.attr." + mod)) {
                    continue;
                }
                float value = augmentData.getFloat(mod);
                boolean bad = isAdditive(mod) && value < 0
                        || isAdditive(mod) && value > 0 && isInverse(mod)
                        || isMultiplicative(mod) && (isInverse(mod) ? value > 1.0 : value < 1.0);

                IFormattableTextComponent modText = new StringTextComponent("" +
                        (isAdditive(mod) && value > 0 ? "+" : "") +
                        (isInteger(mod) ? DF0.format(value) : isMultiplicative(mod) ? DF2.format(value) + "x" : DF0.format(value * 100) + "%"))
                        .withStyle(bad ? TextFormatting.RED : TextFormatting.GREEN);

                if (isMaximized(mod)) {
                    modText.withStyle(TextFormatting.UNDERLINE);
                }
                tooltip.add(getTextComponent("info.thermal.augment.attr." + mod)
                        .append(": ")
                        .withStyle(TextFormatting.GRAY)
                        .append(modText)
                );
            }
        }
    }

    @SubscribeEvent
    public static void handleRenderWorldLast(RenderWorldLastEvent event) {

        ClientPlayerEntity player = Minecraft.getInstance().player;

        if (player != null) {
            Item heldItem = player.getMainHandItem().getItem();
            if (heldItem instanceof WrenchItem && ((WrenchItem) heldItem).getMode(player.getMainHandItem()) > 0) {
                renderOperationalAreas(player, event.getMatrixStack());
            }
        }
    }

    // region HELPERS
    private static boolean playerWithinDistance(BlockPos pos, PlayerEntity player, double distanceSq) {

        return pos.distSqr(player.position(), true) <= distanceSq;
    }

    private static void blueLine(IVertexBuilder builder, Matrix4f positionMatrix, BlockPos pos, float dx1, float dy1, float dz1, float dx2, float dy2, float dz2) {

        builder.vertex(positionMatrix, pos.getX() + dx1, pos.getY() + dy1, pos.getZ() + dz1)
                .color(0.0f, 0.0f, 1.0f, 1.0f)
                .endVertex();
        builder.vertex(positionMatrix, pos.getX() + dx2, pos.getY() + dy2, pos.getZ() + dz2)
                .color(0.0f, 0.0f, 1.0f, 1.0f)
                .endVertex();
    }

    private static void renderOperationalAreas(ClientPlayerEntity player, MatrixStack matrixStack) {

        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        IVertexBuilder builder = buffer.getBuffer(CoreRenderType.OVERLAY_LINES);
        matrixStack.pushPose();

        Vector3d projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        matrixStack.translate(-projectedView.x, -projectedView.y, -projectedView.z);

        Matrix4f positionMatrix = matrixStack.last().pose();
        BlockPos.Mutable pos = new BlockPos.Mutable();

        for (IAreaEffectTile tile : ProxyClient.getAreaEffectTiles()) {
            if (!tile.canPlayerAccess(player) || !playerWithinDistance(tile.pos(), player, 576)) {
                continue;
            }
            AxisAlignedBB area = tile.getArea();

            pos.set(area.minX, area.minY, area.minZ);
            float lenX = (float) (area.maxX - area.minX);
            float lenY = (float) (area.maxY - area.minY);
            float lenZ = (float) (area.maxZ - area.minZ);

            blueLine(builder, positionMatrix, pos, 0, 0, 0, lenX, 0, 0);
            blueLine(builder, positionMatrix, pos, 0, lenY, 0, lenX, lenY, 0);
            blueLine(builder, positionMatrix, pos, 0, 0, lenZ, lenX, 0, lenZ);
            blueLine(builder, positionMatrix, pos, 0, lenY, lenZ, lenX, lenY, lenZ);

            blueLine(builder, positionMatrix, pos, 0, 0, 0, 0, 0, lenZ);
            blueLine(builder, positionMatrix, pos, lenX, 0, 0, lenX, 0, lenZ);
            blueLine(builder, positionMatrix, pos, 0, lenY, 0, 0, lenY, lenZ);
            blueLine(builder, positionMatrix, pos, lenX, lenY, 0, lenX, lenY, lenZ);

            blueLine(builder, positionMatrix, pos, 0, 0, 0, 0, lenY, 0);
            blueLine(builder, positionMatrix, pos, lenX, 0, 0, lenX, lenY, 0);
            blueLine(builder, positionMatrix, pos, 0, 0, lenZ, 0, lenY, lenZ);
            blueLine(builder, positionMatrix, pos, lenX, 0, lenZ, lenX, lenY, lenZ);
        }
        matrixStack.popPose();
        RenderSystem.disableDepthTest();
        buffer.endBatch(CoreRenderType.OVERLAY_LINES);
    }
    // endregion
}
