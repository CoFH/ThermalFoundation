package cofh.thermal.lib.tileentity;

import cofh.core.tileentity.TileCoFH;
import cofh.core.util.control.ISecurableTile;
import cofh.core.util.control.SecurityControlModule;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntityType;

import static cofh.lib.util.constants.NBTTags.TAG_BLOCK_ENTITY;

public class ThermalTileSecurable extends TileCoFH implements ISecurableTile {

    protected SecurityControlModule securityControl = new SecurityControlModule(this);

    public ThermalTileSecurable(TileEntityType<?> tileEntityTypeIn) {

        super(tileEntityTypeIn);
    }

    @Override
    public ItemStack createItemStackTag(ItemStack stack) {

        CompoundNBT nbt = stack.getOrCreateTagElement(TAG_BLOCK_ENTITY);
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
    public void load(BlockState state, CompoundNBT nbt) {

        super.load(state, nbt);

        securityControl.read(nbt);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {

        super.save(nbt);

        securityControl.write(nbt);

        return nbt;
    }
    // endregion

    // region NETWORK

    // CONTROL
    @Override
    public PacketBuffer getControlPacket(PacketBuffer buffer) {

        super.getControlPacket(buffer);

        securityControl.writeToBuffer(buffer);

        return buffer;
    }

    @Override
    public void handleControlPacket(PacketBuffer buffer) {

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
