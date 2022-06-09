package cofh.thermal.core.block.entity.device;

import cofh.lib.block.entity.ICoFHTickableTile;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.thermal.core.config.ThermalCoreConfig;
import cofh.thermal.core.inventory.container.device.DeviceComposterContainer;
import cofh.thermal.lib.tileentity.DeviceTileBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiPredicate;

import static cofh.lib.util.StorageGroup.INPUT;
import static cofh.lib.util.StorageGroup.OUTPUT;
import static cofh.lib.util.constants.NBTTags.TAG_AUGMENT_TYPE_FILTER;
import static cofh.lib.util.constants.NBTTags.TAG_AUGMENT_TYPE_UPGRADE;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_COMPOSTER_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;

public class DeviceComposterTile extends DeviceTileBase implements ICoFHTickableTile.IServerTickable {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_FILTER);

    protected ItemStorageCoFH outputSlot = new ItemStorageCoFH();

    protected int process;

    public DeviceComposterTile(BlockPos pos, BlockState state) {

        super(DEVICE_COMPOSTER_TILE, pos, state);

        inventory.addSlots(INPUT, 15, item -> filter.valid(item));
        inventory.addSlot(outputSlot, OUTPUT);

        addAugmentSlots(ThermalCoreConfig.deviceAugments);
        initHandlers();
    }

    @Override
    public void tickServer() {

        updateActiveState();

        if (!isActive) {
            return;
        }
        --process;
        if (process > 0) {
            return;
        }
        // process = processMax;

    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        return new DeviceComposterContainer(i, level, worldPosition, inventory, player);
    }

}
