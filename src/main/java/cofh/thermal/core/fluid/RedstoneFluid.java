package cofh.thermal.core.fluid;

import cofh.lib.fluid.FluidCoFH;
import cofh.thermal.lib.common.ThermalItemGroups;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static cofh.lib.util.helpers.BlockHelper.lightValue;
import static cofh.thermal.core.ThermalCore.*;
import static cofh.thermal.lib.common.ThermalIDs.ID_FLUID_REDSTONE;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.of;

public class RedstoneFluid extends FluidCoFH {

    private static final Material REDSTONE_FLUID = (new Material.Builder(MaterialColor.COLOR_RED)).noCollider().notSolidBlocking().nonSolid().destroyOnPush().replaceable().liquid().build();

    private static RedstoneFluid INSTANCE;

    public static RedstoneFluid instance() {

        if (INSTANCE == null) {
            INSTANCE = new RedstoneFluid();
        }
        return INSTANCE;
    }

    protected RedstoneFluid() {

        super(FLUIDS, ID_FLUID_REDSTONE);

        particleColor = new Vector3f(0.4F, 0.0F, 0.0F);

        block = BLOCKS.register(fluid(ID_FLUID_REDSTONE), () -> new FluidBlock(stillFluid, of(Material.WATER).lightLevel(lightValue(7)).noCollission().strength(100.0F).noLootTable()));
        bucket = ITEMS.register(bucket(ID_FLUID_REDSTONE), () -> new BucketItem(stillFluid, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ThermalItemGroups.THERMAL_ITEMS)));
    }

    @Override
    protected ForgeFlowingFluid.Properties fluidProperties() {

        return new ForgeFlowingFluid.Properties(type(), stillFluid, flowingFluid).block(block).bucket(bucket);
    }

    @Override
    protected Supplier<FluidType> type() {

        return TYPE;
    }

    public static final RegistryObject<FluidType> TYPE = FLUID_TYPES.register(ID_FLUID_REDSTONE, () -> new FluidType(FluidType.Properties.create()
            .fallDistanceModifier(0F)
            .lightLevel(7)
            .density(1200)
            .viscosity(1500)
            .rarity(Rarity.UNCOMMON)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)) {

        @Override
        public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {

            consumer.accept(new IClientFluidTypeExtensions() {

                private static final ResourceLocation
                        STILL = new ResourceLocation("thermal:block/fluids/redstone_still"),
                        FLOW = new ResourceLocation("thermal:block/fluids/redstone_flow");

                @Override
                public ResourceLocation getStillTexture() {

                    return STILL;
                }

                @Override
                public ResourceLocation getFlowingTexture() {

                    return FLOW;
                }

                @Nullable
                @Override
                public ResourceLocation getOverlayTexture() {

                    return WATER_OVERLAY;
                }

                @Override
                public ResourceLocation getRenderOverlayTexture(Minecraft mc) {

                    return UNDERWATER_LOCATION;
                }

                @Override
                public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {

                    return instance().particleColor;
                }

                @Override
                public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {

                    nearDistance = -8F;
                    farDistance = 24F;

                    if (farDistance > renderDistance) {
                        farDistance = renderDistance;
                        shape = FogShape.CYLINDER;
                    }

                    RenderSystem.setShaderFogStart(nearDistance);
                    RenderSystem.setShaderFogEnd(farDistance);
                    RenderSystem.setShaderFogShape(shape);
                }

            });
        }
    });

    // region BLOCK CLASS
    public static class FluidBlock extends LiquidBlock {

        public FluidBlock(Supplier<? extends FlowingFluid> fluidSup, Properties properties) {

            super(fluidSup, properties);
        }

        @Override
        public boolean isSignalSource(BlockState state) {

            return true;
            // return redstoneMushroomSignal.get();
        }

        @Override
        public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {

            return Math.max(15 - 2 * blockState.getValue(LEVEL), 1);
            // return redstoneMushroomSignal.get() && blockState.getValue(AGE_0_4) == 4 ? 7 : 0;
        }

    }
    // endregion
}
