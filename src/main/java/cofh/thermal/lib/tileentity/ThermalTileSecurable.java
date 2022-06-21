package cofh.thermal.lib.tileentity;

import cofh.core.content.block.entity.TileCoFH;
import cofh.core.util.control.ISecurableTile;
import cofh.core.util.control.SecurityControlModule;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static cofh.lib.util.constants.NBTTags.TAG_BLOCK_ENTITY;

public class ThermalTileSecurable extends TileCoFH implements ISecurableTile {

    protected SecurityControlModule securityControl = new SecurityControlModule(this);

    public ThermalTileSecurable(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {

        super(tileEntityTypeIn, pos, state);
    }

    @Override
    public ItemStack createItemStackTag(ItemStack stack) {

        CompoundTag nbt = stack.getOrCreateTagElement(TAG_BLOCK_ENTITY);
        if (hasSecurity()) {
            securityControl().write(nbt);
        }
        if (!nbt.isEmpty()) {
            stack.addTagElement(TAG_BLOCK_ENTITY, nbt);
        }
        return super.createItemStackTag(stack);
    }

    // region NBT
    @Override
    public void load(CompoundTag nbt) {

        super.load(nbt);

        securityControl.read(nbt);
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {

        super.saveAdditional(nbt);

        securityControl.write(nbt);
    }
    // endregion

    // region NETWORK

    // CONTROL
    @Override
    public FriendlyByteBuf getControlPacket(FriendlyByteBuf buffer) {

        super.getControlPacket(buffer);

        securityControl.writeToBuffer(buffer);

        return buffer;
    }

    @Override
    public void handleControlPacket(FriendlyByteBuf buffer) {

        super.handleControlPacket(buffer);

        securityControl.readFromBuffer(buffer);
    }
    // endregion

    // region MODULES
    @Override
    public SecurityControlModule securityControl() {

        return securityControl;
    }
    // endregion
}
